package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.TaskSubmission;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Objects;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentViewHolder> {

    private List<TaskSubmission> taskSubmissions; // List to hold task submissions
    private FirebaseFirestore db;
    private OnItemClickListener listener;
    private OnMarkButtonClickListener buttonClickListener;

    public StudentListAdapter(List<TaskSubmission> taskSubmissions) {
        this.taskSubmissions = taskSubmissions;
        this.db = FirebaseFirestore.getInstance();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnMarkButtonClickListener(OnMarkButtonClickListener listener) {
        this.buttonClickListener = listener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_list_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        TaskSubmission submission = taskSubmissions.get(position);

        // Retrieve student name from Firestore based on studentId
        db.collection("students").document(submission.getStudentId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String studentName = document.getString("name");
                                holder.studentName.setText(studentName);
                            } else {
                                holder.studentName.setText("Student Not Found");
                            }
                        } else {
                            holder.studentName.setText("Error");
                        }
                    }
                });

        holder.taskStatus.setText(submission.getStatus());
        holder.completionTime.setText("Submitted On: " + submission.getSubmissionTime());

        // Check if submission is marked
        if (Objects.equals(submission.getStatus(), "Marked")) {
            holder.markButton.setClickable(false);
            holder.markButton.setText("Marks: " + submission.getMarks());
        } else {
            holder.markButton.setClickable(true);
            holder.markButton.setText("Mark");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(submission); // Notify the listener
                }
            }
        });

        holder.markButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonClickListener != null) {
                    buttonClickListener.onMarkButtonClick(position);
                }
            }
        });
    }


    private void loadStudentName(String studentId, TextView textView) {
        db.collection("students").document(studentId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String studentName = documentSnapshot.getString("name");
                        textView.setText(studentName);
                    } else {
                        textView.setText("Student Not Found");
                    }
                })
                .addOnFailureListener(e -> {
                    textView.setText("Error");
                });
    }

    @Override
    public int getItemCount() {
        return taskSubmissions.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView studentName, taskStatus, completionTime;
        Button markButton;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.student_name);
            taskStatus = itemView.findViewById(R.id.task_status);
            completionTime = itemView.findViewById(R.id.completion_time);
            markButton = itemView.findViewById(R.id.markButton);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TaskSubmission submission);
    }

    public interface OnMarkButtonClickListener {
        void onMarkButtonClick(int position);
    }
}
