package org.impactindia.llemeddocket.task;

import android.content.Context;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.orm.DAOException;
import org.impactindia.llemeddocket.orm.StateDAO;
import org.impactindia.llemeddocket.pojo.Base;
import org.impactindia.llemeddocket.pojo.State;
import org.impactindia.llemeddocket.pojo.WsModel;
import org.impactindia.llemeddocket.ws.WebService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GetStatesTask extends BaseServiceTask implements Runnable {

    private StateDAO stateDAO;
    private boolean campComplete;

    public GetStatesTask(Context context, boolean campComplete) {
        super(context);
        stateDAO = new StateDAO(context, db);
        this.campComplete = campComplete;
    }

    @Override
    public void run() {
        initWebService();
    }

    private void initWebService() {
        try {
            Map<String, Object> params = new HashMap<>();
            WebService<WsModel> getStatesWs = new WebService<WsModel>(params, AttributeSet.Params.GET_STATE_DETAIL, Base.class, AttributeSet.Constants.ZERO);
            getStatesWs.setDebug(AttributeSet.Constants.DEBUG);
            Base base = (Base) getStatesWs.getResponseObject();
            if (base != null && base.getErrorCode() == AttributeSet.Constants.ZERO && base.getStateList() != null) {
                for (State state : base.getStateList()) {
                    State oldState = stateDAO.findFirstByField(State.STATE_ID, state.getStateId());
                    if (oldState != null) {
                        state.setId(oldState.getId());
                        stateDAO.update(state);
                    } else {
                        stateDAO.create(state);
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
