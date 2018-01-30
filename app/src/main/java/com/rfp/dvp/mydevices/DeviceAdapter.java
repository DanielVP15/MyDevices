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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.rfp.dvp.mydevices.commons.DeviceExtras;
import com.rfp.dvp.mydevices.commons.Firebase;
import com.rfp.dvp.mydevices.objects.Device;
import com.rfp.dvp.mydevices.objects.User;
import com.rfp.dvp.mydevices.objects.Uso;
import com.rfp.dvp.mydevices.utils.ItemClickListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by rfpereira on 23/01/2018.
 */

public class DeviceAdapter extends RecyclerView.Adapter {

    private List<Device> devices;
    private List<Uso> usages;
    private Context context;
    private DatabaseReference mDatabase;
    private final Lock mutexAdd = new ReentrantLock();
    private final Lock mutexInterator = new ReentrantLock();

    public static final String AVAILABLE = "Disponível";
    public static final String UNAVAILABLE = "Indisponível";
    public static final String USED = "Ultimo uso:";
    public static final String USING = "Em uso:";

    public DeviceAdapter(List<Device> devices, Context context) {
        this.devices = devices;
        this.context = context;
        usages = new ArrayList<>();
        mDatabase = Firebase.getDatabase();
        loadUsages();

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

        final Device device = devices.get(position);

        holder.model.setText(device.getModel());

        switch (device.getModel()) {
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

        getStatusInformation(holder, device);


        holder.buttonDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                device.setStatus(false);
                updateDevice(device);
                startUsage(device);
                DeviceAdapter.this.notifyItemChanged(position);
            }
        });

        holder.buttonOffDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                device.setStatus(true);
                updateDevice(device);
                finishUse(device);
                DeviceAdapter.this.notifyItemChanged(position);
            }
        });


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    Toast.makeText(context, "Long Click: " + devices.get(position), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, devices.get(position).getModel(), Toast.LENGTH_SHORT).show();
                    callDeviceInformationActivity(devices.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public void callDeviceInformationActivity(Device device) {

        Intent it = new Intent(context, DeviceInformationActivity.class);
        it.putExtra(DeviceExtras.TAG_DEVICE, device);
        context.startActivity(it);

    }


    public void getStatusInformation(DeviceViewHolder holder, Device device) {

        String[] userNameArray = device.getCurrentUser().split(" ");

        User mUser = Firebase.getUser();

        if (device.getStatus()) {

            holder.status.setText(AVAILABLE);
            holder.status.setTextColor(Color.GREEN);

            holder.supportStatusUser.setText(USED);

            holder.statusUser.setText(userNameArray[0]);
            holder.statusUser.setTextColor(Color.BLACK);

            holder.lastNameStatusUser.setText(userNameArray[1]);
            holder.lastNameStatusUser.setTextColor(Color.BLACK);

            holder.buttonDevice.setVisibility(View.VISIBLE);
            holder.buttonOffDevice.setVisibility(View.GONE);

        } else {

            holder.status.setText(UNAVAILABLE);
            holder.status.setTextColor(Color.RED);

            holder.supportStatusUser.setText(USING);

            holder.statusUser.setText(userNameArray[0]);
            holder.statusUser.setTextColor(Color.BLACK);

            holder.lastNameStatusUser.setText(userNameArray[1]);
            holder.lastNameStatusUser.setTextColor(Color.BLACK);
            if (mUser.getName().equals(device.getCurrentUser())) {
                holder.buttonDevice.setVisibility(View.GONE);
                holder.buttonOffDevice.setVisibility(View.VISIBLE);
            }else{
                holder.buttonDevice.setVisibility(View.GONE);
                holder.buttonOffDevice.setVisibility(View.GONE);
            }
        }

    }

    private void updateListItem(int position) {
        notifyItemChanged(position);
    }

    private void updateDevice(Device device) {
        mDatabase.child("devices").push();
        User mUser = Firebase.getUser();
        device.setCurrentUserID(mUser.getId());
        if (mUser.getName() != null) {
            device.setCurrentUser(mUser.getName());
        } else {
            device.setCurrentUserID(mUser.getEmail());
        }
        Map<String, Object> deviceValue = device.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/devices/" + device.getId() + "/", deviceValue);

        mDatabase.updateChildren(childUpdates);
    }

    private void startUsage(Device device) {
        Date data = new Date();
        Uso usage = new Uso(Firebase.getUser().getEmail(), device.getModel(), device.getId(), data.toString(), false);
        mDatabase.child(DeviceExtras.TAG_USAGES).child(data.toString()).setValue(usage);
    }

    private void finishUse(Device device) {
        Date data = new Date();
        mutexAdd.lock();
        for (Uso usage : usages) {
            if (!usage.getReturned() && usage.getDeviceId().equals(device.getId())) {
                usage.setFim(data.toString());
                usage.isReturned();
                Map<String, Object> usageValue = usage.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/usos/" + usage.getInicio() + "/", usageValue);
                Log.e("teste", "finishUse");
                mDatabase.updateChildren(childUpdates);
            }
        }
        mutexAdd.unlock();

    }

    public void updateUsageList(Uso usage) {
        int fim = usages.size();
        mutexInterator.lock();
        for (int i = 0; i < fim; ++i) {
            Uso mUsage = usages.get(i);
            if (mUsage.getInicio() == usage.getInicio()) {
                usages.remove(usages.get(i));
                usages.add(usage);
            }
        }
        mutexInterator.unlock();

    }

    public void loadUsages() {
        mDatabase.child(DeviceExtras.TAG_USAGES).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    Uso usageAdd = dataSnapshot.getValue(Uso.class);
                    mutexAdd.lock();
                    usages.add(usageAdd);
                    mutexAdd.unlock();
                    Log.e("teste", "addU");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    Uso usage = dataSnapshot.getValue(Uso.class);
                    mutexAdd.lock();
                    updateUsageList(usage);
                    Log.e("teste", "changedU");
                    mutexAdd.unlock();

                }
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
}
