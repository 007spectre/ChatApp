package com.example.saurabhgoyal.buyhatkeproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by saurabh goyal on 10/21/2016.
 */
public class ChatActivity extends ActionBarActivity {

    private EditText messageET;
    private ListView messagesContainer;
    ProgressDialog pd;
    private Button sendBtn;
    private ChatAdapter adapter;
    SQLiteHandler db;
    private ArrayList<MessageEntity> chatHistory=new ArrayList<MessageEntity>();
    public static final String KEY_MESSAGE = "message";
    private static final String REGISTER_URL = "http://www.creativecolors.in/gcm/send.php";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USEREMAIL = "useremail";

    String email;
    String token;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
           db=   SQLiteHandler.getInstance(getApplicationContext());

        email=  getIntent().getExtras().getString("email");
        chatHistory=db.fetchMessage(MainActivity.KEY_USEREMAIL, email);

        token=  getIntent().getExtras().getString("token");
        username=  getIntent().getExtras().getString("username");

        initControls();
    }

    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);

        TextView meLabel = (TextView) findViewById(R.id.meLbl);
        TextView companionLabel = (TextView) findViewById(R.id.friendLabel);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        companionLabel.setText("My Buddy");// Hard Coded
    //    getData1();
//        loadDummyHistory();
        adapter = new ChatAdapter(ChatActivity.this, chatHistory);
        if(chatHistory.size()>0) {
            displayMessage(chatHistory);
        }


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }
                submit();


                MessageEntity chatMessage = new MessageEntity();
                chatMessage.setMessage(messageText);
                chatMessage.setUseremail(MainActivity.KEY_USEREMAIL);
                chatMessage.setUsername(MainActivity.usertext);
                chatMessage.setRecieveremail(email);
                chatMessage.setRecievername(username);

                chatMessage.setToken(token);

                chatHistory.add(chatMessage);
                db.insertMessage(chatMessage);

                displayMessage(chatHistory);
            }
        });
    }


    public void submit(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        messageET.setText("");


                        Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();
//                        Intent intent=new Intent(ChatActivity.this,ListDisplay.class);
//                        startActivity(intent);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChatActivity.this,"Connection Error",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put(KEY_MESSAGE, messageET.getText().toString());
                Log.v("email", "emailid" + email);

                params.put(KEY_EMAIL, email);
                params.put(KEY_USEREMAIL, MainActivity.KEY_USEREMAIL);

                params.put(KEY_TOKEN, token);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }




//    private void getData1() {
//        pd = new ProgressDialog(ChatActivity.this) {
//            @Override
//            public void onBackPressed() {
//                pd.dismiss();
//            }};
//        pd.setMessage("Loading");
//        pd.setCancelable(false);
//        pd.show();
//
//
//        //Showing a progress dialog
//        //  final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, false);
//
//        //Creating a json array request
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(MainActivity.DATA_URL1,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        //Dismissing progress dialog
//                        pd.dismiss();
//                        Log.v("response","response"+response);
//                        //calling method to parse json array
//                        parseData(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//
//        //Creating request queue
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        //Adding request to the queue
//        requestQueue.add(jsonArrayRequest);
//    }
//
//
//
//    private void parseData(JSONArray array){
//        chatHistory = new ArrayList<ChatMessage>();
//
//        for(int i = 0; i<array.length(); i++) {
//            ChatMessage jdetail = new ChatMessage();
//
//            JSONObject json = null;
//
//            try {
//                json = array.getJSONObject(i);
////                que=json.getString("question");
////                anss1=json.getString("ans1");
////                anss2=json.getString("ans2");
////                anss3=json.getString("ans3");
////                anss4=json.getString("ans4");
//                // superHero.setImageUrl(json.getString(Config.TAG_IMAGE_URL));
////                jdetail.setEmail(json.getString(KEY_EMAIL));
////                jdetail.setToken(json.getString(KEY_TOKEN));
////                jdetail.setUsername(json.getString(KEY_USERNAME));
//
//
//
//
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            chatHistory.add(jdetail);
//
//
//        }
//
//        adapter = new ChatAdapter(ChatActivity.this, new ArrayList<ChatMessage>());
//        messagesContainer.setAdapter(adapter);
//
//        for(int i=0; i<chatHistory.size(); i++) {
//            ChatMessage message = chatHistory.get(i);
//            displayMessage(message);
//        }
//
//        //Finally initializing our adapter
//        //  adapter = new ListingJobAdapter(listSuperHeroes, this);
//
//        //Adding adapter to recyclerview
//        //recyclerView.setAdapter(adapter);
//
//
//    }






    public void displayMessage(ArrayList<MessageEntity> message) {

        this.chatHistory=message;

        messagesContainer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

//    private void loadDummyHistory(){
//
//        chatHistory = new ArrayList<ChatMessage>();
//
//        ChatMessage msg = new ChatMessage();
//        msg.setId(1);
//        msg.setMe(false);
//        msg.setMessage("Hi");
////        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
//        chatHistory.add(msg);
//        ChatMessage msg1 = new ChatMessage();
//        msg1.setId(2);
//        msg1.setMe(false);
//        msg1.setMessage("How r u doing???");
////        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
//        chatHistory.add(msg1);
//
//        adapter = new ChatAdapter(ChatActivity.this, new ArrayList<ChatMessage>());
//        messagesContainer.setAdapter(adapter);
//
//        for(int i=0; i<chatHistory.size(); i++) {
//            ChatMessage message = chatHistory.get(i);
//            displayMessage(message);
//        }
//    }
}
