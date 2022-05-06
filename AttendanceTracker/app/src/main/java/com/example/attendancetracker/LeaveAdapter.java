package com.example.attendancetracker;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.icu.text.Transliterator;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaveAdapter extends RecyclerView.Adapter<LeaveAdapter.ViewHolder>{
    private ArrayList<String> id;
    private ArrayList<String> leave_type;
    private ArrayList<String> leave_from;
    private ArrayList<String> leave_status;
    private OnItemClickListener mListener;
    private Context context;
    private ArrayList<String> userID;


    public LeaveAdapter(ArrayList<String> id,ArrayList<String> leave_type,ArrayList<String> leave_from,ArrayList<String> leave_status,ArrayList<String> userID) {
        this.id = id;
        this.leave_type = leave_type;
        this.leave_from=leave_from;
        this.leave_status = leave_status;
        this.userID = userID;
    }
    public void filterList(ArrayList<String> filteredListui, ArrayList<String> filteredListt, ArrayList<String> filteredListd, ArrayList<String> filteredListu){
        id = filteredListui;
        leave_type = filteredListt;
        leave_from = filteredListd;
        leave_status = filteredListu;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cards_leave_status, parent,false);
        ViewHolder viewHolder = new ViewHolder(view,mListener);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       id.get(position);
        String dates = leave_from.get(position);
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyy");
        try {

            holder.dateStart.setText(format.format(new SimpleDateFormat("yyyy/MM/dd").parse(dates)));

        } catch (ParseException e) {
            e.printStackTrace();
        }
       int status = Integer.parseInt(leave_status.get(position));
       if (status==0){
           holder.leaveStatus.setText("Pending");
       }else if (status==1){
           holder.leaveStatus.setText("Confirmed");
       } else if(status==2){
           holder.leaveStatus.setText("Declined");
       } else{
           holder.leaveStatus.setText("Cancelled");
       }
        holder.leaveReason.setText(leave_type.get(position));
        holder.leavecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = id.get(position);
                String text1 = userID.get(position);
                Intent intent = new Intent(context,ViewLeave.class);
                intent.putExtra("id",text);
                intent.putExtra("userID",text1);
                context.startActivity(intent);
            }
        });

        if(position % 2 == 0)
            holder.leavecard.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.green)));
        else
            holder.leavecard.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.sky)));

    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leavecard;
        TextView leaveReason;
        TextView dateStart;
        TextView leaveStatus;



        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            leaveReason = itemView.findViewById(R.id.txtTitle2);
            dateStart = itemView.findViewById(R.id.txtDateAnnce2);
            leaveStatus = itemView.findViewById(R.id.txtTimeAnnce2);
            leavecard = itemView.findViewById(R.id.leavecard);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(listener !=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }


    public interface OnItemClickListener  {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }



}