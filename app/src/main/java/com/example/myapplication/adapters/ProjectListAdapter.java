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
import com.example.myapplication.models.Project;
import com.example.myapplication.models.Project;

import java.util.List;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ProjectViewHolder> {

    private List<Project> projects;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Project project);
    }
    public ProjectListAdapter(List<Project> Projects,OnItemClickListener listener) {
        this.projects = Projects;
        this.listener = listener;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for a single Project item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        Project Project = projects.get(position);

        holder.ProjectTitle.setText(Project.getproject_title());
        holder.dueDate.setText(Project.getDue_date());
        holder.status.setImageResource(R.drawable.ic_cancel);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(Project);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public static class ProjectViewHolder extends RecyclerView.ViewHolder {

        public TextView ProjectTitle;
        public ImageView status;
        public TextView dueDate;

        public ProjectViewHolder(View itemView) {
            super(itemView);

            ProjectTitle = itemView.findViewById(R.id.task_title);
            status = itemView.findViewById(R.id.task_status);
            dueDate = itemView.findViewById(R.id.date);
        }
    }
}

