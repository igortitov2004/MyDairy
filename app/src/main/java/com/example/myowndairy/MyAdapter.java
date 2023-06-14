package com.example.myowndairy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final RecycleViewInterface recycleViewInterface;
    Context context;
    ArrayList<Tasks> tasksArrayList;


    public MyAdapter(RecycleViewInterface recycleViewInterface, Context context, ArrayList<Tasks> tasksArrayList){
        this.recycleViewInterface = recycleViewInterface;
        this.context = context;
        this.tasksArrayList = tasksArrayList;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.task_item,parent,false);
        return new MyViewHolder(v,recycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
         Tasks tasks = tasksArrayList.get(position);
         holder.tvHeading.setText(tasks.heading);

    }

    @Override
    public int getItemCount() {
        return tasksArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvHeading;

        public MyViewHolder(@NonNull View itemView,RecycleViewInterface recycleViewInterface) {
            super(itemView);
            tvHeading = itemView.findViewById(R.id.tvHeading);
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
