package com.mgsofttech.ddmods;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.mgsofttech.ddmods.extras.TinyDB;
import com.mgsofttech.ddmods.model.ModelUser;

import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.DialogPropertiesPendulum;
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum;

import java.util.Date;
import java.util.HashMap;

public class SplashActivity extends AppCompatActivity {

    int GOOGLE_SIGN_IN_KEY = 123;
    FirebaseAnalytics firebaseAnalytics;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    boolean isFirstRun = true;
    GoogleSignInClient mGoogleSignInClient;
    TinyDB tinyDB;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        this.tinyDB = new TinyDB(this);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        checkInternet();
        this.mGoogleSignInClient = GoogleSignIn.getClient((Activity) this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build());
        new Handler().postDelayed(new Runnable() {
            public void run() {
                SplashActivity.this.checkIfUserSignedIn();
            }
        }, 1200);


    }

    private void checkInternet() {

        // No Internet Dialog: Pendulum

        NoInternetDialogPendulum.Builder builder = new NoInternetDialogPendulum.Builder(
                this,
                getLifecycle()
        );

        DialogPropertiesPendulum properties = builder.getDialogProperties();

        properties.setConnectionCallback(new ConnectionCallback() { // Optional
            @Override
            public void hasActiveConnection(boolean hasActiveConnection) {
                // ...
            }
        });

        properties.setCancelable(false);
        properties.setNoInternetConnectionTitle("No Internet");
        properties.setNoInternetConnectionMessage("Check your Internet connection and try again");
        properties.setShowInternetOnButtons(true);
        properties.setPleaseTurnOnText("Please turn on");
        properties.setWifiOnButtonText("Wifi");
        properties.setMobileDataOnButtonText("Mobile data");

        properties.setOnAirplaneModeTitle("No Internet");
        properties.setOnAirplaneModeMessage("You have turned on the airplane mode.");
        properties.setPleaseTurnOffText("Please turn off");
        properties.setAirplaneModeOffButtonText("Airplane mode");
        properties.setShowAirplaneModeOffButtons(true);

        builder.build().show();
    }

    @SuppressLint("ResourceType")
    protected void onActivityResult (int i, int i2, Intent intent){
        super.onActivityResult(i, i2, intent);
        if (i == this.GOOGLE_SIGN_IN_KEY) {
            Task<GoogleSignInAccount> signedInAccountFromIntent = GoogleSignIn.getSignedInAccountFromIntent(intent);
            try {
                showGoogleSignUpDialog();
                firebaseAuthWithGoogle(signedInAccountFromIntent.getResult(ApiException.class));
            } catch (ApiException e) {
                if (e.getStatusCode() == 12500) {
                    Snackbar.make(findViewById(android.R.id.content), (CharSequence) "Sign In Error! Update Google Play Service.", 0).show();
                }
            }
        }
    }

    private void checkIfUserSignedIn() {
        FirebaseUser currentUser = this.firebaseAuth.getCurrentUser();
        this.firebaseUser = currentUser;
        if (currentUser != null) {
            checkIfUserBlocked();
        } else {
            startActivityForResult(this.mGoogleSignInClient.getSignInIntent(), this.GOOGLE_SIGN_IN_KEY);
        }
    }

    private void checkIfUserBlocked() {
        this.firebaseFirestore.collection("User")
                .document(this.tinyDB.getUser().getUserID())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            ModelUser modelUser = (ModelUser) task.getResult().toObject(ModelUser.class);
                            if (modelUser == null) {
                                SplashActivity.this.getStudentList();
                            } else if (modelUser.isUserBlocked()) {
                                new MaterialAlertDialogBuilder(SplashActivity.this).setTitle((CharSequence) "Access restricted!").setMessage((CharSequence) modelUser.getUserBlockedReason()).setPositiveButton((CharSequence) "Exit App", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        SplashActivity.this.finishAffinity();
                                    }
                                }).setCancelable(false).create().show();
                            } else {
                                SplashActivity.this.getStudentList();
                            }
                        } else {
                            SplashActivity.this.getStudentList();
                        }
                    }
                });
    }



    private void showGoogleSignUpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(getLayoutInflater().inflate(R.layout.sign_in_success, (ViewGroup) null));
        AlertDialog create = builder.create();
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        create.setCancelable(false);
        create.show();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount googleSignInAccount) {
        this.firebaseAuth.signInWithCredential(GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), (String) null)).addOnCompleteListener((Activity) this, new OnCompleteListener<AuthResult>() {
            @SuppressLint("ResourceType")
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.e("logSplashData", "Signed in");
                    FirebaseUser currentUser = SplashActivity.this.firebaseAuth.getCurrentUser();
                    SplashActivity.this.storeUserInfo(currentUser.getPhotoUrl().toString(), currentUser.getUid(), currentUser.getDisplayName(), currentUser.getEmail());
                    return;
                }
                Log.e("logSplashData", "Error: " + task.getException().getLocalizedMessage());
                Snackbar.make( SplashActivity.this.findViewById(android.R.id.content), (CharSequence) "Sign In Failed!", 0).show();
            }
        });
    }

    private void storeUserInfo(String str, String str2, String str3, String str4) {
        final String str5 = str2;
        final String str6 = str;
        final String str7 = str3;
        final String str8 = str4;

        this.firebaseAuth.getCurrentUser().updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(str3).setPhotoUri(Uri.parse(str)).build()).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {

                    SplashActivity.this.firebaseFirestore.collection("User")
                            .document(str5)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.getData() == null || documentSnapshot.getData().size() <= 0) {
                                        SplashActivity.this.createUserData(str5, str6, str7, str8);
                                        return;
                                    }
                                    SplashActivity.this.tinyDB.putUser((ModelUser) documentSnapshot.toObject(ModelUser.class));
                                    SplashActivity.this.checkIfUserBlocked();
                                }
                            });
                }
            }
        });
    }


    private void createUserData(String str, String str2, String str3, String str4) {
        HashMap hashMap = new HashMap();
        hashMap.put("userID", str);
        hashMap.put("userFullName", str3);
        hashMap.put("userEmail", str4);
        hashMap.put("userImage", str2);
        hashMap.put("userGender", "male");
        hashMap.put("userPoints", 0);
        hashMap.put("userVerified", false);
        hashMap.put("timestamp", FieldValue.serverTimestamp());
        hashMap.put("timestampUpdated", FieldValue.serverTimestamp());
        final String str5 = str;
        final String str6 = str3;
        final String str7 = str4;
        final String str8 = str2;
        this.firebaseFirestore.collection("User")
                .document(str5)
                .set(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    public void onSuccess(Void voidR) {
                        ModelUser modelUser = new ModelUser();
                        modelUser.setUserID(str5);
                        modelUser.setUserFullName(str6);
                        modelUser.setUserEmail(str7);
                        modelUser.setUserImage(str8);
                        modelUser.setUserGender("male");
                        modelUser.setTimestamp(new Date());
                        SplashActivity.this.tinyDB.putUser(modelUser);
                        SplashActivity.this.checkIfUserBlocked();
                    }
                });
    }

    private void getStudentList() {
        goToHomepage();
    }

    private void goToHomepage() {
        if (!isDestroyed()) {
            startActivity(new Intent(this, MainActivity.class));
            finishAffinity();
        }
    }
}