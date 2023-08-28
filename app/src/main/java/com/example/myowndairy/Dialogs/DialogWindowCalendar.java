package com.example.myowndairy.Dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myowndairy.R;
import com.example.myowndairy.TasksPage.FragmentCreateTask;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DialogWindowCalendar extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    EditText setTextDate;

    Button setTaskTime;
    EditText eTsetTime;

    public int notif_day;
    public int notif_month;

    public DialogWindowTime dialogWindowTime;

    FragmentCreateTask fragmentCreateTask;



    public DialogWindowCalendar(FragmentCreateTask fragmentCreateTask) {
        this.setTaskTime = fragmentCreateTask.setTaskTime;
        this.eTsetTime = fragmentCreateTask.setTime;
        this.fragmentCreateTask = fragmentCreateTask;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), this, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

        // Create a new instance of DatePickerDialog and return it
        return datePickerDialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        fragmentCreateTask.getDialogWindowTime().setNotif_month(month);
        fragmentCreateTask.getDialogWindowTime().setNotif_day(dayOfMonth);

//        notif_month = month;
//        notif_day = dayOfMonth;
//        dialogWindowTime  = new DialogWindowTime(notif_month,notif_day);
        //КРУТОЙ КОСТЫЛЬ
        if(month==12){
            month=1;
        }else {
            month++;
        }


//        dialogWindowTime =  new DialogWindowTime(notif_month,notif_day);

        String dateStr = "" + dayOfMonth + "/" + month + "/" + year;
        Date date;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
            setTextDate = getActivity().findViewById(R.id.setDate);
            setTextDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        fragmentCreateTask.getDialogWindowTime().setDateStr(new SimpleDateFormat("dd/MM/yyyy").format(date));
        setTaskTime.setVisibility(View.VISIBLE);
        eTsetTime.setVisibility(View.VISIBLE);
//        eTsetTime = getActivity().findViewById(R.id.setTime);
        eTsetTime.setText(null);
    }




}
