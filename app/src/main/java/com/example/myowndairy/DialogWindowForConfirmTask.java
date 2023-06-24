package com.example.myowndairy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Date;

public class DialogWindowForConfirmTask extends DialogFragment{


   public Fragment fragment;


  Tasks tasks = new Tasks();
   DialogWindowTime dialogWindowTime;

   FragmentCreateTaskToday fragmentCreateTaskToday;

   FragmentEditTask fragmentEditTask;

   Fragment editableFragment;


HomeFragment homeFragment;

    public DialogWindowForConfirmTask(FragmentCreateTaskToday fragmentCreateTaskToday) {
        this.fragmentCreateTaskToday = fragmentCreateTaskToday;
    }

    public DialogWindowForConfirmTask(FragmentEditTask fragmentEditTask) {
        this.fragmentEditTask = fragmentEditTask;
    }

    public DialogWindowForConfirmTask(){

    }

//    public DialogWindowForConfirmTask(HomeFragment homeFragment) {
//        this.homeFragment = homeFragment;
//    }




    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Вы уверены?")

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                       if(editableFragment == homeFragment.createEditTaskFragment){
                           fragmentCreateTaskToday.saveTask();
                           fragmentCreateTaskToday.setTime.setText("");
                           fragmentCreateTaskToday.heading.setText("");
                           fragmentCreateTaskToday.description.setText("");
                       } else if(editableFragment == homeFragment.editTaskFragment){
                           fragmentEditTask.saveTask();
                        }

                        replaceFragment(fragment);

                        Toast.makeText(
                                getActivity(),
                                "Задача cохранена!",
                                Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                             dialog.cancel();
                    }
                });

        return builder.create();
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.frame_layout,fragment).commit();
    }

}
