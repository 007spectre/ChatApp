package com.example.saurabhgoyal.buyhatkeproject;

/**
 * Created by saurabh goyal on 10/20/2016.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class ListDisplay extends Activity {
    // Array of strings...
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_TOKEN = "token";
    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X"};
ListView list;
    ProgressDialog pd;
    ArrayList<Users> userList=new ArrayList<>();
    RecyclerView recyclerView;

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
     recyclerView= (RecyclerView) findViewById(R.id.drawerlist);


     //   ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, mobileArray);
getData1();


 }
    private void getData1() {
        pd = new ProgressDialog(ListDisplay.this) {
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
                        Log.v("response","response"+response);
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(jsonArrayRequest);
    }



    private void parseData(JSONArray array){
        for(int i = 0; i<array.length(); i++) {
            Users jdetail = new Users();

            JSONObject json = null;

            try {
                json = array.getJSONObject(i);
//                que=json.getString("question");
//                anss1=json.getString("ans1");
//                anss2=json.getString("ans2");
//                anss3=json.getString("ans3");
//                anss4=json.getString("ans4");
                // superHero.setImageUrl(json.getString(Config.TAG_IMAGE_URL));
                jdetail.setEmail(json.getString(KEY_EMAIL));
                jdetail.setToken(json.getString(KEY_TOKEN));
                jdetail.setUsername(json.getString(KEY_USERNAME));






            } catch (JSONException e) {
                e.printStackTrace();
            }
            userList.add(jdetail);


        }
        VivzAdapter vivzAdapter=new VivzAdapter(getApplicationContext(),userList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Log.v("list", "list" + userList);

        recyclerView.setAdapter(vivzAdapter);
        vivzAdapter.setClickListener(new VivzAdapter.ClickListener() {
            @Override
            public void itemclicked(View view, int position) {
Toast.makeText(getApplicationContext(), userList.get(position).getUsername(), Toast.LENGTH_LONG).show();
                Intent intent=new Intent(ListDisplay.this,ChatActivity.class);
                intent.putExtra("email",userList.get(position).getEmail());
                intent.putExtra("username",userList.get(position).getUsername());

                intent.putExtra("token",userList.get(position).getToken());

                startActivity(intent);


            }
        });

        //Finally initializing our adapter
        //  adapter = new ListingJobAdapter(listSuperHeroes, this);

        //Adding adapter to recyclerview
        //recyclerView.setAdapter(adapter);


    }
}