package com.example.myowndairy;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class TaskFragment extends Fragment implements RecycleViewInterface {
    Button calendarMainButton;


    FloatingActionButton addTask;

    ArrayList<Tasks> tasksArrayList;
    private String[] tasksHeading;
    private RecyclerView recyclerview;

    private String[] tasksDate;
    private String[] tasksTime;
    private String[] tasksDescription;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        calendarMainButton = view.findViewById(R.id.calendarMainButton);
        addTask = view.findViewById(R.id.addTask);

        calendarMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                showDialogCalendarForChoiceTask();
            }
        });
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment createTaskFragment = new FragmentCreateTask();

                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frame_layout,createTaskFragment).commit();
            }
        });
        return view;
    }


    public void showDialogCalendarForChoiceTask(){
        DialogWindowCalendarForChoiceTask dialog = new DialogWindowCalendarForChoiceTask();

        dialog.show((getActivity().getSupportFragmentManager()),"dataPicker");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataInitialize();
        recyclerview = view.findViewById(R.id.recycleView);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        MyAdapter myAdapter = new MyAdapter(getContext(),tasksArrayList,this);
        recyclerview.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    private void dataInitialize() {
        tasksArrayList = new ArrayList<>();
        tasksHeading = new String[]{
                getString(R.string.task_1),
                getString(R.string.task_2),
                getString(R.string.task_3),
                getString(R.string.task_4),
                getString(R.string.task_5)
           };

        tasksDate = new String[]{
                "12.03.2023",
                "13.03.2023",
                "14.03.2023",
                "15.03.2023",
                "16.03.2023"
        };

        tasksTime = new String[]{
                "12.00",
                "13.00",
                "14.00",
                "15.00",
                "16.00"

        };

        tasksDescription = new String[]{
                "Подышать",
                "Поесть",
                "Попить",
                "Полежать",
                "Походить"
        };

        for (int counter = 0;counter < tasksHeading.length;counter++){
            Tasks tasks = new Tasks(counter,tasksHeading[counter],tasksDate[counter],tasksTime[counter],tasksDescription[counter]);
            tasksArrayList.add(tasks);
        }
    }


    @Override
    public void onItemClick(Tasks tasks) {

    }
}