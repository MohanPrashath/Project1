package com.mgsofttech.ddmods.helperClass;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.Constants;
import com.mgsofttech.ddmods.BuildConfig;
import com.mgsofttech.ddmods.InterfaceProduct;
import com.mgsofttech.ddmods.extras.TinyDB;
import com.mgsofttech.ddmods.model.ModelAppVersion;
import com.mgsofttech.ddmods.model.ModelProduct;
import com.mgsofttech.ddmods.model.ModelServer;
import com.onesignal.outcomes.OSOutcomeConstants;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class HelperServer {
    String SERVER_URL = "";
    Context context;
    HelperSecret helperSecret;
    TinyDB tinyDB;
    private Exception e;


    public void orderCreate() {
    }

    public void orderVerify() {
    }

    public HelperServer(Context context2) {
        this.context = context2;
        this.tinyDB = new TinyDB(context2);
        HelperSecret helperSecret2 = new HelperSecret(context2);
        this.helperSecret = helperSecret2;
        this.SERVER_URL = helperSecret2.getSERVER_URL();
    }

    public void getProductList(final InterfaceProduct interfaceProduct) {
        Volley.newRequestQueue(this.context).add(new StringRequest(0, this.SERVER_URL, new Response.Listener<String>() {
            public void onResponse(String str) {
                Response.Listener<String> r2;
                String str2;
                String str3 = null;
                String str4;
                String str5;
                JSONObject jSONObject;
                String str6;
                String str7;
                String str8 = "listLink";
                String str9 = "listImage";
                String str10 = Constants.ScionAnalytics.MessageType.DATA_MESSAGE;
                String str11 = "appVersion";
                try {
                    JSONObject jSONObject2 = new JSONObject(str);
                    ModelServer modelServer = new ModelServer();
                    ArrayList arrayList = new ArrayList();
                    int i = 0;
                    while (true) {
                        int length = jSONObject2.getJSONArray(str11).length();
                        str2 = "description";
                        if (i >= length) {
                            break;
                        }
                        try {
                            JSONObject jSONObject3 = jSONObject2.getJSONArray(str11).getJSONObject(i);
                            boolean z = jSONObject3.getBoolean("blocked");
                            String string = jSONObject3.getString(str3);
                            String string2 = jSONObject3.getString("animation");
                            String string3 = jSONObject3.getString(str2);
                            String string4 = jSONObject3.getString("version");
                            String string5 = jSONObject3.getString("buttonTitle");
                            String str12 = str11;
                            String string6 = jSONObject3.getString("buttonLink");
                            boolean z2 = jSONObject3.getBoolean("button");
                            String str13 = str8;
                            String str14 = str9;
                            Log.d("logHelperBlcok", "VERSION: " + string4 + " --- " + modelServer.isBlocked());
                            ModelAppVersion modelAppVersion = new ModelAppVersion();
                            modelAppVersion.setBlocked(z);
                            modelAppVersion.setTitle(string);
                            modelAppVersion.setAnimation(string2);
                            modelAppVersion.setDescription(string3);
                            modelAppVersion.setVersion(string4);
                            modelAppVersion.setButton(z2);
                            modelAppVersion.setButtonTitle(string5);
                            modelAppVersion.setButtonLink(string6);
                            arrayList.add(modelAppVersion);
                            if (string4.equals(BuildConfig.VERSION_NAME)) {
                                Log.d("logBlockedData", "GOT VERSION: IF");
                                Log.d("logBlockedData", "APP VERSION: 8.0.0");
                                Log.d("logBlockedData", "GOT VERSION: " + string4);
                                Log.d("logBlockedData", "BLOCKED: " + z);
                                Log.d("logBlockedData", "BUTTON : " + z2);
                                modelServer.setBlocked(z);
                                modelServer.setBlockTitle(string);
                                modelServer.setAnimation(string2);
                                modelServer.setBlockDescription(string3);
                                modelServer.setButton(z2);
                                modelServer.setButtonTitle(string5);
                                modelServer.setButtonLink(string6);
                            } else {
                                Log.d("logBlockedData", "GOT VERSION: ELSE");
                            }
                            i++;
                            str11 = str12;
                            str8 = str13;
                            str9 = str14;
                        } catch (JSONException e) {
                            e = e;
                            r2 = this;
                            interfaceProduct.onFailed(e.getLocalizedMessage());
                        }
                    }
                    String str15 = str8;
                    String str16 = str9;
                    modelServer.setModelAppVersionList(arrayList);
                    ArrayList arrayList2 = new ArrayList();
                    int i2 = 0;
                    while (i2 < jSONObject2.getJSONArray(str10).length()) {
                        JSONObject jSONObject4 = jSONObject2.getJSONArray(str10).getJSONObject(i2);
                        String string7 = jSONObject4.getString(OSOutcomeConstants.OUTCOME_ID);
                        String string8 = jSONObject4.getString("type");
                        int i3 = jSONObject4.getInt("countView");
                        String string9 = jSONObject4.getString(str2);
                        boolean z3 = jSONObject4.getBoolean("free");
                        ArrayList arrayList3 = new ArrayList();
                        int i4 = 0;
                        while (true) {
                            str4 = str16;
                            str5 = str10;
                            if (i4 >= jSONObject4.getJSONArray(str4).length()) {
                                break;
                            }
                            arrayList3.add(jSONObject4.getJSONArray(str4).getString(i4));
                            i4++;
                            str10 = str5;
                            str16 = str4;
                        }
                        ArrayList arrayList4 = new ArrayList();
                        int i5 = 0;
                        while (true) {
                            jSONObject = jSONObject2;
                            str6 = str15;
                            str7 = str2;
                            if (i5 >= jSONObject4.getJSONArray(str6).length()) {
                                break;
                            }
                            arrayList4.add(jSONObject4.getJSONArray(str6).getString(i5));
                            i5++;
                            str15 = str6;
                            jSONObject2 = jSONObject;
                            str2 = str7;
                        }
                        String str17 = str4;
                        long j = jSONObject4.getLong(FirebaseAnalytics.Param.PRICE);
                        String string10 = jSONObject4.getString("size");
                        String str18 = str6;
                        Date firebaseTimestamp = HelperDate.getFirebaseTimestamp(jSONObject4.getString("timestamp"));
                        ModelServer modelServer2 = modelServer;
                        Date firebaseTimestamp2 = HelperDate.getFirebaseTimestamp(jSONObject4.getString("timestampUpdated"));
                        int i6 = i2;
                        String string11 = jSONObject4.getString(str3);
                        String str19 = str3;
                        jSONObject4.getString("video");
                        boolean z4 = jSONObject4.getBoolean("visible");
                        ModelProduct modelProduct = new ModelProduct();
                        modelProduct.setId(string7);
                        modelProduct.setType(string8);
                        modelProduct.setCountView(i3);
                        modelProduct.setDescription(string9);
                        modelProduct.setFree(z3);
                        modelProduct.setListImage(arrayList3);
                        modelProduct.setListLink(arrayList4);
                        modelProduct.setPrice((float) j);
                        modelProduct.setSize(string10);
                        modelProduct.setTimestamp(firebaseTimestamp);
                        modelProduct.setTimestampUpdated(firebaseTimestamp2);
                        modelProduct.setTitle(string11);
                        if (z4) {
                            arrayList2.add(modelProduct);
                        }
                        i2 = i6 + 1;
                        jSONObject2 = jSONObject;
                        str10 = str5;
                        str2 = str7;
                        modelServer = modelServer2;
                        str3 = str19;
                        str16 = str17;
                        str15 = str18;
                    }
                    ModelServer modelServer3 = modelServer;
                    r2 = this;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        arrayList2.sort(new Comparator<ModelProduct>() {
                            public int compare(ModelProduct modelProduct, ModelProduct modelProduct2) {
                                return modelProduct2.getTimestamp().compareTo(modelProduct.getTimestamp());
                            }
                        });
                    }
                    ModelServer modelServer4 = modelServer3;
                    modelServer4.setModelProductList(arrayList2);
                    interfaceProduct.onSuccess(modelServer4);
                } catch (JSONException e3) {
                    e = e3;
                    r2 = this;
                    interfaceProduct.onFailed(e.getLocalizedMessage());
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                interfaceProduct.onFailed("Connection problem, error: " + volleyError.getLocalizedMessage());
            }
        }));
    }
}
