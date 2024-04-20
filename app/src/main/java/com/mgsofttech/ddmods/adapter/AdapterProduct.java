package com.mgsofttech.ddmods.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.mgsofttech.ddmods.DownloadActivity;
import com.mgsofttech.ddmods.DownloaderActivity;
import com.mgsofttech.ddmods.ProductActivity;
import com.mgsofttech.ddmods.R;
import com.mgsofttech.ddmods.helperClass.HelperCode;
import com.mgsofttech.ddmods.model.ModelProduct;

import java.util.ArrayList;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.MyViewHolder> {
    Activity activity;
    int listType;
    ArrayList<ModelProduct> modelProductList;

    public AdapterProduct(Activity activity2, ArrayList<ModelProduct> arrayList, int i) {
        this.listType = i;
        this.activity = activity2;
        this.modelProductList = arrayList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        int i2 = this.listType;
        if (i2 == 1 || i2 == 2) {
            return new MyViewHolder(LayoutInflater.from(this.activity).inflate(R.layout.item_product_list, viewGroup, false));
        }
        return new MyViewHolder(LayoutInflater.from(this.activity).inflate(R.layout.item_product, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {
        String str;
        HelperCode.setFontPrice(this.activity, myViewHolder.itemPrice);
        myViewHolder.itemTitle.setText(this.modelProductList.get(i).getTitle());
        myViewHolder.itemDescription.setText(this.modelProductList.get(i).getDescription());
        TextView textView = myViewHolder.itemPrice;
        if (this.modelProductList.get(i).isFree()) {
            str = "FREE";
        } else {
            str = "₹" + HelperCode.limitTwoDecimalString(String.valueOf(this.modelProductList.get(i).getPrice()));
        }
        textView.setText(str);
        ((RequestBuilder) Glide.with(this.activity).load(this.modelProductList.get(i).getListImage().get(0)).placeholder((int) R.drawable.bg_placeholder_rec)).into(myViewHolder.itemImage);
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (AdapterProduct.this.listType == 1) {
                    AdapterProduct.this.activity.startActivity(new Intent(AdapterProduct.this.activity, DownloadActivity.class));
                } else if (AdapterProduct.this.listType == 2) {
                    AdapterProduct.this.activity.startActivity(new Intent(AdapterProduct.this.activity, DownloaderActivity.class).putExtra("modelProduct", AdapterProduct.this.modelProductList.get(i)));
                } else {
                    AdapterProduct.this.activity.startActivity(new Intent(AdapterProduct.this.activity, ProductActivity.class).putExtra("modelProduct", AdapterProduct.this.modelProductList.get(i)));
                }
            }
        });
    }

    public int getItemCount() {
        return this.modelProductList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemDescription;
        ImageView itemImage;
        TextView itemPrice;
        TextView itemTitle;

        public MyViewHolder(View view) {
            super(view);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);
            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
            this.itemPrice = (TextView) view.findViewById(R.id.itemPrice);
            this.itemDescription = (TextView) view.findViewById(R.id.itemDescription);
            this.itemImage.setClipToOutline(true);
        }
    }
}
