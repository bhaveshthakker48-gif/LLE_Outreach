package org.impactindia.llemeddocket.task;

import android.content.Context;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.orm.DAOException;
import org.impactindia.llemeddocket.orm.TagDAO;
import org.impactindia.llemeddocket.pojo.Base;
import org.impactindia.llemeddocket.pojo.State;
import org.impactindia.llemeddocket.pojo.Tag;
import org.impactindia.llemeddocket.pojo.WsModel;
import org.impactindia.llemeddocket.ws.WebService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GetTagsTask extends BaseServiceTask implements Runnable {

    private TagDAO tagDAO;
    private boolean campComplete;

    public GetTagsTask(Context context, boolean campComplete) {
        super(context);
        tagDAO = new TagDAO(context, db);
        this.campComplete = campComplete;
    }

    @Override
    public void run() {
        initWebService();
    }

    private void initWebService() {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("Userid", getApp().getSettings().getUserId());
            WebService<WsModel> getTagsWs = new WebService<WsModel>(params, AttributeSet.Params.GET_TAG_DETAIL, Base.class, AttributeSet.Constants.ZERO);
            getTagsWs.setDebug(AttributeSet.Constants.DEBUG);
            Base base = (Base) getTagsWs.getResponseObject();
            if (base != null && base.getErrorCode() == AttributeSet.Constants.ZERO && base.getTagList() != null) {
                tagDAO.deleteAll();
                for (Tag tag : base.getTagList()) {
                    Tag oldTag = tagDAO.findFirstByField(Tag.TAG_ID, tag.getTagId());
                    if (oldTag != null) {
                        tag.setId(oldTag.getId());
                        tagDAO.update(tag);
                    } else {
                        tagDAO.create(tag);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            releaseResources();
        }

    }
}
