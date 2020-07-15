package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.ImageView;
import android.widget.TextView;

public class graphdisplay extends observer {
    public graphdisplay(board c){
        this.b = c;
        b.add_observer(this);
    }
    public void update(TextView view){
        return;
    }
    public void update_graphic(ImageView v){
        for(int i = 0; i < 13; ++i){
            for (int j = 0; j < 13; ++j){
                b.draw_block(i, j, v);
            }
        }
    }
}
