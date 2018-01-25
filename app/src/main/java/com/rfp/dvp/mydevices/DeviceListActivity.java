package com.rfp.dvp.mydevices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rfp.dvp.mydevices.devices.Device;
import com.rfp.dvp.mydevices.devices.User;

import java.util.ArrayList;
import java.util.List;

public class DeviceListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        fireBaseCheck();
        init();

    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        Device a5 = new Device("Galaxy A5", "06101701", "rodrigo");
        Device k4 = new Device("LG K4", "06101702", "rafael");
        Device g1 = new Device("Motorola G1", "06101704", "daniel");
        Device xa = new Device("Sony Xperia XA", "06101703", "calazans");
        List<Device> device = new ArrayList<>();
        device.add(a5);
        device.add(k4);
        device.add(g1);
        device.add(xa);
        writeDevices(device);
        recyclerView.setAdapter(new DeviceAdapter(device, this));

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
    }

    public void fireBaseCheck() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        writeNewUser(user.getUid(),user.getDisplayName(),user.getEmail());
    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);
        mDatabase.child("users").child(userId).setValue(user);
    }

    private void writeDevices(List<Device> devices){
        for(Device device : devices){
            String deviceId = device.getId();
            device.setId(null);
            mDatabase.child("devices").child(deviceId).setValue(device);
        }

    }
}
