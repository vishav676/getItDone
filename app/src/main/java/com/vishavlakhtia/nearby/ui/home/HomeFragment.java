package com.vishavlakhtia.nearby.ui.home;

import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vishavlakhtia.nearby.R;
import com.vishavlakhtia.nearby.ViewHolder.TaskDisplayHolder;
import com.vishavlakhtia.nearby.outline.tasks;
import com.vishavlakhtia.nearby.posted;
import com.vishavlakhtia.nearby.task_detail;
import com.vishavlakhtia.nearby.ui.gallery.GalleryViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class HomeFragment extends Fragment {


    DatabaseReference taskRef;
    RecyclerView.LayoutManager layoutManager;
    private GalleryViewModel galleryViewModel;
    RecyclerView task_reycler;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        taskRef = FirebaseDatabase.getInstance().getReference().child("Tasks").child("unAssigned");
        task_reycler = root.findViewById(R.id.recycler_view_task);
        task_reycler.setHasFixedSize(true);
        task_reycler.setLayoutManager(layoutManager);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        final FirebaseRecyclerOptions<tasks> options = new FirebaseRecyclerOptions.Builder<tasks>()
                .setQuery(taskRef,tasks.class).build();
        final FirebaseRecyclerAdapter<tasks, TaskDisplayHolder> adapter =
                new FirebaseRecyclerAdapter<tasks, TaskDisplayHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull TaskDisplayHolder holder, int position, @NonNull final tasks model) {

                        holder.tv_title.setText(model.getTitle());
                        holder.tv_description.setText(model.getDescription());
                        holder.tv_price.setText(model.getPrice());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), task_detail.class);
                                intent.putExtra("taskId", model.getTaskID());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public TaskDisplayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_display_layout, parent,false);
                        TaskDisplayHolder holder = new TaskDisplayHolder(view);
                        return holder;
                    }

                };
        task_reycler.setAdapter(adapter);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        sglm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        task_reycler.setLayoutManager(sglm);
        adapter.startListening();
    }

}
