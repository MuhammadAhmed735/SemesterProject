package com.example.myapplication.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapters.ProjectListAdapter;

import com.example.myapplication.models.Project;
import com.example.myapplication.models.Task;
import com.example.myapplication.ui.activities.AddTaskActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProjectsTabFragment extends Fragment implements  ProjectListAdapter.OnItemClickListener {

    private RecyclerView teacherProjectList;
    private ProjectListAdapter adapter;
    private ExtendedFloatingActionButton addProjectButton;

    private List<Project> projects = new ArrayList<>();

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

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

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_projects_tab, container, false);

        teacherProjectList= view.findViewById(R.id.project_list_tab);
        addProjectButton = view.findViewById(R.id.add_project_button);


        teacherProjectList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProjectListAdapter(projects, this);
        teacherProjectList.setAdapter(adapter);



        loadProjects();


        teacherProjectList.setAdapter(adapter);

        addProjectButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddTaskActivity.class);
                intent.putExtra("type","project");

                startActivity(intent);
            }
        });

        // Implement logic for item clicks and other functionalities

        return view;
    }
    @Override
    public void onItemClick(Project project) {
        TeacherProjectDetail fragment = TeacherProjectDetail.newInstance(project);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
    public void loadProjects()
    {
        String teacherId = auth.getCurrentUser().getUid();
        firestore.collection("projects")
                .whereEqualTo("teacherId", teacherId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot value, FirebaseFirestoreException error) {
                        if (error != null) {
                            Toast.makeText(getContext(), "Error loading Projects", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        projects.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            Project project = doc.toObject(Project.class);
                            projects.add(project);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }


}