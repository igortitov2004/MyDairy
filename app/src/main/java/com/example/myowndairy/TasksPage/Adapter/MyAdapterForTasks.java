package com.example.myowndairy.TasksPage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myowndairy.Interfaces.RecycleViewInterface;
import com.example.myowndairy.R;
import com.example.myowndairy.Model.Tasks;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterForTasks extends RecyclerView.Adapter<MyAdapterForTasks.MyViewHolder> {



    private RecycleViewInterface recycleViewInterface;
    Context context;
    List<Tasks> tasksArrayList;


    public MyAdapterForTasks(RecycleViewInterface recycleViewInterface, Context context, ArrayList<Tasks> tasksArrayList){
        this.context = context;
        this.tasksArrayList = tasksArrayList;
        this.recycleViewInterface = recycleViewInterface;

    }
    @NonNull
    @Override
    public MyAdapterForTasks.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.task_item,parent,false);
        return new MyAdapterForTasks.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//         Tasks tasks = tasksArrayList.get(position);
//         holder.numberOfTasks.setText(tasks.heading);

         holder.date.setText(tasksArrayList.get(position).getDate());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycleViewInterface.onItemClick(tasksArrayList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return tasksArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ConstraintLayout constraintLayout;
        TextView numberOfTasks;

        TextView date;
         TextView time;
         TextView description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            numberOfTasks = itemView.findViewById(R.id.numberOfTasks);
            date = itemView.findViewById(R.id.taskDate);

            constraintLayout = itemView.findViewById((R.id.item));
        }
    }
}
