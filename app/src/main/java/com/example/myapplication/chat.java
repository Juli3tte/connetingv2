package com.example.myapplication;

import android.os.Message;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public class chat {
    List<String> chatmessages;
    List<String>  chatnames;
    public chat(){
    }

    public chat(List<String> m){
        this.chatmessages = m;
    }

    public List<String> getChatmessages() {
        return chatmessages;
    }

    public List<String> getChatnames() {
        return chatnames;
    }
}

