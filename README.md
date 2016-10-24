# ChatAppBuyHatke
Libraries used
dependencies used:-
   1. 'com.android.support:appcompat-v7:23.4.0'
   2. 'com.android.support:design:23.4.0' //for the material design
   3. files('libs/volley.jar')  // for the calling rest api for gcm and storing its token and Sign up services
   4.  "com.google.android.gms:play-services:8.4.0" // for GCM
   5. 'com.android.support:cardview-v7:23.4.0'  
   6. 'com.google.android.gms:play-services-drive:8.4.0' //for google drive api
   
   
   for GCM class used:_
        1.GCMPushReceiverService.java
        2. GCMRegistrationIntentService.java
        3.GCMTokenRefreshListenerService.java
        So just change the gcm_falut SenderId in the string.xml according to project id in the console
            <string name="gcm_defaultSenderId">319322556887</string>
            
            
            Services used:-
            
            1. GCMRegId.php
            2. gcm.php
            3. send.php
            so just edit the api_key value in the send.php that is generated from Google Api console
           -Add the google-services.json generated from the console
           
           
      for Google Drive Api for storing and retreiving back the messages :-      
      1.ApiClientAsyncTask.java
      2. BaseDemoActivity.java
      3.CreateFileActivity.java
      4.RetrieveContentsActivity.java
      dependency used :- 'com.google.android.gms:play-services-drive:8.4.0'
      you need to generate :-
      1 Sha1  
      2. oath2 Authentication in the console 
      
      main pin points:-
                  DriveId driveId = result.getDriveFile().getDriveId();
                  driveId.getResourceId() ; //it gives null after waiting too much time 
                  what I have done After reading GDAA
                  
                  
                    Drive.DriveApi.getFile(getGoogleApiClient(), driveId).addChangeListener(getGoogleApiClient(), mChgeLstnr);
    final private ChangeListener mChgeLstnr = new ChangeListener() {
        @Override
        public void onChange(ChangeEvent event) {
            BaseDemoActivity.EXISTING_FILE_ID=""+event.getDriveId().getResourceId(); // this trigger it and we have the resource id and we set it to file id so it will retrive content from there 



        }
    };
    for stroring Json format used:_
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

                        writer.write(""+jsonArray);
                        writer.close();
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
    
    
        <a href="https://github.com/007spectre/ChatAppBuyHatke/blob/master/Sample/New%20file.txt">Sample of Json uploaded to google drive</a>
	
	
	

    
    
    for saving the data or to reduce app size  I have used TinyPng:-
    <a href="https://tinypng.com/">Tiny Png for App Size</a>

   
    
    
    for Storing data Locally:-
      SQLiteHandler.java 
      queries used:
      
      1.For retrieving user messages 
		Cursor cursor = db
				.rawQuery(
						"select * from message where useremail='" + email + "' OR recieveremail='" + email + "'", null);
                 // to retrieve user  messages from the user table according to email whether it can be either useremail or recieveremail
                 
        2. for Search:_
        		Cursor cursor = db
				.rawQuery(
						"select * from message where message like '%"+message+"%'", null);
                  // message from message table according to user type in text by Like query
		  
	Material Design concept:_
	1. Sliding Tab Layout
	2. Recyler View
	3. Card View 
	
	Validation check:-
	1. Email type validation
	2. Blank test validation
	
	Other Concepts:
	1. RoundImageView
	2. Webview
	3. Connection Dectector for checking the internet
	4. Fragments


##Screenshots
<table>
  <tr>
    <td><img src="https://github.com/007spectre/ChatAppBuyHatke/blob/master/ScreenShots/Screenshot_2016-10-24-01-05-06.png"></td>
    <td><img src="https://github.com/007spectre/ChatAppBuyHatke/blob/master/ScreenShots/Screenshot_2016-10-24-01-06-12.png"></td>
    <td><img src="https://github.com/007spectre/ChatAppBuyHatke/blob/master/ScreenShots/Screenshot_2016-10-24-01-06-44.png"></td>
  </tr>
  </table>
  <table>
  <tr>
    <td><img src="https://github.com/007spectre/ChatAppBuyHatke/blob/master/ScreenShots/Screenshot_2016-10-24-01-07-29.png"></td>
    <td><img src="https://github.com/007spectre/ChatAppBuyHatke/blob/master/ScreenShots/Screenshot_2016-10-24-01-07-36.png"></td>
    <td><img src="https://github.com/007spectre/ChatAppBuyHatke/blob/master/ScreenShots/Screenshot_2016-10-24-01-08-01.png"></td>

  </tr>
  </table>
  <table>
  <tr>
      <td><img src="https://github.com/007spectre/ChatAppBuyHatke/blob/master/ScreenShots/Screenshot_2016-10-24-01-08-39.png"></td>
      <td><img src="https://github.com/007spectre/ChatAppBuyHatke/blob/master/ScreenShots/Screenshot_2016-10-24-07-23-10.png"></td>
      <td><img src="https://github.com/007spectre/ChatAppBuyHatke/blob/master/ScreenShots/Screenshot_2016-10-24-09-25-47.png"></td>

    </tr>
</table>
