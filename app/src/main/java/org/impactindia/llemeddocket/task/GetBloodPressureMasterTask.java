package org.impactindia.llemeddocket.task;

import android.content.Context;

import com.google.gson.JsonSyntaxException;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.orm.BloodPressureNRDataDAO;
import org.impactindia.llemeddocket.orm.DAOException;
import org.impactindia.llemeddocket.pojo.Base;
import org.impactindia.llemeddocket.pojo.BloodPressureNRData;
import org.impactindia.llemeddocket.pojo.WsModel;
import org.impactindia.llemeddocket.ws.WebService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GetBloodPressureMasterTask extends BaseServiceTask implements Runnable {

    private BloodPressureNRDataDAO bloodPressureNRDataDAO;

    public GetBloodPressureMasterTask(Context context) {
        super(context);
        bloodPressureNRDataDAO = new BloodPressureNRDataDAO(context, db);
    }

    @Override
    public void run() {
        initWebServices();
    }

    private void initWebServices() {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            WebService<WsModel> getBloodPressureNRDataWs = new WebService<WsModel>(
                    params, AttributeSet.Params.GET_BLOOD_PRESSURE_LIST,
                    Base.class, 0);
            getBloodPressureNRDataWs.setDebug(true);
            Base bp = (Base) getBloodPressureNRDataWs.getResponseObject();
            if (bp != null && bp.getBloodPressureList() != null) {
                bloodPressureNRDataDAO.deleteAll();
                for (BloodPressureNRData data : bp.getBloodPressureList()) {
                    bloodPressureNRDataDAO.create(data);
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

    }

}
