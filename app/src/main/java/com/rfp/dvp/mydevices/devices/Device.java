package com.rfp.dvp.mydevices.devices;

/**
 * Created by rfpereira on 23/01/2018.
 */

public class Device {

    String model;
    String id;
    boolean active;
    String user;

    public  Device(){

    }

    public Device(String model, String id, String user) {

        this.model = model;
        this.id = id;

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

    public void setId(String id){
        this.id = id;
    }

}
