package com.example.myapplication.ui.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapters.ProjectStudentListAdapter;
import com.example.myapplication.adapters.StudentListAdapter;
import com.example.myapplication.models.Project;
import com.example.myapplication.models.ProjectSubmission;
import com.example.myapplication.models.Task;
import com.example.myapplication.models.TaskSubmission;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class TeacherProjectDetail extends Fragment {

    private static final String ARG_PROJECT = "project";

    private Project project;
    private RecyclerView recyclerView;
    private ProjectStudentListAdapter adapter;
    private List<ProjectSubmission> projectSubmissions;

    // Firebase Firestore instance
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference projectSubmissionRef = db.collection("ProjectSubmissions");

    public TeacherProjectDetail() {
        // Required empty public constructor
    }



    public static TeacherProjectDetail newInstance(Project project) {
        TeacherProjectDetail fragment = new TeacherProjectDetail();
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

        projectSubmissions = new ArrayList<>();
        adapter = new ProjectStudentListAdapter(projectSubmissions);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_task_details, container, false);

        recyclerView = view.findViewById(R.id.studentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Query Firestore for task submissions
        queryProjectSubmissions();

        // Setup other views in the layout
        TextView projectTitle = view.findViewById(R.id.taskTitle);
        TextView dueDate = view.findViewById(R.id.dueDate);
        TextView projectDescription = view.findViewById(R.id.taskDescription);
        TextView assignedDate = view.findViewById(R.id.assignedDate);

        projectTitle.setText(project.getproject_title());
        dueDate.setText(project.getDue_date());
        projectDescription.setText(project.getDescription());
        assignedDate.setText(project.getAssignedDate());

        adapter.setOnItemClickListener(new ProjectStudentListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProjectSubmission submission) {

            }

        });
        adapter.setOnMarkButtonClickListener(new ProjectStudentListAdapter.OnMarkButtonClickListener() {
            @Override
            public void onMarkButtonClick(int position) {
                // Handle button click here
                ProjectSubmission clickedSubmission = projectSubmissions.get(position);
                // Display a dialog or popup to get the mark from the user
                showMarkInputDialog(clickedSubmission);
            }
        });
        return view;
    }

    private void queryProjectSubmissions() {

        projectSubmissionRef.whereEqualTo("projectId", project.getprojectId())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    projectSubmissions.clear();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        ProjectSubmission submission = documentSnapshot.toObject(ProjectSubmission.class);
                        projectSubmissions.add(submission);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(),"No Submissions yet",Toast.LENGTH_SHORT).show();

                });

    }


    private void showMarkInputDialog(ProjectSubmission submission) {
        // Create a custom dialog or use AlertDialog to get user input
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Enter Marks : ");

        // Set up the input
        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String marksStr = input.getText().toString().trim();
                if (!marksStr.isEmpty()) {
                    int marks = Integer.parseInt(marksStr);
                    if (marks >= 1 && marks <= 10) {
                        // Update the submission in Firestore
                        updateSubmission(submission, marks);
                    } else {
                        Toast.makeText(getContext(), "Please enter valid marks between 1 and 10", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Please enter a mark between 1 and 10", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void updateSubmission(ProjectSubmission submission, int marks) {
        // Update the submission document in Firestore
        db.collection("ProjectSubmissions").document(submission.getSubmissionId())
                .update("status", "Marked", "marks", marks)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Submission marked successfully", Toast.LENGTH_SHORT).show();
                        // Optionally update local list and notify adapter
                        submission.setStatus("Marked");
                        submission.setMarks(marks);
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed to mark submission: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
