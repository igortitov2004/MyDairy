package com.example.myowndairy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

public class FragmentEditTask extends DialogFragment {
    Button setTaskTime;
    Button confirmTask;

    Button returnToFragmemt;




    EditText setTime;

    EditText setDescription;

    String descriptionText;

    String headerText;

    String timeText;

    public  EditText headerEdit;

    HomeFragment homeFragment;



    DialogWindowTime dialogWindowTime = new DialogWindowTime();
    DialogWindowForReturn dialogWindowForReturn = new DialogWindowForReturn();
    DialogWindowForConfirmTask dialogWindowForConfirmTask = new DialogWindowForConfirmTask();


    public FragmentEditTask(HomeFragment homeFragment){
        this.homeFragment = homeFragment;
    }

    public FragmentEditTask(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_edit_task, container, false);

        setTaskTime = view.findViewById(R.id.buttonSetTaskTimeEdit);
        confirmTask = view.findViewById(R.id.buttonConfirmTaskEdit);
        returnToFragmemt = view.findViewById(R.id.editTaskBack);
        headerEdit = view.findViewById(R.id.headerEdit);
        setDescription = view.findViewById(R.id.setDescription);

        setTime = view.findViewById(R.id.setTimeToday);

        setTime.setFocusable(false);

        headerEdit.setText(headerText);
        setTime.setText(timeText);
        setDescription.setText(descriptionText);


        returnToFragmemt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialogWindowForReturn);
                dialogWindowForReturn.fragment = homeFragment;
            }
        });
        confirmTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialogWindowForConfirmTask);
                dialogWindowForConfirmTask.fragment = homeFragment;

            }
        });



        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogWindowTime.timeField = R.id.setTimeToday;
                showDialog(dialogWindowTime);
            }
        });


        setTaskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogWindowTime.timeField = R.id.setTimeToday;
                showDialog(dialogWindowTime);
            }
        });
        return view;
    }




    public void showDialog(DialogFragment dialogFragment){
        dialogFragment.show((getActivity().getSupportFragmentManager()),"custom");
    }
}
