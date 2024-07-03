package com.example.myapplication.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.models.Task;

public class TaskDetailFragment extends Fragment {

    private static final String ARG_TASK = "task";

    private Task task;

    public static TaskDetailFragment newInstance(Task task) {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TASK, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            task = getArguments().getParcelable(ARG_TASK);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);


        TextView taskTitle = view.findViewById(R.id.task_title);
        TextView dueDate = view.findViewById(R.id.due_date);
        TextView taskDescription = view.findViewById(R.id.task_description);
        TextView assignedDate = view.findViewById(R.id.assigned_date);



        taskTitle.setText(task.getTask_title());
        dueDate.setText(task.getDue_date());
        taskDescription.setText(task.getDescription());
        assignedDate.setText(task.getAssignedDate());


        return view;
    }
}
