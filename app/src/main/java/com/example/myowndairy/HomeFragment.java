package com.example.myowndairy;

import android.app.LauncherActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myowndairy.DB.DBHelper;
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

    ArrayList<Tasks> tasksArrayList = new ArrayList<>() ;


//    public HomeFragment(){
//        this.tasksHeading = new String[]{
//                "БЖЧ",
//                "МИСРЭТ",
//                "АЛИМПУС",
//                "ТВИМС",
//                "МИКЭМС"
//        };
//
//        this.tasksTime = new String[]{
//                "12.00",
//                "13.00",
//                "14.00",
//                "15.00",
//                "16.00"
//
//        };
//
//        this.tasksDescription = new String[]{
//                "Подышать",
//                "Поесть",
//                "Попить",
//                "Полежать",
//                "Походить"
//        };
//
//        for (int counter=0;counter<tasksHeading.length;counter++){
//            Tasks tasks = new Tasks(tasksHeading[counter],"сегодня",tasksTime[counter],tasksDescription[counter]);
//            tasksArrayList.add(tasks);
//        }
//    }
    private String[] tasksHeading;

   private String[] tasksDate;
    private String[] tasksTime;
    private String[] tasksDescription;

    private RecyclerView recyclerview;


//    DBHelper dbHelper;
//
//    SQLiteDatabase database;
    FragmentCreateTaskToday createEditTaskFragment = new FragmentCreateTaskToday(this);

    ConstraintLayout item;
    DialogWindowForConfirmTask dialogWindowForConfirmTask;

//    FragmentEditTask fragmentEditTask = new FragmentEditTask();
    FragmentEditTask editTaskFragment = new FragmentEditTask(this);


    DBHelper dbHelperHome;

    SQLiteDatabase databaseHome;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHelperHome =new DBHelper(this);
        databaseHome =dbHelperHome.getReadableDatabase();

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        toolbar = view.findViewById(R.id.homeToolBar);
        dayText = toolbar.findViewById(R.id.presentDay);
        dateText = toolbar.findViewById(R.id.presentDate);
        addTaskToday = view.findViewById(R.id.addTaskToday);



        dataShow();

        addTaskToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frame_layout,createEditTaskFragment).commit();
            }
        });



        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("");



        dateText.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
        dayText.setText(new SimpleDateFormat("EEEE", new Locale("RU")).format(date));

//        createEditTaskFragment.dataInitialize();

        // Inflate the layout for this fragment
//        dbHelper = new DBHelper(this);


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        dataInitialize();
        recyclerview = getActivity().findViewById(R.id.recycleViewForHome);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        MyAdapterForHome myAdapterForHome = new MyAdapterForHome(this,getContext(),tasksArrayList);
        recyclerview.setAdapter(myAdapterForHome);
        myAdapterForHome.notifyDataSetChanged();




    }

    public void showDialog(DialogFragment dialogFragment){
        dialogFragment.show((getActivity().getSupportFragmentManager()),"custom");
    }

    @Override
    public void onItemClick(Tasks tasks) {
        FragmentEditTask fragmentEditTask = new FragmentEditTask(this);

        fragmentEditTask.idTask = tasks.getId();
        fragmentEditTask.headerText = tasks.getHeading();
        fragmentEditTask.timeText = tasks.getTime();
        fragmentEditTask.descriptionText = tasks.getDescription();
        editTaskFragment = fragmentEditTask;

        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.frame_layout,editTaskFragment).commit();

    }

//    public void addItem(){
//        tasksArrayList.add(createEditTaskFragment.tasks);
//        MyAdapterForHome myAdapterForHome = new MyAdapterForHome(this,getContext(),tasksArrayList);
//        recyclerview.setAdapter(myAdapterForHome);
//    }

    public void dataShow() {
        tasksArrayList.clear();

        Cursor cursor = databaseHome.query(DBHelper.TABLE_TASKS, null,null,null,null,null,DBHelper.KEY_TIME);
        if(cursor.moveToFirst()){

            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int headerIndex = cursor.getColumnIndex(DBHelper.KEY_HEADER);
            int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
            int timeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
            int descriptionIndex = cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION);
            do{
                Tasks tasks = new Tasks(cursor.getInt(idIndex),cursor.getString(headerIndex),cursor.getString(dateIndex),
                        cursor.getString(timeIndex),cursor.getString(descriptionIndex));
                tasksArrayList.add(tasks);

            }while (cursor.moveToNext());

        }else{
            Log.d("mLog", "0 rows");
        }

        cursor.close();
        dbHelperHome.close();
    }
}