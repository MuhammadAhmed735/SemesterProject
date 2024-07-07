package com.example.myapplication.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.myapplication.R;
import com.example.myapplication.adapters.TaskListAdapter;
import com.example.myapplication.models.Task;
import com.example.myapplication.ui.activities.AddTaskActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class StudentTaskTabFragment extends Fragment implements TaskListAdapter.OnItemClickListener {

    private RecyclerView studentTaskList;
    private TaskListAdapter adapter;

    private List<Task> tasks = new ArrayList<>();

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    public StudentTaskTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_task_tab, container, false);

        studentTaskList = view.findViewById(R.id.teacher_task_list_tab);


        studentTaskList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TaskListAdapter(tasks, this);
        studentTaskList.setAdapter(adapter);


        loadTasks();

        return view;
    }

    private void loadTasks() {
        firestore.collection("tasks")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot value, FirebaseFirestoreException error) {
                        if (error != null) {
                            Toast.makeText(getContext(), "Error loading tasks", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        tasks.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            Task task = doc.toObject(Task.class);
                            tasks.add(task);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onItemClick(Task task) {
        ProjectDetailFragment fragment = ProjectDetailFragment.newInstance(task);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
