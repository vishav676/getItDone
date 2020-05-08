package com.vishavlakhtia.nearby.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vishavlakhtia.nearby.R;
import com.vishavlakhtia.nearby.ViewHolder.TaskDisplayHolder;
import com.vishavlakhtia.nearby.ViewHolder.postedTaskHolder;
import com.vishavlakhtia.nearby.outline.tasks;
import com.vishavlakhtia.nearby.posted;
import com.vishavlakhtia.nearby.task_detail;
import com.vishavlakhtia.nearby.ui.home.HomeViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class GalleryFragment extends Fragment {


    DatabaseReference taskref;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView posted_recycler;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        taskref = FirebaseDatabase.getInstance().getReference().child("Poster").child(user.getUid());
        posted_recycler = root.findViewById(R.id.posted_rv);
        posted_recycler.setHasFixedSize(true);
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),posted.class));
            }
        });
        posted_recycler.setLayoutManager(layoutManager);
        final FirebaseRecyclerOptions<tasks> options = new FirebaseRecyclerOptions.Builder<tasks>()
                .setQuery(taskref,tasks.class).build();
        final FirebaseRecyclerAdapter<tasks, postedTaskHolder> adapter =
                new FirebaseRecyclerAdapter<tasks, postedTaskHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull postedTaskHolder holder, int position, @NonNull final tasks model) {

                        holder.taskTitle.setText(model.getTitle());


                    }

                    @NonNull
                    @Override
                    public postedTaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.posted_item_layout,parent,false);
                        postedTaskHolder holder = new postedTaskHolder(view);
                        return holder;
                    }

                };
        posted_recycler.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        posted_recycler.setLayoutManager(layoutManager);
        adapter.startListening();

        return root;
    }
}