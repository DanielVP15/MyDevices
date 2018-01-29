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

public class Uso {


    private String user;
    private String deviceModel;
    private String deviceId;
    private boolean returned;
    private String inicio;
    private String fim;

    public Uso() {
    }

    public Uso(String user, String deviceModel, String deviceId, String inicio, boolean returned) {
        this.user = user;
        this.deviceModel = deviceModel;
        this.deviceId = deviceId;
        this.inicio = inicio;
        this.returned = returned;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", user);
        result.put("modelo", deviceModel);
        result.put("ID", deviceId);
        result.put("inicio", inicio);
        result.put("fim", fim);
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

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
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
