package com.example.attendancetracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Reports extends Fragment {
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> details = new ArrayList<>();
    ArrayList<String> drive = new ArrayList<>();
    ArrayList<String> user = new ArrayList<>();

    ArrayList<String> filterListui = new ArrayList<>();
    ArrayList<String> filterListt = new ArrayList<>();
    ArrayList<String> filterListd = new ArrayList<>();
    ArrayList<String> filterListu = new ArrayList<>();


    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    TextView p_refresh, p_filter;
    EditText p_search;
    int click = 0;
    ArrayAdapter<String> arrayAdapter;


    FloatingActionButton btnAdd;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View reportview= inflater.inflate(R.layout.fragment_reports, container, false);

        floatingActionButton = reportview.findViewById(R.id.btnAdd);



        View reportView = inflater.inflate(R.layout.fragment_reports, container, false);
        floatingActionButton = reportview.findViewById(R.id.btnAdd);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),SubmitReport.class);
                startActivity(intent);
            }
        });
        new Connection().execute();


        btnAdd = reportView.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(),SubmitReport.class);
                startActivity(i);
            }
        });
        return reportView;
    }
    class Connection extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            String result = "asd";
            String host = "http://192.168.1.19/upload_file/read.php";

            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(host));
                HttpResponse response = client.execute(request);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer stringBuffer = new StringBuffer("");

                String line = "";
                while ((line = reader.readLine()) != null) {
                    stringBuffer.append(line);
                    break;
                }
                reader.close();
                result = stringBuffer.toString();
            } catch (Exception e) {

                return new String(e.getMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result){


            recyclerView = getView().findViewById(R.id.report_view);
            p_search = getView().findViewById(R.id.p_search);
            p_filter = getView().findViewById(R.id.p_filter);
            p_refresh = getView().findViewById(R.id.p_refresh);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int success = jsonObject.getInt("success");
                JSONArray projs = jsonObject.getJSONArray("projs");
                for (int i = 0; i < projs.length(); i++) {
                    JSONObject proj = projs.getJSONObject(i);
                    int report_id = proj.getInt("report_id");
                    int user_acc_id = proj.getInt("user_acc_id");
                    String drive_link = proj.getString("gdrive_link");
                    String detailsreport = proj.getString("report_details");
                    String titlereport = proj.getString("report_subject");
                    String date_submitted = proj.getString("date_submitted");
                    id.add(String.valueOf(report_id));
                    user.add(String.valueOf(user_acc_id));
                    title.add(titlereport);
                    drive.add(drive_link);
                    details.add(detailsreport);
                    date.add(date_submitted);
                }

                ReportAdapter adapter = new ReportAdapter(id, title, date, user);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());


                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


                p_filter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (click==0){
                            linearLayoutManager.setReverseLayout(true);
                            linearLayoutManager.setStackFromEnd(true);
                            click=1;
                        }
                        else {
                            linearLayoutManager.setReverseLayout(false);
                            linearLayoutManager.setStackFromEnd(false);
                            click=0;
                        }
                    }
                });
                p_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Reports()).commit();
                    }
                });

                p_search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                            filterListui.clear();
                            filterListt.clear();
                            filterListd.clear();
                            filterListu.clear();

                            String asd = s.toString();
                            String t = "usd";
                            String ui = "";
                            String d = "";
                            String u = "";


                            for (int i=0; i<title.size(); i++){
                                t=title.get(i);
                                ui=id.get(i);
                                d=date.get(i);
                                u=user.get(i);


                                if(t.toLowerCase().contains(asd.toLowerCase())){
                                    filterListt.add(t);
                                    filterListui.add(ui);
                                    filterListd.add(d);
                                    filterListu.add(u);
                                }
                            }
                            if (!filterListui.isEmpty()){
                                adapter.filterList(filterListui,filterListt,filterListd,filterListu);

                            }


                    }
                });




            } catch (JSONException e) {
                e.printStackTrace();
            }




        }
    }
    }
