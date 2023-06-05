package com.example.myowndairy;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class DialogWindowDateTimeEdit extends DialogFragment {

    Button timeEditButton, dataEditButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dialog_window_date_and_time, container, false);

        dataEditButton = view.findViewById(R.id.dateEditButton);
        timeEditButton = view.findViewById(R.id.timeEditButton);

        timeEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                showDialogTimeEdit();
            }
        });

        dataEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                showDialogDataEdit();
            }
        });

        return view;
    }

    public void showDialogTimeEdit(){
        DialogWindowTimeEdit dialog = new DialogWindowTimeEdit();

        dialog.show((getActivity().getSupportFragmentManager()),"timePicker");
    }

    public void showDialogDataEdit(){
        DialogWindowDateEdit dialog = new DialogWindowDateEdit();

        dialog.show((getActivity().getSupportFragmentManager()),"dataPicker");
    }
}
