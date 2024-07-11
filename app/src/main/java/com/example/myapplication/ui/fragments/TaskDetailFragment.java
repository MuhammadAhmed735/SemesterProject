package com.example.myapplication.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.StudentListAdapter;
import com.example.myapplication.models.Task;
import com.example.myapplication.models.TaskSubmission;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class TaskDetailFragment extends Fragment {

    private static final String ARG_TASK = "task";

    private Task task;
    private RecyclerView recyclerView;
    private StudentListAdapter adapter;
    private List<TaskSubmission> taskSubmissions;

    // Firebase Firestore instance
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference taskSubmissionRef = db.collection("TaskSubmissions");

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

        taskSubmissions = new ArrayList<>();
        adapter = new StudentListAdapter(taskSubmissions);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_task_details, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.studentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Query Firestore for task submissions
        queryTaskSubmissions();

        // Setup other views in the layout
        TextView taskTitle = view.findViewById(R.id.taskTitle);
        TextView dueDate = view.findViewById(R.id.dueDate);
        TextView taskDescription = view.findViewById(R.id.taskDescription);
        TextView assignedDate = view.findViewById(R.id.assignedDate);

        taskTitle.setText(task.getTask_title());
        dueDate.setText(task.getDue_date());
        taskDescription.setText(task.getDescription());
        assignedDate.setText(task.getAssignedDate());

        adapter.setOnItemClickListener(new StudentListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TaskSubmission submission) {
                // Handle item click here
                // For example, you can open a new fragment or activity to show more details
                // about the selected task submission.

            }
        });
        adapter.setOnMarkButtonClickListener(new StudentListAdapter.OnMarkButtonClickListener() {
            @Override
            public void onMarkButtonClick(int position) {
                // Handle button click here
                TaskSubmission clickedSubmission = taskSubmissions.get(position);
                // Display a dialog or popup to get the mark from the user
                showMarkInputDialog(clickedSubmission);
            }
        });
        return view;
    }

    private void queryTaskSubmissions() {
        // Query task submissions for current task ID
        taskSubmissionRef.whereEqualTo("taskId", task.getTaskId())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    taskSubmissions.clear();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        TaskSubmission submission = documentSnapshot.toObject(TaskSubmission.class);
                        taskSubmissions.add(submission);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(),"No Submissions yet",Toast.LENGTH_SHORT).show();

                });

    }


    private void showMarkInputDialog(TaskSubmission submission) {
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
                        Toast.makeText(getContext(), "Please enter a valid mark between 1 and 10", Toast.LENGTH_SHORT).show();
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

    private void updateSubmission(TaskSubmission submission, int marks) {
        // Update the submission document in Firestore
        db.collection("TaskSubmissions").document(submission.getSubmissionId())
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

