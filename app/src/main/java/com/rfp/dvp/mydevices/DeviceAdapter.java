package com.rfp.dvp.mydevices;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rfp.dvp.mydevices.commons.DeviceExtras;
import com.rfp.dvp.mydevices.devices.Device;
import com.rfp.dvp.mydevices.utils.ItemClickListener;

import java.util.List;

/**
 * Created by rfpereira on 23/01/2018.
 */

public class DeviceAdapter extends RecyclerView.Adapter {

    private List<Device> devices;
    private Context context;

    private static final String TAG_DEVICE_A5 = "Galaxy A5";
    private static final String TAG_DEVICE_K4 = "LG K4";
    private static final String TAG_DEVICE_G1 = "Motorola G1";
    private static final String TAG_DEVICE_XA = "Sony Xperia XA";

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

        if(device.getModel().equals(TAG_DEVICE_A5)){
            holder.image.setImageResource(R.drawable.a5);
        }else if(device.getModel().equals(TAG_DEVICE_K4)){
            holder.image.setImageResource(R.drawable.k4);
        }else if(device.getModel().equals(TAG_DEVICE_G1)){
            holder.image.setImageResource(R.drawable.g1);
        }else if(device.getModel().equals(TAG_DEVICE_XA)){
            holder.image.setImageResource(R.drawable.xa);
        }

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick){
                    Toast.makeText(context, "Long Click: "+ devices.get(position), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, " "+ devices.get(position).getModel(), Toast.LENGTH_SHORT).show();

                    callDeviceInformationActivity(devices.get(position));

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public void callDeviceInformationActivity(Device device){

        Intent it = new Intent(context, DeviceInformationActivity.class);
        it.putExtra(DeviceExtras.TAG_DEVICE, device);
        context.startActivity(it);

    }
}
