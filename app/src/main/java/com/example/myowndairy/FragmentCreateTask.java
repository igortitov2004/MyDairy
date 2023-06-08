package com.example.myowndairy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
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


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//
//        int id = item.getItemId();
//        switch (id) {
//            case android.R.id.home:
//                showDialog(new DialogWindowCalendarForChoiceTask());
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }


    ActivityMainBinding binding;



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
                showDialog(new DialogWindowForConfirmTask());
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
                showDialog(new DialogWindowTime());
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
                showDialog(new DialogWindowTime());
            }
        });
        return view;
    }




   public void showDialog(DialogFragment dialogFragment){
       dialogFragment.show((getActivity().getSupportFragmentManager()),"custom");
   }

}
