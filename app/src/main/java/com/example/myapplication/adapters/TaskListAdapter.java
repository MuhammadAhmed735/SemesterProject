package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Task;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private List<Task> tasks;

    public TaskListAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for a single task item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);

        // Set the task data to the view holder elements
        holder.taskTitle.setText(task.getTask_title());
        holder.taskDescription.setText(task.getDescription());

        // (Optional) Set deadline, priority, or other task attributes
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        public TextView taskTitle;
        public TextView taskDescription;

        public TaskViewHolder(View itemView) {
            super(itemView);

            taskTitle = itemView.findViewById(R.id.task_title);
            taskDescription = itemView.findViewById(R.id.task_title);
        }
    }
}

