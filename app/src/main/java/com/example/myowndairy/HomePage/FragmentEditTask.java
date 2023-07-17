package com.example.myowndairy.HomePage;

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
import com.example.myowndairy.Dialogs.DialogWindowForConfirmTask;
import com.example.myowndairy.Dialogs.DialogWindowForDeleteTask;
import com.example.myowndairy.Dialogs.DialogWindowForReturn;
import com.example.myowndairy.Dialogs.DialogWindowTime;
import com.example.myowndairy.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FragmentEditTask extends DialogFragment {
    private Button setTaskTime;
    private Button confirmTask;
    private Button returnToFragmemt;
    private EditText setTime;
    private EditText setDescription;

    // сделать геттеры и сеттерны чтобы не было хуйни
    public String descriptionText;
    public String headerText;
    public String timeText;
    public int idTask;
    private EditText headerEdit;
    private HomeFragment homeFragment;
    private Button deleteTask;

    private DialogWindowTime dialogWindowTime = new DialogWindowTime();
    private DialogWindowForReturn dialogWindowForReturn = new DialogWindowForReturn();
    private DialogWindowForDeleteTask dialogWindowForDeleteTask = new DialogWindowForDeleteTask(this);
    private DialogWindowForConfirmTask dialogWindowForConfirmTask = new DialogWindowForConfirmTask(this);

    public FragmentEditTask(HomeFragment homeFragment){
        this.homeFragment = homeFragment;
    }


    public FragmentEditTask(){
    }

    private DBHelper dbHelper;

    public SQLiteDatabase database;





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

    private void showDialog(DialogFragment dialogFragment){
        dialogFragment.show((getActivity().getSupportFragmentManager()),"custom");
    }
    public void saveTask(){
        Date date = new Date();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_HEADER,headerEdit.getText().toString());
        if(homeFragment.textDayTask==null){
            contentValues.put(DBHelper.KEY_DATE,new SimpleDateFormat("dd/MM/yyyy").format(date));
        } else{
            contentValues.put(DBHelper.KEY_DATE,homeFragment.textDayTask);
        }
        contentValues.put(DBHelper.KEY_TIME, setTime.getText().toString());
        contentValues.put(DBHelper.KEY_DESCRIPTION,setDescription.getText().toString());
        database.update(DBHelper.TABLE_TASKS, contentValues,DBHelper.KEY_ID + "= ?", new String[] {String.valueOf(idTask)});

    }
}
