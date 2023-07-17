package com.example.myowndairy.TasksPage;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myowndairy.DB.DBHelper;
import com.example.myowndairy.Dialogs.DialogWindowCalendarForChoiceTask;
import com.example.myowndairy.HomePage.Adapter.MyAdapterForHome;
import com.example.myowndairy.HomePage.FragmentEditTask;
import com.example.myowndairy.HomePage.HomeFragment;
import com.example.myowndairy.Interfaces.RecycleViewInterface;
import com.example.myowndairy.TasksPage.Adapter.MyAdapterForTasks;
import com.example.myowndairy.R;
import com.example.myowndairy.Model.Tasks;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class TaskFragment extends Fragment implements RecycleViewInterface {
    Button calendarMainButton;


    FloatingActionButton addTask;

    ArrayList<Tasks> tasksArrayList = new ArrayList<>();
    private String[] tasksHeading;
    private RecyclerView recyclerview;

    private String[] tasksDate;
    private String[] tasksTime;
    private String[] tasksDescription;

    private DBHelper dbHelper;
    private SQLiteDatabase database;


    public FragmentCreateTask fragmentCreateTask = new FragmentCreateTask(this);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHelper = new DBHelper(this);
        database = dbHelper.getReadableDatabase();
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        calendarMainButton = view.findViewById(R.id.calendarMainButton);
        addTask = view.findViewById(R.id.addTask);

        dataShow();

        calendarMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                showDialogCalendarForChoiceTask();
            }
        });
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment(fragmentCreateTask);

//                Fragment createTaskFragment = new FragmentCreateTask();
//
//                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
//                fm.replace(R.id.frame_layout,createTaskFragment).commit();
            }
        });
        return view;
    }





    public void showDialogCalendarForChoiceTask(){
        DialogWindowCalendarForChoiceTask dialog = new DialogWindowCalendarForChoiceTask();

        dialog.show((getActivity().getSupportFragmentManager()),"dataPicker");
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        dataInitialize();
//        recyclerview = view.findViewById(R.id.recycleView);
//        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerview.setHasFixedSize(true);
//        MyAdapterForTasks myAdapterForTasks = new MyAdapterForTasks(getContext(),tasksArrayList,this);
//        recyclerview.setAdapter(myAdapterForTasks);
//        myAdapterForTasks.notifyDataSetChanged();
//    }
//
//    private void dataInitialize() {
//        tasksArrayList = new ArrayList<>();
//        tasksHeading = new String[]{
//                getString(R.string.task_1),
//                getString(R.string.task_2),
//                getString(R.string.task_3),
//                getString(R.string.task_4),
//                getString(R.string.task_5)
//           };
//
//        tasksDate = new String[]{
//                "12.03.2023",
//                "13.03.2023",
//                "14.03.2023",
//                "15.03.2023",
//                "16.03.2023"
//        };
//
//        tasksTime = new String[]{
//                "12.00",
//                "13.00",
//                "14.00",
//                "15.00",
//                "16.00"
//
//        };
//
//        tasksDescription = new String[]{
//                "Подышать",
//                "Поесть",
//                "Попить",
//                "Полежать",
//                "Походить"
//        };
//
//        for (int counter = 0;counter < tasksHeading.length;counter++){
//            Tasks tasks = new Tasks(counter,tasksHeading[counter],tasksDate[counter],tasksTime[counter],tasksDescription[counter]);
//            tasksArrayList.add(tasks);
//        }
//    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerview = getActivity().findViewById(R.id.recycleView);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        MyAdapterForTasks myAdapterForTasks = new MyAdapterForTasks(this, getContext(), tasksArrayList);
        recyclerview.setAdapter(myAdapterForTasks);
        myAdapterForTasks.notifyDataSetChanged();
    }



    @Override
    public void onItemClick(Tasks tasks) {

        HomeFragment homeFragment = new HomeFragment();
        homeFragment.textDayTask = tasks.getDate();



       replaceFragment(homeFragment);

    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.frame_layout,fragment).commit();
    }

    public void dataShow() {
        tasksArrayList.clear();
        Date date = new Date();

        Cursor cursor = database.query(DBHelper.TABLE_TASKS, null, null, null, DBHelper.KEY_DATE, null, null);
        if (cursor.moveToFirst()) {

            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int headerIndex = cursor.getColumnIndex(DBHelper.KEY_HEADER);
            int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
            int timeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
            int descriptionIndex = cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION);
            do {
                Tasks tasks = new Tasks(cursor.getInt(idIndex), cursor.getString(headerIndex), cursor.getString(dateIndex),
                        cursor.getString(timeIndex), cursor.getString(descriptionIndex));

                dataRefresh(cursor.getString(dateIndex).split("/"));
                if(dateCheck.compareTo(new Date())>=0 ){
                    tasksArrayList.add(tasks);
                }
                if(tasksArrayList==null){
                    ///
                }



            } while (cursor.moveToNext());

        } else {
            Log.d("mLog", "0 rows");
        }
        cursor.close();
        dbHelper.close();
    }
    private Date dateCheck;
    private void dataRefresh(String[] array){
        String dateStr = array[0] + "/" + array[1] + "/" + array[2];

        try {
            dateCheck = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


}