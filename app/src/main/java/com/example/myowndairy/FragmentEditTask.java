package com.example.myowndairy;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.myowndairy.DB.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FragmentEditTask extends DialogFragment {
    Button setTaskTime;
    Button confirmTask;

    Button returnToFragmemt;




    EditText setTime;

    EditText setDescription;

    String descriptionText;

    String headerText;

    String timeText;

    int idTask;

    public  EditText headerEdit;

    HomeFragment homeFragment;

    Button deleteTask;

    DialogWindowTime dialogWindowTime = new DialogWindowTime();
    DialogWindowForReturn dialogWindowForReturn = new DialogWindowForReturn();

    DialogWindowForDeleteTask dialogWindowForDeleteTask = new DialogWindowForDeleteTask(this);
    DialogWindowForConfirmTask dialogWindowForConfirmTask = new DialogWindowForConfirmTask(this);


    public FragmentEditTask(HomeFragment homeFragment){
        this.homeFragment = homeFragment;
    }

    public FragmentEditTask(){

    }


    DBHelper dbHelper;

    SQLiteDatabase database;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHelper = new DBHelper(this);
        database= dbHelper.getWritableDatabase();

        View view = inflater.inflate(R.layout.fragment_edit_task, container, false);

        deleteTask = view.findViewById(R.id.deleteTask);
        setTaskTime = view.findViewById(R.id.buttonSetTaskTimeEdit);
        confirmTask = view.findViewById(R.id.buttonConfirmTaskEdit);
        returnToFragmemt = view.findViewById(R.id.editTaskBack);
        headerEdit = view.findViewById(R.id.headerEdit);
        setDescription = view.findViewById(R.id.setDescription);

        setTime = view.findViewById(R.id.setTimeToday);

        setTime.setFocusable(false);

        headerEdit.setText(headerText);
        setTime.setText(timeText);
        setDescription.setText(descriptionText);

        deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialogWindowForDeleteTask);
                dialogWindowForDeleteTask.fragment = homeFragment;
//                int delCount = database.delete(DBHelper.TABLE_TASKS,DBHelper.KEY_ID + "= "+ idTask , null);
//                Log.d("mLog", "deleted rows count = " + delCount);

            }
        });


        returnToFragmemt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialogWindowForReturn);
                dialogWindowForReturn.fragment = homeFragment;
            }
        });
        confirmTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialogWindowForConfirmTask);
                dialogWindowForConfirmTask.fragment = homeFragment;
                dialogWindowForConfirmTask.homeFragment = homeFragment;
                dialogWindowForConfirmTask.editableFragment = homeFragment.editTaskFragment;
            }
        });



        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogWindowTime.timeField = R.id.setTimeToday;
                showDialog(dialogWindowTime);
            }
        });


        setTaskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogWindowTime.timeField = R.id.setTimeToday;
                showDialog(dialogWindowTime);
            }
        });
        return view;
    }




    public void showDialog(DialogFragment dialogFragment){
        dialogFragment.show((getActivity().getSupportFragmentManager()),"custom");
    }

    public void saveTask(){
        Date date = new Date();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_HEADER,headerEdit.getText().toString());
        contentValues.put(DBHelper.KEY_DATE,new SimpleDateFormat("dd/MM/yyyy").format(date));
        contentValues.put(DBHelper.KEY_TIME, setTime.getText().toString());
        contentValues.put(DBHelper.KEY_DESCRIPTION,setDescription.getText().toString());
        database.update(DBHelper.TABLE_TASKS, contentValues,DBHelper.KEY_ID + "= ?", new String[] {String.valueOf(idTask)});
    }
}
