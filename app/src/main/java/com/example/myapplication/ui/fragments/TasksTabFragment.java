package com.example.myapplication.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapters.TaskAdapter;
import com.example.myapplication.adapters.TaskListAdapter;
import com.example.myapplication.adapters.TasksPagerAdapter;
import com.example.myapplication.models.Task;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TasksTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TasksTabFragment extends Fragment {

    private RecyclerView teacherTaskList;
    private TaskListAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TasksTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TasksTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TasksTabFragment newInstance(String param1, String param2) {
        TasksTabFragment fragment = new TasksTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_tasks_tab, container, false);

        teacherTaskList= view.findViewById(R.id.teacher_task_list_tab);

        // Replace with your actual data fetching and adapter logic
        List<Task> tasks = new ArrayList<Task>();// Fetch your task data here (e.g., from database or API)
                adapter = new TaskListAdapter(tasks);

        teacherTaskList.setAdapter(adapter);

        // Implement logic for item clicks and other functionalities

        return view;


    }
}