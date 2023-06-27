package com.example.myowndairy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
        builder.setTitle(getString(R.string.CONST_NAME_CONFIRMATION))

                .setPositiveButton(getString(R.string.CONST_NAME_OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                       if(editableFragment == homeFragment.fragmentCreateTaskToday){
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
                                getString(R.string.CONST_NAME_TASK_SAVED),
                                Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton(getString(R.string.CONST_NAME_CANCEL), new DialogInterface.OnClickListener() {
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
