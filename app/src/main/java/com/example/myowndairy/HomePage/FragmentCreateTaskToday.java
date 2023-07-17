package com.example.myowndairy.HomePage;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import com.example.myowndairy.Activity.MainActivity;
import com.example.myowndairy.AlarmReceiver;
import com.example.myowndairy.DB.DBHelper;
import com.example.myowndairy.Dialogs.DialogWindowForConfirmTask;
import com.example.myowndairy.Dialogs.DialogWindowForReturn;
import com.example.myowndairy.Dialogs.DialogWindowTime;
import com.example.myowndairy.Model.Tasks;
import com.example.myowndairy.R;
import com.example.myowndairy.databinding.ActivityMainBinding;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.prefs.Preferences;


public class FragmentCreateTaskToday extends DialogFragment {
    private Button setTaskTime;
    private Button confirmTask;
    private Button returnToFragmemt;
    public EditText setTime;

    private HomeFragment homeFragment;
    private DialogWindowTime dialogWindowTime = new DialogWindowTime();
    private DialogWindowForReturn dialogWindowForReturn = new DialogWindowForReturn();
    private DialogWindowForConfirmTask dialogWindowForConfirmTask = new DialogWindowForConfirmTask(this);

    public FragmentCreateTaskToday(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    public EditText getHeading() {
        return heading;
    }

    public EditText getDescription() {
        return description;
    }

    public  EditText heading;
    public EditText description;

    private DBHelper dbHelper;

    private SQLiteDatabase database;



    private AlarmManager alarmManager;

    private PendingIntent pendingIntent;

    Calendar calendar;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




//        setAlarm();

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
                dialogWindowForConfirmTask.editableFragment = homeFragment.fragmentCreateTaskToday;
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

        createNotificationChannel();



        return view;
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

    public void showDialog(DialogFragment dialogFragment){
        dialogFragment.show((getActivity().getSupportFragmentManager()),"custom");
    }

//    public void dataCheck() {
//        Cursor cursor = database.query(DBHelper.TABLE_TASKS, null,null,null,null,null,null);
//        if(cursor.moveToFirst()){
//
//            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
//            int headerIndex = cursor.getColumnIndex(DBHelper.KEY_HEADER);
//            int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
//            int timeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
//            int descriptionIndex = cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION);
//            do{
//                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
//                        ", header = " + cursor.getString(headerIndex)+
//                        ", date = " + cursor.getString(dateIndex)+
//                        ", time = " + cursor.getString(timeIndex)+
//                        ", description = " + cursor.getString(descriptionIndex));
//            }while (cursor.moveToNext());
//
//        }else{
//            Log.d("mLog", "0 rows");
//        }
//
//        cursor.close();
//        dbHelper.close();
//    }


    public Tasks getDataForNotification(){

        Cursor cursor = database.query(DBHelper.TABLE_TASKS, null,null,null,null,null,null);
        cursor.moveToLast();
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int headerIndex = cursor.getColumnIndex(DBHelper.KEY_HEADER);
            int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
            int timeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
            int descriptionIndex = cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION);
            Tasks tasks = new Tasks(cursor.getInt(idIndex),cursor.getString(headerIndex),cursor.getString(dateIndex),
                    cursor.getString(timeIndex),cursor.getString(descriptionIndex));

            cursor.close();
            dbHelper.close();
            return tasks;


    }

    public void setAlarm(){

            Intent intent = new Intent(getActivity(), AlarmReceiver.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);


//        Tasks tasks = getDataForNotification();
//        String str = tasks.getHeading();
        intent.putExtra("TEXT",getString(R.string.CONST_NAME_TEXT_NOTIF));
        intent.putExtra("TITLE",getString(R.string.CONST_NAME_TITLE_NOTIF));
//        intent.putExtra(EXTRA_MESSAGE,"1244555");







            pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
            alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

//            AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(dialogWindowTime.calendar.getTimeInMillis(),getAlarmInfoPendingIntent());
//
//            alarmManager.setAlarmClock(alarmClockInfo,pendingIntent);
            alarmManager.set(AlarmManager.RTC_WAKEUP,dialogWindowTime.calendar.getTimeInMillis(),pendingIntent);
            Toast.makeText(getContext(),"URA",Toast.LENGTH_SHORT).show();
    }

    private PendingIntent getAlarmInfoPendingIntent(){
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        return PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
    }

    private PendingIntent getAlarmActionPendingIntent(){
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        return  PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
    }




    public void cancelAlarm(){
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        if(alarmManager == null){
            alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        }
        alarmManager.cancel(pendingIntent);

        Toast.makeText(getContext(),"ALARM OFF",Toast.LENGTH_SHORT).show();
    }


    public void saveTask(){
        Date date = new Date();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_HEADER,heading.getText().toString());
        if(homeFragment.textDayTask==null){
            contentValues.put(DBHelper.KEY_DATE,new SimpleDateFormat("dd/MM/yyyy").format(date));
        } else{
            contentValues.put(DBHelper.KEY_DATE,homeFragment.textDayTask);
        }
        contentValues.put(DBHelper.KEY_TIME, setTime.getText().toString());
        contentValues.put(DBHelper.KEY_DESCRIPTION,description.getText().toString());
        database.insert(DBHelper.TABLE_TASKS,null,contentValues);

        setTime.setText(null);
        heading.setText(null);
        description.setText(null);

    }
}
