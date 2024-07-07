package com.example.myapplication.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.models.Project;
import com.example.myapplication.models.Task;

import com.example.myapplication.models.ProjectSubmission;
;
import com.example.myapplication.models.TaskSubmission;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ProjectDetailFragment extends Fragment {

    private static final String ARG_PROJECT = "project";
    private static final String ARG_TASK = "task";

    private Project project;
    private Task task;
    private String type;

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    public static ProjectDetailFragment newInstance(Project project) {
        ProjectDetailFragment fragment = new ProjectDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PROJECT, project);
        fragment.setArguments(args);
        return fragment;
    }

    public static ProjectDetailFragment newInstance(Task task) {
        ProjectDetailFragment fragment = new ProjectDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TASK, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            project = getArguments().getParcelable(ARG_PROJECT);
            task = getArguments().getParcelable(ARG_TASK);
        }

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_detail, container, false);


        TextView projectTitle = view.findViewById(R.id.projectTitle);
        TextView dueDate = view.findViewById(R.id.dueDate);
        TextView projectDescription = view.findViewById(R.id.projectDescription);
        TextView assignedDate = view.findViewById(R.id.assignedDate);
        TextView status = view.findViewById(R.id.status);
        TextView teacher = view.findViewById(R.id.assignedBy);
        MaterialButton submitButton = view.findViewById(R.id.submit);


       if(project!=null) {
           type="project";
           projectTitle.setText(project.getproject_title());
           dueDate.setText(project.getDue_date());
           projectDescription.setText(project.getDescription());
           assignedDate.setText(project.getAssignedDate());
           status.setText(project.getStatus());
           teacher.setText(project.getAssignedByTeacherName());
           submitButton.setText("Submit Project");

       }
       else if (task!=null)
       {
           type="task";
           projectTitle.setText(task.getTask_title());
           dueDate.setText(task.getDue_date());
           projectDescription.setText(task.getDescription());
           assignedDate.setText(task.getAssignedDate());
           status.setText(task.getStatus());
           teacher.setText(task.getAssignedByTeacherName());
           submitButton.setText("Submit Task");
       }


        submitButton.setOnClickListener(this::submitTask);
        return view;
    }
    public void submitTask(View view)
    {
        String studentId = auth.getCurrentUser().getUid();
        String currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

        if (Objects.equals(type, "task")) {
            String taskId = task.getTaskId();
            String submissionId = studentId + "_" + taskId;
            TaskSubmission taskSubmission = new TaskSubmission(submissionId, taskId, studentId, "Submitted", null, currentTime, null);

            firestore.collection("TaskSubmissions")
                    .document(submissionId)
                    .set(taskSubmission)
                    .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Task submitted successfully", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to submit task", Toast.LENGTH_SHORT).show());

        } else if (Objects.equals(type, "project")) {
            String projectId = project.getprojectId();
            String submissionId = studentId + "_" + projectId;
            ProjectSubmission projectSubmission = new ProjectSubmission(submissionId, projectId, studentId, "Submitted", null, currentTime, null);

            firestore.collection("ProjectSubmissions")
                    .document(submissionId)
                    .set(projectSubmission)
                    .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Project submitted successfully", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to submit project", Toast.LENGTH_SHORT).show());
        }
    }
}
