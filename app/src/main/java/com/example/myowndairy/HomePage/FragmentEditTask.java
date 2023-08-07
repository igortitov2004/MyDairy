package com.example.myowndairy.HomePage;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
    private EditText description;

    // сделать геттеры и сеттерны чтобы не было хуйни
    public String descriptionText;
    public String headerText;
    public String timeText;
    public int idTask;
    private EditText heading;
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
        heading = view.findViewById(R.id.headerEdit);
        description = view.findViewById(R.id.setDescription);

        setTime = view.findViewById(R.id.setTimeToday);

        setTime.setFocusable(false);

        heading.setText(headerText);
        setTime.setText(timeText);
        description.setText(descriptionText);

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
                if(isEmpty(heading,description,setTime)){
                    Toast.makeText(
                            getActivity(),
                            getString(R.string.CONST_NAME_ISEMPTY_FIELD),
                            Toast.LENGTH_SHORT).show();
                }else {
                    showDialog(dialogWindowForConfirmTask);
                    dialogWindowForConfirmTask.fragment = homeFragment;
                    dialogWindowForConfirmTask.homeFragment = homeFragment;
                    dialogWindowForConfirmTask.editableFragment = homeFragment.editTaskFragment;
                }

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

    private boolean isEmpty(EditText et1,EditText et2,EditText et3){
        if(TextUtils.isEmpty(et1.getText()) || TextUtils.isEmpty(et2.getText()) || TextUtils.isEmpty(et3.getText())){
            return true;
        }
        return false;
    }

//    private boolean isAllFilled(EditText et1,EditText et2,EditText et3){
//        if(TextUtils.isEmpty(et1.getText()) && TextUtils.isEmpty(et2.getText()) && TextUtils.isEmpty(et3.getText())){
//            return true;
//        }
//        return false;
//    }
    public void replaceFragment(Fragment fragment){
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.frame_layout,fragment).commit();
    }

    private void showDialog(DialogFragment dialogFragment){
        dialogFragment.show((getActivity().getSupportFragmentManager()),"custom");
    }
    public void saveTask(){
        Date date = new Date();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_HEADER, heading.getText().toString());
        if(homeFragment.dayOfTaskFromTaskFragment ==null){
            contentValues.put(DBHelper.KEY_DATE,new SimpleDateFormat("dd/MM/yyyy").format(date));
        } else{
            contentValues.put(DBHelper.KEY_DATE,homeFragment.dayOfTaskFromTaskFragment);
        }
        contentValues.put(DBHelper.KEY_TIME, setTime.getText().toString());
        contentValues.put(DBHelper.KEY_DESCRIPTION, description.getText().toString());
        contentValues.put(DBHelper.KEY_IS_DONE,0);
        database.update(DBHelper.TABLE_TASKS, contentValues,DBHelper.KEY_ID + "= ?", new String[] {String.valueOf(idTask)});

    }
}
