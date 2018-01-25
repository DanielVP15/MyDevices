package com.rfp.dvp.mydevices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rfp.dvp.mydevices.devices.Device;

import java.util.ArrayList;
import java.util.List;

public class DeviceListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        init();

    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        Device a5 = new Device("Galaxy A5","1",true,"Rodrigo Felippo");
        Device k4 = new Device("LG K4","2",true,"Rafael Brito");
        Device g1 = new Device("Motorola G1","3",false,"Daniel Pires");
        Device xa = new Device("Sony Xperia XA","4",true,"Gabriel Calazans");
        List<Device> device = new ArrayList<>();
        device.add(a5);
        device.add(k4);
        device.add(g1);
        device.add(xa);
        recyclerView.setAdapter(new DeviceAdapter(device,this));

        recyclerView.setHasFixedSize(true);
        layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
    }


}
