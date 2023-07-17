package com.example.myowndairy.TasksPage;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import androidx.fragment.app.DialogFragment;

import com.example.myowndairy.DB.DBHelper;
import com.example.myowndairy.Dialogs.DialogWindowCalendar;
import com.example.myowndairy.Dialogs.DialogWindowTime;
import com.example.myowndairy.Dialogs.DialogWindowForConfirmTask;
import com.example.myowndairy.Dialogs.DialogWindowForReturn;
import com.example.myowndairy.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class FragmentCreateTask extends DialogFragment {
   private Button setTaskDate;
   private Button setTaskTime;
   private Button confirmTask;
   private EditText setDate;
   private Button returnToFragment;

   private EditText setTime;

   private DialogWindowTime dialogWindowTime = new DialogWindowTime();

   private DialogWindowCalendar dialogWindowCalendar = new DialogWindowCalendar();
   private DialogWindowForReturn dialogWindowForReturn = new DialogWindowForReturn();
   private DialogWindowForConfirmTask dialogWindowForConfirmTask = new DialogWindowForConfirmTask(this);


   private TaskFragment taskFragment;

   public EditText heading;
   public EditText description;

    private DBHelper dbHelper;

    private SQLiteDatabase database;

    public FragmentCreateTask(TaskFragment taskFragment) {
        this.taskFragment = taskFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        View view = inflater.inflate(R.layout.fragment_create_task, container, false);


        setTaskDate = view.findViewById(R.id.buttonSetTaskDate);
        setTaskTime = view.findViewById(R.id.buttonSetTaskTime);
        confirmTask = view.findViewById(R.id.buttonConfirmTask);
        returnToFragment = view.findViewById(R.id.taskBack);

        heading = view.findViewById(R.id.heading);
        description = view.findViewById(R.id.description);

        setDate = view.findViewById(R.id.setDate);
        setTime = view.findViewById(R.id.setTime);

        setTime.setFocusable(false);
        setDate.setFocusable(false);

        returnToFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialogWindowForReturn);
                dialogWindowForReturn.fragment = taskFragment;
            }
        });

        confirmTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialogWindowForConfirmTask);
                dialogWindowForConfirmTask.taskFragment = taskFragment;
                dialogWindowForConfirmTask.fragment = taskFragment;
                dialogWindowForConfirmTask.editableFragment = taskFragment.fragmentCreateTask;



            }
        });

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialogWindowCalendar);
            }
        });
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogWindowTime.timeField = R.id.setTime;
                showDialog(dialogWindowTime);
            }
        });


        setTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                showDialog(dialogWindowCalendar);
            }
        });

        setTaskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogWindowTime.timeField = R.id.setTime;
                showDialog(dialogWindowTime);
            }
        });
        return view;
    }


   public void showDialog(DialogFragment dialogFragment){
       dialogFragment.show((getActivity().getSupportFragmentManager()),"custom");
   }
    public void saveTask(){

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_HEADER,heading.getText().toString());
        contentValues.put(DBHelper.KEY_TIME, setTime.getText().toString());
        contentValues.put(DBHelper.KEY_DATE,setDate.getText().toString());
        contentValues.put(DBHelper.KEY_DESCRIPTION,description.getText().toString());
        database.insert(DBHelper.TABLE_TASKS,null,contentValues);

        setTime.setText(null);
        setDate.setText(null);
        heading.setText(null);
        description.setText(null);
    }





}
