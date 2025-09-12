package org.impactindia.llemeddocket.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.LLEApplication;
import org.impactindia.llemeddocket.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BaseFrag extends Fragment {
    public final static String EMPTY = "";
    public static final String BLANK = " ";

    private String tag;
    private ProgressDialog progressDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        logInfo("onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logInfo("onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        logInfo("onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        logInfo("onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        logInfo("onViewStateRestored");
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onStart() {
        logInfo("onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        logInfo("onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        logInfo("onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        logInfo("onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        logInfo("onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        logInfo("onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        logInfo("onDetach");
        super.onDetach();
    }

    public SpannableStringBuilder getSpanStringBuilder(String error) {
        SpannableStringBuilder ssbuilder = null;
        if (error != null) {
            int ecolor = Color.RED; // whatever color you want
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(ecolor);
            ssbuilder = new SpannableStringBuilder(error);
            ssbuilder.setSpan(fgcspan, 0, error.length(), 0);
            // myedittext.setError(ssbuilder);
            return ssbuilder;
        }
        return ssbuilder;
    }

    protected void showMessageDialog(String msg) {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(msg);
        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected String getPreviousMonthDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(AttributeSet.Constants.REVERSE_SHORT_DATE);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        return sdf.format(c.getTime());
    }

    protected String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(AttributeSet.Constants.REVERSE_SHORT_DATE);
        return sdf.format(System.currentTimeMillis());
    }

    protected String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(AttributeSet.Constants.MODIFIED_DATE_FORMAT);
        return sdf.format(System.currentTimeMillis());
    }

    protected String getCurrentTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat(AttributeSet.Constants.TIME_STAMP_FORMAT);
        return sdf.format(System.currentTimeMillis());
    }

    protected void log(String msg) {
        Log.d(getAppTag(), msg);
    }

    protected void logInfo(String msg) {
        Log.i(getAppTag(), msg);
    }

    protected void log(String msg, Throwable tr) {
        Log.e(getAppTag(), msg, tr);
    }

    protected void shortToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void longToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    public void showProgDialog(Context context) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new ProgressDialog(context, R.style.prog_diag_style);
//            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setCancelable(true);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }
    }

    public void showProgDialog(Context context, String msg) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new ProgressDialog(context, R.style.prog_diag_style);
            progressDialog.setMessage(msg);
            progressDialog.setCancelable(true);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }
    }

    public void closeProgDialog() {
        if (progressDialog != null || progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    protected boolean isEmpty(String value) {
        if (TextUtils.isEmpty(value)) {
            return true;
        }
        return false;
    }

    protected String quote(String s) {
        return new StringBuilder()
                .append('\'')
                .append(s)
                .append('\'')
                .toString();
    }

    protected String safe(String str) {
        return isEmpty(str) ? EMPTY : str;
    }

    protected String getAppTag() {
        if (tag == null) {
            tag = getString(R.string.app_name) + " " + getClass().getSimpleName();
        }
        return tag;
    }

    protected LLEApplication getApp() {
        LLEApplication app = null;
        Activity activity;
        activity = getActivity();
        if (activity != null) {
            app = (LLEApplication) activity.getApplication();
        }
        return app;
    }
}
