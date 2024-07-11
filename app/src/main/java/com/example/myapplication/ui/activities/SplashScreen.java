package com.example.myapplication.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.myapplication.models.Student;
import com.example.myapplication.models.Teacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.myapplication.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 3000;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    // User is logged in
                    fetchUserRoleAndRedirect(currentUser.getUid());
                } else {
                    // No user is logged in
                    Intent mainIntent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    private void fetchUserRoleAndRedirect(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference studentRef = db.collection("students").document(userId);
        studentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                // User is a student
                Student student = task.getResult().toObject(Student.class);

                Intent intent = new Intent(this, StudentDashbaordActivity.class);
                intent.putExtra("student", student);
                startActivity(intent);
                finish();
            } else {
                DocumentReference teacherRef = db.collection("teachers").document(userId);
                teacherRef.get().addOnCompleteListener(teacherTask -> {
                    if (teacherTask.isSuccessful() && teacherTask.getResult().exists()) {
                        // User is a teacher
                        Teacher teacher = teacherTask.getResult().toObject(Teacher.class);
                        Intent intent = new Intent(this, TeacherDashboardActivity.class);

                        intent.putExtra("teacher", teacher);
                        startActivity(intent);
                         finish();
                    } else {
                        Toast.makeText(this, "User role not found.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
