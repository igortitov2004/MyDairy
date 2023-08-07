package com.example.myowndairy.SettingsPage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.myowndairy.Interfaces.SetLanguageInterface;
import com.example.myowndairy.R;

import java.util.Locale;

public class DialogWindowLanguage extends DialogFragment implements SetLanguageInterface {

    private String selectedLanguage;



    private int checkedItem;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
//        getLanguage();

        final String[] languageNamesArray = {getString(R.string.CONST_NAME_ENGLISH), getString(R.string.CONST_NAME_RUSSIAN)};
//       // для установки активности языка в меню
//        getLanguage();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(getString(R.string.CONST_NAME_LANGUAGE_SELECTION))
                // добавляем переключатели
                .setSingleChoiceItems(languageNamesArray,-1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int item) {
                                selectedLanguage = languageNamesArray[item];
                            }
                        })
                .setPositiveButton(getString(R.string.CONST_NAME_OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(selectedLanguage != null){
                            Toast.makeText(
                                    getActivity(),
                                    getString(R.string.CONST_NAME_SELECTED_LANGUAGE)+": "
                                            + selectedLanguage,
                                    Toast.LENGTH_SHORT).show();
                            if(selectedLanguage == getString(R.string.CONST_NAME_ENGLISH)){
                                setLocal("en");
                                getActivity().finish();
                                startActivity(getActivity().getIntent());
                            }else if(selectedLanguage == getString(R.string.CONST_NAME_RUSSIAN)){
                                setLocal("ru");
                                getActivity().finish();
                                startActivity(getActivity().getIntent());
                            }
                        }
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
    @Override
    public void setLocal(String langCode){

        // назначение языка
        Locale locale = new Locale(langCode);
        locale.setDefault(locale);
        Resources resources = getActivity().getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());

        // сохранение языка
        SharedPreferences.Editor editor = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE).edit();
        editor.putString("My_Lang",langCode);
        editor.apply();

    }

//    private void getLanguage(){
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Settings", Activity.MODE_PRIVATE);
//        String language = sharedPreferences.getString("My_Lang","");
//        if(language=="en"){
//            checkedItem=0;
//        }else if(language=="ru"){
//            checkedItem=1;
//        }
//    }



}

