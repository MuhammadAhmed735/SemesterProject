package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Task;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private List<Task> tasks;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Task task);
    }
    public TaskListAdapter(List<Task> tasks,OnItemClickListener listener) {
        this.tasks = tasks;
        this.listener = listener;
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

        holder.taskTitle.setText(task.getTask_title());
        holder.dueDate.setText(task.getDue_date());
        holder.status.setImageResource(R.drawable.ic_cancel);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(task);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        public TextView taskTitle;
        public ImageView status;
        public TextView dueDate;

        public TaskViewHolder(View itemView) {
            super(itemView);

            taskTitle = itemView.findViewById(R.id.task_title);
            status = itemView.findViewById(R.id.task_status);
            dueDate = itemView.findViewById(R.id.date);
        }
    }
}

