package com.vishavlakhtia.nearby.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vishavlakhtia.nearby.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    EditText post_title, post_description, post_price;
    String saveCurrentDate, saveCurrentTime;
    String title,description,price;
    String TaskRandomKey;
    Button postButton;
    DatabaseReference taskRef;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);*/
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        /*final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        taskRef = FirebaseDatabase.getInstance().getReference().child("Tasks");
        post_title = (EditText) root.findViewById(R.id.post_task_title);
        post_description = root.findViewById(R.id.post_task_description);
        post_price = root.findViewById(R.id.post_price);
        post_title.setText("hi");

        title = post_title.getText().toString();

        title = "Title Goes here";
        description = "Description Goes here";
        price = "1000";

        postButton = root.findViewById(R.id.post_taskOpen);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),""+description,Toast.LENGTH_SHORT).show();
                if(TextUtils.isEmpty(title))
                {
                    Toast.makeText(getContext(),""+title,Toast.LENGTH_SHORT).show();
                }else
                {
                    Calendar calendar = Calendar.getInstance();

                    SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                    saveCurrentDate = currentDate.format(calendar.getTime());

                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                    saveCurrentTime = currentTime.format(calendar.getTime());

                    TaskRandomKey = saveCurrentDate+saveCurrentTime;
                    saveTask();

                }
            }
        });

        return root;
    }

    private void saveTask()
    {
        HashMap<String,Object> taskMap = new HashMap<>();
        taskMap.put("taskID",TaskRandomKey);
        taskMap.put("date",saveCurrentDate);
        taskMap.put("time",saveCurrentTime);
        taskMap.put("title",title);
        taskMap.put("description",description);
        taskMap.put("price",price);

        taskRef.child(TaskRandomKey).updateChildren(taskMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(),"Task Added Successfully",Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
