package org.impactindia.llemeddocket.task.add;

import android.content.Context;
import android.util.Log;
import com.google.gson.JsonSyntaxException;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.orm.DAOException;
import org.impactindia.llemeddocket.orm.PatientDAO;
import org.impactindia.llemeddocket.pojo.Base;
import org.impactindia.llemeddocket.pojo.Patient;
import org.impactindia.llemeddocket.pojo.WsModel;
import org.impactindia.llemeddocket.task.BaseServiceTask;
import org.impactindia.llemeddocket.ws.WebService;

import java.io.IOException;
import java.util.List;

public class AddPatientRegistrationTask extends BaseServiceTask implements Runnable {

	private PatientDAO dataDAO;
	private boolean campComplete;

	public AddPatientRegistrationTask(Context context, boolean campComplete) {
		super(context);
		dataDAO = new PatientDAO(context, db);
		this.campComplete = campComplete;
	}

	@Override
	public void run() {
		if (dataDAO.getUnuploadedCount() > 0) {
			initWebServices();
		} else {
			clearData();
			releaseResources();
		}
	}

	private void clearData() {
		if (campComplete) {
			try {
				for (Patient patient : dataDAO.findAllUploaded()) {
					dataDAO.delete(patient.getId());
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
			base.setPatientList(dataDAO.findAllUnuploaded());
			WebService<WsModel> addPatientData = new WebService<WsModel>(base, AttributeSet.Params.ADD_PATIENT_REGISTRATION, Base.class, 1);
			addPatientData.setDebug(true);
			Base response = (Base) addPatientData.getResponseObject();
			if (response != null && response.getPatientDataList() != null && response.getPatientDataList().size() > 0) {
				for (Patient data : response.getPatientDataList()) {
					if (data.getErrorCode().longValue() == AttributeSet.Constants.ZERO) {
						Patient oldPatient = dataDAO.findFirstByField(Patient.REG_NO, data.getRegNo());
						if (oldPatient != null) {
							oldPatient.setPatientId(data.getPatientId());
							oldPatient.setFresh(false);
							dataDAO.update(oldPatient);
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
