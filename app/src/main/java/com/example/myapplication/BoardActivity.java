package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity {
    TextView display;
    Button player1done;
    Button player2done, enter;
    Button left, right, up, down;
    String player1 = "A";
    String player2 = "B";
    ImageView v1;
    int n = 4;
    int xpos = 0;
    int ypos = 0;
    board mainboard = new board(player1, player2, n);
    textdisplay textdis = new textdisplay(mainboard);
    graphdisplay graphdis = new graphdisplay(mainboard);
    Integer type = 0;
    //  Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        player1done = findViewById(R.id.player1done);
        player2done = findViewById(R.id.player2done);
        enter = findViewById(R.id.enter);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);

        v1 = (ImageView) findViewById(R.id.grid);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (xpos == 0){
                    Toast.makeText(BoardActivity.this, "Cannot go left", Toast.LENGTH_SHORT).show();
                }
                else{
                    erase_line(xpos, ypos);
                    xpos = xpos - 1;
                    draw_line(xpos, ypos);
                }
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (xpos == 12){
                    Toast.makeText(BoardActivity.this, "Cannot go right", Toast.LENGTH_SHORT).show();
                }
                else{
                    erase_line(xpos, ypos);
                    xpos = xpos + 1;
                    draw_line(xpos, ypos);
                }
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ypos == 0){
                    Toast.makeText(BoardActivity.this, "Cannot go up", Toast.LENGTH_SHORT).show();
                }
                else{
                    erase_line(xpos, ypos);
                    ypos = ypos - 1;
                    draw_line(xpos, ypos);
                }
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ypos == 12){
                    Toast.makeText(BoardActivity.this, "Cannot go down", Toast.LENGTH_SHORT).show();
                }
                else{
                    erase_line(xpos, ypos);
                    ypos = ypos + 1;
                    draw_line(xpos, ypos);
                }
            }
        });

        //  Toast.makeText(this, "here2", Toast.LENGTH_SHORT).show();
        //  Bitmap bitmap = Bitmap.createBitmap(v1.getWidth(), v1.getHeight(), Bitmap.Config.RGB_565);
        //Toast.makeText(this, "here1", Toast.LENGTH_SHORT).show();
        //  canvas = new Canvas(bitmap);
        // v.draw(canvas);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Integer row_num = xpos + 1;
                Integer column_num = ypos + 1;

                if (row_num.compareTo(0) <= 0 || row_num.compareTo(13) > 0 || column_num.compareTo(0) <= 0 || column_num.compareTo(13) > 0) {
                    Toast.makeText(BoardActivity.this, "Invalid Trial", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Integer type_num = Integer.parseInt(type.getText().toString());
                block temp;
                if (type.compareTo(1) == 0) {
                    temp = new straight_block_horizontal(row_num - 1, column_num - 1);
                } else if (type.compareTo(2) == 0) {
                    temp = new straight_block_vertical(row_num - 1, column_num - 1);
                } else if (type.compareTo(3) == 0) {
                    temp = new upper_left_block(row_num - 1, column_num - 1);
                } else if (type.compareTo(4) == 0) {
                    temp = new upper_right_block(row_num - 1, column_num - 1);
                } else if (type.compareTo(5) == 0) {
                    temp = new lower_left_block(row_num - 1, column_num - 1);
                } else if (type.compareTo(6) == 0) {
                    temp = new lower_right_block(row_num - 1, column_num - 1);
                } else {
                    Toast.makeText(BoardActivity.this, "Invalid Type", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Toast.makeText(BoardActivity.this, "Successfully Constructed", Toast.LENGTH_SHORT).show();
                ArrayList<block> temp2 = new ArrayList<block>();
                temp2.add(temp);
                int result = updateboard(temp2);
                if (result != 1) {
                    Toast.makeText(BoardActivity.this, "It is not valid", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    public void draw_line(int xpos, int ypos) {
        Bitmap bitmap = Bitmap.createBitmap(v1.getWidth(), v1.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        v1.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        canvas.drawLine(v1.getWidth()/52 * (4 * xpos + 1), v1.getHeight() / 26 * (2 * ypos + 1), v1.getWidth()/52 * (4 * xpos + 3), v1.getHeight() / 26 * (2 * ypos + 1), paint);
        v1.setImageBitmap(bitmap);
    }
       public void  erase_line(int xpos, int ypos){
        Bitmap bitmap = Bitmap.createBitmap(v1.getWidth(), v1.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        v1.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color. WHITE);
        paint.setStrokeWidth(10);
        canvas.drawLine(v1.getWidth()/52 * (4 * xpos + 1), v1.getHeight() / 26 * (2 * ypos + 1), v1.getWidth()/52 * (4 * xpos + 3), v1.getHeight() / 26 * (2 * ypos + 1), paint);
        v1.setImageBitmap(bitmap);
    }


    public void click1(View view) {
        type = 1;
    }

    public void click2(View view) {
        type = 2;
    }
    public void click3(View view) {
        type = 3;
    }
    public void click4(View view) {
        type = 4;
    }
    public void click5(View view) {
        type = 5;
    }
    public void click6(View view) {
        type = 6;
    }

    public int updateboard(ArrayList<block> n){
        return mainboard.updateBoard(n, display, v1);
    }

}