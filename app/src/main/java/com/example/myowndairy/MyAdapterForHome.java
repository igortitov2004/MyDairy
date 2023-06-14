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

    private final RecycleViewInterface recycleViewInterface;
    Context context;
    ArrayList<Tasks> tasksArrayList;


    public MyAdapterForHome(RecycleViewInterface recycleViewInterface, Context context, ArrayList<Tasks> tasksArrayList){
        this.recycleViewInterface = recycleViewInterface;
        this.context = context;
        this.tasksArrayList = tasksArrayList;

    }
    @NonNull
    @Override
    public MyAdapterForHome.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.task_item_today,parent,false);
        return new MyAdapterForHome.MyViewHolder(v,recycleViewInterface);
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

        public MyViewHolder(@NonNull View itemView,RecycleViewInterface recycleViewInterface) {
            super(itemView);
            tvHeadingToday = itemView.findViewById(R.id.tvHeadingToday);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recycleViewInterface!=null){
                        int pos = getAdapterPosition();
                        if(pos !=RecyclerView.NO_POSITION){
                            recycleViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
