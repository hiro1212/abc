package com.example.asm_android_nang_cao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LoadPageActivity extends AppCompatActivity {
    TextView text1;
    TextView text2;
    ImageView tab_bar;
    ImageView weight_lose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_page);
        text1 = findViewById(R.id.text_1);
        text2 = findViewById(R.id.text_2);
        tab_bar = findViewById(R.id.tab_bar);
        weight_lose = findViewById(R.id.imgwl);
        Animation zoomout = AnimationUtils.loadAnimation(this, R.anim.phongto);
        Animation tada = AnimationUtils.loadAnimation(this, R.anim.xuathien);
        Animation slidedown = AnimationUtils.loadAnimation(this, R.anim.rotxuong);
        text1.setAnimation(slidedown);
        text2.setAnimation(slidedown);
        tab_bar.setAnimation(slidedown);
//        weight_lose.setAnimation(tada);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadPageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}