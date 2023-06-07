package com.example.myowndairy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class FragmentCreateTask extends DialogFragment {

    Button setTaskDate;
    Button setTaskTime;
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState){
//
//
//
//        View view =
//
//        builder.setView(R.layout.fragment_dialog_window_task_creation)
//                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                })
//                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//        return builder.create();
//    }
EditText setDate;
    EditText setTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_task, container, false);

        setTaskDate=view.findViewById(R.id.buttonSetTaskDate);
        setTaskTime=view.findViewById(R.id.buttonSetTaskTime);


        setDate = view.findViewById(R.id.setDate);
        setTime = view.findViewById(R.id.setTime);
        setTime.setFocusable(false);
        setDate.setFocusable(false);

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDate(view);
            }
        });

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogTime(view);
            }
        });


        setTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                showDialogDate(view);
            }
        });

        setTaskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogTime(view);
            }
        });




        return view;
    }

    public void showDialogDate(View view){
        DialogWindowCalendar dialog = new DialogWindowCalendar();


        dialog.show((getActivity().getSupportFragmentManager()),"timePicker");
    }

    public void showDialogTime(View view){
        DialogWindowTime dialog = new DialogWindowTime();

        dialog.show((getActivity().getSupportFragmentManager()),"datePicker");
    }
}
