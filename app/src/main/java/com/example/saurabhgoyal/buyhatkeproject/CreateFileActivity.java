/**
 * Copyright 2013 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.saurabhgoyal.buyhatkeproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveApi.DriveContentsResult;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder.DriveFileResult;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ChangeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * An activity to illustrate how to create a file.
 */
public class CreateFileActivity extends BaseDemoActivity {

    private static final String TAG = "CreateFileActivity";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_FILEID = "fileid";


    private static final String STORE_URL = "http://www.creativecolors.in/gcm/idselect.php";

    SQLiteHandler db;
    ArrayList<MessageEntity>userMessageList=new ArrayList<>();
    ProgressDialog pd;


    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);
        // create new contents resource
        db=SQLiteHandler.getInstance(getApplicationContext());
        pd = new ProgressDialog(CreateFileActivity.this);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();


        Drive.DriveApi.newDriveContents(getGoogleApiClient())
                .setResultCallback(driveContentsCallback);
    }

    final private ResultCallback<DriveApi.DriveContentsResult> driveContentsCallback = new
            ResultCallback<DriveApi.DriveContentsResult>() {
        @Override
        public void onResult(DriveApi.DriveContentsResult result) {
            if (!result.getStatus().isSuccess()) {
                showMessage("Error while trying to create new file contents");
                return;
            }
            final DriveContents driveContents = result.getDriveContents();

            // Perform I/O off the UI thread.
            new Thread() {
                @Override
                public void run() {
                    // write content to DriveContents
                    OutputStream outputStream = driveContents.getOutputStream();
                    Writer writer = new OutputStreamWriter(outputStream);
                    String text="";
                    try {


                        userMessageList   =db.fetchUserMessage(MainActivity.KEY_USEREMAIL);
                        JSONArray jsonArray=new JSONArray();




                        for(int i=0;i<userMessageList.size();i++){
                            MessageEntity messageEntity=userMessageList.get(i);
                            JSONObject messageObject = new JSONObject();
                            messageObject.put("message", messageEntity.getMessage());
                            messageObject.put("useremail", messageEntity.getUseremail());
                            messageObject.put("username",  messageEntity.getUsername());
                            messageObject.put("recieveremail", messageEntity.getRecieveremail());
                            messageObject.put("recievername",messageEntity.getRecievername() );
                            messageObject.put("token",messageEntity.getToken());
                            messageObject.put("id", messageEntity.getId());
                            jsonArray.put(messageObject);



                        }

                        Log.v("resId","text"+jsonArray);
                        writer.write(""+jsonArray);
                        writer.close();
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                            .setTitle("New file")
                            .setMimeType("text/plain")
                            .setStarred(true).build();

                    // create a file on root folder
                    Drive.DriveApi.getRootFolder(getGoogleApiClient())
                            .createFile(getGoogleApiClient(), changeSet, driveContents)
                            .setResultCallback(fileCallback);
                }
            }.start();
        }
    };

    final private ResultCallback<DriveFileResult> fileCallback = new
            ResultCallback<DriveFileResult>() {
        @Override
        public void onResult(DriveFileResult result) {
            pd.dismiss();
            if (!result.getStatus().isSuccess()) {
                showMessage("Error while trying to create the file");
                return;
            }
            showMessage("Created a file with content: " + result.getDriveFile().getDriveId());
//            DriveFile file = Drive.DriveApi.getFile(getGoogleApiClient(), result.getDriveFile().getDriveId());
            DriveId driveId = result.getDriveFile().getDriveId();


            Drive.DriveApi.getFile(getGoogleApiClient(), driveId).addChangeListener(getGoogleApiClient(), mChgeLstnr);

//            file.addChangeSubscription(getGoogleApiClient());
      }
    };
    final private ChangeListener mChgeLstnr = new ChangeListener() {
        @Override
        public void onChange(ChangeEvent event) {
            Log.d(TAG, "event: " + event + " resId: " + event.getDriveId().getResourceId());
            BaseDemoActivity.EXISTING_FILE_ID=""+event.getDriveId().getResourceId();
            storefileid();



        }
    };
    public void storefileid(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, STORE_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.v("success", "" + response);

                        Toast.makeText(getApplicationContext(), "File Id Store Successfully", Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(CreateFileActivity.this,HomeActivity.class);
                        startActivity(intent);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateFileActivity.this,"Upload File Error",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_FILEID, BaseDemoActivity.EXISTING_FILE_ID);

                params.put(KEY_EMAIL, MainActivity.KEY_USEREMAIL);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }

}
