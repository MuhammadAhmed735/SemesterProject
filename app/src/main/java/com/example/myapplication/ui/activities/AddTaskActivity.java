package com.example.myapplication.ui.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.models.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    private ImageView taskIcon;
    private EditText taskTitle, taskDescription;
    private TextInputEditText dueDate;
    
    private MultiAutoCompleteTextView assignedTo;
    private Button saveButton;

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Initialize Firebase
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Initialize views
        taskIcon = findViewById(R.id.taskIcon);
        taskTitle = findViewById(R.id.taskTitle);
        taskDescription = findViewById(R.id.taskDescription);
        dueDate = findViewById(R.id.dueDate);

        assignedTo = findViewById(R.id.assignedTo);
        saveButton = findViewById(R.id.saveButton);

        // Set due date field click listener
        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });

        // Set save button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });
    }

    private void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        final Calendar date = Calendar.getInstance();
        new DatePickerDialog(AddTaskActivity.this, (view, year, month, dayOfMonth) -> {
            date.set(year, month, dayOfMonth);
            new TimePickerDialog(AddTaskActivity.this, (view1, hourOfDay, minute) -> {
                date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                date.set(Calendar.MINUTE, minute);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                dueDate.setText(sdf.format(date.getTime()));
            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    private void saveTask() {
        // Get current date and time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDate = sdf.format(new Date());

        // Get input values
        String title = taskTitle.getText().toString().trim();
        String description = taskDescription.getText().toString().trim();
        String due = dueDate.getText().toString().trim();
        String teacherId = auth.getCurrentUser().getUid();
        List<String> assignedStudents = getAssignedStudents();

        // Validate input
        if (title.isEmpty() || description.isEmpty() || due.isEmpty() || assignedStudents.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create Task object
        Task task = new Task(
                // Icon resource
                title,
                description,
                due,
                currentDate,
                // Status icon
                teacherId
        );
        task.setAssignedToStudentIds(assignedStudents);
        task.setStatus("incomplete");

        // Save task to Firestore
        firestore.collection("tasks")
                .add(task)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(AddTaskActivity.this, "Task added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(AddTaskActivity.this, "Error adding task", Toast.LENGTH_SHORT).show());
    }

    private List<String> getAssignedStudents() {
        String input = assignedTo.getText().toString();
        String[] studentsArray = input.split(",\\s*");
        List<String> studentsList = new ArrayList<>();
        for (String student : studentsArray) {
            studentsList.add(student.trim());
        }
        return studentsList;
    }
}
