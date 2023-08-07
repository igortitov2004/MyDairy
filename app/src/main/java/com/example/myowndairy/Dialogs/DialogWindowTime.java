package com.example.myowndairy.Dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.myowndairy.HomePage.FragmentCreateTaskToday;
import com.example.myowndairy.R;
import com.example.myowndairy.TasksPage.FragmentCreateTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DialogWindowTime extends DialogFragment implements TimePickerDialog.OnTimeSetListener {



    EditText setTextDate;





    FragmentCreateTask fragmentCreateTask;
    Date currentDay = new Date();

    public DialogWindowTime(FragmentCreateTask fragmentCreateTask) {
        this.fragmentCreateTask = fragmentCreateTask;

    }

    public DialogWindowTime(){

    }

    FragmentCreateTaskToday fragmentCreateTaskToday;

    public DialogWindowTime(FragmentCreateTaskToday fragmentCreateTaskToday) {
        this.fragmentCreateTaskToday = fragmentCreateTaskToday;
    }

    public int notif_month;
    public int notif_day;

    public DialogWindowTime(int month, int dayOfMonth) {
        this.notif_month = month;
        this.notif_day = dayOfMonth;
    }


    public EditText getSetTextTime() {
        return setTextTime;
    }

    EditText setTextTime;
    public int timeField;

    String dateStr;

    final Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);
    public final Calendar calendar = Calendar.getInstance();


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        try{
            setTextDate = fragmentCreateTask.setDate;
            dateStr = setTextDate.getText().toString();
        }catch (RuntimeException e){
            try {
                if(fragmentCreateTaskToday.getHomeFragment().dayOfTaskFromTaskFragment==null){
                    dateStr=new SimpleDateFormat("dd/MM/yyyy").format(currentDay);
                }else{
                    dateStr = fragmentCreateTaskToday.getHomeFragment().dayOfTaskFromTaskFragment;
                }

            }catch (RuntimeException runtimeException){

            }

        }

        // Use the current time as the default values for the picker
//        if(setTextDate!=null){
//
//
//        } else {
//
//        }


        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));


        // Create a new instance of TimePickerDialog and return it
        return timePickerDialog;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

       try{
           if (dateStr.equals(new SimpleDateFormat("dd/MM/yyyy").format(currentDay))) {
               if (hourOfDay <= hour){
                   if(minute <= this.minute){
                       Toast.makeText(getActivity(), getString(R.string.CONST_NAME_WRONG_TIME),
                               Toast.LENGTH_SHORT).show();
                   }else{
                       String timeStr = "" + hourOfDay + ":" + minute;
                       try {

                           calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH));
                           calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH));
                           calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                           calendar.set(Calendar.MINUTE, minute);
                           calendar.set(Calendar.SECOND, 0);
                           calendar.set(Calendar.MILLISECOND, 0);
                           Date date = new SimpleDateFormat("HH:mm").parse(timeStr);
                           setTextTime = getActivity().findViewById(timeField);
                           setTextTime.setText(new SimpleDateFormat("HH:mm").format(date));
                       } catch (ParseException e) {
                           throw new RuntimeException(e);
                       }
                   }

               } else {

                   String timeStr = "" + hourOfDay + ":" + minute;
                   try {
                       calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH));
                       calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH));
                       calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                       calendar.set(Calendar.MINUTE, minute);
                       calendar.set(Calendar.SECOND, 0);
                       calendar.set(Calendar.MILLISECOND, 0);
                       Date date = new SimpleDateFormat("HH:mm").parse(timeStr);
                       setTextTime = getActivity().findViewById(timeField);
                       setTextTime.setText(new SimpleDateFormat("HH:mm").format(date));
                   } catch (ParseException e) {
                       throw new RuntimeException(e);
                   }
               }


           }
       }catch (RuntimeException e){
           String timeStr = "" + hourOfDay + ":" + minute;
           try {
               calendar.set(Calendar.MONTH, notif_month);
               calendar.set(Calendar.DAY_OF_MONTH,notif_day);
               calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
               calendar.set(Calendar.MINUTE, minute);
               calendar.set(Calendar.SECOND, 0);
               calendar.set(Calendar.MILLISECOND, 0);
               Date date = new SimpleDateFormat("HH:mm").parse(timeStr);
               setTextTime = getActivity().findViewById(timeField);
               setTextTime.setText(new SimpleDateFormat("HH:mm").format(date));
           } catch (ParseException exception) {
               throw new RuntimeException(exception);
           }
       }



    }
}