package com.example.attendancetracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProjectStatus extends Fragment {
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> user = new ArrayList<>();
    ArrayList<Integer> status = new ArrayList<>();

FloatingActionButton floatingActionButton;
RecyclerView recyclerView;
TextView p_refresh,p_filter,p_team;
    int click = 0;
    int leaderid=267;

ArrayList<Date> datelists = new ArrayList<>();
EditText p_search;
projectAdapter projectAdapter;
ArrayAdapter<String> arrayAdapter;
    ArrayList<String> filteredListi = new ArrayList<>();
    ArrayList<String> filteredListt = new ArrayList<>();
    ArrayList<String> filteredListd = new ArrayList<>();
    ArrayList<String> filteredListu = new ArrayList<>();
    ArrayList<Integer> filteredLists = new ArrayList<>();
    String host ="http://192.168.1.4/crud/read.php";
    int rclick =0;
private ProjectStatus projectStatus;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View statusview   = inflater.inflate(R.layout.fragment_project_status, container, false);


        floatingActionButton = statusview.findViewById(R.id.submit_proj);
        p_refresh = statusview.findViewById(R.id.p_refresh);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),SubmitProject.class);
                startActivity(intent);
            }
        });
        new Connection().execute();

        p_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProjectStatus()).commit();
            }
        });




        return statusview;


    }
    private void filter(String text){




    }

    class Connection extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String result = "asd";


            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(host));
                HttpResponse response = client.execute(request);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer stringBuffer = new StringBuffer("");

                String line ="";
                while((line= reader.readLine()) != null){
                         stringBuffer.append(line);
                         break;
                }
                reader.close();
                result = stringBuffer.toString();
            }
            catch (Exception e){

                return new String(e.getMessage());
            }




            return result;
        }
        @Override
        protected void onPostExecute(String result){


            recyclerView = getView().findViewById(R.id.status_view);
            p_search = getView().findViewById(R.id.p_search);
            p_filter = getView().findViewById(R.id.p_filter);

            try {
                JSONObject jsonObject = new JSONObject(result);
                int success = jsonObject.getInt("success");
                JSONArray projs = jsonObject.getJSONArray("projs");
                for(int i=0; i<projs.length(); i++){
                    JSONObject proj = projs.getJSONObject(i);
                    int project_id = proj.getInt("project_id");
                    int user_id = proj.getInt("user_acc_id");
                    String task_name = proj.getString("task_name");
                    String date_submitted = proj.getString("date_submitted");


                    id.add(String.valueOf(project_id));
                    user.add(String.valueOf(user_id));
                    title.add(task_name);
                    date.add(date_submitted);
                    status.add(0);


                }


                projectAdapter adapter = new projectAdapter(id,title,date,user,status);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


                p_filter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(click==0){
                            linearLayoutManager.setReverseLayout(true);
                            linearLayoutManager.setStackFromEnd(true);
                            click=1;
                        }else{
                            linearLayoutManager.setReverseLayout(false);
                            linearLayoutManager.setStackFromEnd(false);
                            click=0;
                        }
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
                        filteredListi.clear();
                        filteredListt.clear();
                        filteredListd.clear();
                        filteredListu.clear();
                        String asd =s.toString();
                        String t="asd";
                        String ui="";
                        String d="";
                        String u="";
                        int stat= 0;
                        for(int i=0; i<title.size();i++){
                            t=title.get(i);
                            ui=id.get(i);
                            d = date.get(i);
                            u = user.get(i);
                            stat= status.get(i);

                            if(t.toLowerCase().contains(asd.toLowerCase())){
                                filteredListt.add(t);
                                filteredListi.add(ui);
                                filteredListd.add(d);
                                filteredListu.add(u);
                                filteredLists.add(stat);
                            }
                        }
                        if(!filteredListi.isEmpty()){
                            adapter.filterlist(filteredListt,filteredListi,filteredListd,filteredListu,filteredLists);
                        }


                    }
                });




            } catch (JSONException e) {
                e.printStackTrace();
            }




        }
    }

    class Connection2 extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String result = "asd";
            host ="http://192.168.1.4/crud/reads.php";


            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(host));
                HttpResponse response = client.execute(request);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer stringBuffer = new StringBuffer("");

                String line ="";
                while((line= reader.readLine()) != null){
                    stringBuffer.append(line);
                    break;
                }
                reader.close();
                result = stringBuffer.toString();
            }
            catch (Exception e){

                return new String(e.getMessage());
            }




            return result;
        }
        @Override
        protected void onPostExecute(String result){


            recyclerView = getView().findViewById(R.id.status_view);
            p_search = getView().findViewById(R.id.p_search);
            p_filter = getView().findViewById(R.id.p_filter);

            try {
                JSONObject jsonObject = new JSONObject(result);
                int success = jsonObject.getInt("success");
                JSONArray projs = jsonObject.getJSONArray("projs");
                for(int i=0; i<projs.length(); i++){
                    JSONObject proj = projs.getJSONObject(i);
                    int project_id = proj.getInt("team_project_id");
                    int user_id = proj.getInt("user_acc_id");
                    String task_name = proj.getString("task_name");
                    String date_submitted = proj.getString("date_submitted");


                    id.add(String.valueOf(project_id));
                    user.add(String.valueOf(user_id));
                    title.add(task_name);
                    date.add(date_submitted);
                    status.add(1);


                }


                projectAdapter adapter = new projectAdapter(id,title,date,user,status);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


                p_filter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(click==0){
                            linearLayoutManager.setReverseLayout(true);
                            linearLayoutManager.setStackFromEnd(true);
                            click=1;
                        }else{
                            linearLayoutManager.setReverseLayout(false);
                            linearLayoutManager.setStackFromEnd(false);
                            click=0;
                        }
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
                        filteredListi.clear();
                        filteredListt.clear();
                        filteredListd.clear();
                        filteredListu.clear();
                        filteredLists.clear();
                        String asd =s.toString();
                        String t="asd";
                        String ui="";
                        String d="";
                        String u="";
                        int stat =1;
                        for(int i=0; i<title.size();i++){
                            t=title.get(i);
                            ui=id.get(i);
                            d = date.get(i);
                            u = user.get(i);
                            stat = status.get(i);

                            if(t.toLowerCase().contains(asd.toLowerCase())){
                                filteredListt.add(t);
                                filteredListi.add(ui);
                                filteredListd.add(d);
                                filteredListu.add(u);
                                filteredLists.add(stat);
                            }
                        }
                        if(!filteredListi.isEmpty()){
                            adapter.filterlist(filteredListt,filteredListi,filteredListd,filteredListu,filteredLists);
                        }


                    }
                });




            } catch (JSONException e) {
                e.printStackTrace();
            }




        }
    }


}
