package com.example.myowndairy.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myowndairy.HomePage.FragmentCreateTaskToday;
import com.example.myowndairy.HomePage.FragmentEditTask;
import com.example.myowndairy.HomePage.HomeFragment;
import com.example.myowndairy.R;
import com.example.myowndairy.TasksPage.FragmentCreateTask;
import com.example.myowndairy.TasksPage.TaskFragment;

import java.io.FileNotFoundException;
import java.util.Calendar;

public class DialogWindowForConfirmTask extends DialogFragment{


   public Fragment fragment;




   private FragmentCreateTaskToday fragmentCreateTaskToday;

   FragmentEditTask fragmentEditTask;

  public Fragment editableFragment;


public HomeFragment homeFragment;

public TaskFragment taskFragment;

    public FragmentCreateTask fragmentCreateTask;
    public DialogWindowForConfirmTask(FragmentCreateTask fragmentCreateTask) {
        this.fragmentCreateTask = fragmentCreateTask;
    }

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

                       try{
                           if(editableFragment == taskFragment.fragmentCreateTask ){
                               fragmentCreateTask.saveTask();
                               try {
                                   fragmentCreateTask.setAlarm();
                               } catch (FileNotFoundException ex) {
                                   throw new RuntimeException(ex);
                               }
                           }
                       }catch (RuntimeException e){
                           if(editableFragment == homeFragment.fragmentCreateTaskToday){

                               fragmentCreateTaskToday.saveTask();
                               if(homeFragment.dayOfTaskFromTaskFragment==null){
                                   try {
                                       fragmentCreateTaskToday.setAlarm();
                                   } catch (FileNotFoundException ex) {
                                       throw new RuntimeException(ex);
                                   }
                               }


                           }else if(editableFragment == homeFragment.editTaskFragment){
                               fragmentEditTask.saveTask();


                           }
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
