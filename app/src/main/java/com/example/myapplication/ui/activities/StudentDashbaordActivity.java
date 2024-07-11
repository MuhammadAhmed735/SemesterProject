package com.example.myapplication.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.models.Student;
import com.example.myapplication.models.Teacher;
import com.google.firebase.auth.FirebaseAuth;
import com.example.myapplication.R;
import com.example.myapplication.ui.fragments.CoursesFragment;
import com.example.myapplication.ui.fragments.ProfileFragment;

import com.example.myapplication.ui.fragments.StudentTaskList;
import com.example.myapplication.ui.fragments.TasksFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentDashbaordActivity extends AppCompatActivity {


    BottomNavigationView student_navBar;
    private FirebaseAuth auth;
    Student loggedInStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashbaord);





        auth = FirebaseAuth.getInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,new StudentTaskList()).commit();


        student_navBar = findViewById(R.id.std_bottom_nav);

        student_navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int item_id = item.getItemId();
                if(item_id==R.id.tasksList)
                {
                    selectedFragment = new StudentTaskList();
                }
                else if (item_id == R.id.profile)
                {
               //     Toast.makeText(getApplicationContext(),loggedInStudent.getName(),Toast.LENGTH_SHORT).show();
                    selectedFragment = new ProfileFragment();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid Selection",Toast.LENGTH_SHORT).show();
                }


                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }


                return false;

            }


        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.std_options_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            auth.signOut();
            Intent intent = new Intent(StudentDashbaordActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the activity stack
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}