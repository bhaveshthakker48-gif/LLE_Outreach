package org.impactindia.llemeddocket.task.add;

import android.content.Context;

import com.google.gson.JsonSyntaxException;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.orm.DAO;
import org.impactindia.llemeddocket.orm.DAOException;
import org.impactindia.llemeddocket.orm.MedicalDetailsDAO;
import org.impactindia.llemeddocket.pojo.Base;
import org.impactindia.llemeddocket.pojo.MedicalDetails;
import org.impactindia.llemeddocket.pojo.Patient;
import org.impactindia.llemeddocket.pojo.WsModel;
import org.impactindia.llemeddocket.task.BaseServiceTask;
import org.impactindia.llemeddocket.ws.WebService;

import java.io.IOException;

public class AddMedicalDetailsTask extends BaseServiceTask implements Runnable {

    private MedicalDetailsDAO medicalDetailsDAO;
    private boolean campComplete;

    public AddMedicalDetailsTask(Context context, boolean campComplete) {
        super(context);
        medicalDetailsDAO = new MedicalDetailsDAO(context, db);
        this.campComplete = campComplete;
    }

    @Override
    public void run() {
        if (medicalDetailsDAO.getUnuploadedCount() > 0) {
            initWebServices();
        } else {
            clearData();
            releaseResources();
        }

    }

    private void clearData() {
        if (campComplete) {
            try {
                for (MedicalDetails medicalDetails : medicalDetailsDAO.findAllUploaded()) {
                    medicalDetailsDAO.delete(medicalDetails.getId());
                }
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initWebServices() {
        try {
            Base base;
            base = new Base();
            base.setMedicalDetailsList(medicalDetailsDAO.findAllUnuploaded());
            WebService<WsModel> addMedicalDetails = new WebService<WsModel>(base, AttributeSet.Params.ADD_PATIENT_MEDICAL_DETAILS, Base.class, 1);
            addMedicalDetails.setDebug(true);
            Base response = (Base) addMedicalDetails.getResponseObject();
            if (response != null && response.getMedicalDetailsRespList() != null && response.getMedicalDetailsRespList().size() > 0) {
                for (MedicalDetails data : response.getMedicalDetailsRespList()) {
                    if (data.getErrorCode().longValue() == AttributeSet.Constants.ZERO) {
                        MedicalDetails oldMedicalDetails = medicalDetailsDAO.findFirstByField(MedicalDetails.PATIENT_ID, String.valueOf(data.getPatientId()), DAO.ID, String.valueOf(data.getId()));
                        if (oldMedicalDetails != null) {
                            oldMedicalDetails.setFresh(false);
                            medicalDetailsDAO.update(oldMedicalDetails);
                        }
                    }
                }
            }
            clearData();
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            releaseResources();
        }
    }
}
