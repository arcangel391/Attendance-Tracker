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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class projectAdapter extends RecyclerView.Adapter<projectAdapter.MyViewHolder> {
    private ArrayList<String> idlist;
    private ArrayList<String> titlelist;
    private ArrayList<String> datelist;
    private ArrayList<String> userlist;
    private OnItemClickListener mListener;
    private ArrayList<Integer> status;
    private Context context;


public projectAdapter(ArrayList<String> idlist,ArrayList<String> titlelist,ArrayList<String> datelist,ArrayList<String> userlist,ArrayList<Integer> status){
    this.idlist = idlist;
    this.titlelist = titlelist;
    this.datelist=datelist;
    this.userlist = userlist;
    this.status =status;


}
public class MyViewHolder extends RecyclerView.ViewHolder{
    private TextView title;
    private TextView date;
    private TextView id;
    private LinearLayout linearLayout;

    public MyViewHolder(final View view,OnItemClickListener listener){
        super(view);
        title=view.findViewById(R.id.txtTitle2);
        date=view.findViewById(R.id.txtDateAnnce2);
        id = view.findViewById(R.id.txtTimeAnnce2);
        linearLayout = view.findViewById(R.id.cardbg);



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
    public projectAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View projView= LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_project_status,parent,false);
        context = parent.getContext();
        return new MyViewHolder(projView,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull projectAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        String title= titlelist.get(position);
        String date = datelist.get(position);
        String id = idlist.get(position);
        String user = userlist.get(position);
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
        try {
            holder.date.setText(format.format(new SimpleDateFormat("yyyy-MM-dd").parse(date)));
        
        } catch (ParseException e) {
            e.printStackTrace();
        }


        
        holder.title.setText(title);
        holder.id.setText(id);

        int s = status.get(position);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = idlist.get(position);
                String text2 = userlist.get(position);
                Intent intent = new Intent(context,ProjectView.class);
                intent.putExtra("id",text);
                intent.putExtra("user",text2);
                if(s==0){
                    intent.putExtra("status",String.valueOf(s));
                }else{
                    intent.putExtra("status",String.valueOf(s));
                }

                context.startActivity(intent);
            }
        });

        if(position % 2 == 0){
            holder.linearLayout.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.green)));
        }
        else {

            holder.linearLayout.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.sky)));

        }

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

    public void filterlist(ArrayList<String> filteredListt, ArrayList<String> filteredListi, ArrayList<String> filteredListd, ArrayList<String> filteredListu,ArrayList<Integer> filteredlists){
    titlelist=filteredListt;
    idlist = filteredListi;
    datelist =filteredListd;
    userlist =filteredListu;
    status=filteredlists;
    notifyDataSetChanged();


    }
}
