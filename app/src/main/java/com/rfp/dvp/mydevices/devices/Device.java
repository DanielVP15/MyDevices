package com.rfp.dvp.mydevices.devices;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rfpereira on 23/01/2018.
 */

public class Device implements Parcelable {

    private String model;
    private String id;
    private boolean status;
    private String user;

    public Device(String model, String id,
                 boolean status, String user) {

        this.model = model;
        this.id = id;
        this.status = status;
        this.user = user;
    }

    public String getModel() {
        return model;
    }

    public String getId() {
        return id;
    }

    public boolean getStatus() { return status;   }

    public String getUser() {
        return user;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    public void setUser(String user){
        this.user = user;
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
        user = in.readString();
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
        parcel.writeString(user);
        parcel.writeInt(status ? 1 : 0);
    }
}
