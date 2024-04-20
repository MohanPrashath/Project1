package com.mgsofttech.ddmods;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cashfree.pg.core.api.CFSession;
import com.cashfree.pg.core.api.callback.CFCheckoutResponseCallback;
import com.cashfree.pg.core.api.utils.CFErrorResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mgsofttech.ddmods.adapter.AdapterImage;
import com.mgsofttech.ddmods.extras.TimeAgo;
import com.mgsofttech.ddmods.extras.TinyDB;
import com.mgsofttech.ddmods.helperClass.HelperCode;
import com.mgsofttech.ddmods.helperClass.HelperSecret;
import com.mgsofttech.ddmods.model.ModelProduct;
import com.mgsofttech.ddmods.model.ModelUser;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductActivity extends ActivityBase  implements CFCheckoutResponseCallback {
    AlertDialog dialogPaymentProcessing;
    FirebaseAnalytics firebaseAnalytics;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore firebaseFirestore;
    LinearLayout itemBuyNow;
    TextView itemBuyNowText;
    TextView itemDescription;
    SliderView itemImage;
    TextView itemPrivacyLetter;
    TextView itemStatDate;
    TextView itemStatSize;
    TextView itemStatView;
    TextView itemTitle;
    TextView itemStatType;
    ModelProduct modelProduct;
    ModelUser modelUser;
    String stringBillId = "";
    String stringDeviceId;
    String stringOrderAmount;
    String stringOrderId;
    String stringToken;
    TinyDB tinyDB;
    HelperSecret helperSecret;
    CFSession.Environment cfEnvironment = CFSession.Environment.PRODUCTION;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_product);
        setupBackButton();
        setupBuyNow();
        this.stringDeviceId = Settings.Secure.getString(getContentResolver(), "android_id");
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        TinyDB tinyDB2 = new TinyDB(this);
        this.tinyDB = tinyDB2;
        this.modelUser = tinyDB2.getUser();
        ModelProduct modelProduct2 = (ModelProduct) getIntent().getSerializableExtra("modelProduct");
        this.modelProduct = modelProduct2;
        this.itemStatType = (TextView) findViewById(R.id.itemStatType);
        this.stringOrderAmount = HelperCode.limitDecimalString(String.valueOf(modelProduct2.getPrice() * 100.0f));
        this.itemImage = (SliderView) findViewById(R.id.itemImage);
        this.itemTitle = (TextView) findViewById(R.id.itemTitle);
        this.itemDescription = (TextView) findViewById(R.id.itemDescription);
        this.itemStatSize = (TextView) findViewById(R.id.itemStatSize);
        this.itemStatDate = (TextView) findViewById(R.id.itemStatDate);
        this.itemStatView = (TextView) findViewById(R.id.itemStatView);
        this.itemBuyNow = (LinearLayout) findViewById(R.id.itemBuyNow);
        this.itemBuyNowText = (TextView) findViewById(R.id.itemBuyNowText);
        setupModInfo();
    }

    private void setupBuyNow() {
        this.itemPrivacyLetter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProductActivity.this.startActivity(new Intent(ProductActivity.this, LegalActivity.class));
            }
        });
        this.itemBuyNow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProductActivity.this.showProgressBar();
                ProductActivity.this.firebaseFirestore.collection("Product").document(ProductActivity.this.modelProduct.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            final ArrayList arrayList = new ArrayList();
                            ModelProduct modelProduct = (ModelProduct) task.getResult().toObject(ModelProduct.class);
                            modelProduct.setId(task.getResult().getId());
                            if (modelProduct.getListPurchase() != null && modelProduct.getListPurchase().size() > 0) {
                                arrayList.addAll(modelProduct.getListPurchase());
                            }
                            ProductActivity.this.firebaseFirestore.collection("ProductNew").document(ProductActivity.this.modelProduct.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                public void onComplete(Task<DocumentSnapshot> task) {
                                    ModelProduct modelProduct = (ModelProduct) task.getResult().toObject(ModelProduct.class);
                                    modelProduct.setId(task.getResult().getId());
                                    if (modelProduct.getListPurchase() != null && modelProduct.getListPurchase().size() > 0) {
                                        arrayList.addAll(modelProduct.getListPurchase());
                                    }
                                    ProductActivity.this.hideProgressBar();
                                    if (arrayList.contains(ProductActivity.this.modelUser.getUserID())) {
                                        ProductActivity.this.startActivity(new Intent(ProductActivity.this, DownloaderActivity.class).putExtra("modelProduct", ProductActivity.this.modelProduct));
                                    } else {
                                        ProductActivity.this.purchaseProduct();
                                    }
                                }
                            });
                            return;
                        }
                        Toast.makeText(ProductActivity.this, "Failed to connect with server. Try again!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public void purchaseProduct() {
        String str;
        if (!checkMobileNumberAdded()) {
            return;
        }
        if (!this.modelProduct.isFree() && ((str = this.stringToken) == null || str.length() < 10)) {
            paymentCreateOrder(true);
        } else if (this.modelProduct.isFree()) {
            purchaseCompleted("FREE_" + System.currentTimeMillis());
        } else {
            paymentMakePurchase();
        }
    }

    private boolean checkMobileNumberAdded() {
        String userMobile = this.tinyDB.getUser().getUserMobile();
        if (userMobile != null && userMobile.length() == 10) {
            return true;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = getLayoutInflater().inflate(R.layout.dialog_user_mobile, (ViewGroup) null);
        builder.setView(inflate);
        final AlertDialog create = builder.create();
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        final TextInputEditText textInputEditText = (TextInputEditText) inflate.findViewById(R.id.itemMobileNumberEt);
        final TextInputLayout textInputLayout = (TextInputLayout) inflate.findViewById(R.id.itemMobileNumberEtLayout);
        ((MaterialButton) inflate.findViewById(R.id.itemCancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                create.dismiss();
            }
        });
        ((MaterialButton) inflate.findViewById(R.id.itemSave)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String obj = textInputEditText.getText().toString();
                if (obj.length() != 10) {
                    textInputLayout.setError("Enter a valid mobile number!");
                    return;
                }
                create.dismiss();
                ProductActivity activityProduct = ProductActivity.this;
                activityProduct.modelUser = activityProduct.tinyDB.getUser();
                ProductActivity.this.modelUser.setUserMobile(obj);
                ProductActivity.this.tinyDB.putUser(ProductActivity.this.modelUser);
                ProductActivity.this.purchaseProduct();
            }
        });
        create.setCancelable(false);
        create.show();
        return false;
    }

    private void purchaseCompleted(String str) {
        showPaymentProcessDialog();
        this.firebaseFirestore.collection("ProductNew").document(this.modelProduct.getId()).update("listPurchase", (Object) FieldValue.arrayUnion(this.modelUser.getUserID()), new Object[0]).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(Task<Void> task) {
                ActivityConfig.tempOrderProductList.add(ProductActivity.this.modelProduct.getId());
                ArrayList<String> arrayList = new ArrayList<>();
                if (ProductActivity.this.modelProduct.getListPurchase() != null) {
                    arrayList = ProductActivity.this.modelProduct.getListPurchase();
                }
                arrayList.add(ProductActivity.this.modelUser.getUserID());
                ProductActivity.this.modelProduct.setListPurchase(arrayList);
                ProductActivity.this.dialogPaymentProcessing.dismiss();
                if (task.isSuccessful()) {
                    ProductActivity.this.startActivity(new Intent(ProductActivity.this, MainActivity.class));
                    ProductActivity.this.startActivity(new Intent(ProductActivity.this, DownloaderActivity.class).putExtra("modelProduct", ProductActivity.this.modelProduct));
                    ProductActivity.this.finishAffinity();
                    return;
                }
                Log.d("logProductOrder", "Error B: " + task.getException().getLocalizedMessage());
                new MaterialAlertDialogBuilder(ProductActivity.this, R.style.ThemeProductDialog).setTitle((CharSequence) "Oh no! There's an error.").setMessage((CharSequence) "Error while registering order. Contact app support team!").setPositiveButton((CharSequence) "Okay", (DialogInterface.OnClickListener) null).setCancelable(false).create().show();
            }
        });
    }

    private void showPaymentProcessDialog() {

    }

    private void paymentCreateOrder(boolean z) {
        if (!this.modelProduct.isFree() && this.tinyDB.getUser().getUserMobile() != null && this.tinyDB.getUser().getUserMobile().length() == 10) {
            if (z) {
                showProgressBar();
            }
            Volley.newRequestQueue(this).add(new JsonObjectRequest(0, this.helperSecret.getORDER_CREATE() + "?orderId=" + this.stringOrderId.replaceAll(StringUtils.SPACE, "%20") + "&orderNote=Order placed by DD App user&orderAmount=" + HelperCode.limitTwoDecimalString(String.valueOf(this.modelProduct.getPrice())) + "&orderCurrency=INR&customerId=" + this.modelUser.getUserID() + "&customerName=" + this.modelUser.getUserFullName().replaceAll(StringUtils.SPACE, "%20") + "&customerEmail=" + this.modelUser.getUserEmail() + "&customerPhone=" + this.modelUser.getUserMobile(), (JSONObject) null, new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject jSONObject) {
                    if (!ProductActivity.this.isDestroyed()) {
                        ProductActivity.this.hideProgressBar();
                        try {
                            ProductActivity.this.stringToken = jSONObject.getString("order_token");
                            Log.d("logProductOrderData", "Response: " + ProductActivity.this.stringToken);
                            if (z) {
                                ProductActivity.this.paymentMakePurchase();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError volleyError) {
                    Log.d("logProductOrderData", "Error: " + volleyError.getLocalizedMessage());
                    if (z) {
                        Toast.makeText(ProductActivity.this, "Failed to purchase this item. Try again!", Toast.LENGTH_SHORT).show();
                    }
                }
            }));
        }
    }

    private void paymentMakePurchase() {

    }


    private void setupModInfo() {
        this.itemTitle.setText(this.modelProduct.getTitle());
        this.itemDescription.setText(this.modelProduct.getDescription());
        this.itemImage.setSliderAdapter(new AdapterImage(this, this.modelProduct.getListImage()));
        this.itemImage.setIndicatorAnimation(IndicatorAnimationType.WORM);
        this.itemImage.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        this.itemImage.setAutoCycleDirection(2);
        this.itemImage.setIndicatorSelectedColor(-1);
        this.itemImage.setIndicatorUnselectedColor(-7829368);
        this.itemImage.setScrollTimeInSec(4);
        this.itemImage.startAutoCycle();
        this.itemStatSize.setText(this.modelProduct.getSize());
        this.itemStatDate.setText(TimeAgo.getTimeAgo(this.modelProduct.getTimestamp(), this));
        this.itemStatView.setText(HelperCode.prettyNumber(Integer.valueOf(this.modelProduct.getCountView())) + " Views");
        if (this.modelProduct.getType().equals("bussid")) {
            this.itemStatType.setText("BUSSID MOD");
        } else if (this.modelProduct.getType().equals("ets2")) {
            this.itemStatType.setText("ETS2 MOD");
        } else {
            this.itemStatType.setText("TDD MODS");
        }
        if (this.modelProduct.isFree()) {
            this.itemBuyNowText.setText("Download For Free");
        } else {
            this.itemBuyNowText.setText("Pay ₹" + HelperCode.limitTwoDecimalString(String.valueOf(this.modelProduct.getPrice())) + " and download now");
        }
    }

    @Override
    public void onPaymentVerify(String orderID) {
        verifyPayment();

    }
        private void verifyPayment() {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Verifying payment...");
            progressDialog.create();
            progressDialog.show();
            Volley.newRequestQueue(this).add(new JsonObjectRequest(0, this.helperSecret.getORDER_VERIFY() + "?orderId=" + this.stringOrderId, (JSONObject) null, new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject jSONObject) {
                    try {
                        String string = jSONObject.getString("cf_order_id");
                        String string2 = jSONObject.getString("order_status");
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (string2.equals("PAID")) {
                            ProductActivity.this.purchaseCompleted(string);
                        } else if (string2.equals("ACTIVE")) {
                            Toast.makeText(ProductActivity.this, "Sorry, the transaction failed!", Toast.LENGTH_LONG).show();
                        }
                        Log.d("logResponseVerifyData", "Verify Response: " + string2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("logResponseVerifyData", "Verify Error: " + e.getLocalizedMessage());
                        Toast.makeText(ProductActivity.this, "Sorry, something went wrong!", Toast.LENGTH_LONG).show();
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError volleyError) {
                    Log.d("logResponseVerifyData", "Verify Error: " + volleyError.getLocalizedMessage());
                }
            }));
        }

    @Override
    public void onPaymentFailure(CFErrorResponse cFErrorResponse, String str) {
        Log.d("logResponseVerifyData", "Error A: " + str);
        Log.d("logResponseVerifyData", "Error B: " + cFErrorResponse.getDescription());
        Log.d("logResponseVerifyData", "Error C: " + cFErrorResponse.getMessage());
        Log.d("logResponseVerifyData", "Error D: " + cFErrorResponse.getStatus());
        Log.d("logResponseVerifyData", "Error E: " + cFErrorResponse.getType());
        Log.d("logResponseVerifyData", "Error F: " + cFErrorResponse.getCode());
        if (cFErrorResponse.getCode().equals("payment_method_unsupported")) {
            Toast.makeText(this, "Sorry, the payment method is not supported. Try different CARD or UPI.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Sorry, transaction has failed!", Toast.LENGTH_SHORT).show();
        }
    }
}
