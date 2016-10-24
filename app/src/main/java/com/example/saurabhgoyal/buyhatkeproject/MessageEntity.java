package com.example.saurabhgoyal.buyhatkeproject;

/**
 * Created by saurabh goyal on 10/22/2016.
 */
public class MessageEntity {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRecieveremail() {
        return recieveremail;
    }

    public void setRecieveremail(String recieveremail) {
        this.recieveremail = recieveremail;
    }

    public String getRecievername() {
        return recievername;
    }

    public void setRecievername(String recievername) {
        this.recievername = recievername;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



    private String message;
    private String useremail;
    private String username;
    private String recieveremail;
    private String recievername;
    private String token;
    private String id;



}
