package com.rfp.dvp.mydevices.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rfpereira on 23/01/2018.
 */

public class Device implements Parcelable {

    private String model;
    private String id;
    private boolean status;

    public Device(){

    }
    public Device(String model, String id) {

        this.model = model;
        this.id = id;
        this.status = false;
    }

    public String getModel() {
        return model;
    }

    public String getId() {
        return id;
    }

    public boolean getStatus() { return status;   }


    public void setModel(String model) {
        this.model = model;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    public static final Creator<Device> CREATOR = new Creator<Device>() {
        @Override
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };

    protected Device(Parcel in) {
        model = in.readString();
        id = in.readString();
        status  = (in.readInt() == 0) ? false : true;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(model);
        parcel.writeString(id);
        parcel.writeInt(status ? 1 : 0);
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("model", model);
        result.put("status", status);
        return result;
    }
}
