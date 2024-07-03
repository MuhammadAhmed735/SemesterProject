package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.example.myapplication.R;
import com.example.myapplication.models.Task;

import java.util.List;


public class TaskAdapter extends ArrayAdapter<Task>
{
    private Context context;
    private List<Task> tasksList;

    public TaskAdapter(@NonNull Context context, List<Task> tasks) {
    super(context, R.layout.task_list_item,tasks);

    this.tasksList = tasks;
    this.context =  context;
}
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.task_list_item,parent,false);

        ImageView taskIcon   = view.findViewById(R.id.task_icon);
        TextView taskTitle    = view.findViewById(R.id.task_title);
        TextView date = view.findViewById(R.id.date);
        ImageView status   = view.findViewById(R.id.task_status);

        Task task = tasksList.get(position);
        taskIcon.setImageResource(R.drawable.task_icons);
        taskTitle.setText(task.getTask_title());
        date.setText(task.getTask_date());
        status.setImageResource(R.drawable.ic_done);

        return view;
    }

}
