package com.example.myowndairy.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import java.text.ParseException;
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


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.CONST_NAME_CONFIRMATION))

                .setPositiveButton(getString(R.string.CONST_NAME_OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                       try{
                           if(editableFragment == homeFragment.fragmentCreateTaskToday){
                               fragmentCreateTaskToday.saveTask();
                               if(homeFragment.dayOfTaskFromTaskFragment==null){
                                   if(preferences.contains("checked") && preferences.getBoolean("checked",false) == true) {
                                       fragmentCreateTaskToday.setAlarm();
                                   }
                               }
                           }else if(editableFragment == homeFragment.editTaskFragment){
                               fragmentEditTask.saveTask();
                               if(preferences.contains("checked") && preferences.getBoolean("checked",false) == true) {
                                   fragmentEditTask.setAlarm();
                               }
                           }
                       }catch (RuntimeException e){
                           if(editableFragment == taskFragment.fragmentCreateTask ){
                               fragmentCreateTask.saveTask();
                               if(preferences.contains("checked") && preferences.getBoolean("checked",false) == true) {
                                   fragmentCreateTask.setAlarm();
                               }
                           }
                       } catch (ParseException e) {
                           throw new RuntimeException(e);
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
