package com.example.myowndairy.SettingsPage;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import com.example.myowndairy.AlarmReceiver;
import com.example.myowndairy.DB.DBHelper;
import com.example.myowndairy.HomePage.FragmentCreateTaskToday;
import com.example.myowndairy.Model.Tasks;
import com.example.myowndairy.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DialogWindowNotifications extends DialogFragment {
    Date date = new Date();
    private Switch aSwitch;
    private String switchValue = "";
    private String on;
    private String off;

    DBHelper dbHelper;
    SQLiteDatabase database;






    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        dbHelper = new DBHelper(this);
        database = dbHelper.getReadableDatabase();


        on = getString(R.string.CONST_NAME_NOTIFICATIONS_ON);

        off = getString(R.string.CONST_NAME_NOTIFICATIONS_OFF);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_dialog_window_notifications,null);

        aSwitch = view.findViewById(R.id.switchNotifications);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final SharedPreferences.Editor editor = preferences.edit();
        if(preferences.contains("checked") && preferences.getBoolean("checked",false) == true) {
            aSwitch.setChecked(true);
            aSwitch.setText(on);
        }else {
            aSwitch.setChecked(false);
            aSwitch.setText(off);
        }
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    editor.putBoolean("checked", true);
                    editor.apply();

                    aSwitch.setText(on);
                    switchValue=on;
                }else {
                    editor.putBoolean("checked", false);
                    editor.apply();
                    aSwitch.setText(off);
                    switchValue=off;
                }
            }
        });

        builder.setView(view)
                .setPositiveButton(getString(R.string.CONST_NAME_OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(switchValue == on){


//                            editor = sharedPreferences.edit();
//                            editor.putBoolean("checkbox",true);


                            alarmOn();
                            Toast.makeText(
                                    getActivity(),
                                    getString(R.string.CONST_NAME_NOTIFICATIONS)+" " + on,
                                    Toast.LENGTH_SHORT).show();
                        }else if(switchValue == off){
//                            editor = sharedPreferences.edit();
//                            editor.putBoolean("checkbox",false);
                            alarmOff();
                            Toast.makeText(
                                    getActivity(),
                                    getString(R.string.CONST_NAME_NOTIFICATIONS)+" "+ off,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(getString(R.string.CONST_NAME_CANCEL), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    public void cancelAlarm(int notifId){
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(), notifId, intent, PendingIntent.FLAG_IMMUTABLE);
        if(alarmManager == null){
            alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        }
        alarmManager.cancel(pendingIntent);

//        Toast.makeText(getContext(),"ALARM OFF",Toast.LENGTH_SHORT).show();
    }

    public void alarmOff(){

        Cursor cursor = database.query(DBHelper.TABLE_TASKS, null,null,null,null,null,DBHelper.KEY_TIME);
        if(cursor.moveToFirst()){
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int headerIndex = cursor.getColumnIndex(DBHelper.KEY_HEADER);
            int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
            int timeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
            int descriptionIndex = cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION);
            int isDoneIndex = cursor.getColumnIndex(DBHelper.KEY_IS_DONE);
            do{
                Tasks tasks = new Tasks(cursor.getInt(idIndex),cursor.getString(headerIndex),cursor.getString(dateIndex),
                        cursor.getString(timeIndex),cursor.getString(descriptionIndex),cursor.getInt(isDoneIndex));
                cancelAlarm(tasks.getId());
            }while (cursor.moveToNext());
        }else{
        }
        cursor.close();
        dbHelper.close();
    }

    public void alarmOn(){


        Cursor cursor = database.query(DBHelper.TABLE_TASKS, null,null,null,null,null,DBHelper.KEY_TIME);
        if(cursor.moveToFirst()){
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int headerIndex = cursor.getColumnIndex(DBHelper.KEY_HEADER);
            int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
            int timeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
            int descriptionIndex = cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION);
            int isDoneIndex = cursor.getColumnIndex(DBHelper.KEY_IS_DONE);
            do{
                Tasks tasks = new Tasks(cursor.getInt(idIndex),cursor.getString(headerIndex),cursor.getString(dateIndex),
                        cursor.getString(timeIndex),cursor.getString(descriptionIndex),cursor.getInt(isDoneIndex));
                dataRefresh(cursor.getString(dateIndex).split("/"));
                if(cursor.getString(dateIndex).equals(new SimpleDateFormat("dd/MM/yyyy").format(date)) || dateCheck.compareTo(new Date())>0){
                    timeRefresh(cursor.getString(timeIndex).split(":"));
                    if (dateTaskInTimeFormat.compareTo(dateInTimeFormat) > 0) {
                        createAlarm(tasks.getId(),tasks);
                    }
                }


            }while (cursor.moveToNext());
        }else{
        }
        cursor.close();
        dbHelper.close();
    }

    AlarmReceiver alarmReceiver;

    public void createAlarm(int notifId,Tasks tasks){

        alarmReceiver = new AlarmReceiver();

        Intent intent = new Intent(getActivity(), alarmReceiver.getClass());
        alarmReceiver.notificationManagerCompat = NotificationManagerCompat.from(getContext());

        String str = tasks.getHeading();
        intent.putExtra("TEXT",getString(R.string.CONST_NAME_TEXT_NOTIF));
        intent.putExtra("TITLE",getString(R.string.CONST_NAME_TITLE_NOTIF));
        intent.putExtra("TASK", str);


        pendingIntent = PendingIntent.getBroadcast(getContext(), notifId, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

//            AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(dialogWindowTime.calendar.getTimeInMillis(),getAlarmInfoPendingIntent());
//
//            alarmManager.setAlarmClock(alarmClockInfo,pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
//            Toast.makeText(getContext(),"URA",Toast.LENGTH_SHORT).show();
    }


    Calendar calendar;

    Date dateInTimeFormat;
    Date dateTaskInTimeFormat;
    private void timeRefresh(String[] array) {
        String timeStr = array[0] + ":" + array[1] ;
        String currentDayTime = new SimpleDateFormat("HH:mm").format(date);
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(array[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(array[1]));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        try {
            dateInTimeFormat = new SimpleDateFormat("HH:mm").parse(currentDayTime);
            dateTaskInTimeFormat = new SimpleDateFormat("HH:mm").parse(timeStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
    Date dateCheck;
    private void dataRefresh(String[] array){
        String dateStr = array[0] + "/" + array[1] + "/" + array[2];

        try {
            dateCheck = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void getConditionOfNotif(){


    }


}
