package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Record extends AppCompatActivity {
    Button savebutton;
    Button loadbutton;
    TextView display;
    EditText Input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        savebutton = findViewById(R.id.save);
        loadbutton = findViewById(R.id.load);
        display = findViewById(R.id.display);
        Input = findViewById(R.id.input);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savefile();
            }
        });
        loadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadfile();
            }
        });
    }

    public void savefile() {
        try {
            String input_string = Input.getText().toString();
            FileOutputStream fileOutputStream = openFileOutput("Username.txt", MODE_PRIVATE);
            fileOutputStream.write(input_string.getBytes());
            fileOutputStream.close();
            Toast.makeText(getApplicationContext(), "Text Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadfile() {
        try{
            FileInputStream fileInputStream = openFileInput("Username.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String lines;
            while((lines = bufferedReader.readLine()) != null){
                stringBuffer.append(lines + "\n");
            }
            display.setText(stringBuffer.toString());
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}