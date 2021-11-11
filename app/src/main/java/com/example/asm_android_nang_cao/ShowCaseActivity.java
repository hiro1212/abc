package com.example.asm_android_nang_cao;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class
ShowCaseActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    private CountDownTimer countDownTimer;
    private TextView testtime;
    private Button startandpause;
    private String id,title,content,avt;
    private int calo;
    private int time_intent;
    private long time;
    private ImageView home;
    private TextView title_xuat,content_xuat,timevsb,calo_xuat;
    private ImageView img_xuat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_case);
        title_xuat = findViewById(R.id.et_title);
        content_xuat = findViewById(R.id.et_content);
        img_xuat = findViewById(R.id.image_showcase);
        home = findViewById(R.id.backHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowCaseActivity.this,CaseActivity.class));
            }
        });
        timevsb =findViewById(R.id.time_vsb);
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        avt = intent.getStringExtra("avt");
        time_intent = intent.getIntExtra("time",0);
        calo = intent.getIntExtra("calo",0);
        testtime = findViewById(R.id.testtime);
        calo_xuat = findViewById(R.id.calo_test);
        calo_xuat.setText(String.valueOf(calo));
        time = time_intent;
        testtime.setText(String.valueOf(time)+"s");
        title_xuat.setText(title);
        content_xuat.setText(content);
        Picasso.get().load(avt).into(img_xuat);
        startandpause = findViewById(R.id.startcdtime);
        startandpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timevsb.setVisibility(View.INVISIBLE);
                openDialogAddProducA();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startandpause.setVisibility(View.INVISIBLE);
                        countDownTimer.start();
                    }
                }, 5000);


            }
        });
        countDownTimer = new CountDownTimer(time*1000, 1000){

            @Override
            public void onTick(long l) {
                time = (int)l/1000;
                testtime.setText(String.valueOf(time)+"s");
            }

            @Override
            public void onFinish() {
                openDialogAddProduc();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        chuyen_Trang(id,time_intent,calo);
//                        sau khi chạy hết khoảng thời gian 1s thì tắt
                        startActivity(new Intent(ShowCaseActivity.this,ListActivity.class));
                    }
                }, 5000);
            }
        };

    }
    protected void onStart() {
        super.onStart();
        FirebaseUser user =mAuth.getCurrentUser();
        if(user==null){
            startActivity(new Intent(ShowCaseActivity.this,MainActivity.class));
        }
    }
    public void chuyen_Trang(String id,int time,int calo){
        Intent intent =new Intent(ShowCaseActivity.this,ListActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("time",time);
        intent.putExtra("calo",calo);
        startActivity(intent);
    }
    private void openDialogAddProduc() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_thanhcong1);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(false);
        dialog.show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                        sau khi chạy hết khoảng thời gian 1s thì tắt
                dialog.dismiss();
            }
        }, 5000);
    }
    private void openDialogAddProducA() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_sansang);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(false);
        dialog.show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                        sau khi chạy hết khoảng thời gian 1s thì tắt
                dialog.dismiss();
            }
        }, 5000);
    }

}