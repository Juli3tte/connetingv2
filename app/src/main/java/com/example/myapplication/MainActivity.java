package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.webkit.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT = "EMAIL";
    public static final String USER_ID = "userid";
    EditText useremail, password;
    Button loginbtn;
    Button registerbtn;
    Button startgame;
    TextView displaybtn;
    Button recordbtn;
    TextView title;
    String s;
    String userid_string;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userinfocollection = db.collection("sampleData");
    private DocumentReference document = db.document("sampleData/insp");

    // private DocumentReference userdocref = FirebaseFirestore.getInstance().document("sampleData/inspiration");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.username);
        readname();
        displaybtn = findViewById(R.id.display_b);
        loginbtn = findViewById(R.id.login);
        registerbtn = findViewById(R.id.register);
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToRegister();
            }
        });
        recordbtn = findViewById(R.id.record);
        recordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firsttime();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
        startgame = findViewById(R.id.test);
        startgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Totest();
            }
        });

        //     useremail = findViewById(R.id.email);
        //    password = findViewById(R.id.passowrd);
//        webView = (WebView)findViewById(R.id.webview);
//        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.getSettings().setDomStorageEnabled(true);
//        webView.setWebViewClient(new WebViewClient());
//        webView.setWebChromeClient(new WebChromeClient());
//        webView.setScrollbarFadingEnabled(false);
//
//        webView.setScrollbarFadingEnabled(false);
//
//        String newUA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12) AppleWebKit/602.1.50 (KHTML, like Gecko) Version/10.0 Safari/602.1.50";
//
//        webView.getSettings().setUserAgentString(newUA);
//
//        webView.loadUrl("https://www.netflix.ca");

    }
    public void Totest() {
        Intent intent = new Intent(this, GameMainActivity.class);
       startActivity(intent);
     }

    public void Firsttime() {
        Intent intent = new Intent(this, Record.class);
        startActivity(intent);
    }

    public void readname(){
        try{
            FileInputStream fileInputStream = openFileInput("Username.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String lines;
            while((lines = bufferedReader.readLine()) != null){
                stringBuffer.append(lines + "\n");
            }
            userid_string = stringBuffer.toString();
            String final_string = "Default Username: " + stringBuffer.toString();
            title.setText(final_string);
        }
        catch(FileNotFoundException e){
            String error = "you haven't record a name yet";
            title.setText(error);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


    public void Login() {


        final EditText useremail = (EditText) findViewById(R.id.email);
        EditText userpassword = (EditText) findViewById(R.id.passowrd);

        final String useremail_string = useremail.getText().toString();
        final String userpassword_string = userpassword.getText().toString();


        userinfocollection.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                Roominfo info = documentSnapshot.toObject(Roominfo.class);
                                String room_number = info.getRoomnumber();
                                if(room_number.equals(useremail_string)){
                                    Tonext();
                                }
                        }

                        Toast.makeText(MainActivity.this, "No Existing Users", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void Tonext() {
        EditText user_email = findViewById(R.id.email);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(EXTRA_TEXT, user_email.getText().toString());
        intent.putExtra(USER_ID, userid_string);
        startActivity(intent);
    }

    public void ToRegister() {
        Intent intent = new Intent(this, RoomRegisterActivity.class);
        startActivity(intent);
    }



    public void Register() {




        EditText useremail = (EditText) findViewById(R.id.email);
        EditText userpassword = (EditText) findViewById(R.id.passowrd);

        String useremail_string = useremail.getText().toString();
        String userpassword_string = userpassword.getText().toString();
        userinfo user = new userinfo(useremail_string, userpassword_string);

        if (useremail_string.isEmpty() || userpassword_string.isEmpty()) {
            Toast.makeText(MainActivity.this, "Empty Email or Password", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, Object> userinfo = new HashMap<String, Object>();
            userinfo.put("email", useremail_string);
            userinfo.put("password", userpassword_string);
            userinfo.put("state",  0);
            userinfocollection.document(useremail_string).set(userinfo);
        }
    }
}

