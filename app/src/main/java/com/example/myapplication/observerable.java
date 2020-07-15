package com.example.myapplication;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.*;
public abstract class observerable {
    ArrayList<observer> observers;
    abstract void add_observer(observer o);
    abstract void notify_observer(observer o, TextView v, ImageView v2);
}
