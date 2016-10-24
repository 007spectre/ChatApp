package com.example.saurabhgoyal.buyhatkeproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by saurabh goyal on 10/21/2016.
 */
public class Message extends Activity {
    public static final String KEY_MESSAGE = "message";
    private static final String REGISTER_URL = "http://www.creativecolors.in/gcm/send.php";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USEREMAIL = "useremail";




    EditText message;
    String email;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
   ;
       email=  getIntent().getExtras().getString("email");
         token=  getIntent().getExtras().getString("token");

        message=(EditText)findViewById(R.id.editText);

        final Button submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              submit();
            }
        });

    }
    public void submit(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(Message.this,ListDisplay.class);
                        startActivity(intent);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Message.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put(KEY_MESSAGE, message.getText().toString());
                Log.v("email","emailid"+email);

                params.put(KEY_EMAIL, email);
                params.put(KEY_USEREMAIL, MainActivity.KEY_USEREMAIL);

                params.put(KEY_TOKEN, token);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }

}
