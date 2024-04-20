package com.mgsofttech.ddmods.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ModelUser  implements Serializable {


    ArrayList<String> userListBookmark;
    ArrayList<String> userListFollower;
    ArrayList<String> userListFollowing;
    ArrayList<String> userListLike;
    ArrayList<String> userListQuote;
    String userBio;
    String userBlockedReason;
    String userDOB;
    String userEmail;
    String userFullName;
    String userGender;
    String userID;
    String userImage;
    String userLocation;
    String userMobile;
    String userName;
    String userNumber;
    boolean userVerified;
    boolean userBlocked;
    Date timestamp;
    int phoneNumber, userPoints;



    public ModelUser(ArrayList<String> userListBookmark, ArrayList<String> userListFollower, ArrayList<String> userListFollowing, ArrayList<String> userListLike, ArrayList<String> userListQuote, String userBio, String userBlockedReason, String userDOB, String userEmail, String userFullName, String userGender, String userID, String userImage, String userLocation, String userMobile, String userName, String userNumber, boolean userVerified, boolean userBlocked, Date timestamp, int phoneNumber, int userPoints) {
        this.userListBookmark = userListBookmark;
        this.userListFollower = userListFollower;
        this.userListFollowing = userListFollowing;
        this.userListLike = userListLike;
        this.userListQuote = userListQuote;
        this.userBio = userBio;
        this.userBlockedReason = userBlockedReason;
        this.userDOB = userDOB;
        this.userEmail = userEmail;
        this.userFullName = userFullName;
        this.userGender = userGender;
        this.userID = userID;
        this.userImage = userImage;
        this.userLocation = userLocation;
        this.userMobile = userMobile;
        this.userName = userName;
        this.userNumber = userNumber;
        this.userVerified = userVerified;
        this.userBlocked = userBlocked;
        this.timestamp = timestamp;
        this.phoneNumber = phoneNumber;
        this.userPoints = userPoints;

    }

    public ModelUser() {

    }

    public ArrayList<String> getUserListBookmark() {
        return userListBookmark;
    }

    public void setUserListBookmark(ArrayList<String> userListBookmark) {
        this.userListBookmark = userListBookmark;
    }

    public ArrayList<String> getUserListFollower() {
        return userListFollower;
    }

    public void setUserListFollower(ArrayList<String> userListFollower) {
        this.userListFollower = userListFollower;
    }

    public ArrayList<String> getUserListFollowing() {
        return userListFollowing;
    }

    public void setUserListFollowing(ArrayList<String> userListFollowing) {
        this.userListFollowing = userListFollowing;
    }

    public ArrayList<String> getUserListLike() {
        return userListLike;
    }

    public void setUserListLike(ArrayList<String> userListLike) {
        this.userListLike = userListLike;
    }

    public ArrayList<String> getUserListQuote() {
        return userListQuote;
    }

    public void setUserListQuote(ArrayList<String> userListQuote) {
        this.userListQuote = userListQuote;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public void setUserBlockedReason(String userBlockedReason) {
        this.userBlockedReason = userBlockedReason;
    }

    public String getUserDOB() {
        return userDOB;
    }

    public void setUserDOB(String userDOB) {
        this.userDOB = userDOB;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public boolean isUserVerified() {
        return userVerified;
    }

    public void setUserVerified(boolean userVerified) {
        this.userVerified = userVerified;
    }

    public boolean isUserBlocked() {
        return this.userBlocked;
    }

    public void setUserBlocked(boolean z) {
        this.userBlocked = z;
    }

    public String getUserBlockedReason() {
        return this.userBlockedReason;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public ModelUser setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(int userPoints) {
        this.userPoints = userPoints;
    }




}