package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class GameMainActivity extends AppCompatActivity {

    Graphics gameFrame;
    private final int SPLASH_DISPLAY_LENGTH = 7000;

    String username_id;
    static String document_name;
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference user = db.collection("sampleData");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);

        // Get two strings we need
        Intent intent = getIntent();
        // user-id is the current user id
        username_id = intent.getStringExtra(CreateGame.player_id);
        // document_name is the document for the board
        document_name = intent.getStringExtra(CreateGame.ROOM_STRING);


        ViewGroup view_group = (ViewGroup) findViewById(R.id.main_region);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        final View title = (View)findViewById(R.id.title);

        final Graphics ge = new Graphics(this.getBaseContext());
        ge.setNumColumns(12);
        ge.setNumRows(12);
        ge.setBackgroundColor(Color.parseColor("#e8ddc2"));
        title.setBackgroundColor(Color.parseColor("#e8ddc2"));

        view_group.addView(ge);

        title.setVisibility(View.VISIBLE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // Hide your View after 3 seconds
                title.setVisibility(View.GONE);
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    // Switch turns
    public static int current_player(){
        final int[] temp = {-1};
        user.document(document_name).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                FIAR_board board_temp = documentSnapshot.toObject(FIAR_board.class);
               String current_string = board_temp.getPlayernumber();
               if(current_string.equals("1")){
                    // Player 1 turn
                   temp[0] = 1;
                }
                else{
                    // Player 2 turn
                   temp[0] = 2;
                }
            }
        });
        return temp[0];
    }

    public static void switch_turn(){
        // If current player is 2
        user.document(document_name).update("board.playernumber", "1");
        // If current player is 1
        user.document(document_name).update("board.playernumber", "2");
    }


    // Update the entry i, j for the board on db
    public static void update_entry_board(int i, int j, Integer Value){
        int  position = 12 * i + j;
        String p = "";
        p = p + position;
        user.document(document_name).update("board." + p, Value);
    }

    // update the entry i, j for the piece on db
    public static void update_entry_piece(int i , int j, String value){
        int  position = 12 * i + j;
        String p = "";
        p = p + position;
        user.document(document_name).update("piececolor." + p, value);
    }

}
