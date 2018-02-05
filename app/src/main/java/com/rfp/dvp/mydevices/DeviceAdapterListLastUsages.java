package com.rfp.dvp.mydevices;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rfp.dvp.mydevices.commons.Firebase;
import com.rfp.dvp.mydevices.objects.Device;
import com.rfp.dvp.mydevices.objects.Usage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rfpereira on 05/02/2018.
 */

public class DeviceAdapterListLastUsages extends RecyclerView.Adapter {

    private List<Usage> usages;
    private Context context;

    public DeviceAdapterListLastUsages(List<Usage> usages, Context context) {
        this.usages = usages;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.device_card_list_last_usages, parent, false);
        DeviceViewHolderListLastUsages holderListLastUsages = new DeviceViewHolderListLastUsages(view);
        return holderListLastUsages;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        final DeviceViewHolderListLastUsages holderListLastUsages = (DeviceViewHolderListLastUsages) viewHolder;

        Usage usage = usages.get(position);
        holderListLastUsages.userName.setText(usage.getDeviceModel());
        holderListLastUsages.userTime.setText(usage.getDeviceId());


    }

    @Override
    public int getItemCount() {
        return usages.size();
    }
}
