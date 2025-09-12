package org.impactindia.llemeddocket.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

/**
 * Created by devandroid on 30/5/17.
 */

public class FontedAutoCompleteTextView extends AppCompatAutoCompleteTextView {

    private int myThreshold;

    public FontedAutoCompleteTextView(Context context) {
        super(context);
    }

    public FontedAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FontedAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // This is how to disable AutocompleteTextView filter
    @Override
    protected void performFiltering(CharSequence text, int keyCode) {
        String filterText = "";
        super.performFiltering(filterText, keyCode);
    }

    /*
    * after a selection we have to capture the new value and append to the existing text
    */
    @Override
    protected void replaceText(CharSequence text) {
        super.replaceText(text);
    }

    @Override
    public void setThreshold(int threshold) {
        if (threshold < 0) {
            threshold = 0;
        }
        myThreshold = threshold;
    }

    @Override
    public boolean enoughToFilter() {
        return getText().length() >= myThreshold;
    }

    @Override
    public int getThreshold() {
        return myThreshold;
    }
}
