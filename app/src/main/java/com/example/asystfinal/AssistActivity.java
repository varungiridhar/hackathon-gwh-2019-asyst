package com.example.asystfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AssistActivity extends AppCompatActivity {
    private static final String TAG = "RMMenuActivity";
    private ArrayList<String> mPhoneNumbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assist);

        initPhoneNumbers();

    }

    private void initPhoneNumbers(){

        addPhoneNumbers();



    }

    private void addPhoneNumbers() {
        Log.d(TAG, "addPhoneNumbers:started ");

        mPhoneNumbers.add("9845660365");
        mPhoneNumbers.add("8861958661");
        mPhoneNumbers.add("9345660362");
        mPhoneNumbers.add("9635235663");


        initRecyclerView();

    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mPhoneNumbers, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }









}
