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

public class TasksTabFragment extends Fragment implements TaskListAdapter.OnItemClickListener {

    private RecyclerView teacherTaskList;
    private TaskListAdapter adapter;
    private ExtendedFloatingActionButton addTaskButton;
    private List<Task> tasks = new ArrayList<>();

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    public TasksTabFragment() {
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
        View view = inflater.inflate(R.layout.fragment_tasks_tab, container, false);

        teacherTaskList = view.findViewById(R.id.teacher_task_list_tab);
        addTaskButton = view.findViewById(R.id.add_task_button);

        teacherTaskList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TaskListAdapter(tasks, this);
        teacherTaskList.setAdapter(adapter);

        addTaskButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), AddTaskActivity.class);
            startActivity(intent);
        });

        loadTasks();

        return view;
    }

    private void loadTasks() {
        String teacherId = auth.getCurrentUser().getUid();
        firestore.collection("tasks")
                .whereEqualTo("teacherId", teacherId)
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
        TaskDetailFragment fragment = TaskDetailFragment.newInstance(task);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
