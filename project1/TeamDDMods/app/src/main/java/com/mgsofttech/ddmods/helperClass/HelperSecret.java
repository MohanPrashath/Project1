package com.mgsofttech.ddmods.helperClass;

import android.content.Context;

import com.mgsofttech.ddmods.R;

public class HelperSecret {

    public Context context;
    public String SERVER_URL ;
    public String ORDER_CREATE;
    public String ORDER_VERIFY;
    public String ONESIGNAL_APP_ID = "e11555e8-ca8c-4f35-a54a-0df6d780aff6";


    public HelperSecret(Context context) {
        this.context = context;
    }

    public String getSERVER_URL() {
        return this.context.getString(R.string.SERVER_URL);
    }

    public void setSERVER_URL(String str) {
        this.SERVER_URL = str;
    }

    public String getORDER_CREATE() {
        return this.context.getString(R.string.ORDER_CREATE_URL);
    }

    public void setORDER_CREATE(String str) {
        this.ORDER_CREATE = str;
    }

    public String getORDER_VERIFY() {
        return this.context.getString(R.string.ORDER_VERIFY_URL);
    }

    public void setORDER_VERIFY(String str) {
        this.ORDER_VERIFY = str;
    }

    public String getONESIGNAL_APP_ID() {
       return ONESIGNAL_APP_ID;
    }

    public void setONESIGNAL_APP_ID(String str) {
        this.ONESIGNAL_APP_ID = str;
    }
}
