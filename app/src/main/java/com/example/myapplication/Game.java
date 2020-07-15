package com.example.myapplication;

import java.util.List;

public class Game {
    String game_type;
    List<String> listofplayers;
    public Game(){

    }
    public Game(String game_type, List<String> listofplayers){
        this.game_type = game_type;
        this.listofplayers = listofplayers;
    }

    public String getGame_type() {
        return game_type;
    }

    public List<String> getListofplayers() {
        return listofplayers;
    }
}
