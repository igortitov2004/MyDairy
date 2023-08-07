package com.example.myowndairy.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myowndairy.HomePage.FragmentCreateTaskToday;
import com.example.myowndairy.R;
import com.example.myowndairy.TasksPage.FragmentCreateTask;

public class DialogWindowForReturn extends DialogFragment {

    public Fragment fragment;

    public FragmentCreateTaskToday fragmentCreateTaskToday;

    public DialogWindowForReturn(FragmentCreateTaskToday fragmentCreateTaskToday) {
        this.fragmentCreateTaskToday = fragmentCreateTaskToday;
    }
    public DialogWindowForReturn(){

    }
    public FragmentCreateTask fragmentCreateTask;
    public DialogWindowForReturn(FragmentCreateTask fragmentCreateTask) {
        this.fragmentCreateTask = fragmentCreateTask;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.CONST_NAME_CONFIRMATION_RETURN))

                .setPositiveButton(getString(R.string.CONST_NAME_OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(fragmentCreateTaskToday!=null){
                            fragmentCreateTaskToday.heading.setText(null);
                            fragmentCreateTaskToday.description.setText(null);
                            fragmentCreateTaskToday.setTime.setText(null);
                        }
                        if(fragmentCreateTask!=null){
                            fragmentCreateTask.description.setText(null);
                            fragmentCreateTask.heading.setText(null);
                            fragmentCreateTask.setDate.setText(null);
                            fragmentCreateTask.setTime.setText(null);
                        }

                        replaceFragment(fragment);
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
