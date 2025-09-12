package org.impactindia.llemeddocket.ws;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import org.impactindia.llemeddocket.pojo.WsModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by devandroid on 17/5/17.
 */

public class WebService<T extends WsModel> implements IWebService<T> {

    /** The Constant TAG. */
    final static String TAG = "WebService";

    /** The Constant TYPE_POST. */
    public final static int TYPE_GET = 0, TYPE_POST = 1;

    /** The Constant REQUEST_HEADER. */
    private static final String REQUEST_HEADER = "request_header";

    /** The Constant REQUEST_BODY. */
    private static final String REQUEST_BODY = "request_body";

    /** The debug. */
    private boolean debug, connTimeout;

    public static final int SERVER_CONNECT_TIMEOUT = 12000;

    /** The map for parameters */
    private Map<String, Object> params;
    /** The model. */
    private WsModel model;
    /** The Mapper */
    private Class<?> mapper;
    /** The url. */
    private String url;
    /** The type */
    int type;

    public WebService(Map<String, Object> params, String url, Class<?> mapper, int type) {
        this.params = params;
        this.url = url;
        this.mapper = mapper;
        this.type = type;
    }

    public WebService(WsModel model, String url, Class<?> mapper, int type) {
        this.model = model;
        this.url = url;
        this.mapper = mapper;
        this.type = type;
    }

    @Override
    public T getResponseObject() throws IOException, IllegalStateException, JsonSyntaxException {
        T response = null;
        BufferedInputStream source;
        if (url.startsWith("https")) {
            source = new BufferedInputStream(fetchStreamSecurely(url));
        } else {
            source = new BufferedInputStream(fetchStream(url));
        }
        if (isDebug()) {
            if (source != null) {
                BufferedInputStream bis;
                ByteArrayOutputStream buf;
                String debugString;
                bis = new BufferedInputStream(source);
                buf = readInputStream(source);
                debugString = buf.toString();
                Log.d(TAG, "DEBUG : " + debugString);
                source = new BufferedInputStream(new ByteArrayInputStream(buf.toByteArray()));
            }
        }
        if (source != null) {
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(source);
            JsonReader jsonReader = new JsonReader(reader);
            jsonReader.setLenient(true);
            response = gson.fromJson(jsonReader, mapper);
        }
        return response;
    }

    private ByteArrayOutputStream readInputStream(InputStream in) throws IOException {
        BufferedInputStream bis;
        ByteArrayOutputStream buf;
        buf = new ByteArrayOutputStream();
        bis = new BufferedInputStream(in);

        int result = bis.read();
        while (result != -1) {
            byte b = (byte) result;
            buf.write(b);
            result = bis.read();
        }
        return buf;
    }

    private InputStream fetchStreamSecurely(String url) {
        Uri.Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter(REQUEST_BODY, ConvertObjectToJson.toJSON(getParams()));

        if (type == TYPE_GET) {
            try {
                Log.d(TAG, "IN GET");
                url = builder.build().toString();
                URL urlObj = new URL(url);
                HttpsURLConnection conn = (HttpsURLConnection) urlObj.openConnection();
                if (isConnTimeout()) {
                    conn.setConnectTimeout(SERVER_CONNECT_TIMEOUT);
                    conn.setReadTimeout(SERVER_CONNECT_TIMEOUT);
                }
                conn.connect();
                return fetchResponse(conn);
            } catch (SocketTimeoutException exception) {
                JSONObject respJson = new JSONObject();
                try {
                    respJson.put("ErrorCode", "7");
                    respJson.put("ErrorMessage", "Server connection timeout occured");
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
                exception.printStackTrace();
                InputStream stream = new ByteArrayInputStream(respJson.toString().getBytes());
                return stream;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Log.d(TAG, "IN POST");
                String params;
                params = new Gson().toJson(model);
                Log.d(TAG, params);
                URL urlObj = new URL(url);
                HttpsURLConnection conn = (HttpsURLConnection) urlObj.openConnection();
                // add request header
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                // send post request
                conn.setDoInput(true);
                conn.setDoOutput(true);
                // set connection timeout
                if (isConnTimeout()) {
                    conn.setConnectTimeout(SERVER_CONNECT_TIMEOUT);
                    conn.setReadTimeout(SERVER_CONNECT_TIMEOUT);
                }
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(params);
                writer.flush();
                writer.close();
                return fetchResponse(conn);
            } catch (SocketTimeoutException exception) {
                JSONObject resJson = new JSONObject();
                try {
                    resJson.put("ErrorCode", "7");
                    resJson.put("ErrorMessage", "Server connection timeout occured");
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                exception.printStackTrace();
                InputStream stream = new ByteArrayInputStream(resJson.toString().getBytes());
                return stream;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private InputStream fetchResponse(HttpsURLConnection conn) throws IOException {
        Log.d(TAG, "Status " + conn.getResponseCode() + " for URL " + conn.getURL());
        if (conn.getResponseCode() == HttpsURLConnection.HTTP_OK) {
            return conn.getInputStream();
        }
        return null;
    }

    /**
     * Fetch Stream from the given url
     * @param url the url to fetch stream from.
     * @return the inputstream
     */
    private InputStream fetchStream(String url) {
        Uri.Builder build = Uri.parse(url).buildUpon();
        build.appendQueryParameter(REQUEST_BODY, ConvertObjectToJson.toJSON(getParams()));

        if (type == TYPE_GET) {
            try {
                Log.d(TAG, "IN GET");
//				url = URLEncoder.encode(build.build().toString(), "UTF-8");
                url = build.build().toString();
                URL urlObj = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
                if (isConnTimeout()) {
                    conn.setConnectTimeout(SERVER_CONNECT_TIMEOUT);
                    conn.setReadTimeout(SERVER_CONNECT_TIMEOUT);
                }
                conn.connect();
                return fetchResponse(conn);
            } catch (SocketTimeoutException e) {
                JSONObject resJson = new JSONObject();
                try {
                    resJson.put("ErrorCode", "7");
                    resJson.put("ErrorMessage", "Server connection timeout occured");
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
                InputStream stream = new ByteArrayInputStream(resJson.toString().getBytes());
                return stream;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Log.d(TAG, "IN POST");
                String params;
                params = new Gson().toJson(model);
                Log.d(TAG, params);
                URL urlObj = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
                // add request header
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                // send post request
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(params);
                writer.flush();
                writer.close();
                return fetchResponse(conn);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    private InputStream fetchResponse(HttpURLConnection conn) throws IOException {
        Log.d(getClass().getSimpleName(), "Status " + conn.getResponseCode()
                + " for URL " + conn.getURL());
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return conn.getInputStream();
        }
        return null;
    }

    @Override
    public T getResponseArray() throws IOException, IllegalStateException, JsonSyntaxException {
        return null;
    }

    @Override
    public String getResponseString() throws IOException {
        return null;
    }

    /**
     * Returns all http parameters of current webservice instance.
     *
     * @return parameters in form of Map<String, Object>
     */
    private Map<String, Object> getParams() {
        return params;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isConnTimeout() {
        return connTimeout;
    }

    public void setConnTimeout(boolean connTimeout) {
        this.connTimeout = connTimeout;
    }

    public static class ConvertObjectToJson {
        /** The gson. */
        private static Gson gson = new GsonBuilder().create();

        private static final <T> String toJSON(T t) {
            return gson.toJson(t);
        }
    }
}
