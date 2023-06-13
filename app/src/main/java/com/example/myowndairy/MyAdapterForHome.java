package com.example.myowndairy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterForHome extends RecyclerView.Adapter<MyAdapterForHome.MyViewHolder>{
    Context context;
    ArrayList<Tasks> tasksArrayList;


    public MyAdapterForHome(Context context,ArrayList<Tasks> tasksArrayList){
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
        Tasks tasks = tasksArrayList.get(position);
        holder.tvHeadingToday.setText(tasks.heading);
    }



    @Override
    public int getItemCount() {
        return tasksArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvHeadingToday;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeadingToday = itemView.findViewById(R.id.tvHeadingToday);
        }
    }
}
