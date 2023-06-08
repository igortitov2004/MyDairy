package com.example.myowndairy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class DialogWindowNotifications extends DialogFragment {
    Switch aSwitch;

    String switchValue = "";
   private final String on = "Включены";
   private final String off = "Выключены";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_dialog_window_notifications,null);

        aSwitch = view.findViewById(R.id.switchNotifications);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    aSwitch.setText(on);
                   switchValue=on;
                }else{
                    aSwitch.setText(off);
                    switchValue=off;
                }
            }
        });

        builder.setView(view)
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       if(switchValue==on){
                           Toast.makeText(
                                   getActivity(),
                                   "Уведомления " + on,
                                   Toast.LENGTH_SHORT).show();
                       }else if(switchValue==off){
                           Toast.makeText(
                                   getActivity(),
                                   "Уведомления " + off,
                                   Toast.LENGTH_SHORT).show();
                       }

                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });



        return builder.create();
    }
}
