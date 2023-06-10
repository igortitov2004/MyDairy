package com.example.myowndairy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class DialogWindowThemes extends DialogFragment {

    Button themeEdit;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setView(R.layout.fragment_dialog_window_themes)
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        themeEdit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_palette_24, 0, R.drawable.baseline_keyboard_arrow_right_24, 0);

                    }
                });

        return builder.create();
    }
}
