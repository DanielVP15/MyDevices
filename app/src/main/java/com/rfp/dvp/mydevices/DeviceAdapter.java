package com.rfp.dvp.mydevices;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rfp.dvp.mydevices.devices.Device;

import java.util.List;

/**
 * Created by rfpereira on 23/01/2018.
 */

public class DeviceAdapter extends RecyclerView.Adapter {

    private List<Device> devices;
    private Context context;

    public DeviceAdapter(List<Device> devices, Context context) {
        this.devices = devices;
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.device_card, parent, false);
        DeviceViewHolder holder = new DeviceViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        DeviceViewHolder holder = (DeviceViewHolder) viewHolder;

        Device device  = devices.get(position) ;

        holder.model.setText(device.getModel());

        if(device.getModel().equals("Galaxy A5")){
            holder.image.setImageResource(R.drawable.a5);
        }else if(device.getModel().equals("LG K4")){
            holder.image.setImageResource(R.drawable.k4);
        }else if(device.getModel().equals("Motorola G1")){
            holder.image.setImageResource(R.drawable.g1);
        }else if(device.getModel().equals("Sony Xperia XA")){
            holder.image.setImageResource(R.drawable.xa);
        }

    }

    @Override
    public int getItemCount() {
        return devices.size();
    }
}
