package org.impactindia.llemeddocket.ws;

import android.os.AsyncTask;

import com.google.gson.JsonSyntaxException;
import org.impactindia.llemeddocket.pojo.WsModel;
import java.io.IOException;

/**
 * Created by devandroid on 17/5/17.
 */

public class AsyncWebServiceAdapter<T extends WsModel> {

    /** The wsccl. */
    WsCallCompleteListener wsccl;

    public void getResponseObject(IWebService<T> params, WsCallCompleteListener wsccl, int type) {
        this.wsccl = wsccl;
        new AsyncAdapter(type).execute(params);
    }

    public T[] getResponseArray() throws IOException, IllegalStateException,
            JsonSyntaxException {
        return null;
    }


    public String getResponseString() throws IOException {
        return null;
    }

    class AsyncAdapter extends AsyncTask<Object, Void, Object> {
        int type = -1;

        AsyncAdapter(int type) {
            this.type = type;
        }

        @Override
        protected Object doInBackground(Object... params) {
            T result = null;
            IWebService<T> ws = (IWebService<T>) params[0];
            try {
                result = ws.getResponseObject();
            } catch (JsonSyntaxException exception) {
                exception.printStackTrace();
            } catch (IllegalStateException exception) {
                exception.printStackTrace();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            wsccl.onCallComplete(result, type);
        }
    }
}
