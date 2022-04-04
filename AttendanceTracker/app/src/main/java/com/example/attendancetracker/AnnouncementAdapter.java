package com.example.attendancetracker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

    }

    @Override
    public int getItemCount() {
        return announcementsModels.size();
    }

    class CardHolder extends RecyclerView.ViewHolder{
        private TextView txtAnnouncementTitle, txtAnnouncementDate, txtAnnouncementTime;

        CardHolder(View v){
            super(v);
            txtAnnouncementTitle = v.findViewById(R.id.txtAnnouncementTitle);
            txtAnnouncementDate = v.findViewById(R.id.txtAnnouncementDate);
            txtAnnouncementTime = v.findViewById(R.id.txtAnnouncementTime);
        }

        void setDetails(AnnouncementsModel announcementsModel){
            txtAnnouncementTitle.setText(announcementsModel.getTitle());
            txtAnnouncementDate.setText(announcementsModel.getDate());
            txtAnnouncementTime.setText(announcementsModel.getTime());
        }
    }
}
