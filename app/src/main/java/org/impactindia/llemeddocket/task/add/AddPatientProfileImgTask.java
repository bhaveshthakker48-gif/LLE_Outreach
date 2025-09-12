package org.impactindia.llemeddocket.task.add;

import android.content.Context;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.orm.DAOException;
import org.impactindia.llemeddocket.orm.PatientDAO;
import org.impactindia.llemeddocket.pojo.Base;
import org.impactindia.llemeddocket.pojo.Patient;
import org.impactindia.llemeddocket.pojo.WsModel;
import org.impactindia.llemeddocket.task.BaseServiceTask;
import org.impactindia.llemeddocket.ws.WebService;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import cz.msebera.android.httpclient.Header;

public class AddPatientProfileImgTask extends BaseServiceTask implements Runnable {

    private PatientDAO patientDAO;
    private boolean campComplete;

    public AddPatientProfileImgTask(Context context, boolean campComplete) {
        super(context);
        patientDAO = new PatientDAO(context, db);
        this.campComplete = campComplete;
    }

    @Override
    public void run() {
        try {
            if (!patientDAO.findAllPatientsWithProfileImg().isEmpty()) {
                initWebServices();
            } else {
                releaseResources();
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    private void initWebServices() {
        try {
            List<Patient> patientList = patientDAO.findAllPatientsWithProfileImg();
            if (!patientList.isEmpty()) {
                for (Patient patient : patientList) {
                    uploadDocuments(patient);
                }
            }
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

    private void uploadDocuments(final Patient patient) throws FileNotFoundException {
        File mediaFile = new File(patient.getProfileImgPath());
        if (mediaFile.exists()) {
            Uri.Builder builder = Uri.parse(AttributeSet.Params.UPLOAD_PATIENT_PROFILE).buildUpon();
            String url = builder.build().toString();
            System.out.println("URL "+url);
            RequestParams params = new RequestParams();
            params.put("file", mediaFile);
            SyncHttpClient syncHttpClient = new SyncHttpClient();
            syncHttpClient.setTimeout(AttributeSet.Constants.DEFAULT_TIMEOUT);
            syncHttpClient.post(context, url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    System.out.println("AsyncResponse " + statusCode + " " + response.toString());
                    Gson gson = new Gson();
                    Base base = gson.fromJson(response.toString(), Base.class);
                    if (statusCode == HttpsURLConnection.HTTP_OK && base.getErrorCode() == HttpsURLConnection.HTTP_OK) {
                        patient.setHasImg(false);
                        try {
                            patientDAO.update(patient);
                        } catch (DAOException e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    System.out.println("AsyncResponse " + statusCode + " " + responseString);

                }
            });
        }
    }
}
