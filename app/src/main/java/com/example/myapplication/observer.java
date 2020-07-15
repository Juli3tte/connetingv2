package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.ImageView;
import android.widget.TextView;

abstract class observer {
    board b;
    abstract void update(TextView v);
    abstract void update_graphic(ImageView v);
}
