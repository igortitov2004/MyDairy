package com.example.myowndairy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.ButtonBarLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.myowndairy.databinding.ActivityMainBinding;
import com.google.android.material.button.MaterialButton;

public class FragmentCreateTask extends DialogFragment {
    Button setTaskDate;
    Button setTaskTime;
    Button confirmTask;
    EditText setDate;
    EditText setTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_task, container, false);

        setTaskDate = view.findViewById(R.id.buttonSetTaskDate);
        setTaskTime = view.findViewById(R.id.buttonSetTaskTime);
        confirmTask = view.findViewById(R.id.buttonConfirmTask);

        setDate = view.findViewById(R.id.setDate);
        setTime = view.findViewById(R.id.setTime);

        setTime.setFocusable(false);
        setDate.setFocusable(false);

        confirmTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogConfirmTask();
            }
        });

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDate(view);
            }
        });

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogTime(view);
            }
        });


        setTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                showDialogDate(view);
            }
        });

        setTaskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogTime(view);
            }
        });
        return view;
    }

    

    public void showDialogConfirmTask(){
        DialogWindowForConfirmTask dialog = new DialogWindowForConfirmTask();

        dialog.show((getActivity().getSupportFragmentManager()),"custom");
    }

    public void showDialogDate(View view){
        DialogWindowCalendar dialog = new DialogWindowCalendar();

        dialog.show((getActivity().getSupportFragmentManager()),"timePicker");
    }

    public void showDialogTime(View view){
        DialogWindowTime dialog = new DialogWindowTime();

        dialog.show((getActivity().getSupportFragmentManager()),"datePicker");
    }
}
