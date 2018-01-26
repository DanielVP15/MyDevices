package com.rfp.dvp.mydevices;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rfp.dvp.mydevices.commons.DeviceExtras;
import com.rfp.dvp.mydevices.devices.Device;
import com.rfp.dvp.mydevices.utils.ItemClickListener;

import java.util.List;

import static android.R.color.holo_green_dark;
import static android.R.color.white;

/**
 * Created by rfpereira on 23/01/2018.
 */

public class DeviceAdapter extends RecyclerView.Adapter{

    private List<Device> devices;
    private Context context;

    public static final String AVAILABLE = "Disponível";
    public static final String UNAVAILABLE = "Indisponível";
    public static final String USED = "Ultimo uso: ";
    public static final String USING = "Em uso: ";

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

        final DeviceViewHolder holder = (DeviceViewHolder) viewHolder;

        final Device device  = devices.get(position) ;

        holder.model.setText(device.getModel());

        switch (device.getModel()){
            case DeviceExtras.TAG_A5:
                holder.image.setImageResource(R.drawable.a5);
                break;
            case DeviceExtras.TAG_K4:
                holder.image.setImageResource(R.drawable.k4);
                break;
            case DeviceExtras.TAG_G1:
                holder.image.setImageResource(R.drawable.g1);
                break;
            case DeviceExtras.TAG_XA:
                holder.image.setImageResource(R.drawable.xa);
                break;
        }

        getStatusInformation(holder,device);


        holder.buttonDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                device.setStatus(false);
                DeviceAdapter.this.notifyItemChanged(position);
            }
        });

        holder.buttonOffDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                device.setStatus(true);
                DeviceAdapter.this.notifyItemChanged(position);
            }
        });


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick){
                    Toast.makeText(context, "Long Click: "+ devices.get(position), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,devices.get(position).getModel(), Toast.LENGTH_SHORT).show();
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



    public void getStatusInformation(DeviceViewHolder holder, Device device){

        String[] userNameArray = device.getUser().split(" ");

        if (device.getStatus()){

            holder.status.setText(AVAILABLE);
            holder.status.setTextColor(Color.GREEN);

            holder.supportStatusUser.setText(USED);

            holder.statusUser.setText(userNameArray[0]);
            holder.statusUser.setTextColor(Color.BLACK);

            holder.lastNameStatusUser.setText(userNameArray[1]);
            holder.lastNameStatusUser.setTextColor(Color.BLACK);

            holder.buttonDevice.setVisibility(View.VISIBLE);
            holder.buttonOffDevice.setVisibility(View.GONE);

        }else{

            holder.status.setText(UNAVAILABLE);
            holder.status.setTextColor(Color.RED);

            holder.supportStatusUser.setText(USING);

            holder.statusUser.setText(userNameArray[0]);
            holder.statusUser.setTextColor(Color.BLACK);

            holder.lastNameStatusUser.setText(userNameArray[1]);
            holder.lastNameStatusUser.setTextColor(Color.BLACK);

            holder.buttonDevice.setVisibility(View.GONE);
            holder.buttonOffDevice.setVisibility(View.VISIBLE);

        }

    }

    private void updateListItem(int position) {
        notifyItemChanged(position);
    }
}
