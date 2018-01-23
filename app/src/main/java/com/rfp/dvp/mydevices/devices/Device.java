package com.rfp.dvp.mydevices.devices;

/**
 * Created by rfpereira on 23/01/2018.
 */

public class Device {

    final String model;
    final String id;
    final boolean active;
    final String user;

    public Device(String model, String id,
                 boolean active, String user) {

        this.model = model;
        this.id = id;
        this.active = active;
        this.user = user;
    }

    public String getModel() {
        return model;
    }

    public String getId() {
        return id;
    }

    public Boolean getActive() {
        return active;
    }

    public String getUser() {
        return user;
    }
}
