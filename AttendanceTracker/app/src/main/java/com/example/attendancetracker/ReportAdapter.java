package com.example.attendancetracker;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder> {
    private ArrayList<String> idlist;
    private ArrayList<String> titlelist;
    private ArrayList<String> datelist;
    private ArrayList<String> userlist;
    private OnItemClickListener mListener;
    private Context context;
    Calendar calendar = Calendar.getInstance();

    public ReportAdapter(ArrayList<String> idlist,ArrayList<String> titlelist,ArrayList<String> datelist,ArrayList<String> userlist){
        this.idlist = idlist;
        this.titlelist = titlelist;
        this.datelist=datelist;
        this.userlist = userlist;

    }
    public void filterList(ArrayList<String> filteredListui, ArrayList<String> filteredListt, ArrayList<String> filteredListd, ArrayList<String> filteredListu){
        idlist = filteredListui;
        titlelist = filteredListt;
        datelist = filteredListd;
        userlist = filteredListu;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView date;
        private TextView id;
        private LinearLayout cardView;
        public MyViewHolder(final View view,OnItemClickListener listener){
            super(view);
            title=view.findViewById(R.id.txtTitle2);
            date=view.findViewById(R.id.txtDateAnnce2);
            cardView=view.findViewById(R.id.cardView);

            view.setOnClickListener(new View.OnClickListener() {
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

    @NonNull
    @Override
    public ReportAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View projView= LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_reports,parent,false);
        context = parent.getContext();
        return new MyViewHolder(projView,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        String title= titlelist.get(position);
        String dates = datelist.get(position);
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyy");
        try {
            holder.date.setText(format.format(new SimpleDateFormat("yyyy/MM/dd").parse(dates)));

        } catch (ParseException e) {
            e.printStackTrace();
        }


        String id = idlist.get(position);
        String user = userlist.get(position);

        holder.title.setText(title);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = idlist.get(position);
                String text2 = userlist.get(position);
                Intent intent = new Intent(context,ReportView.class);
                intent.putExtra("id",text);
                intent.putExtra("user",text2);


                context.startActivity(intent);
            }
        });

        if(position % 2 == 0)
            holder.cardView.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.green)));
        else
            holder.cardView.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.sky)));
    }

    @Override
    public int getItemCount() {
        return titlelist.size();
    }

    public interface  OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
}

