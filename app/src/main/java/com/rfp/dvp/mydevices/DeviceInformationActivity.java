package com.rfp.dvp.mydevices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.rfp.dvp.mydevices.commons.DeviceExtras;
import com.rfp.dvp.mydevices.devices.Device;

public class DeviceInformationActivity extends AppCompatActivity {

    private Device device;

    private TextView modelInformation;
    private TextView userInformation;
    private TextView idInformation;

    private ImageView imageView;

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
        idInformation = (TextView) findViewById(R.id.id_information_text);

        imageView = (ImageView) findViewById(R.id.device_information_image);

        getDeviceInformation();

    }

    private void getDeviceInformation(){

        device = getIntent().getExtras().getParcelable(DeviceExtras.TAG_DEVICE);

        modelInformation.setText(device.getModel());
        idInformation.setText(device.getId());
        userInformation.setText(device.getStatus()+"");

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
        }
    }





}
