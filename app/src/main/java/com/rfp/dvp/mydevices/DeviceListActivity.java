package com.rfp.dvp.mydevices;


import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.rfp.dvp.mydevices.commons.DeviceExtras;
import com.rfp.dvp.mydevices.commons.Firebase;
import com.rfp.dvp.mydevices.objects.Device;

import java.util.ArrayList;
import java.util.List;

public class DeviceListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DeviceAdapter mAdapter;
    RecyclerView.LayoutManager layout;
    private DatabaseReference mDatabase;
    List<Device> deviceList = new ArrayList<>();
    private boolean isLoaded;


    private AlertDialog.Builder alert;
    private AlertDialog alt;
    private boolean isAlertCreate;

    private int contDeviceListSize;

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

        if (alert == null) {
            createAlertDialog();
        }
        isLoaded = false;

        contDeviceListSize = 0;

        initRecycleView();
    }

    private void initRecycleView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        mAdapter = new DeviceAdapter(deviceList, this);
        recyclerView.setAdapter(mAdapter);
        layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);


    }

    private void createAlertDialog() {
        if (!isAlertCreate) {
            isAlertCreate = true;
            alert = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_alertdialog_layout, null);
            alert.setView(dialogView);
            alert.setCancelable(false);
            alt = alert.create();
            alt.show();
        }
    }

    private void dismissProgressDialog() {
        if (alt != null && alt.isShowing()) {
            alt.dismiss();
            isAlertCreate = false;
        }
    }


    private void loadDevices() {
        isLoaded = true;
        mDatabase.child(DeviceExtras.TAG_DEVICES).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists() && deviceList.size() <= contDeviceListSize) {
                    Device device = dataSnapshot.getValue(Device.class);
                    if (device != null) {
                        Log.e("teste", "add");
                        device.setId(dataSnapshot.getKey());
                        deviceList.add(device);
                        contDeviceListSize++;
                    }
                    mAdapter.notifyDataSetChanged();
                    dismissProgressDialog();
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
                Log.e("teste", "removed");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.e("teste", "moved");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("teste", "cancelled");
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!isLoaded) {
            loadDevices();
        }

    }

    public void callLoginActivity() {
        Intent it = new Intent(this, LoginActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(it);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Firebase.logout();
        callLoginActivity();

    }
}
