package com.mgsofttech.ddmods;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.mgsofttech.ddmods.adapter.AdapterProduct;
import com.mgsofttech.ddmods.extras.TinyDB;
import com.mgsofttech.ddmods.model.ModelProduct;
import com.mgsofttech.ddmods.model.ModelUser;

import java.util.ArrayList;
import java.util.Comparator;

public class DownloadActivity extends ActivityBase {

    FirebaseFirestore firebaseFirestore;
    View itemEmpty;
    RecyclerView itemList;
    LottieAnimationView itemProgress;
    ArrayList<ModelProduct> modelProductList = new ArrayList<>();
    ModelUser modelUser;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        setupBackButton();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        TinyDB tinyDB2 = new TinyDB(this);
        this.tinyDB = tinyDB2;
        this.modelUser = tinyDB2.getUser();
        this.itemList = (RecyclerView) findViewById(R.id.itemList);
        this.itemEmpty = findViewById(R.id.itemEmpty);
        this.itemProgress = (LottieAnimationView) findViewById(R.id.itemProgress);
        loadListFirst();
    }

    private void loadListFirst() {
        final AdapterProduct adapterProduct = new AdapterProduct(this, this.modelProductList, 1);
        this.itemList.setLayoutManager(new LinearLayoutManager(this));
        this.itemList.setHasFixedSize(true);
        this.itemList.setNestedScrollingEnabled(false);
        this.itemList.setAdapter(adapterProduct);
        final ArrayList arrayList = new ArrayList();
        this.firebaseFirestore.collection("Product").whereArrayContains("listPurchase", (Object) this.modelUser.getUserID()).orderBy("timestamp", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot next : task.getResult().getDocuments()) {
                        ModelProduct modelProduct = (ModelProduct) next.toObject(ModelProduct.class);
                        modelProduct.setId(next.getId());
                        arrayList.add(next.getId());
                        DownloadActivity.this.modelProductList.add(modelProduct);
                    }
                    DownloadActivity.this.firebaseFirestore.collection("ProductNew").whereArrayContains("listPurchase", (Object) DownloadActivity.this.modelUser.getUserID()).orderBy("timestamp", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        public void onComplete(Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && task.getResult().size() > 0) {
                                DownloadActivity.this.itemEmpty.setVisibility(View.GONE);
                                for (DocumentSnapshot next : task.getResult().getDocuments()) {
                                    ModelProduct modelProduct = (ModelProduct) next.toObject(ModelProduct.class);
                                    modelProduct.setId(next.getId());
                                    if (!arrayList.contains(next.getId())) {
                                        arrayList.add(next.getId());
                                        DownloadActivity.this.modelProductList.add(modelProduct);
                                    }
                                }
                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                DownloadActivity.this.modelProductList.sort(new Comparator<ModelProduct>() {
                                    public int compare(ModelProduct modelProduct, ModelProduct modelProduct2) {
                                        return modelProduct.getTimestamp().compareTo(modelProduct2.getTimestamp());
                                    }
                                });
                            }
                            adapterProduct.notifyDataSetChanged();
                            DownloadActivity.this.itemProgress.setVisibility(View.GONE);
                            if (adapterProduct.getItemCount() > 0) {
                                DownloadActivity.this.itemEmpty.setVisibility(View.GONE);
                            } else {
                                DownloadActivity.this.itemEmpty.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    return;
                }
                DownloadActivity.this.itemEmpty.setVisibility(View.VISIBLE);
            }
        });
    }
}