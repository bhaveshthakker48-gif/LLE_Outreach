package org.impactindia.llemeddocket.task;

import android.content.Context;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.orm.CampDAO;
import org.impactindia.llemeddocket.orm.DAOException;
import org.impactindia.llemeddocket.pojo.Base;
import org.impactindia.llemeddocket.pojo.Camp;
import org.impactindia.llemeddocket.pojo.Tag;
import org.impactindia.llemeddocket.pojo.WsModel;
import org.impactindia.llemeddocket.ws.WebService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GetCampDetailsTask extends BaseServiceTask implements Runnable {

    private CampDAO campDAO;
    private boolean campComplete;

    public GetCampDetailsTask(Context context, boolean campComplete) {
        super(context);
        campDAO = new CampDAO(context, db);
        this.campComplete = campComplete;
    }

    @Override
    public void run() {
        initWebService();
    }

    private void clearData() {
        if (campComplete) {
            for (Camp camp : campDAO.findAll()) {
                try {
                    campDAO.delete(camp.getId());
                } catch (DAOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initWebService() {
        try {
            Map<String, Object> params = new HashMap<>();
            WebService<WsModel> getCampsWs = new WebService<WsModel>(params, AttributeSet.Params.GET_CAMP_DETAIL, Base.class, AttributeSet.Constants.ZERO);
            getCampsWs.setDebug(AttributeSet.Constants.DEBUG);
            Base base = (Base) getCampsWs.getResponseObject();
            if (base != null && base.getErrorCode() == AttributeSet.Constants.ZERO && base.getCampList() != null) {
                campDAO.deleteAll();
                for (Camp camp : base.getCampList()) {
                    Camp oldCamp = campDAO.findFirstByField(Camp.CAMP_ID, camp.getCampId());
                    if (oldCamp != null) {
                        camp.setId(oldCamp.getId());
                        campDAO.update(camp);
                    } else {
                        campDAO.create(camp);
                    }
                    getApp().getSettings().setCampEndDate(camp.getToDate());
                    getApp().getSettings().setCampStartDate(camp.getFromDate());
                }
            }
            clearData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            releaseResources();
        }

    }
}
