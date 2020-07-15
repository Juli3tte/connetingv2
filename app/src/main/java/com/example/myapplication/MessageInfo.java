package com.example.myapplication;

public class MessageInfo {
    private String username;
    private String usermessage;
    public void MessageInfo(){
    }
    public MessageInfo(String username, String usermessage){
        this.username = username;
        this.usermessage = usermessage;
    }

    public String getUsername() {
        return username;
    }

    public String getUsermessage() {
        return usermessage;
    }

    public String message_info(){
        String s = "";
        s  = s + username + "\n";
        s = s + usermessage + "\n";
        return s;
    }
}
