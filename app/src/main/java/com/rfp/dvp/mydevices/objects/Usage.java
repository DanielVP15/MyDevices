package com.rfp.dvp.mydevices.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dvpires on 29/01/2018.
 */

public class Usage {


    private String user;
    private String deviceModel;
    private String deviceId;
    private boolean returned;
    private String start;
    private String end;

    public Usage() {
    }

    public Usage(String user, String deviceModel, String deviceId, String start, boolean returned) {
        this.user = user;
        this.deviceModel = deviceModel;
        this.deviceId = deviceId;
        this.start = start;
        this.returned = returned;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", user);
        result.put("modelo", deviceModel);
        result.put("ID", deviceId);
        result.put("inicio", start);
        result.put("fim", end);
        result.put("returned", returned);
        return result;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean getReturned() {
        return returned;
    }

    public void isReturned() {
        returned = true;
    }


}
