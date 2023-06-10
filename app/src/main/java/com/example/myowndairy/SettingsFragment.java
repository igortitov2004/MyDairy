package com.example.myowndairy;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
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

    Button notificationsEdit;
    Button languageEgit;


    Button themesEdit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        notificationsEdit = view.findViewById(R.id.buttonNotifications);
        languageEgit = view.findViewById(R.id.buttonLanguage);
        themesEdit = view.findViewById(R.id.buttonTheme);





        themesEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showSettingsDialog(new DialogWindowThemes());
            }
        });
        languageEgit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                showSettingsDialog(new DialogWindowLanguage());
            }
        });
        notificationsEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showSettingsDialog(new DialogWindowNotifications());
            }
        });

        return view;
    }

    public void showSettingsDialog(DialogFragment dialogFragment){
        dialogFragment.show((getActivity().getSupportFragmentManager()),"custom");
    }



//    public void showDialogStartDateTime(View view){
//        DialogWindowDateTimeEdit dialog = new DialogWindowDateTimeEdit();
//
//        dialog.show((getActivity().getSupportFragmentManager()),"custom");
//    }
}