package com.example.rajdeeprao.inclass07;
/*
    InClass07
    CustomAdapter.java
    Yateen Kedare | Rajdeep Rao
 */


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yatee on 2/27/2017.
 */

public class CustomAdapter extends ArrayAdapter {
    private Context context;
    ArrayList<Note> noteArrayList;
    String displayContents;
    IData activity;

    public CustomAdapter(Context context, ArrayList<Note> objects, String status, IData data) {
        super(context, R.layout.my_list, objects);
        this.context = context;
        this.activity  = data;
        this.noteArrayList = objects;
        this.displayContents = status;
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(displayContents.equals("COMPLETED")){
            if(!noteArrayList.get(position).getStatus().equals("COMPLETED")) {
                return new View(context);
            }
        }
        else if(displayContents.equals("PENDING")){
            if(!noteArrayList.get(position).getStatus().equals("PENDING")) {
                return new View(context);
            }
        }

        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.my_list, parent, false);
            holder = new ViewHolder();
            holder.titleText = (TextView) convertView.findViewById(R.id.TitleTV);
            holder.dateText = (TextView) convertView.findViewById(R.id.TimeTV);
            holder.statusText = (CheckBox) convertView.findViewById(R.id.checkBox);
            holder.priorityText = (TextView) convertView.findViewById(R.id.PriorityTv);

            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();
        TextView title = holder.titleText;
        TextView date = holder.dateText;
        final CheckBox status = holder.statusText;
        TextView priority = holder.priorityText;

        PrettyTime p = new PrettyTime();
        Date dateFormat = new Date(noteArrayList.get(position).getUpdate_time());
        title.setText(noteArrayList.get(position).getNote());
        date.setText(p.format(dateFormat).toString());
        status.setChecked(noteArrayList.get(position).getStatus().equals("COMPLETED"));
        priority.setText(noteArrayList.get(position).getPriority());

        status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {



                AlertDialog.Builder builder=new AlertDialog.Builder(context);


                if(noteArrayList.get(position).getStatus().equals("COMPLETED"))
                    builder.setMessage("Do you want to mark it as Pending");
                else
                    builder.setMessage("Do you want to mark it as Completed");

                    builder.setMessage("Do you want to mark it as Pending")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Note n = new Note(noteArrayList.get(position).getNote(),isChecked?"COMPLETED":"PENDING",noteArrayList.get(position).getPriority(),noteArrayList.get(position).getUpdate_time());
                                    n.set_id(noteArrayList.get(position).get_id());
                                    MainActivity.dm.updatedNote(n);
                                    Log.d("BUILDER", noteArrayList.get(position).toString());
                                    activity.callbackAdapter();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("NO: ","Clicked No");

                                }
                            });
                    final AlertDialog alert=builder.create();
                    alert.show();





            }
        });
        return convertView;
    }

    class ViewHolder{
        TextView titleText;
        TextView dateText;
        CheckBox statusText;
        TextView priorityText;
    }
    interface IData{
        public void callbackAdapter();
    }
}
