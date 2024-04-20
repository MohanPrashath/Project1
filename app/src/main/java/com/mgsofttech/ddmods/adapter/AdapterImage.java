package com.mgsofttech.ddmods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.mgsofttech.ddmods.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import java.util.ArrayList;

public class AdapterImage extends SliderViewAdapter<AdapterImage.MyViewHolder> {
    Context context;
    ArrayList<String> modelImageList;

    public AdapterImage(Context context2, ArrayList<String> arrayList) {
        this.context = context2;
        this.modelImageList = arrayList;
    }

    public void renewItems(ArrayList<String> arrayList) {
        this.modelImageList = arrayList;
        notifyDataSetChanged();
    }

    public void deleteItem(int i) {
        this.modelImageList.remove(i);
        notifyDataSetChanged();
    }

    public void addItem(String str) {
        this.modelImageList.add(str);
        notifyDataSetChanged();
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image, (ViewGroup) null));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        ((RequestBuilder) ((RequestBuilder) Glide.with(this.context).load(this.modelImageList.get(i)).placeholder((int) R.drawable.bg_placeholder_rec)).centerCrop()).into(myViewHolder.itemImage);
    }

    public int getCount() {
        return this.modelImageList.size();
    }

    static class MyViewHolder extends ViewHolder {
        ImageView itemImage;

        public MyViewHolder(View view) {
            super(view);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);
        }
    }
}
