package com.example.myowndairy.Dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DialogWindowTime extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    public EditText getSetTextTime() {
        return setTextTime;
    }

    EditText setTextTime;
    public int timeField;


    public Calendar calendar;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);


        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String timeStr = "" + hourOfDay + ":" + minute ;
        calendar = Calendar.getInstance();
        try {
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(Calendar.MINUTE,minute);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
            Date date = new SimpleDateFormat("HH:mm").parse(timeStr);
            setTextTime = getActivity().findViewById(timeField);
            setTextTime.setText(new SimpleDateFormat("HH:mm").format(date));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


}