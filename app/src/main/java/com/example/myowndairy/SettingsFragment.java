package com.example.myowndairy;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class SettingsFragment extends Fragment {


    Button dateTimeSet;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_settings, container, false);

        dateTimeSet=view.findViewById(R.id.buttonDate);
        dateTimeSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                showDialog(view);
            }
        });
        return view;
    }

    public void showDialog(View v){
        DialogWindow dialog= new DialogWindow();

        dialog.show((getActivity().getSupportFragmentManager()),"custom");
    }












}