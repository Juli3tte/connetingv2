package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.DocumentCollections;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static com.example.myapplication.MainActivity.USER_ID;

public class LoginActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference user = db.collection("sampleData");
    TextView useremail;
    String useremail_string;
    Button Logout;
    Button sentbtn;
    TextView chatbtn;
    EditText message;
    TextView userid;
    Button createbtn;
    String userid_string;
    EditText Type;
    Button joinbtn;
    String game_string;
    public static final String GAME_TYPE = "TYPE";
    public static final String GAME_TY = "Ty";
    public static final String id = "Id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        useremail_string = intent.getStringExtra(MainActivity.EXTRA_TEXT);
        userid_string = intent.getStringExtra(MainActivity.USER_ID);
        useremail = (TextView) findViewById(R.id.room_number);
        Logout = findViewById(R.id.logout);
        sentbtn = findViewById(R.id.sent);
        userid = findViewById(R.id.user_id);
        userid.setText(userid_string);
        chatbtn = findViewById(R.id.chat);
        message = findViewById(R.id.message);
        createbtn = findViewById(R.id.create);
        joinbtn = findViewById(R.id.joingame);
        Type = findViewById(R.id.game_type);
        sentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sentmessage();
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recovermessage();
            }
        });
        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_game();
            }
        });
        joinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                join_game();
            }
        });
        /*
        Intent intent = getIntent();
//        useremail_string = intent.getStringExtra(MainActivity.EXTRA_TEXT);
        useremail = (TextView) findViewById(R.id.username);
        useremail.setText(useremail_string);
        DisplayUsers();
        Logout = findViewById(R.id.logout);
       Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> userinfo = new HashMap<String, Object>();
                userinfo.put("state", false);
                user.document(useremail_string).update(userinfo);
                ToNext();
            }
        });
    }

    public void ToNext(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
   public void DisplayUsers(){
       user.whereEqualTo("state", 1).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String Data = "";
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                   {
                        String userid = documentSnapshot.getString("email");
                        String userpassword = documentSnapshot.getString("password");
                        Data += "User ID " + userid + "\n";
                    }
                }
                TextView display = (TextView) findViewById(R.id.userinfo);
                display.setText(Data);
            }
        });
    }
*/
    }
    public void join_game(){
        String type = Type.getText().toString();
        game_string = "ROOM " +  useremail_string + "Game " + type;
        user.document(game_string).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user.document(game_string).update("listofplayers", FieldValue.arrayUnion(userid_string));
            }
        });
        Intent intent2 = new Intent(this, CreateGame.class);
        intent2.putExtra(GAME_TYPE, game_string);
        intent2.putExtra(GAME_TY, Type.getText().toString());
        intent2.putExtra(id, userid_string);
        startActivity(intent2);
    }
    public void create_game(){
        String type = Type.getText().toString();
        String game_string = "ROOM " +  useremail_string + "Game " + type;
        String[] stringofuser = {userid_string};
        List<String> listofstrings = Arrays.asList(stringofuser);
        Game g = new Game(game_string, listofstrings);
        user.document(game_string).set(g);
        Intent intent2 = new Intent(this, CreateGame.class);
        intent2.putExtra(GAME_TYPE, game_string);
        intent2.putExtra(GAME_TY, Type.getText().toString());
        intent2.putExtra(id, userid_string);
        startActivity(intent2);
    }

    public void recovermessage(){
        user.document(useremail_string).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String s = "";
                chat c = documentSnapshot.toObject(chat.class);
                List<String> listof = c.getChatmessages();
                for(String d: listof) {
                    s = s + d + "\n";
                }
                chatbtn.setText(s);
            }
        });
    }
    public void Sentmessage(){

        user.document(useremail_string).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String message_string_s = message.getText().toString();
                String username = "1";
                String message_string = "ID: " + userid_string + " " + message_string_s;
                if(documentSnapshot.exists()){
                   chat c = documentSnapshot.toObject(chat.class);
                   List<String> listof = c.getChatmessages();
                   user.document(useremail_string).update("chatmessages", FieldValue.arrayUnion(message_string));
                }
                else{
                    String[] Stringarray = {message_string};
                    List<String> input = Arrays.asList(Stringarray);
                    chat c = new chat(input);
                    user.document(useremail_string).set(c);
                }
            }
        });
    }
}