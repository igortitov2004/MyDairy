package com.example.myowndairy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class DialogWindowLanguage extends DialogFragment {


    private String selectedLanguage;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        final String[] languageNamesArray = {"English", "Русский"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Выберите язык")
                // добавляем переключатели
                .setSingleChoiceItems(languageNamesArray, 1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int item) {
                                selectedLanguage = languageNamesArray[item];
                            }
                        })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(selectedLanguage != null){
                            Toast.makeText(
                                    getActivity(),
                                    "Выбран язык: "
                                            + selectedLanguage,
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }
}

