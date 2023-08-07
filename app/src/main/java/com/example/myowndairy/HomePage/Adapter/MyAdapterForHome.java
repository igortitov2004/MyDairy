package com.example.myowndairy.HomePage.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myowndairy.DB.DBHelper;
import com.example.myowndairy.Interfaces.RecycleViewInterface;
import com.example.myowndairy.R;
import com.example.myowndairy.Model.Tasks;
import com.example.myowndairy.TasksPage.Adapter.MyAdapterForTasks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MyAdapterForHome extends RecyclerView.Adapter<MyAdapterForHome.MyViewHolder>{
    private RecycleViewInterface recycleViewInterface;
    Context context;
    List<Tasks> tasksArrayList;

    Boolean isDone;





    public MyAdapterForHome(RecycleViewInterface recycleViewInterface, Context context, ArrayList<Tasks> tasksArrayList,Boolean isDone){
        this.recycleViewInterface = recycleViewInterface;
        this.context = context;
        this.tasksArrayList = tasksArrayList;
        this.isDone = isDone;

    }
    @NonNull
    @Override
    public MyAdapterForHome.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.task_item_today,parent,false);

        return new MyAdapterForHome.MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvHeadingToday.setText(tasksArrayList.get(position).getHeading());
        holder.time.setText(tasksArrayList.get(position).getTime());

        if(isDone==null){
            holder.checkBox.setVisibility(View.VISIBLE);
        }else{
            holder.checkBox.setVisibility(View.GONE);
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                recycleViewInterface.onCheckedChanged(tasksArrayList.get(position));
                deleteItem(holder,position);
                Toast.makeText(context,"ura",Toast.LENGTH_SHORT).show();
            }
        });
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycleViewInterface.onItemClick(tasksArrayList.get(position));

            }
        });
    }

    private void deleteItem(MyViewHolder holder,int position) {
        tasksArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, tasksArrayList.size());
        holder.itemView.setVisibility(View.GONE);
    }


    @Override
    public int getItemCount() {
        return tasksArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvHeadingToday;
        TextView time;
        ConstraintLayout constraintLayout;



        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeadingToday = itemView.findViewById(R.id.tvHeadingToday);
            time = itemView.findViewById(R.id.taskTime);


            checkBox = itemView.findViewById(R.id.check);


            constraintLayout = itemView.findViewById(R.id.taskItemToday);
        }
    }
}
