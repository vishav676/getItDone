package com.vishavlakhtia.nearby.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vishavlakhtia.nearby.R;
import com.vishavlakhtia.nearby.ViewHolder.TaskDisplayHolder;
import com.vishavlakhtia.nearby.outline.tasks;

public class GalleryFragment extends Fragment {

    DatabaseReference taskRef;
    RecyclerView.LayoutManager layoutManager;

    private GalleryViewModel galleryViewModel;
    RecyclerView task_reycler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        taskRef = FirebaseDatabase.getInstance().getReference().child("Tasks");
        /*final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        task_reycler = root.findViewById(R.id.recycler_view_task);
        task_reycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        task_reycler.setLayoutManager(layoutManager);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<tasks> options = new FirebaseRecyclerOptions.Builder<tasks>()
                .setQuery(taskRef,tasks.class).build();
        FirebaseRecyclerAdapter<tasks, TaskDisplayHolder> adapter =
                new FirebaseRecyclerAdapter<tasks, TaskDisplayHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull TaskDisplayHolder holder, int position, @NonNull tasks model) {
                        holder.tv_title.setText(model.getTitle());
                        holder.tv_description.setText(model.getDescription());
                        holder.tv_price.setText(model.getPrice());


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
        adapter.startListening();
    }
}
