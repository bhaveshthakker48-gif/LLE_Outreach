package org.impactindia.llemeddocket.task;

import android.content.Context;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.orm.DAOException;
import org.impactindia.llemeddocket.orm.SubTagDAO;
import org.impactindia.llemeddocket.orm.TagDAO;
import org.impactindia.llemeddocket.pojo.Base;
import org.impactindia.llemeddocket.pojo.SubTag;
import org.impactindia.llemeddocket.pojo.Tag;
import org.impactindia.llemeddocket.pojo.WsModel;
import org.impactindia.llemeddocket.ws.WebService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GetSubTagTask extends BaseServiceTask implements Runnable {

    private SubTagDAO subTagDAO;
    private boolean campComplete;

    public GetSubTagTask(Context context, boolean campComplete) {
        super(context);
        subTagDAO = new SubTagDAO(context, db);
        this.campComplete = campComplete;
    }

    @Override
    public void run() {
        initWebService();
    }

    private void initWebService() {
        try {
            Map<String, Object> params = new HashMap<>();
            WebService<WsModel> getTagsWs = new WebService<WsModel>(params, AttributeSet.Params.GET_SUB_TAG_DETAIL, Base.class, AttributeSet.Constants.ZERO);
            getTagsWs.setDebug(AttributeSet.Constants.DEBUG);
            Base base = (Base) getTagsWs.getResponseObject();
            if (base != null && base.getErrorCode() == AttributeSet.Constants.ZERO && base.getSubTagList() != null) {
                subTagDAO.deleteAll();
                for (SubTag subTag : base.getSubTagList()) {
                    SubTag oldSubTag = subTagDAO.findFirstByField(SubTag.SUB_TAG_ID, subTag.getSubTagId());
                    if (oldSubTag != null) {
                        subTag.setId(oldSubTag.getId());
                        subTagDAO.update(subTag);
                    } else {
                        subTagDAO.create(subTag);
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
