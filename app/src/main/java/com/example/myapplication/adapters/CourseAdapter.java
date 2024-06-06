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
import com.example.myapplication.models.Course;


import java.util.List;

public class CourseAdapter extends ArrayAdapter<Course>
    {
        private Context context;
        private List<Course> coursesList;

    public CourseAdapter(@NonNull Context context, List<Course> courses) {
        super(context, R.layout.course_list_item,courses);

        this.coursesList = courses;
        this.context =  context;
    }
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.course_list_item,parent,false);

            TextView courseTitle = view.findViewById(R.id.coursetitle);


            Course course = coursesList.get(position);
            courseTitle.setText(course.getCourseTitle());

            return view;
        }

    }
