package com.example.myapplication;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;

import java.util.*;


public abstract class block {
    // where this block sit on
    int xcor;
    int ycor;
    int type;

    // observer
    ArrayList<observer> observers;

    // get functions
    public int getxcor(){
        return this.xcor;
    }
    public int getycor(){
        return this.ycor;
    }
    // functions
    abstract int get_type();
    abstract block copy_operator();
    abstract int validity(board currentboard);
    abstract void draw_graphic(int xpos, int ypos,  float width, float height, ImageView v);
}

 class empty_block extends block{
    public empty_block(int xcor, int ycor){
        this.xcor = xcor;
        this.ycor = ycor;
        this.type = 0;
    }
    public int get_type(){
        return 0;
   }
   public block copy_operator(){
        block result = new empty_block(this.xcor, this.ycor);
        result.type = 0;
        return result;
   }
   public int validity(board currentboard){
        return 1;
   }
   public void draw_graphic(int xpos, int ypos, float width, float height, ImageView v){
        return;
   }
}

 class straight_block_horizontal extends block {
    public straight_block_horizontal(int xcor, int ycor) {
        this.xcor = xcor;
        this.ycor = ycor;
        this.type = 1;
    }
    public void draw_graphic(int xpos, int ypos, float width, float height, ImageView v){
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        canvas.drawLine(width * (2 * xpos), height * (2 * ypos + 1) , width * (2 * xpos + 2), height * (2 * ypos + 1), paint);
        v.setImageBitmap(bitmap);
    }

    public block copy_operator() {
        block result = new empty_block(this.xcor, this.ycor);
        result.type = 1;
        return result;
    }

    public int get_type(){
        return 1;
    }
    public int validity(board currentboard){
        // Check if touch the wall
        if (ycor == 0 || ycor == 12){
            return 0;
        }
        else {
            if (currentboard.coordinate[this.xcor][this.ycor + 1].get_type() != 0) {
                if ((currentboard.coordinate[this.xcor][this.ycor + 1].get_type() == 4) ||
                        (currentboard.coordinate[this.xcor][this.ycor + 1].get_type() == 6) ||
                        (currentboard.coordinate[this.xcor][this.ycor + 1].get_type() == 2)) {
                    return 0;
                }
            }
            if (currentboard.coordinate[this.xcor][this.ycor - 1].get_type() != 0){
                if((currentboard.coordinate[this.xcor][this.ycor - 1].get_type() == 3) ||
                        (currentboard.coordinate[this.xcor][this.ycor - 1].get_type() == 5) ||
                        (currentboard.coordinate[this.xcor][this.ycor - 1].get_type() == 2)){
                    return 0;
                }
            }
            if (this.xcor != 0) {
                if (currentboard.coordinate[this.xcor - 1][this.ycor].get_type() != 0) {
                    if ((currentboard.coordinate[this.xcor - 1][this.ycor].get_type() == 5) ||
                            (currentboard.coordinate[this.xcor - 1][this.ycor].get_type() == 6) ||
                            (currentboard.coordinate[this.xcor - 1][this.ycor].get_type() == 2)) {
                        return 0;
                    }
                }
            }
            if (this.ycor != 12){
                if (currentboard.coordinate[this.xcor + 1][this.ycor].get_type() != 0){
                    if((currentboard.coordinate[this.xcor + 1][this.ycor].get_type() == 3) ||
                        (currentboard.coordinate[this.xcor + 1][this.ycor].get_type() == 4) ||
                        (currentboard.coordinate[this.xcor + 1][this.ycor].get_type() == 2)){
                        return 0;
                    }
                }
            }
            return 1;
        }
    }
}

class straight_block_vertical extends block{
    public straight_block_vertical(int xcor, int ycor) {
        this.xcor = xcor;
        this.ycor = ycor;
        this.type = 2;
    }
    public void draw_graphic(int xpos, int ypos, float width, float height, ImageView v){
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        canvas.drawLine(width * (2 * xpos  + 1), height * (2 * ypos ) , width * (2 * xpos + 1), height * (2 * ypos + 2), paint);
        v.setImageBitmap(bitmap);
    }

    public block copy_operator() {
        block result = new empty_block(this.xcor, this.ycor);
        result.type = 1;
        return result;
    }

    public int get_type(){
        return 2;
    }
    public int validity(board board){ return 1; }
}

class upper_left_block extends block {
    public upper_left_block(int xcor, int ycor) {
        this.xcor = xcor;
        this.ycor = ycor;
        this.type = 3;
    }
    public void draw_graphic(int xpos, int ypos, float width, float height, ImageView v){
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        canvas.drawLine(width * (2 * xpos), height * (2 * ypos + 1) , width * (2 * xpos + 1), height * (2 * ypos + 1 ), paint);
        canvas.drawLine(width * (2 * xpos + 1),  height * (2 * ypos), width * (2 * xpos + 1), height * (2 * ypos + 1), paint);
        v.setImageBitmap(bitmap);
    }

    public block copy_operator() {
        block result = new empty_block(this.xcor, this.ycor);
        result.type = 1;
        return result;
    }

    public int get_type(){
        return 3;
    }
    public int validity(board board){
        return 1;
    }
}


class upper_right_block extends block{
    public upper_right_block(int xcor, int ycor) {
        this.xcor = xcor;
        this.ycor = ycor;
        this.type = 4;
    }
    public void draw_graphic(int xpos, int ypos, float width, float height, ImageView v){
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        canvas.drawLine(width * (2 * xpos + 1), height * (2 * ypos + 1) , width * (2 * xpos + 2), height * (2 * ypos + 1 ), paint);
        canvas.drawLine(width * (2 * xpos + 1),  height * (2 * ypos), width * (2 * xpos + 1), height * (2 * ypos + 1), paint);
        v.setImageBitmap(bitmap);
    }

    public block copy_operator() {
        block result = new empty_block(this.xcor, this.ycor);
        result.type = 1;
        return result;
    }
    public int get_type(){
        return 4;
    }
    public int validity(board board){
        return 1;
    }
}

class lower_left_block extends block{
    public lower_left_block(int xcor, int ycor) {
        this.xcor = xcor;
        this.ycor = ycor;
        this.type = 5;
    }
    public void draw_graphic(int xpos, int ypos, float width, float height, ImageView v){
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        canvas.drawLine(width * (2 * xpos), height * (2 * ypos + 1) , width * (2 * xpos + 1), height * (2 * ypos + 1 ), paint);
        canvas.drawLine(width * (2 * xpos + 1),  height * (2 * ypos + 1), width * (2 * xpos + 1), height * (2 * ypos + 2), paint);
        v.setImageBitmap(bitmap);
    }

    public block copy_operator() {
        block result = new empty_block(this.xcor, this.ycor);
        result.type = 1;
        return result;
    }

    public int get_type(){
        return 5;
    }

    public int validity(board board){
        return 1;
    }
}

class lower_right_block extends block{
    public lower_right_block(int xcor, int ycor) {
        this.xcor = xcor;
        this.ycor = ycor;
        this.type = 6;
    }
    public void draw_graphic(int xpos, int ypos, float width, float height, ImageView v){
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        canvas.drawLine(width * (2 * xpos + 1), height * (2 * ypos + 1) , width * (2 * xpos + 2), height * (2 * ypos + 1 ), paint);
        canvas.drawLine(width * (2 * xpos + 1),  height * (2 * ypos + 1), width * (2 * xpos + 1), height * (2 * ypos + 2), paint);
        v.setImageBitmap(bitmap);
    }
    public block copy_operator() {
        block result = new empty_block(this.xcor, this.ycor);
        result.type = 1;
        return result;
    }

    public int get_type(){
        return 6;
    }
    public int validity(board board){
        return 1;
    }
}



