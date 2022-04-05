package com.example.attendancetracker;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.CardHolder> {

    private Context context;
    private ArrayList<AnnouncementsModel> announcementsModels;


    public AnnouncementAdapter(Context context, ArrayList<AnnouncementsModel> announcementsModels) {
        this.context = context;
        this.announcementsModels = announcementsModels;
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cards_announcement, parent, false);

        return new CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        AnnouncementsModel announcementsModel = announcementsModels.get(position);
        holder.setDetails(announcementsModel);

        if(position % 2 == 0)
            holder.announcementCards.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.green)));
        else
            holder.announcementCards.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.sky)));

    }

    @Override
    public int getItemCount() {
        return announcementsModels.size();
    }

    class CardHolder extends RecyclerView.ViewHolder{
        private TextView txtAnnouncementTitle, txtAnnouncementDate, txtAnnouncementTime;
        private CardView announcementCards;


        CardHolder(View v){
            super(v);

            txtAnnouncementTitle = v.findViewById(R.id.txtAnnouncementTitle);
            txtAnnouncementDate = v.findViewById(R.id.txtAnnouncementDate);
            txtAnnouncementTime = v.findViewById(R.id.txtAnnouncementTime);
            announcementCards = v.findViewById(R.id.announcementCardView);

            txtAnnouncementTime.setTextColor(context.getResources().getColor(R.color.white));
            txtAnnouncementDate.setTextColor(context.getResources().getColor(R.color.white));
            txtAnnouncementTitle.setTextColor(context.getResources().getColor(R.color.white));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent (view.getContext(),AnnouncementsDetails.class);
                    i.putExtra("title", announcementsModels.get(getAdapterPosition()).getTitle());
                    i.putExtra("date", announcementsModels.get(getAdapterPosition()).getDate());
                    i.putExtra("details", announcementsModels.get(getAdapterPosition()).getDetails());
                    view.getContext().startActivity(i);
                }
            });


        }

        void setDetails(AnnouncementsModel announcementsModel){
            txtAnnouncementTitle.setText(announcementsModel.getTitle());
            txtAnnouncementDate.setText(announcementsModel.getDate());
            txtAnnouncementTime.setText(announcementsModel.getTime());
        }
    }
}
