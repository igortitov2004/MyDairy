package com.example.myowndairy.Dialogs;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myowndairy.AlarmReceiver;
import com.example.myowndairy.DB.DBHelper;
import com.example.myowndairy.HomePage.FragmentEditTask;
import com.example.myowndairy.HomePage.HomeFragment;
import com.example.myowndairy.R;

public class DialogWindowForDeleteTask extends DialogFragment {

    public Fragment fragment;

    FragmentEditTask fragmentEditTask;

    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    public DialogWindowForDeleteTask(FragmentEditTask fragmentEditTask) {
        this.fragmentEditTask = fragmentEditTask;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.CONST_NAME_CONFIRMATION))

                .setPositiveButton(getString(R.string.CONST_NAME_OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        replaceFragment(fragment);

                        cancelAlarm(fragmentEditTask.idTask);
                        // Удаление задачи из БД
                        fragmentEditTask.database.delete(DBHelper.TABLE_TASKS,DBHelper.KEY_ID + "= "+ fragmentEditTask.idTask , null);

                        Toast.makeText(
                                getActivity(),
                                getString(R.string.CONST_NAME_TASK_DELETED),
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

    public void replaceFragment2(){
        Fragment fragment = new HomeFragment();
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.frame_layout,fragment).commit();
    }

    public void cancelAlarm(int notifId){
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(), notifId, intent, PendingIntent.FLAG_IMMUTABLE);
        if(alarmManager == null){
            alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        }
        alarmManager.cancel(pendingIntent);

//        Toast.makeText(getContext(),"ALARM OFF",Toast.LENGTH_SHORT).show();
    }

}
