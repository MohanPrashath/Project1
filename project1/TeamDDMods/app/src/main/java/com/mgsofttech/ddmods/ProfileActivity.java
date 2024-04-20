package com.mgsofttech.ddmods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.mgsofttech.ddmods.adapter.AdapterProduct;
import com.mgsofttech.ddmods.extras.TinyDB;
import com.mgsofttech.ddmods.model.ModelProduct;
import com.mgsofttech.ddmods.model.ModelUser;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends ActivityBase {

    private ImageView itemBack;
    private ImageView itemLogout;
    private TextView userEmail,userFullName;
    private CircleImageView userImage;
    private TinyDB tinyDB;
    private ModelUser modelUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private GoogleSignInClient googleSignInClient;
    private   boolean canLogout = true;

    View itemEmpty;
    RecyclerView itemList;

    ArrayList<ModelProduct> modelProductList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TinyDB tinyDB2 = new TinyDB(this);
        this.tinyDB = tinyDB2;
        this.modelUser = tinyDB2.getUser();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.googleSignInClient = GoogleSignIn.getClient((Activity) this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build());
        this.userFullName = (TextView) findViewById(R.id.userFullName);
        this.userEmail = (TextView) findViewById(R.id.userEmail);
        this.userFullName.setText(this.modelUser.getUserFullName());
        this.userEmail.setText(this.modelUser.getUserEmail());
        this.itemBack = (ImageView) findViewById(R.id.itemBack);
        this.itemLogout = (ImageView) findViewById(R.id.itemLogout);
        this.userImage = (CircleImageView) findViewById(R.id.userImage);
        ((RequestBuilder) Glide.with((FragmentActivity) this).load(this.modelUser.getUserImage()).placeholder((int) R.drawable.ic_user)).into((ImageView) this.userImage);
        setupBackButton();
        setupModlist();
        this.itemLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileActivity.this.logoutAccount();
            }
        });

    }

    private void setupModlist() {
        this.itemList = (RecyclerView) findViewById(R.id.itemList);
        this.itemEmpty = findViewById(R.id.itemEmpty);
        final AdapterProduct adapterProduct = new AdapterProduct(this, this.modelProductList, 1);
        this.itemList.setLayoutManager(new LinearLayoutManager(this));
        this.itemList.setHasFixedSize(true);
        this.itemList.setNestedScrollingEnabled(false);
        this.itemList.setAdapter(adapterProduct);
        this.firebaseFirestore.collection("Product").whereArrayContains("listPurchase", (Object) this.modelUser.getUserID()).orderBy("timestamp", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            public void onComplete(Task<QuerySnapshot> task) {
                if (!task.isSuccessful() || task.getResult().size() <= 0) {
                    ProfileActivity.this.itemEmpty.setVisibility(View.VISIBLE);
                    return;
                }
                ProfileActivity.this.itemEmpty.setVisibility(View.GONE);
                for (DocumentSnapshot object : task.getResult().getDocuments()) {
                    ProfileActivity.this.modelProductList.add((ModelProduct) object.toObject(ModelProduct.class));
                }
                adapterProduct.notifyDataSetChanged();
            }
        });
    }

    private void logoutAccount() {
        new MaterialAlertDialogBuilder(this).setTitle((CharSequence) "Log out account").setMessage((CharSequence) "Are you sure you want to log out and exit the app?").setPositiveButton((CharSequence) "Logout", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                final ProgressDialog progressDialog = new ProgressDialog(ProfileActivity.this);
                progressDialog.setMessage("Sign in out, hold on...");
                progressDialog.setCancelable(false);
                progressDialog.create();
                progressDialog.show();
                ProfileActivity.this.googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(Task<Void> task) {
                        ProfileActivity.this.firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                                if (ProfileActivity.this.canLogout) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                    ProfileActivity.this.canLogout = false;
                                    if (firebaseAuth.getCurrentUser() == null) {
                                        ProfileActivity.this.tinyDB.clear();
                                        ProfileActivity.this.startActivity(new Intent(ProfileActivity.this, SplashActivity.class));
                                        ProfileActivity.this.finishAffinity();
                                    }
                                }
                            }
                        });
                        ProfileActivity.this.firebaseAuth.signOut();
                    }
                });
            }
        }).setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) null).show();
    }
}