package com.rfp.dvp.mydevices;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rfp.dvp.mydevices.commons.DeviceExtras;
import com.rfp.dvp.mydevices.commons.Firebase;
import com.rfp.dvp.mydevices.objects.Device;
import com.rfp.dvp.mydevices.objects.User;

import java.util.ArrayList;
import java.util.List;

public class DeviceListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DeviceAdapter mAdapter;
    RecyclerView.LayoutManager layout;
    private DatabaseReference mDatabase;
    List<Device> deviceList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initFirebase();
        init();
        Log.e("teste", "oncreate");

    }

    private void initFirebase() {
        mDatabase = Firebase.getDatabase();
    }

    private void init() {
        initRecycleView();
    }

    private void initRecycleView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        mAdapter = new DeviceAdapter(deviceList, this);
        recyclerView.setAdapter(mAdapter);
        //recyclerView.setHasFixedSize(true);
        layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
    }

    private void loadDevices() {

        mDatabase.child(DeviceExtras.TAG_DEVICES).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    Device device = dataSnapshot.getValue(Device.class);
                    if (device != null) {
                        Log.e("teste", "add");
                        device.setId(dataSnapshot.getKey());
                        deviceList.add(device);
                    }

                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                Device device = dataSnapshot.getValue(Device.class);
                if (device != null) {
                    device.setId(dataSnapshot.getKey());
                    Log.e("teste", "changed");
                }
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        loadDevices();
    }
}
