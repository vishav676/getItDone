package com.vishavlakhtia.nearby.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vishavlakhtia.nearby.R;
import com.vishavlakhtia.nearby.interFace.itemClickListner;

public class TaskDisplayHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tv_title, tv_description, tv_price;
    itemClickListner listner;
    public TaskDisplayHolder(@NonNull View itemView) {
        super(itemView);
        tv_title = (TextView)itemView.findViewById(R.id.project_title);
        tv_description = (TextView)itemView.findViewById(R.id.project_description);
        tv_price = (TextView)itemView.findViewById(R.id.project_price);

    }

    public void setItemClickListner(itemClickListner listner)
    {
        this.listner = listner;
    }


    @Override
    public void onClick(View view) {
        listner.onClick(view,getAdapterPosition(),false);
    }
}
