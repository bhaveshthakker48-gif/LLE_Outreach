package org.impactindia.llemeddocket.task;

import android.content.Context;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.orm.CampDAO;
import org.impactindia.llemeddocket.orm.DAOException;
import org.impactindia.llemeddocket.orm.MedicalDetailsDAO;
import org.impactindia.llemeddocket.pojo.Base;
import org.impactindia.llemeddocket.pojo.Camp;
import org.impactindia.llemeddocket.pojo.MedicalDetails;
import org.impactindia.llemeddocket.pojo.WsModel;
import org.impactindia.llemeddocket.ws.WebService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetMedicalDetailsTask extends BaseServiceTask implements Runnable {

    private CampDAO campDAO;
    private MedicalDetailsDAO medicalDetailsDAO;
    private Camp camp;
    private boolean campComplete;

    public GetMedicalDetailsTask(Context context, boolean campComplete) {
        super(context);
        campDAO = new CampDAO(context, db);
        medicalDetailsDAO = new MedicalDetailsDAO(context, db);
        this.campComplete = campComplete;
    }

    @Override
    public void run() {
        initWebService();
    }

    private void clearData() {
        if (campComplete) {
            for (MedicalDetails medicalDetails : medicalDetailsDAO.findAll()) {
                try {
                    medicalDetailsDAO.delete(medicalDetails.getId());
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
            WebService<WsModel> getMedicalDetails = new WebService<WsModel>(params, AttributeSet.Params.GET_MEDICAL_LIST, Base.class, AttributeSet.Constants.ZERO);
            getMedicalDetails.setDebug(AttributeSet.Constants.DEBUG);
            Base base = (Base) getMedicalDetails.getResponseObject();
            if (base != null && base.getErrorCode() == AttributeSet.Constants.ZERO && base.getMedicalDetailsList() != null) {
                for (MedicalDetails details : medicalDetailsDAO.findAllUploaded()) {
                    medicalDetailsDAO.delete(details.getId());
                }
                for (MedicalDetails details : base.getMedicalDetailsList()) {
                    medicalDetailsDAO.create(details);
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
