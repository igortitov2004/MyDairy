package com.example.myowndairy.TasksPage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myowndairy.Interfaces.RecycleViewInterface;
import com.example.myowndairy.R;
import com.example.myowndairy.Model.Tasks;

import java.util.ArrayList;

public class MyAdapterForTasks extends RecyclerView.Adapter<MyAdapterForTasks.MyViewHolder> {



    private RecycleViewInterface recycleViewInterface;
    Context context;
    ArrayList<Tasks> tasksArrayList;


    public MyAdapterForTasks(Context context, ArrayList<Tasks> tasksArrayList, RecycleViewInterface recycleViewInterface){
        this.context = context;
        this.tasksArrayList = tasksArrayList;
        this.recycleViewInterface = recycleViewInterface;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.task_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
         Tasks tasks = tasksArrayList.get(position);
//         holder.numberOfTasks.setText(tasks.heading);
         holder.date.setText(tasks.getDate());

    }

    @Override
    public int getItemCount() {
        return tasksArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView numberOfTasks;

        TextView date;
         TextView time;
         TextView description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            numberOfTasks = itemView.findViewById(R.id.numberOfTasks);
            date = itemView.findViewById(R.id.taskDate);
        }
    }
}
