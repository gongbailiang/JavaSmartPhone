package com.mamarecipe.model;

import com.google.gson.Gson;

/**
 * Created by Jeremiah on 7/26/15.
 */
public class UserPO {
    private long userID;
    private String userName;
    private String userPass;
    private String ipAddress;

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
