package com.example.myowndairy.HomePage;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myowndairy.AlarmReceiver;
import com.example.myowndairy.DB.DBHelper;
import com.example.myowndairy.Dialogs.DialogWindowForConfirmTask;
import com.example.myowndairy.Dialogs.DialogWindowForDeleteTask;
import com.example.myowndairy.Dialogs.DialogWindowForReturn;
import com.example.myowndairy.Dialogs.DialogWindowTime;
import com.example.myowndairy.Model.Tasks;
import com.example.myowndairy.R;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    public HomeFragment getHomeFragment() {
        return homeFragment;
    }

    public HomeFragment homeFragment;
    private Button deleteTask;

    private DialogWindowTime dialogWindowTime = new DialogWindowTime(this);
    private DialogWindowForReturn dialogWindowForReturn = new DialogWindowForReturn();
    private DialogWindowForDeleteTask dialogWindowForDeleteTask = new DialogWindowForDeleteTask(this);
    private DialogWindowForConfirmTask dialogWindowForConfirmTask = new DialogWindowForConfirmTask(this);

    Boolean isDone;
    private AlarmManager alarmManager;

    private PendingIntent pendingIntent;

    public FragmentEditTask(HomeFragment homeFragment,Boolean isDone){
        this.homeFragment = homeFragment;
        this.isDone = isDone;
    }
    public FragmentEditTask(HomeFragment homeFragment){
        this.homeFragment = homeFragment;

    }


    public FragmentEditTask(){
    }

    private DBHelper dbHelper;

    public SQLiteDatabase database;



    TextView textView;



    private final Calendar calendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        dbHelper = new DBHelper(this);
        database= dbHelper.getWritableDatabase();

        View view = inflater.inflate(R.layout.fragment_edit_task, container, false);

        textView = view.findViewById(R.id.textEditToolBar);


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

        if(isDone!=null){
            textView.setText("Просмотр задачи");
            returnToFragmemt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replaceFragment(new HomeFragment(isDone,homeFragment.dayOfTaskFromTaskFragment));
                }
            });

            // какашки
            confirmTask.setVisibility(View.GONE);
            setTime.setFocusable(false);
            setTaskTime.setClickable(false);
            heading.setFocusable(false);
            description.setFocusable(false);
        }else{
            returnToFragmemt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(dialogWindowForReturn);
                    dialogWindowForReturn.fragment = homeFragment;
                }
            });
            Toast toast = Toast.makeText(
                    getActivity(),
                    getString(R.string.CONST_NAME_ISEMPTY_FIELD),
                    Toast.LENGTH_SHORT);
            confirmTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isEmpty(heading,description,setTime)){
                        toast.show();
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
                    Date date = new Date();
                    dialogWindowTime.timeField = R.id.setTimeToday;
                    showDialog(dialogWindowTime);
                    if(homeFragment.dayOfTaskFromTaskFragment==null){
                        dialogWindowTime.dateStr = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                    }else if( homeFragment.dayOfTaskFromTaskFragment!=null && homeFragment.dayOfTaskFromTaskFragment.equals(new SimpleDateFormat("dd/MM/yyyy").format(date))){
                        dialogWindowTime.dateStr = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                    }else {
                        dialogWindowTime.dateStr=null;
                    }
                }
            });

            setTaskTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogWindowTime.timeField = R.id.setTimeToday;
                    showDialog(dialogWindowTime);
                    if(homeFragment.dayOfTaskFromTaskFragment==null){
                        dialogWindowTime.dateStr = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                    }else if( homeFragment.dayOfTaskFromTaskFragment!=null && homeFragment.dayOfTaskFromTaskFragment.equals(new SimpleDateFormat("dd/MM/yyyy").format(new Date()))){
                        dialogWindowTime.dateStr = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                    }else {
                        dialogWindowTime.dateStr=null;
                    }
                }
            });
        }



        return view;
    }

//    private boolean isEmpty(EditText et1,EditText et2,EditText et3){
//        if(TextUtils.isEmpty(et1.getText()) || TextUtils.isEmpty(et2.getText()) || TextUtils.isEmpty(et3.getText())){
//            return true;
//        }
//        return false;
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
    public Tasks getDataForNotification(){

        Cursor cursor = database.query(DBHelper.TABLE_TASKS, null,null,null,null,null,null);
//        cursor.moveToLast();


        cursor.moveToFirst();
        int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
        int headerIndex = cursor.getColumnIndex(DBHelper.KEY_HEADER);
        int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
        int timeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
        int descriptionIndex = cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION);
        int isDoneIndex = cursor.getColumnIndex(DBHelper.KEY_IS_DONE);
        do{
            if(cursor.getInt(idIndex)==idTask){
                Tasks tasks = new Tasks(cursor.getInt(idIndex),cursor.getString(headerIndex),cursor.getString(dateIndex),
                        cursor.getString(timeIndex),cursor.getString(descriptionIndex),cursor.getInt(isDoneIndex));
                cursor.close();
                dbHelper.close();
                return tasks;
            }
        }while (cursor.moveToNext());

        return null;


    }

//    private void createNotificationChannel() {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            CharSequence name = "DairyChannel";
//            String description = "Channel for alarm manager";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel("Dairy",name,importance);
//            channel.setDescription(description);
//
//            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
//
//            notificationManager.createNotificationChannel(channel);
//        }
//    }



    AlarmReceiver alarmReceiver;
    public void setAlarm() throws ParseException {
        cancelAlarm();
        Tasks tasks = getDataForNotification();
        String str = tasks.getHeading();
        String[] arrayListTime =  tasks.getTime().split(":");

        String[] arrayListDate =  tasks.getDate().split("/");


        alarmReceiver = new AlarmReceiver();

        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        Intent intent = new Intent(getActivity(), alarmReceiver.getClass());
        alarmReceiver.notificationManagerCompat = NotificationManagerCompat.from(getContext());
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        cancelAlarm(tasks.getId());
        intent.putExtra("ISEDIT",true);
        intent.putExtra("TEXT",getString(R.string.CONST_NAME_TEXT_NOTIF));
        intent.putExtra("TITLE",getString(R.string.CONST_NAME_TITLE_NOTIF));
        intent.putExtra("TASK", str);
        pendingIntent = PendingIntent.getBroadcast(getContext(), idTask, intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
//            AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(dialogWindowTime.calendar.getTimeInMillis(),getAlarmInfoPendingIntent());
//            alarmManager.setAlarmClock(alarmClockInfo,pendingIntent);

//        try{
//            alarmManager.set(AlarmManager.RTC_WAKEUP,dialogWindowTime.calendar.getTimeInMillis(),pendingIntent);
//        }catch (RuntimeException e){
            if(Build.VERSION.SDK_INT>=19){
                calendar.set(Calendar.MONTH,Integer.parseInt(arrayListDate[1])-1);
                calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(arrayListDate[0]));
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrayListTime[0]));
                calendar.set(Calendar.MINUTE,Integer.parseInt(arrayListTime[1]));
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
            }else{
                alarmManager.set(AlarmManager.RTC_WAKEUP,dialogWindowTime.calendar.getTimeInMillis(),pendingIntent);

            }

//        }



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





    public void cancelAlarm(){

            Intent intent = new Intent(getActivity(), AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(getContext(), idTask, intent, PendingIntent.FLAG_IMMUTABLE);
            if(alarmManager == null){
                alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            }
            alarmManager.cancel(pendingIntent);
//            Toast.makeText(getContext(),"ALARM OFF",Toast.LENGTH_SHORT).show();

    }
}
