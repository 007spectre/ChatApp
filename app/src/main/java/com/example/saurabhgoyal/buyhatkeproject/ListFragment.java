package com.example.saurabhgoyal.buyhatkeproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by saurabh goyal on 10/21/2016.
 */
public class ListFragment extends Fragment {
    RecyclerView recyclerView;

    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X"};
    ListView list;
    ProgressDialog pd;
    ArrayList<Users> userList=new ArrayList<>();
    SQLiteHandler db;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_listview, container, false);
        recyclerView= (RecyclerView)v. findViewById(R.id.drawerlist);
       db=   SQLiteHandler.getInstance(getActivity());



        //   ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, mobileArray);
        getData1();




        return  v;

    }
    private void getData1() {
        pd = new ProgressDialog(getActivity()) {
            @Override
            public void onBackPressed() {
                pd.dismiss();
            }};
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();


        //Showing a progress dialog
        //  final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, false);

        //Creating a json array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(MainActivity.DATA_URL1,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Dismissing progress dialog
                        pd.dismiss();
                        Log.v("response", "response" + response);
                        //calling method to parse json array
                        parseData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Adding request to the queue
        requestQueue.add(jsonArrayRequest);
    }



    private void parseData(JSONArray array){
        for(int i = 0; i<array.length(); i++) {
            Users jdetail = new Users();

            JSONObject json = null;

            try {
                json = array.getJSONObject(i);

                jdetail.setEmail(json.getString(SQLiteHandler.KEY_EMAIL));
                jdetail.setToken(json.getString(SQLiteHandler.KEY_TOKEN));
                jdetail.setUsername(json.getString(SQLiteHandler.KEY_USERNAME));
                jdetail.setId(json.getString(SQLiteHandler.KEY_ID));







            } catch (JSONException e) {
                e.printStackTrace();
            }
            userList.add(jdetail);
            db.insertUser(userList);


        }
        VivzAdapter vivzAdapter=new VivzAdapter(getActivity(),userList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.v("list", "list" + userList);

        recyclerView.setAdapter(vivzAdapter);
        vivzAdapter.setClickListener(new VivzAdapter.ClickListener() {
            @Override
            public void itemclicked(View view, int position) {
                Users user=db.fetchUser().get(position);
                Toast.makeText(getActivity(),""+user.getUsername()+user.getToken(),Toast.LENGTH_LONG).show();
                Log.v("token","token"+user.getToken());
                Intent intent=new Intent(getActivity(),ChatActivity.class);
                intent.putExtra("email", user.getEmail());
                intent.putExtra("token",user.getToken());

                startActivity(intent);


            }
        });

        //Finally initializing our adapter
        //  adapter = new ListingJobAdapter(listSuperHeroes, this);

        //Adding adapter to recyclerview
        //recyclerView.setAdapter(adapter);


    }
}
