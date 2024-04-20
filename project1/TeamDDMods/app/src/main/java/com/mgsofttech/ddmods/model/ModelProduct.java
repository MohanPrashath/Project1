package com.mgsofttech.ddmods.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ModelProduct implements Serializable {
    int countView;
    String description;
    boolean free;
    String icon;
    String id;
    ArrayList<String> listBookmark;
    ArrayList<String> listComment;
    ArrayList<String> listImage;
    ArrayList<String> listLike;
    ArrayList<String> listLink;
    ArrayList<String> listPurchase;
    ArrayList<String> listSearch;
    ArrayList<String> listView;
    float price;
    String size;
    Date timestamp;
    Date timestampUpdated;
    String title;
    String type;

    public ModelProduct() {
    }

    public ModelProduct(Date date, Date date2, String str, String str2, boolean z, int i, float f, String str3, String str4, String str5, String str6, ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3, ArrayList<String> arrayList4, ArrayList<String> arrayList5, ArrayList<String> arrayList6, ArrayList<String> arrayList7, ArrayList<String> arrayList8) {
        this.timestamp = date;
        this.timestampUpdated = date2;
        this.countView = i;
        this.price = f;
        this.title = str3;
        this.free = z;
        this.type = str6;
        this.id = str;
        this.size = str2;
        this.description = str4;
        this.icon = str5;
        this.listImage = arrayList;
        this.listSearch = arrayList2;
        this.listView = arrayList3;
        this.listLike = arrayList4;
        this.listLink = arrayList7;
        this.listPurchase = arrayList8;
        this.listComment = arrayList5;
        this.listBookmark = arrayList6;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public int getCountView() {
        return this.countView;
    }

    public void setCountView(int i) {
        this.countView = i;
    }

    public boolean isFree() {
        return this.free;
    }

    public void setFree(boolean z) {
        this.free = z;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date date) {
        this.timestamp = date;
    }

    public Date getTimestampUpdated() {
        return this.timestampUpdated;
    }

    public void setTimestampUpdated(Date date) {
        this.timestampUpdated = date;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String str) {
        this.size = str;
    }

    public float getPrice() {
        return this.price;
    }

    public void setPrice(float f) {
        this.price = f;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String str) {
        this.icon = str;
    }

    public ArrayList<String> getListImage() {
        return this.listImage;
    }

    public void setListImage(ArrayList<String> arrayList) {
        this.listImage = arrayList;
    }

    public ArrayList<String> getListLink() {
        return this.listLink;
    }

    public void setListLink(ArrayList<String> arrayList) {
        this.listLink = arrayList;
    }

    public ArrayList<String> getListPurchase() {
        return this.listPurchase;
    }

    public void setListPurchase(ArrayList<String> arrayList) {
        this.listPurchase = arrayList;
    }

    public ArrayList<String> getListSearch() {
        return this.listSearch;
    }

    public void setListSearch(ArrayList<String> arrayList) {
        this.listSearch = arrayList;
    }

    public ArrayList<String> getListView() {
        return this.listView;
    }

    public void setListView(ArrayList<String> arrayList) {
        this.listView = arrayList;
    }

    public ArrayList<String> getListLike() {
        return this.listLike;
    }

    public void setListLike(ArrayList<String> arrayList) {
        this.listLike = arrayList;
    }

    public ArrayList<String> getListComment() {
        return this.listComment;
    }

    public void setListComment(ArrayList<String> arrayList) {
        this.listComment = arrayList;
    }

    public ArrayList<String> getListBookmark() {
        return this.listBookmark;
    }

    public void setListBookmark(ArrayList<String> arrayList) {
        this.listBookmark = arrayList;
    }
}
