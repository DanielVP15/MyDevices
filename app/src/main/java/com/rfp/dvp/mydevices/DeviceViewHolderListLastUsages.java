package com.rfp.dvp.mydevices;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by rfpereira on 05/02/2018.
 */

public class DeviceViewHolderListLastUsages extends RecyclerView.ViewHolder {

    final TextView userName;
    final TextView userStartTime;
    final TextView userEndTime;


    public DeviceViewHolderListLastUsages(View itemView) {
        super(itemView);

        userName = (TextView) itemView.findViewById(R.id.name_user);
        userStartTime = (TextView) itemView.findViewById(R.id.start_time_user);
        userEndTime = (TextView) itemView.findViewById(R.id.end_time_user);
    }
}
