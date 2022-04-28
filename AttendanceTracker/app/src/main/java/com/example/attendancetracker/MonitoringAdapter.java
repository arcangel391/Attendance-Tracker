package com.example.attendancetracker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MonitoringAdapter  extends RecyclerView.Adapter<MonitoringAdapter.MCardHolder> {

    private Context context;
    private ArrayList<MonitoringModel> monitoringModels;

    public MonitoringAdapter(Context context, ArrayList<MonitoringModel> monitoringModelArrayList) {
        this.context = context;
        this.monitoringModels = monitoringModelArrayList;

    }




    @NonNull
    @Override
    public MCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cards_team_monitoring, parent, false);
        return new MCardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MCardHolder holder, int position) {

        MonitoringModel monitoringModel2 = monitoringModels.get(position);
        holder.setDetails(monitoringModel2);



    }

    @Override
    public int getItemCount() {
        return monitoringModels.size();
    }


    class MCardHolder extends RecyclerView.ViewHolder{
        private TextView txtMemberName, txtMemberRole, txtMemberTeam;
        private CardView MonitoringCard;
        private Button btnReport;
        private LayoutInflater layoutInflater;

        MCardHolder(View v){
            super(v);

            txtMemberName = v.findViewById(R.id.txtNameMonitoring);
            txtMemberRole = v.findViewById(R.id.txtRoleMonitoring);
            txtMemberTeam = v.findViewById(R.id.txtTeam);
            btnReport = v.findViewById(R.id.btn_reportmonitoring);
            MonitoringCard = v.findViewById(R.id.card_monitor);
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            btnReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    View view = inflater.inflate(R.layout.layout_dialog, null);
                    builder.setView(view);
                    builder.setTitle("Report Team Member");
                    builder.show();
                }
            });
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

        void setDetails(MonitoringModel monitoringModel){
            txtMemberName.setText(monitoringModel.getName());
            txtMemberRole.setText(monitoringModel.getRole());
            txtMemberTeam.setText(monitoringModel.getTeam());

        }

    }
}



