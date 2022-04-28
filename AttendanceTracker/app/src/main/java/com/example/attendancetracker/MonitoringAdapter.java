package com.example.attendancetracker;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;



public class MonitoringAdapter  extends RecyclerView.Adapter<MonitoringAdapter.MCardHolder> {

    private Context context;
    private ArrayList<MonitoringModel> monitoringModels;
    String mRole, mTeam,mReason, mUser;

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
        private TextView txtMemberName, txtMemberRole, txtMemberTeam, txtDisplayName, txtDisplayRole,txtReason;
        private CardView MonitoringCard;
        private Button btnReport, btnSubmit;
        private LayoutInflater layoutInflater;
        public AlertDialog.Builder builder = new AlertDialog.Builder(context);

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
                    View view = inflater.inflate(R.layout.layout_dialog, null);
                    txtDisplayName= view.findViewById(R.id.txtName);
                    txtDisplayRole= view.findViewById(R.id.txtRole);
                    btnSubmit = view.findViewById(R.id.btnSubmitRep);

                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "ASDSADAD", Toast.LENGTH_SHORT).show();
                        }
                    });


                    builder.setView(view);
                    builder.setTitle("Report Team Member");
                    builder.show();

                    Intent i = new Intent (view.getContext(),TeamMonitoring.class);
                    i.putExtra("name", monitoringModels.get(getAdapterPosition()).getName());
                    i.putExtra("role", monitoringModels.get(getAdapterPosition()).getRole());
                    i.putExtra("team", monitoringModels.get(getAdapterPosition()).getRole());

                    String getName = i.getStringExtra("name");
                    String getRole = i.getStringExtra("role");
                    mTeam =  i.getStringExtra("team");
                    mUser = "Sample";
                    txtDisplayName.setText(getName);
                    txtDisplayRole.setText(getRole);
                    //insertFileReport(txtDisplayName.getText().toString(),txtDisplayRole.getText().toString(), mTeam,txtReason.getText().toString(),mUser );





                }
            });


//            //SubmitReport
//            View view1 = inflater.inflate(R.layout.layout_dialog, null);
//            btnSubmit = view1.findViewById(R.id.btnSubmitRep);
//            txtDisplayName =view1.findViewById(R.id.txtName);
//            txtDisplayRole =view1.findViewById(R.id.txtRole);
//            txtReason = view1.findViewById(R.id.txtReason);
//
//            btnSubmit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    insertFileReport(txtDisplayName.getText().toString(),txtDisplayRole.getText().toString(), mTeam,txtReason.getText().toString(),mUser );
//
//                }
//            });





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





        public void insertFileReport(final String name, final String role, final String team,final String reason, String user){
            class  SendPostReqAsyncTask extends AsyncTask<String,Void, String> {


                @Override
                protected String doInBackground(String... strings) {

                    String nameHolder = name;
                    String roleHolder = role;
                    String teamHolder = team;
                    String reasonHolder = reason;
                    String userHolder = user;

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("name", nameHolder));
                    nameValuePairs.add(new BasicNameValuePair("role", roleHolder));
                    nameValuePairs.add(new BasicNameValuePair("team", teamHolder));
                    nameValuePairs.add(new BasicNameValuePair("reason", reasonHolder));
                    nameValuePairs.add(new BasicNameValuePair("user", userHolder));

                    try {
                        HttpClient httpClient = new DefaultHttpClient();

                        HttpPost httpPost = new HttpPost( "http://192.168.1.4/MCC-AttendanceTracker/v1/getReport.php");

                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        HttpResponse httpResponse = httpClient.execute(httpPost);

                        HttpEntity httpEntity = httpResponse.getEntity();


                    } catch (ClientProtocolException e) {

                    } catch (IOException e) {

                    }
                    return "Reported Succesfuly";
                }

                @Override
                protected void onPostExecute(String result) {

                    super.onPostExecute(result);

                    Toast.makeText(context, "Successfully Reported", Toast.LENGTH_LONG).show();

                }
            }

            SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

            sendPostReqAsyncTask.execute(name, role, team, reason , user);

        }




}}



