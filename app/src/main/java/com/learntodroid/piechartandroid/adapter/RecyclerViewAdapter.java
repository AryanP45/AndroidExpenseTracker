package com.learntodroid.piechartandroid.adapter;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learntodroid.piechartandroid.MainActivity;
import com.learntodroid.piechartandroid.R;
import com.learntodroid.piechartandroid.rowStructure;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView .Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<rowStructure>  expenselist;

    public RecyclerViewAdapter(Context context, List<rowStructure> expenselist) {
        this.context = context;
        this.expenselist = expenselist;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_expense_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        rowStructure row= expenselist.get(position);
        holder.category.setText(row.getCategory());
        holder.note.setText(row.getNote());
        holder.date.setText(row.getDate());
        holder.amount.setText(row.getAmount());
        holder.id.setText(row.getId());


    }

    @Override
    public int getItemCount() {
        return expenselist.size();
    }



public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView category;
    public  TextView note;
    public TextView amount;
    public TextView date;
    public ImageButton edit;
    public ImageButton delete;
    public  TextView id;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        category = itemView.findViewById(R.id.textView2);
        note = itemView.findViewById(R.id.note_txt);
        amount = itemView.findViewById(R.id.textView3);
        date = itemView.findViewById(R.id.textView4);
        edit = itemView.findViewById(R.id.editButton);
        delete = itemView.findViewById(R.id.deleteButton);
        id=itemView.findViewById(R.id.idview);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, com.learntodroid.piechartandroid.edit.class);
                intent.putExtra("rowId",id.getText().toString());
                context.startActivity(intent);

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, com.learntodroid.piechartandroid.delete.class);
                intent.putExtra("rowId",id.getText().toString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View view) {
        Log.d("adapter", "onClick: Item is clickable");

    }
}
}