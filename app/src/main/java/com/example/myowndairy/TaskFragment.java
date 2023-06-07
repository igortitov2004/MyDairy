package com.example.myowndairy;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class TaskFragment extends Fragment {
    Button calendarMainButton;
    Button addTask;

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


}