package com.example.myowndairy.Dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myowndairy.R;

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
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(requireContext(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        String dateStr = "" + dayOfMonth + "/" + month + "/" + year;
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
            setTextDate = getActivity().findViewById(R.id.setDate);
            setTextDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }




}
