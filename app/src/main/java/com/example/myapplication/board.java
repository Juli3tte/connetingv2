package com.example.myapplication;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;
public class board extends observerable{
    // Main Board
    block[][] coordinate;

    // Two Players(Player one goes first)
    String player1;
    String player2;

    // Room number;
    int n;

    // Player 1 Turn(1 indicate player 1, and 0 indicate player2)
    int Turn1;

    // Total Remaining number of blocks (Initialize as 48)
    int total;

    // copy operator for board
    public board copy_board(){
        board result = new board(this.player1, this.player2, this.n);
        result.Turn1 = this.Turn1;
        result.total = this.total;
        for(int i = 1; i < 13; ++i){
            for (int j = 1; j < 13; ++j){
                result.coordinate[i][j] = this.coordinate[i][j].copy_operator();
            }
        }
        return result;
    }


    // observable function
    public void add_observer(observer c){
        this.observers.add(c);
    }
    public void notify_observer(observer c, TextView v, ImageView vi){
       // c.update(v);
        c.update_graphic(vi);
    }


    public board(String player1, String player2, int n){
        this.observers = new ArrayList<observer>();
        this.player1 = player1;
        this.player2 = player2;
        this.n = n;
        this.Turn1 = 1;
        this.total = 48;
        this.coordinate = new block[13][];
        for(int i = 0; i < 13; ++i){
            this.coordinate[i] = new block[13];
            for(int j = 0; j < 13; ++j){
                this.coordinate[i][j]  = new empty_block(i, j);
            }
        }
    }

    // get method
    public String getplayer1(){
        return this.player1;
    }

    public String getPlayer2(){
        return this.player2;
    }

    public int get_block_type(int xpos, int ypos){
        return this.coordinate[xpos][ypos].get_type();
    }

    public void draw_block(int xpos, int ypos, ImageView v){
        this.coordinate[xpos][ypos].draw_graphic(xpos, ypos, v.getWidth()/26, v.getHeight()/26, v);
        return;
    }

    public int get_room_number(){
        return this.n;
    }

    public int whoseturn(){
        if(this.Turn1 == 1){
            return 1;
        }
        else {
            return 2;
        }
    }
    public void switchturn(TextView v, ImageView v2) {
        if (this.Turn1 == 1) {
            this.Turn1 = 0;
        }
        else{
            this.Turn1 = 1;
        }
        for(observer o : observers){
            notify_observer(o, v, v2);
        }
    }

    public int updateBoard(ArrayList<block> input, TextView v, ImageView v2){
        int length = input.size();
        if(length > 3){
            return 0;
        }
        else if (length < 1){
            return 0;
        }
        else{
            for(block c : input){
                if(c.validity(this) == 1) {
                    coordinate[c.getxcor()][c.getycor()] = c;
                }
                else {
                    return 0;
                }
            }
        }
        for(observer o: observers){
            notify_observer(o, v, v2);
        }
        return 1;
    }
}

