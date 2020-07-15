package com.example.myapplication;

public class Roominfo {
    // The room number for this type
    private String roomnumber = "0";
    // The type for the movie
    private String type;
    // The name for the room
    private String room_name;

    public Roominfo(){

    }

    public Roominfo(String roomnumber, String type, String room_name) {
        this.roomnumber = roomnumber;
        this.type = type;
        this.room_name = room_name;
    }

    public String getRoomnumber() {
        return roomnumber;
    }

    public String getType() {
        return type;
    }

    public String getRoom_name() {
        return room_name;
    }
}

