package com.mgsofttech.ddmods.model;

public class ModelAppVersion {
    String animation;
    boolean blocked;
    boolean button;
    String buttonLink;
    String buttonTitle;
    String description;
    String title;
    String version;

    public ModelAppVersion() {
    }

    public ModelAppVersion(boolean z, String str, String str2, String str3, String str4, boolean z2, String str5, String str6) {
        this.blocked = z;
        this.version = str;
        this.title = str2;
        this.animation = str4;
        this.description = str3;
        this.button = z2;
        this.buttonTitle = str5;
        this.buttonLink = str6;
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

    public String getAnimation() {
        return this.animation;
    }

    public void setAnimation(String str) {
        this.animation = str;
    }

    public boolean isBlocked() {
        return this.blocked;
    }

    public void setBlocked(boolean z) {
        this.blocked = z;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
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

}
