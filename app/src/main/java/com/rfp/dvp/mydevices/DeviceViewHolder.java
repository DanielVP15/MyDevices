package com.rfp.dvp.mydevices;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by rfpereira on 23/01/2018.
 */

public class DeviceViewHolder extends RecyclerView.ViewHolder {

    final TextView model;
    final ImageView image;
    //final TextView id;
    //final TextView active;
    //final TextView user;

    public DeviceViewHolder(View itemView) {
        super(itemView);

        //nome = (TextView) itemView.findViewById(R.id.item_livro_nome);
        model = (TextView) itemView.findViewById(R.id.txt_model);
        image = (ImageView) itemView.findViewById(R.id.image);
    }


}
