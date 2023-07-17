package com.example.myowndairy.HomePage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myowndairy.DB.DBHelper;
import com.example.myowndairy.Interfaces.RecycleViewInterface;
import com.example.myowndairy.HomePage.Adapter.MyAdapterForHome;
import com.example.myowndairy.R;
import com.example.myowndairy.Model.Tasks;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class HomeFragment extends Fragment implements RecycleViewInterface {
    private Date date = new Date();
    private Toolbar toolbar;
    private TextView dayText;
    private TextView dateText;
    private FloatingActionButton addTaskToday;
    private ArrayList<Tasks> tasksArrayList = new ArrayList<>() ;
    private RecyclerView recyclerview;
    public FragmentCreateTaskToday fragmentCreateTaskToday = new FragmentCreateTaskToday(this);
    public FragmentEditTask editTaskFragment = new FragmentEditTask(this);
    private DBHelper dbHelperHome;
    private SQLiteDatabase databaseHome;

    public String textDayTask;

    private LocalDate taskDate;
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

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("");

        if(textDayTask==null){
            dateText.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
        } else{
            dateText.setText(textDayTask);
        }


        //разобраться со днем недели
        dayText.setText(new SimpleDateFormat("EEEE", new Locale("RU")).format(date));


        dataShow();

        addTaskToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frame_layout, fragmentCreateTaskToday).commit();
            }
        });




        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerview = getActivity().findViewById(R.id.recycleViewForHome);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        MyAdapterForHome myAdapterForHome = new MyAdapterForHome(this,getContext(),tasksArrayList);
        recyclerview.setAdapter(myAdapterForHome);
        myAdapterForHome.notifyDataSetChanged();

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
//                dataRefresh(cursor.getString(dateIndex).split("/"));
//                if(dateCheck.compareTo(new Date())==0){
//                    if(textDayTask==null){
//                        if(cursor.getString(dateIndex).equals(new SimpleDateFormat("dd/MM/yyyy").format(date))){
//                            tasksArrayList.add(tasks);
//                        }
//                    } else {
//                        if(cursor.getString(dateIndex).equals(textDayTask)){
//                            tasksArrayList.add(tasks);
//                        }
//                    }
//                }

                if(textDayTask==null){
                    if(cursor.getString(dateIndex).equals(new SimpleDateFormat("dd/MM/yyyy").format(date))){
                        tasksArrayList.add(tasks);
                    }
                } else {
                    if(cursor.getString(dateIndex).equals(textDayTask)){
                        tasksArrayList.add(tasks);
                    }
                }



            }while (cursor.moveToNext());

        }else{
            Log.d("mLog", "0 rows");
        }

        cursor.close();
        dbHelperHome.close();
    }

//    private Date dateCheck;
//    private void dataRefresh(String[] array){
//        String timeStr = "" + array[0] + ":" + array[1] ;
//
//        try {
//            dateCheck = new SimpleDateFormat("HH:mm").parse(timeStr);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }
}