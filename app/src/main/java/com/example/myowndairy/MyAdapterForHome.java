package com.example.myowndairy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterForHome extends RecyclerView.Adapter<MyAdapterForHome.MyViewHolder>{

    private RecycleViewInterface recycleViewInterface;
    Context context;
    List<Tasks> tasksArrayList;


    public MyAdapterForHome(RecycleViewInterface recycleViewInterface, Context context, ArrayList<Tasks> tasksArrayList){
        this.recycleViewInterface = recycleViewInterface;
        this.context = context;
        this.tasksArrayList = tasksArrayList;

    }
    @NonNull
    @Override
    public MyAdapterForHome.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.task_item_today,parent,false);
        return new MyAdapterForHome.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvHeadingToday.setText(tasksArrayList.get(position).getHeading());
        holder.time.setText(tasksArrayList.get(position).getTime());
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
        TextView tvHeadingToday;
        TextView time;

        Button deleteTask;
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeadingToday = itemView.findViewById(R.id.tvHeadingToday);
            time = itemView.findViewById(R.id.taskTime);

            constraintLayout = itemView.findViewById(R.id.taskItemToday);
        }
    }
}
