package com.example.myapplication.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import com.example.myapplication.R;
import com.example.myapplication.models.Student;
import com.example.myapplication.models.Teacher;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialTextView headingTextView;
    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private TextInputEditText emailEditText;
    private TextInputEditText passEditText;
    private MaterialButton loginButton;
    private MaterialButton registerButton;
    private MaterialButton forgotPassButton;
    private FirebaseAuth mAuth;

    private String emailAddress;
    private String password;
    private final static int MINIMUM_PASSWORD_LENGTH = 6;
    private final static int MAXIMUM_PASSWORD_LENGTH = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        initializeViews();
        setClickListeners();
        setTextChangeListeners();
    }

    public void initializeViews() {
        headingTextView = findViewById(R.id.login_heading);
        emailLayout = findViewById(R.id.email_layout);
        passwordLayout = findViewById(R.id.password_layout);
        emailEditText = findViewById(R.id.email_edit_text);
        passEditText = findViewById(R.id.password_edit_text);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        forgotPassButton = findViewById(R.id.forgotPassword);
    }

    public void setClickListeners() {
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        forgotPassButton.setOnClickListener(this);
    }

    public void setTextChangeListeners() {
        passEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence input, int start, int before, int count) {
                int passwordLength = input.length();
                if (passwordLength < MINIMUM_PASSWORD_LENGTH) {
                    passwordLayout.setError("Password is too short");
                } else if (passwordLength > MAXIMUM_PASSWORD_LENGTH) {
                    passwordLayout.setError("Password is too long");
                } else {
                    passwordLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence input, int start, int before, int count) {
                emailLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == loginButton.getId()) {
            validateCredentials();
        } else if (viewId == registerButton.getId()) {
            startSignUpActivity();
        } else if (viewId == forgotPassButton.getId()) {
            startForgotPasswordActivity();
        }
    }

    public void startSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void startForgotPasswordActivity() {
        // Intent intent = new Intent(this, ForgotPasswordActivity.class);
        // startActivity(intent);
    }

    public void validateCredentials() {
        emailAddress = emailEditText.getText().toString().trim();
        password = passEditText.getText().toString().trim();

        boolean isValidEmail = false;
        boolean isValidPassword = false;

        if (emailAddress.isEmpty()) {
            emailLayout.setError("Please Enter Email Address");
        } else {
            emailLayout.setError(null);
            if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                emailLayout.setError("Invalid Email Address");
            } else {
                isValidEmail = true;
            }
        }

        if (password.isEmpty()) {
            passwordLayout.setError("Please Enter Password");
        } else {
            passwordLayout.setError(null);
            if (password.length() < MINIMUM_PASSWORD_LENGTH || password.length() > MAXIMUM_PASSWORD_LENGTH) {
                passwordLayout.setError("Invalid Password");
            } else {
                isValidPassword = true;
            }
        }

        if (isValidEmail && isValidPassword) {
            loginUser(emailAddress, password);
        }
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        fetchUserRoleAndRedirect(user.getUid());
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchUserRoleAndRedirect(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference studentRef = db.collection("students").document(userId);
        studentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                // User is a student
                Student student = task.getResult().toObject(Student.class);
                Intent intent = new Intent(LoginActivity.this, StudentDashbaordActivity.class);
                intent.putExtra("user", student);
                startActivity(intent);
                finish();
            } else {
                DocumentReference teacherRef = db.collection("teachers").document(userId);
                teacherRef.get().addOnCompleteListener(teacherTask -> {
                    if (teacherTask.isSuccessful() && teacherTask.getResult().exists()) {
                        // User is a teacher
                        Teacher teacher = teacherTask.getResult().toObject(Teacher.class);
                        Intent intent = new Intent(LoginActivity.this, TeacherDashboardActivity.class);

                        //intent.putExtra("user", teacher);
                        startActivity(intent);
                       // finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "User role not found.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
