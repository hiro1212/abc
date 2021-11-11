package com.example.asm_android_nang_cao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CaseActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase fbdtb;
    DatabaseReference node;
    FirebaseStorage storage;
    ArrayList<TaskModel> arrtm;
    RecyclerView rcl_view;
    Recycler_Adapter rcl_adt;
    EditText search_bar;
    String textSearch;
    Intent service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case);
        service = new Intent(CaseActivity.this, TaskService.class);
        CaseActivity.this.startService(service);
        mAuth = FirebaseAuth.getInstance();
        arrtm = new ArrayList<>();
        updateDL();
        search_bar =findViewById(R.id.edtSearch);
        search_bar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                textSearch = search_bar.getText().toString();
                fbdtb = FirebaseDatabase.getInstance("https://asm-android-nang-cao-172de-default-rtdb.asia-southeast1.firebasedatabase.app/");
                node = fbdtb.getReference("Quests");
                storage = FirebaseStorage.getInstance("gs://asm-android-nang-cao-172de.appspot.com");
                Query query = node.orderByChild("tieuDe");

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        arrtm.clear();
                        for(DataSnapshot child : dataSnapshot.getChildren()) {
                            TaskModel task = child.getValue(TaskModel.class);
                            if (task.getId() != null) {
                                if (textSearch != null) {
                                    if (task.getTieuDe().contains(textSearch)) {
                                        arrtm.add(task);
                                        listQuests(arrtm);
                                    }
                                } else {
                                    arrtm.add(task);
                                    listQuests(arrtm);
                                }
                            }

                        }
                        System.out.println(arrtm);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                return false;
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.first_fragment);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.first_fragment:
                        return true;
                    case R.id.second_fragment:
                        startActivity(new Intent(getApplicationContext(),ListActivity.class));
                        overridePendingTransition(0, 0);
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
    private void updateDL(){
        arrtm.clear();
        fbdtb = FirebaseDatabase.getInstance("https://asm-android-nang-cao-172de-default-rtdb.asia-southeast1.firebasedatabase.app/");
        node = fbdtb.getReference("Quests");
        storage = FirebaseStorage.getInstance("gs://asm-android-nang-cao-172de.appspot.com");
        Query query = node.orderByChild("tieuDe");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    TaskModel task = child.getValue(TaskModel.class);
                    if (task.getId() != null) {
                        if (textSearch != null) {
                            if (task.getTieuDe().contains(textSearch)) {
                                arrtm.add(task);
                                listQuests(arrtm);
                            }
                        } else {
                            arrtm.add(task);
                            listQuests(arrtm);
                        }
                    }

                }
                System.out.println(arrtm);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot dataSnapshot, @Nullable @org.jetbrains.annotations.Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot dataSnapshot, @Nullable @org.jetbrains.annotations.Nullable String s) {
                TaskModel user = dataSnapshot.getValue(TaskModel.class);
                if (user == null || arrtm == null || arrtm.isEmpty()) {
                    return;
                }
                for (int i = 0; i < arrtm.size(); i++) {
                    if (user.getId() == arrtm.get(i).getId()) {
                        arrtm.set(i, user);
                        break;
                    }
                }
                listQuests(arrtm);
            }

            @Override
            public void onChildRemoved(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot dataSnapshot) {
                TaskModel user = dataSnapshot.getValue(TaskModel.class);
                if (user == null || arrtm == null || arrtm.isEmpty()) {
                    return;
                }
                for (int i = 0; i < arrtm.size(); i++) {
                    if (user.getId() == arrtm.get(i).getId()) {
                        arrtm.remove(arrtm.get(i));

                        break;
                    }
                }
                listQuests(arrtm);
            }

            @Override
            public void onChildMoved(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot dataSnapshot, @Nullable @org.jetbrains.annotations.Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError databaseError) {

            }
        });
    }
    public void chuyen_Trang(String id,String title,String content,String avt,int time,int calo){
        Intent intent =new Intent(CaseActivity.this,ShowCaseActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        intent.putExtra("avt",avt);
        intent.putExtra("time",time);
        intent.putExtra("calo",calo);
        startActivity(intent);
    }
    private void listQuests(ArrayList<TaskModel> TaskModels) {
        rcl_view = findViewById(R.id.xuatds);
        rcl_view.setLayoutManager(new GridLayoutManager(this, 1));
        rcl_view.setItemAnimator(new DefaultItemAnimator());

//        Initilize
        rcl_adt = new Recycler_Adapter(TaskModels, CaseActivity.this);
        rcl_view.setAdapter(rcl_adt);
        rcl_adt.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user =mAuth.getCurrentUser();
        if(user==null){
            startActivity(new Intent(CaseActivity.this,MainActivity.class));
        }
    }
}