package org.impactindia.llemeddocket.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.impactindia.llemeddocket.AttributeSet;
import org.impactindia.llemeddocket.LLEApplication;
import org.impactindia.llemeddocket.R;

import java.text.SimpleDateFormat;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public void hideKeyboard(View edt) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt.getWindowToken(), 0);
    }

    protected String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(AttributeSet.Constants.REVERSE_SHORT_DATE);
        return sdf.format(System.currentTimeMillis());
    }

    protected String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(AttributeSet.Constants.MODIFIED_DATE_FORMAT);
        return sdf.format(System.currentTimeMillis());
    }

    public void showProgDialog(Context context) {
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            ((Activity)context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            ((Activity)context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new ProgressDialog(context, R.style.prog_diag_style);
//            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }
    }

    public void showProgDialog(Context context, String msg) {
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            ((Activity)context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            ((Activity)context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new ProgressDialog(context, R.style.prog_diag_style);
            progressDialog.setMessage(msg);
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }
    }

    public void closeProgDialog(Context context) {
        ((Activity)context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
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

    protected LLEApplication getApp() {
        LLEApplication app = null;
        app = (LLEApplication) getApplication();
        return app;
    }

    protected void shortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void longToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

}
