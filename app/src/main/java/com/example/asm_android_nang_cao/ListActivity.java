package com.example.asm_android_nang_cao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class ListActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    private String id,title,content,avt;
    private int calo;
    private int time_intent;
    private TextView title_xuat,content_xuat,timevsb,calo_xuat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent = getIntent();
        mAuth = FirebaseAuth.getInstance();
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        avt = intent.getStringExtra("avt");
        time_intent = intent.getIntExtra("time",0);
        calo = intent.getIntExtra("calo",0);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.first_fragment);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.first_fragment:
                        startActivity(new Intent(getApplicationContext(),CaseActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.second_fragment:
                        return true;
                    case R.id.third_fragment:
                        startActivity(new Intent(getApplicationContext(), InformationActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });
    }
    protected void onStart() {
        super.onStart();


        FirebaseUser user =mAuth.getCurrentUser();
        if(user==null){
            startActivity(new Intent(ListActivity.this,MainActivity.class));
        }
    }
}