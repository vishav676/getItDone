package com.vishavlakhtia.nearby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vishavlakhtia.nearby.ViewHolder.TaskDisplayHolder;
import com.vishavlakhtia.nearby.ViewHolder.postedTaskHolder;
import com.vishavlakhtia.nearby.outline.tasks;
import com.vishavlakhtia.nearby.ui.home.HomeViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class posted extends AppCompatActivity {

    private HomeViewModel homeViewModel;
    EditText post_title, post_description, post_price;
    String saveCurrentDate, saveCurrentTime;
    String title,description,price,status;
    String TaskRandomKey;
    Button postButton;
    DatabaseReference taskRef;
    DatabaseReference posterRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posted);

        taskRef = FirebaseDatabase.getInstance().getReference().child("Tasks").child("unAssigned");
        posterRef = FirebaseDatabase.getInstance().getReference().child("Poster");
        post_title = findViewById(R.id.post_task_title);
        post_description = findViewById(R.id.post_task_description);
        post_price = findViewById(R.id.post_price);

        postButton = findViewById(R.id.post_taskOpen);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = post_title.getText().toString();
                description = post_description.getText().toString();
                price = post_price.getText().toString();
                if(TextUtils.isEmpty(title))
                {
                    Toast.makeText(getApplicationContext(),""+title,Toast.LENGTH_SHORT).show();
                }else
                {
                    Calendar calendar = Calendar.getInstance();

                    SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                    saveCurrentDate = currentDate.format(calendar.getTime());

                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                    saveCurrentTime = currentTime.format(calendar.getTime());

                    TaskRandomKey = saveCurrentDate+saveCurrentTime;
                    status = "unassigned";
                    saveTask();

                }
            }
        });
    }

    private void saveTask()
    {
        HashMap<String,Object> taskMap = new HashMap<>();
        taskMap.put("taskID",TaskRandomKey);
        taskMap.put("date",saveCurrentDate);
        taskMap.put("time",saveCurrentTime);
        taskMap.put("title",title);
        taskMap.put("status",status);
        taskMap.put("description",description);
        taskMap.put("price",price);

        taskRef.child(TaskRandomKey).updateChildren(taskMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Task Added Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), posted.class));
                    }
                });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        posterRef.child(user.getUid()).child(TaskRandomKey).updateChildren(taskMap);

    }

}
