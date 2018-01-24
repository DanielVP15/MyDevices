package com.rfp.dvp.mydevices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.rfp.dvp.mydevices.commons.DeviceExtras;
import com.rfp.dvp.mydevices.devices.Device;

public class DeviceInformationActivity extends AppCompatActivity {

    private Device device;

    private TextView modelInformation;
    private TextView userInformation;

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
        userInformation = (TextView) findViewById(R.id.user_information_text);

        getDeviceInformation();

    }

    private void getDeviceInformation(){

        device = getIntent().getExtras().getParcelable(DeviceExtras.TAG_DEVICE);

        modelInformation.setText(device.getModel());
        userInformation.setText(device.getUser());
    }





}
