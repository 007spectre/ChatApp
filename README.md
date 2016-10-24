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
            
            
            Services used:-sjsjjs
            
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
