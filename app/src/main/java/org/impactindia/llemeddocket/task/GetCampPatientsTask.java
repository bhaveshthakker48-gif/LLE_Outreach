package org.impactindia.llemeddocket.task;

import android.content.Context;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.orm.CampDAO;
import org.impactindia.llemeddocket.orm.CampPatientDAO;
import org.impactindia.llemeddocket.orm.DAOException;
import org.impactindia.llemeddocket.pojo.Base;
import org.impactindia.llemeddocket.pojo.Camp;
import org.impactindia.llemeddocket.pojo.Patient;
import org.impactindia.llemeddocket.pojo.WsModel;
import org.impactindia.llemeddocket.ws.WebService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetCampPatientsTask extends BaseServiceTask implements Runnable {

    private CampDAO campDAO;
    private CampPatientDAO campPatientDAO;
    private Camp camp;
    private boolean campComplete;

    public GetCampPatientsTask(Context context, boolean campComplete) {
        super(context);
        campDAO = new CampDAO(context, db);
        campPatientDAO = new CampPatientDAO(context, db);
        this.campComplete = campComplete;
    }

    @Override
    public void run() {
        initWebService();
    }

    private void clearData() {
        if (campComplete) {
            for (Patient patient : campPatientDAO.findAll()) {
                try {
                    campPatientDAO.delete(patient.getId());
                } catch (DAOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initWebService() {
        try {
            Map<String, Object> params = new HashMap<>();
            List<Camp> campList = campDAO.findAll();
            if (!campList.isEmpty()) {
                camp = campList.get(0);
            }
            if (camp != null) {
                params.put("CampId", camp.getCampId());
            }
            WebService<WsModel> getCampsPatients = new WebService<WsModel>(params, AttributeSet.Params.GET_CAMP_PATIENT_LIST, Base.class, AttributeSet.Constants.ZERO);
            getCampsPatients.setDebug(AttributeSet.Constants.DEBUG);
            Base base = (Base) getCampsPatients.getResponseObject();
            if (base != null && base.getErrorCode() == AttributeSet.Constants.ZERO && base.getPrList() != null) {
                campPatientDAO.deleteAll();
                for (Patient patient : base.getPrList()) {
                    campPatientDAO.create(patient);
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
