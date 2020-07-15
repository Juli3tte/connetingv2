package com.example.myapplication;

import java.util.List;
import java.util.Map;

public class FIAR_board {
    Map<String, String> board;
    Map<String, String> piececolor;
    List<String> players;
    String playernumber;
    public FIAR_board(){
        // public no arg constructor
    }
    public FIAR_board(List<String> players, String playernumber, Map<String, String> piececolor, Map<String, String> board){
        this.board = board;
        this.piececolor = piececolor;
         this.playernumber = playernumber;
        this.players = players;
    }

    public List<String> getPlayers() {
        return players;
    }


    public String getPlayernumber() {
        return playernumber;
    }



    public Map<String, String> getBoard() {
        return board;
    }

   public Map<String, String> getPiececolor() {
        return piececolor;
    }

}
