package com.vishavlakhtia.nearby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vishavlakhtia.nearby.outline.tasks;

import java.util.HashMap;

public class task_detail extends AppCompatActivity {

    TextView detail_title,detail_desc,detail_price;
    String taskId = "";
    Button bt_assign;
    tasks task_detail_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        final DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference().child("Tasks").child("unAssigned");

        detail_title = findViewById(R.id.detail_title);
        detail_desc = findViewById(R.id.detail_desc);
        detail_price = findViewById(R.id.detail_price);

        taskId = getIntent().getStringExtra("taskId");
        bt_assign = findViewById(R.id.detail_assign);
        bt_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskRef.child(taskId).removeValue();
                DatabaseReference update = FirebaseDatabase.getInstance().getReference().child("Tasks").child("Assigned").child(taskId);
                HashMap<String,Object> taskMap = new HashMap<>();
                taskMap.put("taskID",taskId);
                taskMap.put("date",task_detail_display.getDate());
                taskMap.put("time",task_detail_display.getTime());
                taskMap.put("title",task_detail_display.getTitle());
                taskMap.put("status","assigned");
                taskMap.put("description",task_detail_display.getDescription());
                taskMap.put("price",task_detail_display.getPrice());
                update.updateChildren(taskMap);
            }
        });


        taskRef.child(taskId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    task_detail_display = dataSnapshot.getValue(tasks.class);
                    detail_title.setText(task_detail_display.getTitle());
                    detail_desc.setText(task_detail_display.getDescription());
                    detail_price.setText(task_detail_display.getPrice());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
