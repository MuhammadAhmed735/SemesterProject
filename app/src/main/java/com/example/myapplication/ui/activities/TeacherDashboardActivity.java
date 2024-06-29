package com.example.myapplication.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.fragments.CoursesFragment;
import com.example.myapplication.ui.fragments.ProfileFragment;
import com.example.myapplication.ui.fragments.TasksFragment;
import com.example.myapplication.ui.fragments.TeacherTasksList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TeacherDashboardActivity extends AppCompatActivity {

    BottomNavigationView teacher_navBar;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        toolbar = findViewById(R.id.appbar);


        setSupportActionBar(toolbar);

        toolbar.getOverflowIcon().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,new TeacherTasksList()).commit();


        teacher_navBar = findViewById(R.id.teacher_bottom_nav);

        teacher_navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int item_id = item.getItemId();
                if(item_id==R.id.tasksList)
                {
                    selectedFragment = new TeacherTasksList();
                }
                else if (item_id == R.id.profile)
                {
                    selectedFragment = new ProfileFragment();
                } else if (item_id ==R.id.courses)
                {
                    selectedFragment = new CoursesFragment();
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
}
