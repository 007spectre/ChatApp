package com.example.saurabhgoyal.buyhatkeproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.saurabhgoyal.buyhatkeproject.Utilities.ConnectionDetector;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_TOKEN = "token";
    public static String KEY_USEREMAIL = "useremail";


    public static String usertext;
    public static String email;
    public static final String PREFS_NAME = "AOP_PREFS";
    public static final String PREFS_KEY = "AOP_PREFS_String";

    Boolean flag = true;
    ProgressDialog pd;
    ArrayList<Users> userList = new ArrayList<>();
    public static final String DATA_URL1 = "http://www.creativecolors.in/gcm/selectcount.php";

    private static final String REGISTER_URL = "http://www.creativecolors.in/gcm/userregister.php";
    private static final String FECTHFILE_ID = "http://www.creativecolors.in/gcm/myorder.php";

    private static final String LOGIN_URL = "http://www.creativecolors.in/gcm/login.php";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    EditText username;
    EditText password;
    EditText emailid;
    String token;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KEY_USEREMAIL = getValue(getBaseContext());
        if (KEY_USEREMAIL == null || KEY_USEREMAIL.trim().equals("")) {

        } else {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_main);


        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        emailid = (EditText) findViewById(R.id.emailid);


        //Initializing our broadcast receiver
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {

            //When the broadcast received
            //We are sending the broadcast from GCMRegistrationIntentService

            @Override
            public void onReceive(Context context, Intent intent) {
                //If the broadcast has received with success
                //that means device is registered successfully
                if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)) {
                    //Getting the registration token from the intent
                    token = intent.getStringExtra("token");
                    //Displaying the token as toast
//                    Toast.makeText(getApplicationContext(), "Registration token:" + token, Toast.LENGTH_LONG).show();

                    //if the intent is not with success then displaying error messages
                } else if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)) {
                    Toast.makeText(getApplicationContext(), "GCM registration error!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();
                }
            }
        };

        //Checking play service is available or not
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        //if play service is not available
        if (ConnectionResult.SUCCESS != resultCode) {
            //If play service is supported but not installed
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                //Displaying message that play service is not installed
                Toast.makeText(getApplicationContext(), "Google Play Service is not install/enabled in this device!", Toast.LENGTH_LONG).show();
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());

                //If play service is not supported
                //Displaying an error message
            } else {
                Toast.makeText(getApplicationContext(), "This device does not support for Google Play Service!", Toast.LENGTH_LONG).show();
            }

            //If play service is available
        } else {
            //Starting intent to register device
            Intent itent = new Intent(this, GCMRegistrationIntentService.class);
            startService(itent);
        }


        Button button = (Button) findViewById(R.id.signup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validation validation = new Validation(getApplicationContext());
                String email1 = emailid.getText().toString();
                String password1 = password.getText().toString();
                String username1 = username.getText().toString();

                if (password1 == null || password1.trim().equals("") || username1 == null || username1.trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter all the details", Toast.LENGTH_SHORT).show();
                    flag = false;

                }
                flag = validation.email(email1);


                if (token != null && flag == true) {
                    ConnectionDetector connectionDetector=new ConnectionDetector(getApplicationContext());
                    if(connectionDetector.isConnectingToInternet()==true) {
                        signp();

                    }
                    else {


                        Toast.makeText(getApplicationContext(), "Connect to the internet", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });
        Button button2 = (Button) findViewById(R.id.signin);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validation validation = new Validation(getApplicationContext());
                String email1 = emailid.getText().toString();
                String password1 = password.getText().toString();
                String username1 = username.getText().toString();

                if (password1 == null || password1.trim().equals("") || username1 == null || username1.trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter all the details", Toast.LENGTH_SHORT).show();
                    flag = false;

                }
                flag = validation.email(email1);


                if (token != null && flag == true) {
                    ConnectionDetector connectionDetector=new ConnectionDetector(getApplicationContext());
                    if(connectionDetector.isConnectingToInternet()==true) {
                        signIn();

                    }
                    else {


                        Toast.makeText(getApplicationContext(), "Connect to the internet", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });


    }

    public void save(Context context, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString(PREFS_KEY, text); //3
        editor.commit(); //4
    }

    public void signp() {
        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        KEY_USEREMAIL = emailid.getText().toString();
                        usertext = username.getText().toString();
                        save(getApplicationContext(), KEY_USEREMAIL);
                        pd.dismiss();

                        Toast.makeText(getApplicationContext(), "User Added to the Database", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        Toast.makeText(MainActivity.this, "Connection Error Please try again ", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put(KEY_EMAIL, emailid.getText().toString());
                params.put(KEY_PASSWORD, password.getText().toString());
                params.put(KEY_USERNAME, username.getText().toString());

                params.put(KEY_TOKEN, token);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }

    public void signIn() {
        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.v("success", "" + response);
                        pd.dismiss();

                        if(response.equals("failure")){
                            Toast.makeText(getApplicationContext(), "User Id or Password not matched. Please try again", Toast.LENGTH_LONG).show();

                        }else {
                            KEY_USEREMAIL = emailid.getText().toString();
                            usertext = username.getText().toString();
                            save(getApplicationContext(), KEY_USEREMAIL);


                            Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_LONG).show();
                            alert();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        Toast.makeText(MainActivity.this, "Login Error ,please try again", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put(KEY_EMAIL, emailid.getText().toString());
                params.put(KEY_PASSWORD, password.getText().toString());


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }



    public void alert() {


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Confirm");
        builder.setMessage("Do you want to get back  your Messages ?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                pd = new ProgressDialog(MainActivity.this);
                pd.setMessage("Loading");
                pd.setCancelable(false);
                pd.show();

                // Do nothing but close the dialog
               submit();
            }

        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();

                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);


            }
        });

        AlertDialog alert = builder.create();
        alert.show();


    }

    public void submit(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, FECTHFILE_ID,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        JSONArray jsonArray=null;

                        try {
                             jsonArray = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
for(int i=0; i<jsonArray.length();i++){
    JSONObject json = null;

    try {
        json = jsonArray.getJSONObject(i);
        BaseDemoActivity.EXISTING_FILE_ID=json.getString("fileid");





    } catch (JSONException e) {
        e.printStackTrace();
    }



}

                     Intent intent=new Intent(MainActivity.this,RetrieveContentsActivity.class);
                        startActivity(intent);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();


                params.put(KEY_EMAIL, MainActivity.KEY_USEREMAIL);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }




    public String getValue(Context context) {
        SharedPreferences settings;
        String text;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        text = settings.getString(PREFS_KEY, null); //2
        return text;
    }
    public void navigateUsers(){
//        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//        QuestionFragment rFragment = new QuestionFragment();
//        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
//        ft.replace(R.id.content_frame, rFragment);
//        ft.commit();
        Intent intent=new Intent(MainActivity.this,ListDisplay.class);
        startActivity(intent);




    }
    //Registering receiver on activity resume
    @Override
    protected void onResume() {
        super.onResume();
        Log.w("MainActivity", "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }


    //Unregistering receiver on activity paused
    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
