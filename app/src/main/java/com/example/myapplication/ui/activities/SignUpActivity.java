package com.example.myapplication.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.models.Student;
import com.example.myapplication.models.Teacher;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText emailEditText, passwordEditText, nameEditText;
    private TextInputLayout emailInputLayout, passwordInputLayout, nameInputLayout;
    private RadioGroup roleRadioGroup;
    private MaterialButton loginButton, registerButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth and Firestore
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Initialize views
        emailEditText = findViewById(R.id.email_editText);
        passwordEditText = findViewById(R.id.password_editText);
        nameEditText = findViewById(R.id.name_editText);

        emailInputLayout = findViewById(R.id.email_inputLayout);
        passwordInputLayout = findViewById(R.id.password_inputLayout);
        nameInputLayout = findViewById(R.id.name_inputLayout);

        roleRadioGroup = findViewById(R.id.role_radio_group);
        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.login_button);

        // Set default selected radio button
        roleRadioGroup.check(R.id.radio_student);

        // Set click listener for the register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();

        int selectedRoleId = roleRadioGroup.getCheckedRadioButtonId();

        // Validate inputs
        if (TextUtils.isEmpty(name)) {
            nameInputLayout.setError("Name is required");
            return;
        } else {
            nameInputLayout.setError(null);
        }



        if (TextUtils.isEmpty(email)) {
            emailInputLayout.setError("Email is required");
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.setError("Invalid email format");
            return;
        } else {
            emailInputLayout.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            passwordInputLayout.setError("Password is required");
            return;
        } else if (password.length() < 6 || password.length() > 15) {
            passwordInputLayout.setError("Password must be 6 to 15 characters long");
            return;
        } else {
            passwordInputLayout.setError(null);
        }

        if (selectedRoleId == -1) {
            Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRoleButton = findViewById(selectedRoleId);
        String role = selectedRoleButton.getText().toString();

        // Proceed with Firebase registration
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registration successful
                        String userId = firebaseAuth.getCurrentUser().getUid();

                        // Create user object and store in Firestore
                        if (role.equals("Student")) {
                            List<String> enrolledCoursesIds = new ArrayList<>();
                            List<String> taskIds = new ArrayList<>();
                            Student student = new Student(userId, name, email, password, R.drawable.ic_profile, enrolledCoursesIds, taskIds);
                            firestore.collection("students").document(userId).set(student)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(SignUpActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(SignUpActivity.this, "Failed to save student data: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        } else if (role.equals("Teacher")) {
                            List<String> assignedTaskIds = new ArrayList<>();
                            Teacher teacher = new Teacher(userId, name, email, password,R.drawable.ic_profile);
                            firestore.collection("teachers").document(userId).set(teacher)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(SignUpActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(SignUpActivity.this, "Failed to save teacher data: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    } else {
                        // Registration failed
                        String errorMessage;
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthException e) {
                            errorMessage = e.getMessage();
                        } catch (Exception e) {
                            errorMessage = "Registration failed. Please try again.";
                        }
                        Toast.makeText(SignUpActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void startLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
