package com.mgsofttech.ddmods.model;

import java.util.ArrayList;

public class ModelServer {
    String animation;
    String blockDescription;
    String blockTitle;
    boolean blocked;
    boolean button;
    String buttonLink;
    String buttonTitle;
    ArrayList<ModelAppVersion> modelAppVersionList;
    ArrayList<ModelProduct> modelProductList;

    public ModelServer() {
    }

    public ModelServer(boolean z, String str, String str2, String str3, boolean z2, String str4, String str5, ArrayList<ModelProduct> arrayList) {
        this.blocked = z;
        this.animation = str3;
        this.blockTitle = str;
        this.blockDescription = str2;
        this.button = z2;
        this.buttonTitle = str4;
        this.buttonLink = str5;
        this.modelProductList = arrayList;
    }

    public boolean isBlocked() {
        return this.blocked;
    }

    public void setBlocked(boolean z) {
        this.blocked = z;
    }

    public String getAnimation() {
        return this.animation;
    }

    public void setAnimation(String str) {
        this.animation = str;
    }

    public String getBlockTitle() {
        return this.blockTitle;
    }

    public void setBlockTitle(String str) {
        this.blockTitle = str;
    }

    public String getBlockDescription() {
        return this.blockDescription;
    }

    public void setBlockDescription(String str) {
        this.blockDescription = str;
    }

    public boolean isButton() {
        return this.button;
    }

    public void setButton(boolean z) {
        this.button = z;
    }

    public String getButtonTitle() {
        return this.buttonTitle;
    }

    public void setButtonTitle(String str) {
        this.buttonTitle = str;
    }

    public String getButtonLink() {
        return this.buttonLink;
    }

    public void setButtonLink(String str) {
        this.buttonLink = str;
    }

    public ArrayList<ModelProduct> getModelProductList() {
        return this.modelProductList;
    }

    public void setModelProductList(ArrayList<ModelProduct> arrayList) {
        this.modelProductList = arrayList;
    }

    public ArrayList<ModelAppVersion> getModelAppVersionList() {
        return this.modelAppVersionList;
    }

    public void setModelAppVersionList(ArrayList<ModelAppVersion> arrayList) {
        this.modelAppVersionList = arrayList;
    }
}
