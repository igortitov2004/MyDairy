package com.example.myowndairy.SettingsPage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.myowndairy.R;

public class DialogWindowNotifications extends DialogFragment {
    private Switch aSwitch;
    private String switchValue = "";
    private String on;
    private String off;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        on = getString(R.string.CONST_NAME_NOTIFICATIONS_ON);

        off = getString(R.string.CONST_NAME_NOTIFICATIONS_OFF);


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
                }else {
                    aSwitch.setText(off);
                    switchValue=off;
                }
            }
        });

        builder.setView(view)
                .setPositiveButton(getString(R.string.CONST_NAME_OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(switchValue == on){
                            Toast.makeText(
                                    getActivity(),
                                    getString(R.string.CONST_NAME_NOTIFICATIONS)+" " + on,
                                    Toast.LENGTH_SHORT).show();
                        }else if(switchValue == off){
                            Toast.makeText(
                                    getActivity(),
                                    getString(R.string.CONST_NAME_NOTIFICATIONS)+" "+ off,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(getString(R.string.CONST_NAME_CANCEL), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }
}
