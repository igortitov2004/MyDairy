package com.example.myowndairy.HomePage;

import static android.nfc.NfcAdapter.EXTRA_ID;


import android.annotation.SuppressLint;
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

import com.example.myowndairy.Activity.MainActivity;
import com.example.myowndairy.AlarmReceiver;
import com.example.myowndairy.DB.DBHelper;
import com.example.myowndairy.Dialogs.DialogWindowForConfirmTask;
import com.example.myowndairy.Dialogs.DialogWindowForReturn;
import com.example.myowndairy.Dialogs.DialogWindowTime;
import com.example.myowndairy.Model.Tasks;
import com.example.myowndairy.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class FragmentCreateTaskToday extends DialogFragment {
    private Button setTaskTime;
    private Button confirmTask;
    private Button returnToFragmemt;
    public EditText setTime;

    public HomeFragment getHomeFragment() {
        return homeFragment;
    }

    private HomeFragment homeFragment;
    private DialogWindowTime dialogWindowTime = new DialogWindowTime(this);
    private DialogWindowForReturn dialogWindowForReturn = new DialogWindowForReturn(this);
    private DialogWindowForConfirmTask dialogWindowForConfirmTask = new DialogWindowForConfirmTask(this);

    public FragmentCreateTaskToday(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    public FragmentCreateTaskToday() {

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

//    Calendar calendar;
//
//    Button cancelAlarm;

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
//        cancelAlarm = view.findViewById(R.id.cancelAlarm);


        setTime = view.findViewById(R.id.setTimeToday);

        setTime.setFocusable(false);



        returnToFragmemt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isAllEmpty(heading,description,setTime)){
                    heading.setText(null);
                    description.setText(null);
                    replaceFragment(homeFragment);
                }else {

                    showDialog(dialogWindowForReturn);
                    dialogWindowForReturn.fragment = homeFragment;
                    dialogWindowForReturn.fragmentCreateTaskToday = homeFragment.fragmentCreateTaskToday;
                }




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
                    dialogWindowForConfirmTask.editableFragment = homeFragment.fragmentCreateTaskToday;
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
//        cancelAlarm.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ServiceCast")
//            @Override
//            public void onClick(View v) {
//                Tasks tasks = getDataForNotification();
//                cancelAlarm(tasks.getId());
//            }
//        });
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


    private boolean isEmpty(EditText editText1,EditText editText2,EditText editText3){
//        if(TextUtils.isEmpty(editText1.getText()) || TextUtils.isEmpty(editText2.getText()) || TextUtils.isEmpty(editText3.getText())){
//            return true;
//        }

        if (editText1.getText().toString().matches(".*\\w.*") && editText2.getText().toString().matches(".*\\w.*") && editText3.getText().toString().matches(".*\\w.*")){
            return false;
        }

        return true;
    }

    private boolean isAllEmpty(EditText editText1,EditText editText2,EditText editText3){
//        if(TextUtils.isEmpty(editText1.getText()) && TextUtils.isEmpty(editText2.getText()) && TextUtils.isEmpty(editText3.getText())){
//            return true;
//        }
//        return false;
        if (editText1.getText().toString().matches(".*\\w.*") || editText2.getText().toString().matches(".*\\w.*") || editText3.getText().toString().matches(".*\\w.*")){
            return false;
        }

        return true;


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

//    public void alarmOff(){
//
//        Cursor cursor = database.query(DBHelper.TABLE_TASKS, null,null,null,null,null,DBHelper.KEY_TIME);
//        if(cursor.moveToFirst()){
//            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
//            int headerIndex = cursor.getColumnIndex(DBHelper.KEY_HEADER);
//            int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
//            int timeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
//            int descriptionIndex = cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION);
////            int isDoneIndex = cursor.getColumnIndex(DBHelper.KEY_IS_DONE);
//            do{
//                Tasks tasks = new Tasks(cursor.getInt(idIndex),cursor.getString(headerIndex),cursor.getString(dateIndex),
//                        cursor.getString(timeIndex),cursor.getString(descriptionIndex));
//                   cancelAlarm(tasks.getId());
//            }while (cursor.moveToNext());
//        }else{
//        }
//        cursor.close();
//        dbHelper.close();
//    }

    AlarmReceiver alarmReceiver;
    public void setAlarm() throws FileNotFoundException {


        alarmReceiver = new AlarmReceiver();

        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);


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
            Toast.makeText(getContext(),"URA",Toast.LENGTH_SHORT).show();
    }

//    private PendingIntent getAlarmInfoPendingIntent(){
//        Intent intent = new Intent(getContext(), MainActivity.class);
//        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        return PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
//    }
//
//    private PendingIntent getAlarmActionPendingIntent(){
//        Intent intent = new Intent(getContext(), AlarmReceiver.class);
//        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        return  PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
//    }




    public void cancelAlarm(int notifId){
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(), notifId, intent, PendingIntent.FLAG_IMMUTABLE);
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
        if(homeFragment.dayOfTaskFromTaskFragment ==null){
            contentValues.put(DBHelper.KEY_DATE,new SimpleDateFormat("dd/MM/yyyy").format(date));
        } else{
            contentValues.put(DBHelper.KEY_DATE,homeFragment.dayOfTaskFromTaskFragment);
        }
        contentValues.put(DBHelper.KEY_TIME, setTime.getText().toString());
        contentValues.put(DBHelper.KEY_DESCRIPTION,description.getText().toString());
        contentValues.put(DBHelper.KEY_IS_DONE,0);
        database.insert(DBHelper.TABLE_TASKS,null,contentValues);

        setTime.setText(null);
        heading.setText(null);
        description.setText(null);

    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.frame_layout,fragment).commit();
    }


}
