package com.example.myowndairy.TasksPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import androidx.fragment.app.DialogFragment;

import com.example.myowndairy.Dialogs.DialogWindowCalendar;
import com.example.myowndairy.Dialogs.DialogWindowTime;
import com.example.myowndairy.Dialogs.DialogWindowForConfirmTask;
import com.example.myowndairy.Dialogs.DialogWindowForReturn;
import com.example.myowndairy.R;


public class FragmentCreateTask extends DialogFragment {
    Button setTaskDate;
    Button setTaskTime;
    Button confirmTask;
    EditText setDate;
    Button returnToFragment;



    EditText setTime;

   DialogWindowTime dialogWindowTime = new DialogWindowTime();
   DialogWindowForReturn dialogWindowForReturn = new DialogWindowForReturn();
   DialogWindowForConfirmTask dialogWindowForConfirmTask = new DialogWindowForConfirmTask();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_create_task, container, false);


        setTaskDate = view.findViewById(R.id.buttonSetTaskDate);
        setTaskTime = view.findViewById(R.id.buttonSetTaskTime);
        confirmTask = view.findViewById(R.id.buttonConfirmTask);
        returnToFragment = view.findViewById(R.id.taskBack);

        setDate = view.findViewById(R.id.setDate);
        setTime = view.findViewById(R.id.setTime);

        setTime.setFocusable(false);
        setDate.setFocusable(false);

        returnToFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialogWindowForReturn);
                dialogWindowForReturn.fragment = new TaskFragment();
            }
        });

        confirmTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialogWindowForConfirmTask);
                dialogWindowForConfirmTask.fragment = new TaskFragment();


            }
        });

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(new DialogWindowCalendar());
            }
        });
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogWindowTime.timeField = R.id.setTime;
                showDialog(dialogWindowTime);
            }
        });


        setTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                showDialog(new DialogWindowCalendar());
            }
        });

        setTaskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogWindowTime.timeField = R.id.setTime;
                showDialog(dialogWindowTime);
            }
        });
        return view;
    }


   public void showDialog(DialogFragment dialogFragment){
       dialogFragment.show((getActivity().getSupportFragmentManager()),"custom");
   }
}
