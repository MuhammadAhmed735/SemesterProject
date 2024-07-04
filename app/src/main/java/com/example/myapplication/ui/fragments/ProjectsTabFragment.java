package com.example.myapplication.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.adapters.ProjectListAdapter;
import com.example.myapplication.adapters.TaskAdapter;
import com.example.myapplication.adapters.TaskListAdapter;
import com.example.myapplication.adapters.TasksPagerAdapter;
import com.example.myapplication.models.Project;
import com.example.myapplication.models.Task;
import com.example.myapplication.ui.activities.AddTaskActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProjectsTabFragment extends Fragment implements  ProjectListAdapter.OnItemClickListener {

    private RecyclerView teacherProjectList;
    private ProjectListAdapter adapter;
    private ExtendedFloatingActionButton addProjectButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProjectsTabFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProjectsTabFragment newInstance(String param1, String param2) {
        ProjectsTabFragment fragment = new ProjectsTabFragment();
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
        View view=  inflater.inflate(R.layout.fragment_projects_tab, container, false);

        teacherProjectList= view.findViewById(R.id.project_list_tab);
        addProjectButton = view.findViewById(R.id.add_project_button);

        List<Project> Projects = new ArrayList<Project>();// Fetch your task data here (e.g., from database or API)
        adapter = new ProjectListAdapter(Projects,this);

        teacherProjectList.setAdapter(adapter);

        addProjectButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddTaskActivity.class);

                startActivity(intent);

            }
        });

        // Implement logic for item clicks and other functionalities

        return view;
    }
    @Override
    public void onItemClick(Project project) {
        ProjectDetailFragment fragment = ProjectDetailFragment.newInstance(project);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }


}