package com.vishavlakhtia.nearby.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vishavlakhtia.nearby.R;
import com.vishavlakhtia.nearby.interFace.itemClickListner;

public class postedTaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView taskTitle;
    itemClickListner listner;
    public postedTaskHolder(@NonNull View itemView) {
        super(itemView);
        taskTitle = itemView.findViewById(R.id.posted_text);
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
