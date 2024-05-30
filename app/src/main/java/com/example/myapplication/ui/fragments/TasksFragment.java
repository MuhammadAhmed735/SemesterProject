package com.example.myapplication.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;


import com.example.myapplication.R;
import com.example.myapplication.adapters.TaskAdapter;
import com.example.myapplication.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment {

    private ListView listView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;

    public TasksFragment() {

    }

    public static TasksFragment newInstance(String param1, String param2) {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Handle arguments if needed
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        listView = view.findViewById(R.id.taskListView);

        // Initialize the task list and adapter
        taskList = generateRandomTasks();
        taskAdapter = new TaskAdapter(getContext(), taskList);
        listView.setAdapter(taskAdapter);

        return view;
    }

    private List<Task> generateRandomTasks() {
        List<Task> tasks = new ArrayList<>();


        int taskicon = R.drawable.task_icons;
        String taskTitle = "OOAD Assignment";
        String date = "20/10/2024";
        int taskStatus = R.drawable.ic_done;

        for (int i = 0; i < 10; i++) {

            tasks.add(new Task(taskicon, taskTitle, date, taskStatus));
        }

        return tasks;
    }
}
