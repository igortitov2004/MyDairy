package com.example.myowndairy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;



public class FragmentCreateTaskToday extends DialogFragment {

    Button setTaskTime;
    Button confirmTask;

    Button returnToFragmemt;




    EditText setTime;

    DialogWindowTime dialogWindowTime = new DialogWindowTime();
    DialogWindowForReturn dialogWindowForReturn = new DialogWindowForReturn();
    DialogWindowForConfirmTask dialogWindowForConfirmTask = new DialogWindowForConfirmTask();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_create_task_today, container, false);



        setTaskTime = view.findViewById(R.id.buttonSetTaskTimeToday);
        confirmTask = view.findViewById(R.id.buttonConfirmTaskToday);
        returnToFragmemt = view.findViewById(R.id.todayTaskBack);


        setTime = view.findViewById(R.id.setTimeToday);

        setTime.setFocusable(false);

        returnToFragmemt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialogWindowForReturn);
                dialogWindowForReturn.fragment = new HomeFragment();
            }
        });
        confirmTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialogWindowForConfirmTask);
                dialogWindowForConfirmTask.fragment = new HomeFragment();

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
