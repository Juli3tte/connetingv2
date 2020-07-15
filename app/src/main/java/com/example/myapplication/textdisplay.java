package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class textdisplay extends observer{
    String str;
    public textdisplay(board c){
        this.b = c;
      //  this.v = v;
        String result = "";
        for (int i = 0; i < 13; ++i){
            for (int j = 0; j < 13; ++j) {
                Integer type = b.get_block_type(i, j);
                result = result + type.toString() + " ";
            }
            result = result + "\n";
        }
        Integer roomnumber = b.get_room_number();
        Integer turn = b.whoseturn();
        result = result + "player 1: " + b.player1 + "      "  + "player 2: " + b.player2 + "   room: " + roomnumber.toString() + "\n";
        result = result + "Current Turn " + turn.toString() + "\n";
        this.str = result;
        b.add_observer(this);
    }
    public void update(TextView v){
        String result = "";
        for (int i = 0; i < 13; ++i){
            for (int j = 0; j < 13; ++j) {
                Integer type = b.get_block_type(i, j);
                result = result + type.toString() + " ";
            }
            result = result + "\n";
        }
        Integer roomnumber = b.get_room_number();
        Integer turn = b.whoseturn();
        result = result + "player 1: " + b.player1 + "      "  + "player 2: " + b.player2 + "   room: " + roomnumber.toString() + "\n";
        result = result + "Current Turn " + turn.toString() + "\n";
        this.str = result;
        v.setText(result);
    }
    public String get_string(){
        return this.str;
    }
    public void update_graphic(ImageView v){
        return;
    }
}
