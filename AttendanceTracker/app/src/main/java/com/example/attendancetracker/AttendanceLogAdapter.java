package com.example.attendancetracker;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AttendanceLogAdapter extends RecyclerView.Adapter<AttendanceLogAdapter.CardHolder> {

    private Context context;
    private ArrayList<AttendanceLogModel> attendanceLogModels;


    public AttendanceLogAdapter(Context context, ArrayList<AttendanceLogModel> attendanceLogModels) {
        this.context = context;
        this.attendanceLogModels = attendanceLogModels;
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cards_attendance_log, parent, false);

        return new CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        AttendanceLogModel attendanceLogModel = attendanceLogModels.get(position);
        holder.setDetails(attendanceLogModel);

        if(position % 2 == 0)
            holder.attendanceCard.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.green)));
        else
            holder.attendanceCard.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.sky)));

    }

    @Override
    public int getItemCount() {
        return attendanceLogModels.size();
    }

    class CardHolder extends RecyclerView.ViewHolder{
        private TextView txtAttendanceDate, txtAttendanceDay, txtAttendanceRenderedHours, txtAttendanceTimeIn, txtAttendanceTimeOut, txtAttendanceHoursLeft ;
        private CardView attendanceCard;


        CardHolder(View v){
            super(v);

            txtAttendanceDate = v.findViewById(R.id.txtAttendanceDate);
            txtAttendanceDay = v.findViewById(R.id.txtAttendanceDay);
            txtAttendanceHoursLeft = v.findViewById(R.id.txtAttendanceHoursLeft);
            txtAttendanceRenderedHours = v.findViewById(R.id.txtHoursRendered);
            txtAttendanceTimeIn = v.findViewById(R.id.txtAttendanceTimeIn);
            txtAttendanceTimeOut = v.findViewById(R.id.txtAttendanceTimeOut);

            attendanceCard = v.findViewById(R.id.attendanceCardView);

            txtAttendanceDate.setTextColor(context.getResources().getColor(R.color.white));
            txtAttendanceDay.setTextColor(context.getResources().getColor(R.color.white));
            txtAttendanceHoursLeft.setTextColor(context.getResources().getColor(R.color.white));
            txtAttendanceRenderedHours.setTextColor(context.getResources().getColor(R.color.white));
            txtAttendanceTimeIn.setTextColor(context.getResources().getColor(R.color.white));
            txtAttendanceTimeOut.setTextColor(context.getResources().getColor(R.color.white));

           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent (view.getContext(),AnnouncementsDetails.class);
                    i.putExtra("title", announcementsModels.get(getAdapterPosition()).getTitle());
                    i.putExtra("date", announcementsModels.get(getAdapterPosition()).getDate());
                    i.putExtra("details", announcementsModels.get(getAdapterPosition()).getDetails());
                    view.getContext().startActivity(i);
                }
            });*/


        }

        void setDetails(AttendanceLogModel attendanceLogModel){
            txtAttendanceDate.setText(attendanceLogModel.getDate());
            txtAttendanceDay.setText(attendanceLogModel.getDay());
            txtAttendanceHoursLeft.setText(attendanceLogModel.getHoursLeft());
            txtAttendanceRenderedHours.setText(attendanceLogModel.getHoursRendered());
            txtAttendanceTimeIn.setText(attendanceLogModel.getTimeIn());
            txtAttendanceTimeOut.setText(attendanceLogModel.getTimeOut());
        }
    }
}
