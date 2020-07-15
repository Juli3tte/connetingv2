package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateGame extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference user = db.collection("sampleData");
    TextView Title;
    String userid_string;
    String type;
    String backup;
    Button updatebtn;
    public static final String ROOM_STRING= "room";
    public static final String player_id = "player";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        Title = findViewById(R.id.game_title);
        Intent intent = getIntent();
        final String title_string = intent.getStringExtra(LoginActivity.GAME_TYPE);
        backup = title_string;
        updatebtn = findViewById(R.id.update);
        type = intent.getStringExtra(LoginActivity.GAME_TY);
        userid_string = intent.getStringExtra(LoginActivity.id);
        Title.setText(title_string);
        if(type.equals("1")) {
            // Create a five in a row game

            user.document(title_string).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Integer numbers_of_rows = 12;
                    Integer number_of_columns = 12;
                    Map<String, String> board_map = new HashMap<>();
                    Map<String, String> piece_map = new HashMap<>();
                    for(int i = 0; i < numbers_of_rows * number_of_columns; ++i){
                        String q = "";
                        q = q + i;
                        String k = "-1";
                        String k2 = "false";
                        board_map.put(q, k2);
                        piece_map.put(q, k);
                    }
                    Game g = documentSnapshot.toObject(Game.class);
                    assert g != null;
                    List<String> players =  g.getListofplayers();
                    FIAR_board board = new FIAR_board(players, "1", piece_map, board_map);
                    user.document(title_string).set(board);
                }
            });
            updatebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Tonext();

                }
            });
//            update_entry_piece(0, 0, "hello");


        }
    }
    public void Tonext(){
        Intent new_intent = new Intent(this, GameMainActivity.class);
        new_intent.putExtra(ROOM_STRING, backup);
        new_intent.putExtra(player_id, userid_string);
        startActivity(new_intent);
    }
    public void update_entry_piece(int i , int j, String value){
        int  position = 12 * i + j;
        String p = "";
        p = p + position;
        user.document(backup).update("piececolor." + p, value);
    }
}