package com.jebstern.kanaiscube;

import android.content.Context;


import android.preference.DialogPreference;
import android.util.AttributeSet;

public class OptionDialogPreference extends DialogPreference {

    Context context;

    public OptionDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        persistBoolean(positiveResult);
        if (positiveResult) {
            DBHelper helper = new DBHelper(context);
            helper.resetCubedData();
        }
    }


}