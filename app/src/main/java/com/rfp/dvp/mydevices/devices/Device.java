package com.rfp.dvp.mydevices.devices;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rfpereira on 23/01/2018.
 */

public class Device implements Parcelable {

    final String model;
    final String id;
    //final boolean status;
    final String user;

    public Device(String model, String id,
                 boolean status, String user) {

        this.model = model;
        this.id = id;
    //    this.status = status;
        this.user = user;
    }

    public String getModel() {
        return model;
    }

    public String getId() {
        return id;
    }

    //public Boolean getStatus() { return status;   }

    public String getUser() {
        return user;
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
        //status = in.readString();
        user = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(model);
        parcel.writeString(id);
        //parcel.writeBooleanArray(status);
        parcel.writeString(user);
    }
}
