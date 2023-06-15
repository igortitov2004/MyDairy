package com.example.myowndairy;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class HomeFragment extends Fragment implements RecycleViewInterface{

    Date date = new Date();

    Toolbar toolbar;
    TextView dayText;
    TextView dateText;

    Tasks tasks;

    FloatingActionButton addTaskToday;

    ArrayList<Tasks> tasksArrayList;
    private String[] tasksHeading;

   private String[] tasksDate;
    private String[] tasksTime;
    private String[] tasksDescription;

    private RecyclerView recyclerview;

    ConstraintLayout item;

//    FragmentEditTask fragmentEditTask = new FragmentEditTask();
FragmentEditTask editTaskFragment= new FragmentEditTask();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        toolbar = view.findViewById(R.id.homeToolBar);
        dayText = toolbar.findViewById(R.id.presentDay);
        dateText = toolbar.findViewById(R.id.presentDate);
        addTaskToday = view.findViewById(R.id.addTaskToday);

        addTaskToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment createEditTaskFragment = new FragmentCreateTaskToday();

                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frame_layout,createEditTaskFragment).commit();
            }
        });

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("");



        dateText.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
        dayText.setText(new SimpleDateFormat("EEEE", new Locale("RU")).format(date));

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataInitialize();
        recyclerview = view.findViewById(R.id.recycleViewForHome);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        MyAdapterForHome myAdapterForHome = new MyAdapterForHome(this,getContext(),tasksArrayList);
        recyclerview.setAdapter(myAdapterForHome);
        myAdapterForHome.notifyDataSetChanged();

    }

    private void dataInitialize() {
        tasksArrayList = new ArrayList<>();

        tasksHeading = new String[]{
                "БЖЧ",
                "МИСРЭТ",
                "АЛИМПУС",
                "ТВИМС",
                "МИКЭМС"
        };

        tasksTime = new String[]{
                "12.00",
                "13.00",
                "14.00",
                "15.00",
                "16.00"

        };

        tasksDescription = new String[]{
                "Подышать",
                "Поесть",
                "Попить",
                "Полежать",
                "Походить"
        };

        for (int counter=0;counter<tasksHeading.length;counter++){
            Tasks tasks = new Tasks(tasksHeading[counter],"сегодня",tasksTime[counter],tasksDescription[counter]);
            tasksArrayList.add(tasks);
        }
    }

    public void showDialog(DialogFragment dialogFragment){
        dialogFragment.show((getActivity().getSupportFragmentManager()),"custom");
    }


    @Override
    public void onItemClick(Tasks tasks) {

        editTaskFragment.headerText = tasks.getHeading();
        editTaskFragment.timeText = tasks.getTime();
        editTaskFragment.descriptionText = tasks.getDescription();

        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.frame_layout,editTaskFragment).commit();

    }
}