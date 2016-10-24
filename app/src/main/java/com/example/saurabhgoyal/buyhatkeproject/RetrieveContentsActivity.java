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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi.DriveContentsResult;
import com.google.android.gms.drive.DriveApi.DriveIdResult;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Activity to illustrate how to retrieve and read file contents.
 */
public class RetrieveContentsActivity extends BaseDemoActivity {

    private static final String TAG = "RetrieveContentsActivity";
    SQLiteHandler db;
    ProgressDialog pd;


    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);
        pd = new ProgressDialog(RetrieveContentsActivity.this);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();
        db=SQLiteHandler.getInstance(getApplicationContext());
        Drive.DriveApi.fetchDriveId(getGoogleApiClient(), EXISTING_FILE_ID)
                .setResultCallback(idCallback);
    }

    final private ResultCallback<DriveIdResult> idCallback = new ResultCallback<DriveIdResult>() {
        @Override
        public void onResult(DriveIdResult result) {
            new RetrieveDriveFileContentsAsyncTask(
                    RetrieveContentsActivity.this).execute(result.getDriveId());
        }
    };

    final private class RetrieveDriveFileContentsAsyncTask
            extends ApiClientAsyncTask<DriveId, Boolean, String> {

        public RetrieveDriveFileContentsAsyncTask(Context context) {
            super(context);
        }

        @Override
        protected String doInBackgroundConnected(DriveId... params) {
            String contents = null;
            DriveFile file = params[0].asDriveFile();
            DriveContentsResult driveContentsResult =
                    file.open(getGoogleApiClient(), DriveFile.MODE_READ_ONLY, null).await();
            if (!driveContentsResult.getStatus().isSuccess()) {
                return null;
            }
            DriveContents driveContents = driveContentsResult.getDriveContents();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(driveContents.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                contents = builder.toString();
            } catch (IOException e) {
                Log.e(TAG, "IOException while reading from the stream", e);
            }

            driveContents.discard(getGoogleApiClient());
            return contents;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == null) {
                showMessage("Error while reading from the file");
                pd.dismiss();
                Intent intent=new Intent(RetrieveContentsActivity.this,HomeActivity.class);
                startActivity(intent);
                return;
            }
//            showMessage("File contents: " + result);
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(result);


            for(int i = 0; i<jsonArray.length(); i++) {
                MessageEntity jdetail = new MessageEntity();

                JSONObject json = null;

                try {
                    json = jsonArray.getJSONObject(i);


                    jdetail.setMessage(json.getString("message"));
                    jdetail.setUseremail(json.getString("useremail"));
                    jdetail.setUsername(json.getString("username"));
                    jdetail.setRecieveremail(json.getString("recieveremail"));
                    jdetail.setRecievername(json.getString("recievername"));
                    jdetail.setToken(json.getString("token"));
                    jdetail.setId(json.getString("id"));








                } catch (JSONException e) {
                    e.printStackTrace();
                }

                db.insertMessage(jdetail);


            }
            } catch (JSONException e) {
                e.printStackTrace();
            }

pd.dismiss();
            Intent intent=new Intent(RetrieveContentsActivity.this,HomeActivity.class);
            startActivity(intent);







        }
    }
}
