package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.*;
import android.os.Build;
import android.util.AttributeSet;
import android.util.*;
import android.view.MotionEvent;
import android.view.View;

import java.lang.*;

import androidx.annotation.RequiresApi;

public class Graphics extends View {
    private int numColumns, numRows;
    private int cellWidth, cellHeight;
    private Paint blackPaint = new Paint();
    private Paint greyPaint = new Paint();
    private Paint yellowPaint = new Paint();
    //use string list to convert to these two.
    private boolean[][] cellChecked;
    private int[][] pieceColor;
    private int checkflag = 1;
    private int wightboard;


    public Graphics(Context context) {
        this(context, null);
    }

    public Graphics(Context context, AttributeSet attrs) {
        super(context, attrs);
        blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        greyPaint.setColor(Color.GRAY);
        yellowPaint.setColor(Color.RED);
        yellowPaint.setStyle(Paint.Style.STROKE);
        yellowPaint.setStrokeWidth(5);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
        calculateDimensions();
    }

    public int getNumColumns() {
        return numColumns;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setNumRows(int numRows) {
        this.numRows = numRows;
        calculateDimensions();
    }

    public int getNumRows() {
        return numRows;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculateDimensions();
    }

    @Override public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        wightboard = height;
        //int size = width > height ? height : width;
        setMeasuredDimension(width, height);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void calculateDimensions() {
        if (numColumns < 1 || numRows < 1) {
            return;
        }

        cellWidth = getHeight() / numColumns;
        cellHeight = getHeight() / numRows;

        cellChecked = GameMainActivity.getCheckBoard();
        pieceColor = GameMainActivity.getPiececolor();

        invalidate();
    }

    private String equalColor(int i, int j){

        int color = pieceColor[i][j];
        int cleft = 0;
        int ctop = 0;
        int cleftroll = 0;
        int crightroll = 0;


        //left not going to happen
        if(color == 1 || color == 2) {
            for (int t = 1; t < 5; t++) {
                if (i - t >= 0 && pieceColor[i - t][j] == color) {
                    //left
                    cleft++;
                    Log.d("cleft ","plus 1");
                }
                if (j - t >= 0 && pieceColor[i][j - t] == color) {
                    //top
                    ctop++;
                }
                if (j - t >= 0 && i - t >= 0 && pieceColor[i - t][j - t] == color) {
                    //leftroll
                    cleftroll++;
                }
                if (j - t >= 0 && i + t < numRows && pieceColor[i + t][j - t] == color) {
                    //rightroll
                    crightroll++;
                }
            }
        }
        if(cleft > 0){
            Log.d("cleft is "," "+cleft);
        }

        String realcolor;

        if(color == 1){
            realcolor = "BLACK";
        } else {
            realcolor = "GREY";
        }


        if(cleft >= 4){
            return "cleft";
        }

        if(ctop >= 4){
            return "ctop";
        }

        if(crightroll >= 4){
            return "crightroll";
        }

        if(cleftroll >= 4){
            return "cleftroll";
        }
        return "nowin";
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#e8ddc2"));

        if (numColumns == 0 || numRows == 0) {
            return;
        }

        int width = wightboard;
        int height = getHeight();

        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                if (cellChecked[i][j]) {

                    int a = pieceColor[i][j];

                    if(a == 1) {
                        canvas.drawOval(i * cellWidth, j * cellHeight,
                                (i + 1) * cellWidth, (j + 1) * cellHeight,
                                blackPaint);
                        //Log.d("checkflag",Integer.toString(checkflag));
                    } else{
                        canvas.drawOval(i * cellWidth, j * cellHeight,
                                (i + 1) * cellWidth, (j + 1) * cellHeight,
                                greyPaint);
                    }
                }
            }
        }

        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                String result = equalColor(i, j);
                if (!result.equals("nowin")) {
                    if (result.equals("crightroll")) {
                        Log.d("have winner", "yes " + pieceColor[i][j]);
                        for (int t = 0; t < 5; t++) {
                            canvas.drawOval((i + t) * cellWidth, (j - t) * cellHeight,
                                    (i + t + 1) * cellWidth, (j - t + 1) * cellHeight,
                                    yellowPaint);
                        }
                    } else if (result.equals("cleft")) {
                        Log.d("have winner", "yes " + pieceColor[i][j]);
                        for (int t = 0; t < 5; t++) {
                            canvas.drawOval((i-t) * cellWidth, j * cellHeight,
                                    (i-t + 1) * cellWidth, (j + 1) * cellHeight,
                                    yellowPaint);
                        }
                    } else if (result.equals("ctop")) {
                        Log.d("have winner", "yes " + pieceColor[i][j]);
                        for (int t = 0; t < 5; t++) {
                            canvas.drawOval(i * cellWidth, (j - t) * cellHeight,
                                    (i + 1) * cellWidth, (j - t + 1) * cellHeight,
                                    yellowPaint);
                        }
                    } else if (result.equals("cleftroll")) {
                        Log.d("have winner", "yes " + pieceColor[i][j]);
                        for (int t = 0; t < 5; t++) {
                            canvas.drawOval((i - t) * cellWidth, (j - t) * cellHeight,
                                    (i - t + 1) * cellWidth, (j - t + 1) * cellHeight,
                                    yellowPaint);
                        }
                    } else {
                        //nothing need to be change
                    }
                    GameMainActivity.reinit();
                }
            }
        }



        for (int i = 1; i <= numColumns; i++) {
            float k = (float)(i - 0.5);
            canvas.drawLine(k * cellWidth, 0, k * cellWidth, height, blackPaint);
        }

        for (int i = 1; i <= numRows; i++) {
            float k = (float)(i - 0.5);
            canvas.drawLine(0, k * cellHeight, width, k * cellHeight, blackPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int column = (int)(event.getX() / cellWidth);
            int row = (int)(event.getY() / cellHeight);

            Boolean temp = true;
            int temp2 = 2;

            //we will let player1 always play first
            int user = GameMainActivity.current_player();

            if(checkflag == 1 && user == 1) {
                pieceColor[column][row] = 1;
                checkflag = 2;
                Log.d("get", "1");
                GameMainActivity.switch_turn();
                GameMainActivity.update_entry_board(column,row,"true");
                GameMainActivity.update_entry_piece(column,row,"1");
            }else{
                pieceColor[column][row] = 2;
                checkflag = 1;
                Log.d("get", "2");
                GameMainActivity.switch_turn();
                GameMainActivity.update_entry_board(column,row,"true");
                GameMainActivity.update_entry_piece(column,row,"2");
            }

            Log.d("column", Integer.toString(column));
            Log.d("row",Integer.toString(row));


            //this part could be add if someone want to regret* but maybe later.
            //cellChecked[column][row] = !cellChecked[column][row];
            //invalidate();
        }

        return true;
    }
}
