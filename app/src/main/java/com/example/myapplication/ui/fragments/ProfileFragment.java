package com.example.myapplication.ui.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.models.Student;
import com.example.myapplication.models.Teacher;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private Student student;
    private Teacher teacher;

    TextView userName;
    EditText username;
    EditText userEmail;
    EditText existingPassword;
    EditText newPassword;
    Button changePasswordButton;
    Button updateProfileButton;

    MaterialCardView existingPassword_card;
    MaterialCardView newPassword_card;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Handle arguments if needed
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        userName = view.findViewById(R.id.name_textview);
        username = view.findViewById(R.id.username_edittext);
        userEmail = view.findViewById(R.id.useremail_edittext);
        changePasswordButton = view.findViewById(R.id.resetPasswordButton);
        updateProfileButton = view.findViewById(R.id.updateProfileButton);
        existingPassword = view.findViewById(R.id.currentpassword_edittext);
        newPassword = view.findViewById(R.id.newpassword_edittext);

        newPassword_card = view.findViewById(R.id.card3);
        existingPassword_card = view.findViewById(R.id.card4);

        existingPassword_card.setVisibility(View.GONE);
        newPassword_card.setVisibility(View.GONE);

        // Disable editing of userEmail EditText
        userEmail.setEnabled(false);
        userEmail.setFocusable(false);
        userEmail.setFocusableInTouchMode(false);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (existingPassword_card.getVisibility() == View.GONE && newPassword_card.getVisibility() == View.GONE) {
                    existingPassword_card.setVisibility(View.VISIBLE);
                    newPassword_card.setVisibility(View.VISIBLE);
                    changePasswordButton.setText("Hide");
                } else {
                    existingPassword_card.setVisibility(View.GONE);
                    newPassword_card.setVisibility(View.GONE);
                    changePasswordButton.setText("Reset Password");
                }
            }
        });

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser();
            }
        });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String userId = currentUser.getUid();

            // Fetch student data from Firestore
            DocumentReference studentRef = db.collection("students").document(userId);
            studentRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        student = document.toObject(Student.class);
                        updateStudentUI();
                    }
                }
            });

            // Fetch teacher data from Firestore if student is null
            if (student == null) {
                DocumentReference teacherRef = db.collection("teachers").document(userId);
                teacherRef.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            teacher = document.toObject(Teacher.class);
                            updateTeacherUI();
                        }
                    }
                });
            } else {
                Toast.makeText(getContext(), "No user", Toast.LENGTH_SHORT).show();
            }
        }

        return view;
    }

    public void updateTeacherUI() {
        if (teacher != null) {
            username.setText(teacher.getName());
            userEmail.setText(teacher.getEmail());
            userName.setText(teacher.getName());
        }
    }

    public void updateStudentUI() {
        if (student != null) {
            username.setText(student.getName());
            userEmail.setText(student.getEmail());
            userName.setText(student.getName());
        }
    }

    public void saveUser() {
        String newName = username.getText().toString().trim();
        String currentPassword = existingPassword.getText().toString().trim();
        String newPasswordText = newPassword.getText().toString().trim();

        if (TextUtils.isEmpty(newName)) {
            Toast.makeText(getContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPassword_card.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(newPasswordText)) {
            if (newPasswordText.length() < 6 || newPasswordText.length() > 15) {
                Toast.makeText(getContext(), "Password must be 6-15 characters long", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(currentPassword)) {
                Toast.makeText(getContext(), "Current password is required to change password", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String userId = currentUser.getUid();
            Map<String, Object> updates = new HashMap<>();

            if (student != null) {
                updates.put("name", newName);
                // Do not update email here
                db.collection("students").document(userId).update(updates)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                updatePassword(currentUser, currentPassword, newPasswordText);
                            } else {
                                Toast.makeText(getContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else if (teacher != null) {
                updates.put("name", newName);
                // Do not update email here
                db.collection("teachers").document(userId).update(updates)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                updatePassword(currentUser, currentPassword, newPasswordText);
                            } else {
                                Toast.makeText(getContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }

    private void updatePassword(FirebaseUser currentUser, String currentPassword, String newPasswordText) {
        if (TextUtils.isEmpty(currentPassword) || TextUtils.isEmpty(newPasswordText)) {
            return;
        }

        AuthCredential credential = EmailAuthProvider.getCredential(currentUser.getEmail(), currentPassword);
        currentUser.reauthenticate(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                currentUser.updatePassword(newPasswordText).addOnCompleteListener(passwordUpdateTask -> {
                    if (passwordUpdateTask.isSuccessful()) {
                        // Update password in Firestore
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        String userId = currentUser.getUid();
                        Map<String, Object> updates = new HashMap<>();
                        updates.put("password", newPasswordText); // Assuming there's a password field

                        if (student != null) {
                            db.collection("students").document(userId).update(updates)
                                    .addOnCompleteListener(updateTask -> {
                                        if (updateTask.isSuccessful()) {
                                            Toast.makeText(getContext(), "Password updated in Firestore", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(), "Failed to update password in Firestore", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else if (teacher != null) {
                            db.collection("teachers").document(userId).update(updates)
                                    .addOnCompleteListener(updateTask -> {
                                        if (updateTask.isSuccessful()) {
                                            Toast.makeText(getContext(), "Password updated in Firestore", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(), "Failed to update password in Firestore", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }

                        Toast.makeText(getContext(), "Password updated", Toast.LENGTH_SHORT).show();
                        existingPassword_card.setVisibility(View.GONE);
                        newPassword_card.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getContext(), "Failed to update password", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), "Reauthentication failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
