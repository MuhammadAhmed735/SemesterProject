package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Student;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentViewHolder> {

    private List<Student> students;

    public StudentListAdapter(List<Student> students) {
        this.students = students;
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
        Student student = students.get(position);
        holder.studentName.setText(student.getName());
        /*
        holder.studentId.setText("ID: " + student.getId());
        holder.taskStatus.setText(student.isTaskCompleted() ? "Completed" : "Not Completed");

        if (student.isTaskCompleted()) {
            holder.completionTime.setText(student.getCompletionTime());
            holder.completionTime.setVisibility(View.VISIBLE);
        } else {
            holder.completionTime.setVisibility(View.GONE);
        }
        */
         
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView studentName, studentId, taskStatus, completionTime;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.student_name);

            taskStatus = itemView.findViewById(R.id.task_status);
            completionTime = itemView.findViewById(R.id.completion_time);
        }
    }
}
