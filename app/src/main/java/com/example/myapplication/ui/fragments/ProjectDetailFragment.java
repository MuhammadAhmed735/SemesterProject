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
import com.example.myapplication.models.Project;
import com.example.myapplication.models.Task;

public class ProjectDetailFragment extends Fragment {

    private static final String ARG_PROJECT = "project";

    private Project project;

    public static ProjectDetailFragment newInstance(Project project) {
        ProjectDetailFragment fragment = new ProjectDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PROJECT, project);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            project = getArguments().getParcelable(ARG_PROJECT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_detail, container, false);


        TextView projectTitle = view.findViewById(R.id.projectTitle);
        TextView dueDate = view.findViewById(R.id.dueDate);
        TextView projectDescription = view.findViewById(R.id.projectDescription);
        TextView assignedDate = view.findViewById(R.id.assignedDate);



        projectTitle.setText(project.getproject_title());
        dueDate.setText(project.getDue_date());
        projectDescription.setText(project.getDescription());
        assignedDate.setText(project.getAssignedDate());


        return view;
    }
}
