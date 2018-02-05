package com.rfp.dvp.mydevices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.rfp.dvp.mydevices.commons.DeviceExtras;
import com.rfp.dvp.mydevices.objects.Device;

import java.util.ArrayList;
import java.util.List;

public class DeviceInformationActivity extends AppCompatActivity {

    private Device device;

    private TextView modelInformation;
    private TextView userInformation;
    private TextView supportUserInformation;
    private TextView statusInformation;
    private TextView idInformation;
    private ImageView imageView;

    private RecyclerView recyclerView;
    private DeviceAdapterListLastUsages mAdapter;
    RecyclerView.LayoutManager layout;
    List<Device> deviceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_information);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        init();
    }

    private void init() {

        modelInformation = (TextView) findViewById(R.id.model_information_text);
        statusInformation = (TextView) findViewById(R.id.status_information_text);
        userInformation = (TextView) findViewById(R.id.user_information_text);
        supportUserInformation = (TextView) findViewById(R.id.support_user_information_text);

        idInformation = (TextView) findViewById(R.id.id_information_text);
        imageView = (ImageView) findViewById(R.id.device_information_image);

        getDeviceInformation();

    }

    private void createListUsages(Device device) {
        recyclerView = (RecyclerView) findViewById(R.id.last_usages_list_recycler);
        //deviceList.add(device);

        mAdapter = new DeviceAdapterListLastUsages(device.getLastUsages(), this);
        recyclerView.setAdapter(mAdapter);

        layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
    }



    private void getDeviceInformation(){

        device = getIntent().getExtras().getParcelable(DeviceExtras.TAG_DEVICE);

        modelInformation.setText(device.getModel());
        idInformation.setText(device.getId());

        createListUsages(device);

        if (device.getStatus()){
            statusInformation.setText(getResources().getString(R.string.available));
            supportUserInformation.setText(getResources().getString(R.string.used_text));
            userInformation.setText(device.getCurrentUser());

        }else{
            statusInformation.setText(getResources().getString(R.string.unavailable));
            supportUserInformation.setText(getResources().getString(R.string.using_text));
            userInformation.setText(device.getCurrentUser());
        }

        switch (device.getModel()){
            case DeviceExtras.TAG_A5:
                imageView.setImageResource(R.drawable.a5);
                break;
            case DeviceExtras.TAG_K4:
                imageView.setImageResource(R.drawable.k4);
                break;
            case DeviceExtras.TAG_G1:
                imageView.setImageResource(R.drawable.g1);
                break;
            case DeviceExtras.TAG_XA:
                imageView.setImageResource(R.drawable.xa);
                break;
            case DeviceExtras.TAG_S3_MINI:
                imageView.setImageResource(R.drawable.s3mini);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }




}
