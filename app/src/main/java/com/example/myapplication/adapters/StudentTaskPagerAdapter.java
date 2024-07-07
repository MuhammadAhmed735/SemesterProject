package com.example.myapplication.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.ui.fragments.ProjectsTabFragment;
import com.example.myapplication.ui.fragments.StudentProjectTabFragment;
import com.example.myapplication.ui.fragments.StudentTaskTabFragment;
import com.example.myapplication.ui.fragments.TasksTabFragment;

public class StudentTaskPagerAdapter extends FragmentStateAdapter {

    public StudentTaskPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new StudentTaskTabFragment();
            case 1:
                return new StudentProjectTabFragment();
            default:
                return null;
        }
    }




    @Override
    public int getItemCount() {
        return 2;
    }
}

