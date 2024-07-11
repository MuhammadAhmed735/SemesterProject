package com.example.myapplication.ui.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.models.Project;
import com.example.myapplication.models.Task;
import com.example.myapplication.models.Teacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AddTaskActivity extends AppCompatActivity {

    private ImageView taskIcon;
    private EditText taskTitle, taskDescription, taskId;
    private TextInputEditText dueDate;
    private Button saveButton;

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private boolean isDateSelected = false;
    private boolean isTimeSelected = false;
    String taskType = "task";

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
        taskId = findViewById(R.id.taskId);
        saveButton = findViewById(R.id.saveButton);

        taskType = getIntent().getStringExtra("type");
        if(Objects.equals(taskType, "project"))
        {
            Toast.makeText(this,"Projects",Toast.LENGTH_SHORT).show();
            taskTitle.setHint("Project Title");
            taskDescription.setHint("Project Description");
            taskId.setHint("Project Id");
            saveButton.setText("Assign Project");

        }

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
                if(taskType.equals("project"))
                {
                    saveProject();
                }
                else if (taskType.equals("task"))
                {
                    saveTask();
                }
                else  Toast.makeText(getApplicationContext(),"Unable to save task/project",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        final Calendar date = Calendar.getInstance();
        new DatePickerDialog(AddTaskActivity.this, (view, year, month, dayOfMonth) -> {
            date.set(year, month, dayOfMonth);
            isDateSelected = true;
            new TimePickerDialog(AddTaskActivity.this, (view1, hourOfDay, minute) -> {
                date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                date.set(Calendar.MINUTE, minute);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                dueDate.setText(sdf.format(date.getTime()));
                isTimeSelected = true;
            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    private void saveTask() {
        // Get current date and time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDate = sdf.format(new Date());

        // Get input values
        String taskid = taskId.getText().toString().trim();
        String title = taskTitle.getText().toString().trim();
        String description = taskDescription.getText().toString().trim();
        String due = dueDate.getText().toString().trim();
        String teacherId = auth.getCurrentUser().getUid();

        // Validate input
        if (taskid.isEmpty() || title.isEmpty() || description.isEmpty() || due.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ensure both date and time are selected
        if (!isDateSelected || !isTimeSelected) {
            Toast.makeText(this, "Please select both date and time", Toast.LENGTH_SHORT).show();
            return;
        }

        firestore.collection("teachers").document(teacherId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String teacherName = documentSnapshot.getString("name");

                // Create Task object
                Task task = new Task(taskid, title, description, due, currentDate, teacherName,teacherId);
                task.setStatus("incomplete");

                // Save task to Firestore
                firestore.collection("tasks").document(taskid).get().addOnSuccessListener(document -> {
                    if (document.exists()) {
                        Toast.makeText(this, "Task ID already exists. Please use a unique ID.", Toast.LENGTH_SHORT).show();
                    } else {
                        firestore.collection("tasks").document(taskid).set(task)
                                .addOnSuccessListener(documentReference -> {
                                    updateTeacherTaskList(teacherId, taskid);
                                    showSuccessDialog();
                                })
                                .addOnFailureListener(e -> Toast.makeText(AddTaskActivity.this, "Error adding task", Toast.LENGTH_SHORT).show());
                    }
                });
            } else {
                Toast.makeText(this, "Teacher not found", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(this, "Error fetching teacher", Toast.LENGTH_SHORT).show());
    }

    private void saveProject() {
        // Get current date and time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDate = sdf.format(new Date());

        // Get input values
        String taskid = taskId.getText().toString().trim();
        String title = taskTitle.getText().toString().trim();
        String description = taskDescription.getText().toString().trim();
        String due = dueDate.getText().toString().trim();
        String teacherId = auth.getCurrentUser().getUid();

        // Validate input
        if (taskid.isEmpty() || title.isEmpty() || description.isEmpty() || due.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ensure both date and time are selected
        if (!isDateSelected || !isTimeSelected) {
            Toast.makeText(this, "Please select both date and time", Toast.LENGTH_SHORT).show();
            return;
        }

        firestore.collection("teachers").document(teacherId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String teacherName = documentSnapshot.getString("name");

                // Create Task object
                Project project = new Project(taskid, title, description, due, currentDate, teacherName,teacherId);
                project.setStatus("incomplete");

                // Save task to Firestore
                firestore.collection("projects").document(taskid).get().addOnSuccessListener(document -> {
                    if (document.exists()) {
                        Toast.makeText(this, "Project ID already exists. Please use a unique ID.", Toast.LENGTH_SHORT).show();
                    } else {
                        firestore.collection("projects").document(taskid).set(project)
                                .addOnSuccessListener(documentReference -> {
                                    updateTeacherTaskList(teacherId, taskid);
                                    showProjectSuccessDialog();
                                })
                                .addOnFailureListener(e -> Toast.makeText(AddTaskActivity.this, "Error adding Project", Toast.LENGTH_SHORT).show());
                    }
                });
            } else {
                Toast.makeText(this, "Teacher not found", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(this, "Error fetching teacher", Toast.LENGTH_SHORT).show());
    }
    private void updateTeacherTaskList(String teacherId, String taskid) {
        DocumentReference teacherRef = firestore.collection("teachers").document(teacherId);
        teacherRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                List<String> assignedTaskIds = (List<String>) documentSnapshot.get("assignedProjectIds");
                if (assignedTaskIds == null) {
                    assignedTaskIds = new ArrayList<>();
                }
                assignedTaskIds.add(taskid);
                teacherRef.update("assignedProjectIds", assignedTaskIds);
            } else {
                Toast.makeText(this,"Unable to add teacher",Toast.LENGTH_SHORT).show();
             //   List<String> assignedTaskIds = new ArrayList<>();
              //  assignedTaskIds.add(taskid);
              //  teacherRef.set(new Teacher(teacherId, "", "", "", 0, assignedTaskIds));
            }
        });
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Success!")
                .setMessage("Task added successfully")
                .setIcon(R.drawable.ic_done)
                .setPositiveButton("Add Another Task", (dialog, which) -> {
                    taskId.setText("");
                    taskTitle.setText("");
                    taskDescription.setText("");
                    dueDate.setText("");
                })
                .setNegativeButton("Go Back", (dialog, which) -> finish())

                .show();
    }
    private void showProjectSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Success!")
                .setMessage("Project added successfully")
                .setIcon(R.drawable.ic_done)
                .setPositiveButton("Add Another Project", (dialog, which) -> {
                    taskId.setText("");
                    taskTitle.setText("");
                    taskDescription.setText("");
                    dueDate.setText("");
                })
                .setNegativeButton("Go Back", (dialog, which) -> finish())

                .show();
    }
}
