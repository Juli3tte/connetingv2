package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class RoomRegisterActivity extends AppCompatActivity {


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference user = db.collection("sampleData");
    private DocumentReference document = db.document("sampleData/insp");


    EditText room_number_view;
    Button check_non_repeat;
    Button register;
    EditText room_type;
    EditText room_name;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_register);
        room_number_view = (EditText)findViewById(R.id.room_number);
        check_non_repeat = findViewById(R.id.check_out_button);
        register = findViewById(R.id.register_button);
        room_type = findViewById(R.id.type);
        room_name = findViewById(R.id.room_name);

        check_non_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check_non_repeat();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               register();
            }
        });
    }

    public void Check_non_repeat() {
        EditText room_number_view_temp = (EditText)findViewById(R.id.room_number);
        final String room_number_string = room_number_view_temp.getText().toString();
        user.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Roominfo roominfo = documentSnapshot.toObject(Roominfo.class);
                    String room_num = roominfo.getRoomnumber();

                    if (roominfo.getRoomnumber().equals(room_number_string)) {
                        Toast.makeText(RoomRegisterActivity.this, "Number Repeated", Toast.LENGTH_SHORT).show();
                      //  return;
                    }
                }
                Toast.makeText(RoomRegisterActivity.this, "Number is not repeated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void register() {
       String room_number_string = room_number_view.getText().toString();
        String room_type_string = room_type.getText().toString();
        String room_name_string = room_name.getText().toString();
        Roominfo current_room = new Roominfo(room_number_string, room_type_string, room_name_string);
        user.add(current_room);
    }
}