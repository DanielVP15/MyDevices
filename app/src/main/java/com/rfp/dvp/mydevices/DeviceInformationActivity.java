package com.rfp.dvp.mydevices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.rfp.dvp.mydevices.commons.DeviceExtras;
import com.rfp.dvp.mydevices.objects.Device;

public class DeviceInformationActivity extends AppCompatActivity {

    private Device device;

    private TextView modelInformation;
   // private TextView userInformation;
    private TextView supportUserInformation;
    private TextView statusInformation;
    private TextView idInformation;
    private ImageView imageView;

    public static final String AVAILABLE = "Disponível";
    public static final String UNAVAILABLE = "Indisponível";
    public static final String USED = "Ultimo uso: ";
    public static final String USING = "Em uso: ";

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
        //userInformation = (TextView) findViewById(R.id.user_information_text);
        supportUserInformation = (TextView) findViewById(R.id.support_user_information_text);

        idInformation = (TextView) findViewById(R.id.id_information_text);
        imageView = (ImageView) findViewById(R.id.device_information_image);

        getDeviceInformation();

    }

    private void getDeviceInformation(){

        device = getIntent().getExtras().getParcelable(DeviceExtras.TAG_DEVICE);

        modelInformation.setText(device.getModel());
        idInformation.setText(device.getId());

        if (device.getStatus()){
            statusInformation.setText(AVAILABLE);
            supportUserInformation.setText(USED);
            /*userInformation.setText(device.getUser());*/

        }else{
            statusInformation.setText(UNAVAILABLE);
            supportUserInformation.setText(USING);
           /* userInformation.setText(device.getUser());*/
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
        }
    }





}
