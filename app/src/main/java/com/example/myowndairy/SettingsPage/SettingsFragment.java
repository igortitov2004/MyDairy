package com.example.myowndairy.SettingsPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.myowndairy.Interfaces.SetThemeInterface;
import com.example.myowndairy.R;


public class SettingsFragment extends Fragment implements SetThemeInterface {

    Button notificationsEdit;
    Button languageEdit;
    Switch themeSwitch;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        notificationsEdit = view.findViewById(R.id.buttonNotifications);
        languageEdit = view.findViewById(R.id.buttonLanguage);
        themeSwitch = view.findViewById(R.id.themeSwitch);

         setTheme();
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night",true);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night",false);
                }
                editor.apply();
                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
        });


        languageEdit.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void setTheme() {
        boolean theme;
        sharedPreferences = getActivity().getSharedPreferences("MODE", Context.MODE_PRIVATE);
        theme = sharedPreferences.getBoolean("night",false);
        if(theme){
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            themeSwitch.setChecked(true);

        }
    }




//    public void showDialogStartDateTime(View view){
//        DialogWindowDateTimeEdit dialog = new DialogWindowDateTimeEdit();
//
//        dialog.show((getActivity().getSupportFragmentManager()),"custom");
//    }
}