package com.example.myowndairy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SettingsFragment extends Fragment {
//      Button show;
//      Dialog dialog;
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        show=getActivity().findViewById(R.id.buttonDate);
////        dialog=new Dialog(SettingsFragment.this);
//        show.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showCustomDialog();
//            }
//        });
//
//    }
//
//    public void showCustomDialog(){
//        dialog.setContentView(R.layout.custom_dialog_settings);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
////        Button  = dialog.findViewById();
//        EditText timeE,dateE;
//        timeE= dialog.findViewById(R.id.set_time);
//        dateE = dialog.findViewById(R.id.set_date);
//         dialog.show();
//
//    }
    Button dateTimeSet;
    Button notificationsEdit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        dateTimeSet = view.findViewById(R.id.buttonDate);


        notificationsEdit = view.findViewById(R.id.buttonNotifications);
        dateTimeSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                showDialogStartDateTime(view);
            }
        });
        notificationsEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDialogNotifications(view);
            }
        });

        return view;
    }


    public void showDialogNotifications(View view){
        DialogWindowNotifications dialog = new DialogWindowNotifications();

        dialog.show((getActivity().getSupportFragmentManager()),"custom");
    }

    public void showDialogStartDateTime(View view){
        DialogWindowDateTimeEdit dialog = new DialogWindowDateTimeEdit();

        dialog.show((getActivity().getSupportFragmentManager()),"custom");
    }
}