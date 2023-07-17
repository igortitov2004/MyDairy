package com.example.myowndairy.Dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myowndairy.HomePage.HomeFragment;
import com.example.myowndairy.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DialogWindowCalendarForChoiceTask extends DialogFragment implements DatePickerDialog.OnDateSetListener{
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
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        if(month==12){
            month=1;
        }else {
            month++;
        }
        HomeFragment homeFragment = new HomeFragment();
        String dateStr = "" + dayOfMonth + "/" + month + "/" + year;
        String str;
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);

            str = new SimpleDateFormat("dd/MM/yyyy").format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        homeFragment.textDayTask = str;
        replaceFragment(homeFragment);
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.frame_layout,fragment).commit();
    }
}
