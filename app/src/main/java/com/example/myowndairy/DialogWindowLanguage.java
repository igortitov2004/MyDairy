package com.example.myowndairy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class DialogWindowLanguage extends DialogFragment {


    private String selectedLanguage;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        final String[] languageNamesArray = {getString(R.string.CONST_NAME_ENGLISH), getString(R.string.CONST_NAME_RUSSIAN)};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.CONST_NAME_LANGUAGE_SELECTION))
                // добавляем переключатели
                .setSingleChoiceItems(languageNamesArray, 1,
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
                                setLocal(getActivity(),"en");
                                getActivity().finish();
                                startActivity(getActivity().getIntent());
                            }else if(selectedLanguage == getString(R.string.CONST_NAME_RUSSIAN)){
                                setLocal(getActivity(),"ru");
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

    public void  setLocal(Activity activity, String langCode){
        Locale locale = new Locale(langCode);
        locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());

    }
}

