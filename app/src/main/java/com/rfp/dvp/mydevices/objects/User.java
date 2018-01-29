package com.rfp.dvp.mydevices.objects;

/**
 * Created by dvpires on 24/01/2018.
 */

public class User {

    private String id;
    private String email;
    private String name;
    private String device;

    public User() {
    }

    public User(String email,String name) {
        setEmail(email);
        setName(name);
        setDevice(null);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getDevice() {
        return device;
    }
}
