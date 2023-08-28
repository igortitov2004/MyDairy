package com.example.myowndairy.HomePage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.opengl.Visibility;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.myowndairy.DB.DBHelper;
import com.example.myowndairy.Interfaces.RecycleViewInterface;
import com.example.myowndairy.HomePage.Adapter.MyAdapterForHome;
import com.example.myowndairy.R;
import com.example.myowndairy.Model.Tasks;
import com.example.myowndairy.TasksPage.TaskFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class HomeFragment extends Fragment implements RecycleViewInterface {
    private Date date = new Date();
    private Toolbar toolbar;
    private TextView dayText;
    private TextView dateText;
    private FloatingActionButton addTaskToday;
    private ArrayList<Tasks> tasksArrayList ;
    private RecyclerView recyclerview;
    public FragmentCreateTaskToday fragmentCreateTaskToday = new FragmentCreateTaskToday(this);
    public FragmentEditTask editTaskFragment = new FragmentEditTask(this);
    private DBHelper dbHelperHome;
    private SQLiteDatabase databaseHome;
    public String dayOfTaskFromTaskFragment;

    private MyAdapterForHome myAdapterForHome;

    private Date dateTaskInTimeFormat;
    private Date dateInTimeFormat;
    private Date dateCheckDay;


    private TextView textCenter;


    private Button backButton;

    private Button done;

    private Button notDone;

    private Boolean isDone = null;



//    private boolean isNotDone;



    public HomeFragment(Boolean isDone,String dayOfTaskFromTaskFragment) {
        this.isDone = isDone;
        this.dayOfTaskFromTaskFragment = dayOfTaskFromTaskFragment;
    }

    public HomeFragment(){

    }

    private TextView textDone;
    private TextView textNotDone;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tasksArrayList = new ArrayList<>();


        dbHelperHome = new DBHelper(this);
        databaseHome = dbHelperHome.getWritableDatabase();




        View v = LayoutInflater.from(getContext()).inflate(R.layout.task_item_today,container,false);
        checkBox = v.findViewById(R.id.check);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        BottomNavigationView navigationView = getActivity().findViewById(R.id.bottomNavigationView);




        toolbar = view.findViewById(R.id.homeToolBar);
        dayText = toolbar.findViewById(R.id.presentDay);
        dateText = toolbar.findViewById(R.id.presentDate);
        addTaskToday = view.findViewById(R.id.addTaskToday);
        textCenter = view.findViewById(R.id.textCenter);
        backButton = view.findViewById(R.id.backButton);
        done = view.findViewById(R.id.done);
        notDone = view.findViewById(R.id.notDone);
        textDone = view.findViewById(R.id.textDone);
        textNotDone = view.findViewById(R.id.textNotDone);



        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("");

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                backButton.setVisibility(View.VISIBLE);

                replaceFragment(new HomeFragment(true,dayOfTaskFromTaskFragment));


            }
        });

        notDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                backButton.setVisibility(View.VISIBLE);

                replaceFragment(new HomeFragment(false,dayOfTaskFromTaskFragment));



            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDone!=null){
                    replaceFragment(new HomeFragment(null,dayOfTaskFromTaskFragment));
                }else{
                    replaceFragment(new TaskFragment());
                    navigationView.setVisibility(View.VISIBLE);

                }

            }
        });





        if(dayOfTaskFromTaskFragment!=null ){
            navigationView.setVisibility(View.GONE);
            backButton.setVisibility(View.VISIBLE);
//            done.setVisibility(View.GONE);
            notDone.setVisibility(View.GONE);
//            textDone.setVisibility(View.GONE);
            textNotDone.setVisibility(View.GONE);

            if(dayOfTaskFromTaskFragment.equals(new SimpleDateFormat("dd/MM/yyyy").format(date))){
                textDone.setVisibility(View.VISIBLE);
                textNotDone.setVisibility(View.VISIBLE);
                done.setVisibility(View.VISIBLE);
                notDone.setVisibility(View.VISIBLE);
                backButton.setVisibility(View.VISIBLE);
                }
        } else {
            backButton.setVisibility(View.GONE);

        }



        if(dayOfTaskFromTaskFragment == null){
            dateText.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
            firstUpperCaseDay(date);
        } else{
            dateCheckDay(dayOfTaskFromTaskFragment.split("/"));
            firstUpperCaseDay(dateCheckDay);
            dateText.setText(dayOfTaskFromTaskFragment);
        }





        addTaskToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frame_layout, fragmentCreateTaskToday).commit();
            }
        });




        return view;
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.frame_layout,fragment).commit();
    }


    private void dateCheckDay(String[] array){
        String dateStr = array[0] + "/" + array[1] + "/" + array[2];
        try {
            dateCheckDay = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    private void firstUpperCaseDay(Date d){
        dayText.setText(new SimpleDateFormat("EEEE", new Locale("RU")).format(d).substring(0,1).toUpperCase()
                +new SimpleDateFormat("EEEE", new Locale("RU")).format(d).substring(1));
    }
    CheckBox checkBox;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerview = view.findViewById(R.id.recycleViewForHome);


        myAdapterForHome = new MyAdapterForHome(this,getContext(),tasksArrayList,isDone);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        dataShow();
        recyclerview.setAdapter(myAdapterForHome);

        myAdapterForHome.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(Tasks tasks) {
        FragmentEditTask fragmentEditTask = new FragmentEditTask(this,isDone);

        fragmentEditTask.idTask = tasks.getId();
        fragmentEditTask.headerText = tasks.getHeading();
        fragmentEditTask.timeText = tasks.getTime();
        fragmentEditTask.descriptionText = tasks.getDescription();
        editTaskFragment = fragmentEditTask;

        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.frame_layout,editTaskFragment).commit();

    }

    @Override
    public void onCheckedChanged(Tasks tasks) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_HEADER, tasks.getHeading());

            contentValues.put(DBHelper.KEY_DATE,tasks.getDate());

        contentValues.put(DBHelper.KEY_TIME, tasks.getTime());
        contentValues.put(DBHelper.KEY_DESCRIPTION, tasks.getDescription());
        contentValues.put(DBHelper.KEY_IS_DONE,1);
        databaseHome.update(DBHelper.TABLE_TASKS, contentValues,DBHelper.KEY_ID + "= ?", new String[] {String.valueOf(tasks.getId())});
    }


    public void dataShow() {


//        tasksArrayList.clear();

        Cursor cursor = databaseHome.query(DBHelper.TABLE_TASKS, null,null,null,null,null,DBHelper.KEY_TIME);
        if(cursor.moveToFirst()){
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int headerIndex = cursor.getColumnIndex(DBHelper.KEY_HEADER);
            int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
            int timeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
            int descriptionIndex = cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION);
            int isDoneIndex = cursor.getColumnIndex(DBHelper.KEY_IS_DONE);
            do{
                Tasks tasks = new Tasks(cursor.getInt(idIndex),cursor.getString(headerIndex),cursor.getString(dateIndex),
                        cursor.getString(timeIndex),cursor.getString(descriptionIndex),cursor.getInt(isDoneIndex));
//                tasksArrayList.add(tasks);


                    if(isDone==null ){
                        if(cursor.getInt(isDoneIndex)==0){
                            correctArrayFilling(tasks,cursor,dateIndex,timeIndex);
                        }
//                    correctArrayFillingForNotDone(tasks,cursor,dateIndex,timeIndex);

                    }else if(isDone==false){
                        if(cursor.getInt(isDoneIndex)==0){
                            correctArrayFillingForNotDone(tasks,cursor,dateIndex,timeIndex);
                        }

                    }
                    else {
                        correctArrayFillingForDone(tasks,cursor,dateIndex,isDoneIndex);
//                        if(cursor.getInt(isDoneIndex)==1){
//                            tasksArrayList.add(tasks);
//                        }
                    }






            }while (cursor.moveToNext());
        }else{
//            textCenter.setText(getString(R.string.CONST_NAME_NO_TASKS));

        }
        if(tasksArrayList.isEmpty()){
            if(isDone==null){
                textCenter.setText(getString(R.string.CONST_NAME_NO_TASKS));
            }else if(isDone==false){
                textCenter.setText(getString(R.string.CONST_NAME_NOT_DONE_TASKS));
            }else{
                textCenter.setText(getString(R.string.CONST_NAME_DONE_TASKS));
            }

        }
        cursor.close();
//        dbHelperHome.close();
    }
    private void correctArrayFilling(Tasks tasks,Cursor cursor,int dateIndex,int timeIndex){
        if (dayOfTaskFromTaskFragment == null) {
            if (cursor.getString(dateIndex).equals(new SimpleDateFormat("dd/MM/yyyy").format(date))) {
                timeRefresh(cursor.getString(timeIndex).split(":"));
                if (dateTaskInTimeFormat.compareTo(dateInTimeFormat) > 0) {
                    tasksArrayList.add(tasks);
                    textCenter.setText(null);
                }
            }
        }else{
            if (cursor.getString(dateIndex).equals(dayOfTaskFromTaskFragment)) {
                if(dayOfTaskFromTaskFragment.equals(new SimpleDateFormat("dd/MM/yyyy").format(date))){
//                    tasksArrayList.add(tasks);
                    timeRefresh(cursor.getString(timeIndex).split(":"));
                    if (dateTaskInTimeFormat.compareTo(dateInTimeFormat) > 0) {
                        tasksArrayList.add(tasks);
                        textCenter.setText(null);
                    }
                }
                if(dayOfTaskFromTaskFragment.compareTo(new SimpleDateFormat("dd/MM/yyyy").format(date))>0){
                    tasksArrayList.add(tasks);
//                    textCenter.setText(null);
                }
            }
        }
    }

    private void correctArrayFillingForNotDone(Tasks tasks,Cursor cursor,int dateIndex,int timeIndex){
        if (dayOfTaskFromTaskFragment == null) {
            if (cursor.getString(dateIndex).equals(new SimpleDateFormat("dd/MM/yyyy").format(date))) {
                timeRefresh(cursor.getString(timeIndex).split(":"));
                if (dateInTimeFormat.compareTo(dateTaskInTimeFormat) > 0) {
                    tasksArrayList.add(tasks);
                    textCenter.setText(null);
                }
            }
        }else{
            if (cursor.getString(dateIndex).equals(dayOfTaskFromTaskFragment)) {
                if(dayOfTaskFromTaskFragment.equals(new SimpleDateFormat("dd/MM/yyyy").format(date))){
//                    tasksArrayList.add(tasks);
                    timeRefresh(cursor.getString(timeIndex).split(":"));
                    if (dateTaskInTimeFormat.compareTo(dateInTimeFormat) < 0) {
                        tasksArrayList.add(tasks);
                        textCenter.setText(null);
                    }
                }

            }
        }
    }

    private void correctArrayFillingForDone(Tasks tasks,Cursor cursor,int dateIndex,int isDoneIndex){
        if (dayOfTaskFromTaskFragment == null) {
            if (cursor.getString(dateIndex).equals(new SimpleDateFormat("dd/MM/yyyy").format(date)) && cursor.getInt(isDoneIndex)==1) {

                    tasksArrayList.add(tasks);
                    textCenter.setText(null);

            }
        }else{
            if (cursor.getString(dateIndex).equals(dayOfTaskFromTaskFragment) && cursor.getInt(isDoneIndex)==1) {

                        tasksArrayList.add(tasks);
                        textCenter.setText(null);
                    }
                }


    }








    private void timeRefresh(String[] array){
        String timeStr = array[0] + ":" + array[1] ;
        String currentDayTime = new SimpleDateFormat("HH:mm").format(date);
        try {
            dateInTimeFormat = new SimpleDateFormat("HH:mm").parse(currentDayTime);
            dateTaskInTimeFormat = new SimpleDateFormat("HH:mm").parse(timeStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}