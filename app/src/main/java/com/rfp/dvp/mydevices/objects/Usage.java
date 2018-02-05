package com.rfp.dvp.mydevices.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dvpires on 29/01/2018.
 */

public class Usage implements Parcelable {


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

    protected Usage(Parcel in) {
        user = in.readString();
        deviceModel = in.readString();
        deviceId = in.readString();
        returned = in.readByte() != 0;
        start = in.readString();
        end = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user);
        dest.writeString(deviceModel);
        dest.writeString(deviceId);
        dest.writeByte((byte) (returned ? 1 : 0));
        dest.writeString(start);
        dest.writeString(end);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Usage> CREATOR = new Creator<Usage>() {
        @Override
        public Usage createFromParcel(Parcel in) {
            return new Usage(in);
        }

        @Override
        public Usage[] newArray(int size) {
            return new Usage[size];
        }
    };

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
