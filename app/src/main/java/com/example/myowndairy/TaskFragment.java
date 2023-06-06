package com.example.myowndairy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class TaskFragment extends Fragment {
    Button calendarMainButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        calendarMainButton=view.findViewById(R.id.calendarMainButton);

        calendarMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                showDialogDataEdit();
            }
        });


        return view;
    }



//    public void showDialogTimeEdit(){
//        DialogWindowTimeEdit dialog = new DialogWindowTimeEdit();
//
//        dialog.show((getActivity().getSupportFragmentManager()),"timePicker");
//    }

    public void showDialogDataEdit(){
        DialogWindowDateEdit dialog = new DialogWindowDateEdit();

        dialog.show((getActivity().getSupportFragmentManager()),"dataPicker");
    }
//    public static CalendarFragment newInstance(String param1, String param2) {
//        CalendarFragment fragment = new CalendarFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//

}