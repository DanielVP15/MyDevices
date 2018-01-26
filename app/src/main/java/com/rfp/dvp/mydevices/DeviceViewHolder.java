package com.rfp.dvp.mydevices;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rfp.dvp.mydevices.utils.ItemClickListener;

/**
 * Created by rfpereira on 23/01/2018.
 */

public class DeviceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    final TextView model;
    final TextView status;
    final TextView statusUser;
    final TextView lastNameStatusUser;
    final TextView supportStatusUser;
    final ImageView image;
    final Button buttonDevice;
    final Button buttonOffDevice;
    //final TextView id;
    //final TextView active;
    //final TextView user;

    private ItemClickListener itemClickListener;

    public DeviceViewHolder(View itemView) {
        super(itemView);

        //nome = (TextView) itemView.findViewById(R.id.item_livro_nome);
        model = (TextView) itemView.findViewById(R.id.txt_model);
        status = (TextView) itemView.findViewById(R.id.txt_status);
        statusUser = (TextView) itemView.findViewById(R.id.txt_status_user);
        lastNameStatusUser = (TextView) itemView.findViewById(R.id.txt_last_name_status_user);
        supportStatusUser = (TextView) itemView.findViewById(R.id.txt_support_status_user);
        image = (ImageView) itemView.findViewById(R.id.image);
        buttonDevice = (Button) itemView.findViewById(R.id.button_device);
        buttonOffDevice = (Button) itemView.findViewById(R.id.button_off_device);


        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }

    @Override
    public boolean onLongClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),true);
        return true;
    }
}
