package com.example.myowndairy.TasksPage;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myowndairy.AlarmReceiver;
import com.example.myowndairy.DB.DBHelper;
import com.example.myowndairy.Dialogs.DialogWindowCalendar;
import com.example.myowndairy.Dialogs.DialogWindowTime;
import com.example.myowndairy.Dialogs.DialogWindowForConfirmTask;
import com.example.myowndairy.Dialogs.DialogWindowForReturn;
import com.example.myowndairy.Model.Tasks;
import com.example.myowndairy.R;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FragmentCreateTask extends DialogFragment {
   private Button setTaskDate;
   public Button setTaskTime;
   private Button confirmTask;
   public EditText setDate;
   private Button returnToFragment;

   public EditText setTime;



   public DialogWindowCalendar dialogWindowCalendar ;

    public DialogWindowTime getDialogWindowTime() {
        return dialogWindowTime;
    }

    private DialogWindowTime dialogWindowTime;
   private DialogWindowForReturn dialogWindowForReturn = new DialogWindowForReturn(this);
   private DialogWindowForConfirmTask dialogWindowForConfirmTask = new DialogWindowForConfirmTask(this);


   private TaskFragment taskFragment;

   public EditText heading;
   public EditText description;

    private DBHelper dbHelper;

    private SQLiteDatabase database;

    private AlarmManager alarmManager;

    private PendingIntent pendingIntent;

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


        setTime.setVisibility(View.GONE);
        setTaskTime.setVisibility(View.GONE);

        dialogWindowCalendar = new DialogWindowCalendar(this);







        returnToFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isAllEmpty(heading,setTime,setDate,description)){
                    heading.setText(null);
                    description.setText(null);
                    replaceFragment(taskFragment);
                }else{
                    showDialog(dialogWindowForReturn);
                    dialogWindowForReturn.fragment = taskFragment;
                    dialogWindowForReturn.fragmentCreateTask = taskFragment.fragmentCreateTask;
                }


            }
        });

        confirmTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(heading,setTime,setDate,description)){
                    Toast.makeText(
                            getActivity(),
                            getString(R.string.CONST_NAME_ISEMPTY_FIELD),
                            Toast.LENGTH_SHORT).show();
                }else{
                    showDialog(dialogWindowForConfirmTask);
                    dialogWindowForConfirmTask.taskFragment = taskFragment;
                    dialogWindowForConfirmTask.fragment = taskFragment;
                    dialogWindowForConfirmTask.editableFragment = taskFragment.fragmentCreateTask;
                }




            }
        });

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialogWindowCalendar);
                dialogWindowTime = new DialogWindowTime();
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
                dialogWindowTime = new DialogWindowTime();

            }
        });

        setTaskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogWindowTime.timeField = R.id.setTime;
                showDialog(dialogWindowTime);
//                dialogWindowTime.notif_month=dialogWindowCalendar.notif_month;
//                dialogWindowTime.notif_day=dialogWindowCalendar.notif_day;
            }
        });
//        createNotificationChannel();
        return view;
    }

    private boolean isEmpty(EditText et1,EditText et2,EditText et3,EditText et4){
        if (et1.getText().toString().matches(".*\\w.*") && et2.getText().toString().matches(".*\\w.*") && et3.getText().toString().matches(".*\\w.*") && et4.getText().toString().matches(".*\\w.*")){
            return false;
        }

        return true;
    }

    private boolean isAllEmpty(EditText et1,EditText et2,EditText et3,EditText et4){
        if (et1.getText().toString().matches(".*\\w.*") || et2.getText().toString().matches(".*\\w.*") || et3.getText().toString().matches(".*\\w.*") || et4.getText().toString().matches(".*\\w.*")){
            return false;
        }

        return true;
    }
    public void replaceFragment(Fragment fragment){
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.frame_layout,fragment).commit();
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
        contentValues.put(DBHelper.KEY_IS_DONE,0);
        database.insert(DBHelper.TABLE_TASKS,null,contentValues);

        setTime.setText(null);
        setDate.setText(null);
        heading.setText(null);
        description.setText(null);
    }

    public Tasks getDataForNotification(){

        Cursor cursor = database.query(DBHelper.TABLE_TASKS, null,null,null,null,null,null);
        cursor.moveToLast();
        int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
        int headerIndex = cursor.getColumnIndex(DBHelper.KEY_HEADER);
        int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
        int timeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
        int descriptionIndex = cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION);
        int isDoneIndex = cursor.getColumnIndex(DBHelper.KEY_IS_DONE);
        Tasks tasks = new Tasks(cursor.getInt(idIndex),cursor.getString(headerIndex),cursor.getString(dateIndex),
                cursor.getString(timeIndex),cursor.getString(descriptionIndex),cursor.getInt(isDoneIndex));
        cursor.close();
        dbHelper.close();
        return tasks;
    }


    AlarmReceiver alarmReceiver;
    public void setAlarm() {


        alarmReceiver = new AlarmReceiver();




        Intent intent = new Intent(getActivity(), alarmReceiver.getClass());
        alarmReceiver.notificationManagerCompat = NotificationManagerCompat.from(getContext());


//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);


        Tasks tasks = getDataForNotification();
        String str = tasks.getHeading();
//        cancelAlarm(tasks.getId());
        intent.putExtra("TEXT",getString(R.string.CONST_NAME_TEXT_NOTIF));
        intent.putExtra("TITLE",getString(R.string.CONST_NAME_TITLE_NOTIF));
        intent.putExtra("TASK", str);




        pendingIntent = PendingIntent.getBroadcast(getContext(), tasks.getId(), intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

//            AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(dialogWindowTime.calendar.getTimeInMillis(),getAlarmInfoPendingIntent());
//
//            alarmManager.setAlarmClock(alarmClockInfo,pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP,dialogWindowTime.calendar.getTimeInMillis(),pendingIntent);
//            Toast.makeText(getContext(),"URA",Toast.LENGTH_SHORT).show();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "DairyChannel";
            String description = "Channel for alarm manager";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Dairy",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(channel);
        }
    }





}
