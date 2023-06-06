package com.example.myowndairy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

        calendarMainButton=view.findViewById(R.id.calendarMainButton);
        addTask = view.findViewById(R.id.addTask);

        calendarMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                showDialogDataEdit();
            }
        });
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddTask();
            }
        });


        return view;
    }

    public void showDialogAddTask(){
        DialogWindowCreateTask dialog = new DialogWindowCreateTask();

        dialog.show((getActivity().getSupportFragmentManager()),"custom");
    }

    public void showDialogDataEdit(){
        DialogWindowCalendar dialog = new DialogWindowCalendar();

        dialog.show((getActivity().getSupportFragmentManager()),"dataPicker");
    }
//    public static CalendarFragment newInstance(String param1, String param2) {
//        CalendarFragment fragment = new CalendarFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//

}