package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class GameMainActivity extends AppCompatActivity {

    Graphics gameFrame;
    private final int SPLASH_DISPLAY_LENGTH = 7000;

    public static String username_id;
    public static View player;
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
        player = new TextView(this.getBaseContext());
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
        final int[] temp = new int[1];
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
        int player = current_player();
        if(player == 1) {
            user.document(document_name).update("board.playernumber", "1");
        } else if (player == 2) {
            // If current player is 1
            user.document(document_name).update("board.playernumber", "2");
        } else {
            Log.d("something is wrong",(" on switch turn"));
        }
    }


    // Update the entry i, j for the board on db
    public static void update_entry_board(int i, int j, String Value){
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String[] getPiececolor(){
        final String[][] targetArray = new String[1][1];
        user.document(document_name).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                FIAR_board board_temp = documentSnapshot.toObject(FIAR_board.class);
                Map<String,String> piececolor = board_temp.getPiececolor();
                Collection<String> values = piececolor.values();
                targetArray[0] = values.toArray(new String[values.size()]);
            }
        });
        String[] array = targetArray[0];
        return array;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String[] getCheckBoard(){
        final String[][] targetArray = new String[1][1];
        user.document(document_name).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                FIAR_board board_temp = documentSnapshot.toObject(FIAR_board.class);
                Map<String,String> board = board_temp.getBoard();
                Collection<String> values = board.values();
                targetArray[0] = values.toArray(new String[values.size()]);
            }
        });
        String[] array = targetArray[0];

        return array;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void reinit(){
        user.document(document_name).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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
                    board_map.put(q, k);
                    piece_map.put(q, k);
                }
                Game g = documentSnapshot.toObject(Game.class);
                assert g != null;
                List<String> players =  g.getListofplayers();
                FIAR_board board = new FIAR_board(players, "1", piece_map, board_map);
                user.document(document_name).set(board);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Boolean Moveable(){
        int current_player = current_player();
        final boolean[] re = {false};
        user.document(document_name).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Game g = documentSnapshot.toObject(Game.class);
                assert g != null;
                List<String> players =  g.getListofplayers();
                //username_id is the id for this device.
                if(players.get(current_player).equals(username_id)){
                    //we get an access!
                    re[0] = true;
                }
            }
        });
        return re[0];
    }

}
