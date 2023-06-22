package com.example.myowndairy;

import android.content.ContentValues;
import android.database.Cursor;
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


public class FragmentCreateTaskToday extends DialogFragment {
    Button setTaskTime;
    Button confirmTask;
    Button returnToFragmemt;
    EditText setTime;
    Tasks tasks;

    HomeFragment homeFragment;
    DialogWindowTime dialogWindowTime = new DialogWindowTime();
    DialogWindowForReturn dialogWindowForReturn = new DialogWindowForReturn();
    DialogWindowForConfirmTask dialogWindowForConfirmTask = new DialogWindowForConfirmTask();

    public FragmentCreateTaskToday(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    public EditText getHeading() {
        return heading;
    }

    public EditText getDescription() {
        return description;
    }



    EditText heading;
    EditText description;

    DBHelper dbHelper;

    SQLiteDatabase database;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbHelper = new DBHelper(this);
        database= dbHelper.getWritableDatabase();


        View view = inflater.inflate(R.layout.fragment_create_task_today, container, false);

        setTaskTime = view.findViewById(R.id.buttonSetTaskTimeToday);
        confirmTask = view.findViewById(R.id.buttonConfirmTaskToday);
        returnToFragmemt = view.findViewById(R.id.todayTaskBack);
        heading = view.findViewById(R.id.headerToday);
        description = view.findViewById(R.id.descriptionToday);


        setTime = view.findViewById(R.id.setTimeToday);

        setTime.setFocusable(false);



        ContentValues contentValues = new ContentValues();



//        dataInitialize();




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
//                tasks = new Tasks();
//                tasks.setTime(setTime.getText().toString());
//                tasks.setDate("12.02.2023");
//                tasks.setHeading(heading.getText().toString());
//                tasks.setDescription(description.getText().toString());

                contentValues.put(DBHelper.KEY_HEADER,heading.getText().toString());
                contentValues.put(DBHelper.KEY_DATE,"12/12/2023");
                contentValues.put(DBHelper.KEY_TIME, setTime.getText().toString());
                contentValues.put(DBHelper.KEY_DESCRIPTION,description.getText().toString());
                database.insert(DBHelper.TABLE_TASKS,null,contentValues);

                setTime.setText("");
                heading.setText("");
                description.setText("");
//              heading.getText().toString();
//              description.getText().toString();
                showDialog(dialogWindowForConfirmTask);
                dialogWindowForConfirmTask.fragment = homeFragment;
                dialogWindowForConfirmTask.homeFragment = homeFragment;



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
//        dataCheck();
        return view;
    }

    public void showDialog(DialogFragment dialogFragment){
        dialogFragment.show((getActivity().getSupportFragmentManager()),"custom");
    }

    public void dataCheck() {
        Cursor cursor = database.query(DBHelper.TABLE_TASKS, null,null,null,null,null,null);
        if(cursor.moveToFirst()){

            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int headerIndex = cursor.getColumnIndex(DBHelper.KEY_HEADER);
            int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
            int timeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
            int descriptionIndex = cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION);
            do{
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", header = " + cursor.getString(headerIndex)+
                        ", date = " + cursor.getString(dateIndex)+
                        ", time = " + cursor.getString(timeIndex)+
                        ", description = " + cursor.getString(descriptionIndex));
            }while (cursor.moveToNext());

        }else{
            Log.d("mLog", "0 rows");
        }

        cursor.close();
        dbHelper.close();
    }
}
