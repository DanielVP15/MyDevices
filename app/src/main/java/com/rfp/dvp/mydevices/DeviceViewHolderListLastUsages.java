package com.rfp.dvp.mydevices;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by rfpereira on 05/02/2018.
 */

public class DeviceViewHolderListLastUsages extends RecyclerView.ViewHolder {

    final TextView userName;
    final TextView userTime;


    public DeviceViewHolderListLastUsages(View itemView) {
        super(itemView);

        userName = (TextView) itemView.findViewById(R.id.name_user);
        userTime = (TextView) itemView.findViewById(R.id.time_user);
    }
}
