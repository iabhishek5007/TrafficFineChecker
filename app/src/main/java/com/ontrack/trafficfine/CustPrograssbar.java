package com.ontrack.trafficfine;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class CustPrograssbar {

    private Activity activity;
    private AlertDialog dialog;

    public CustPrograssbar(Activity myActivity){
        activity = myActivity;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custome_dialogg, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public void dismissDialog(){
        dialog.dismiss();
    }
}
