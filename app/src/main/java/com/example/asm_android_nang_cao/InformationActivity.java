package com.example.asm_android_nang_cao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class InformationActivity extends AppCompatActivity {
    LinearLayout admin;
    TextView logout;
    TextView dinhvi;
    FirebaseAuth mAuth;
    String dinhvi1;
    FusedLocationProviderClient fusedLocationProviderClient;
    String email,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        admin = findViewById(R.id.admin_page);
        admin.setVisibility(View.INVISIBLE);
        if(email.equals("baoheo721@gmail.com")){
            admin.setVisibility(View.VISIBLE);
        }
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InformationActivity.this, AddTaskFirebase.class));
            }
        });
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(InformationActivity.this, MainActivity.class));
            }
        });
        dinhvi = findViewById(R.id.dinhvi);
        dinhvi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(InformationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(InformationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.first_fragment);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.first_fragment:
                        startActivity(new Intent(getApplicationContext(), CaseActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.second_fragment:
                        startActivity(new Intent(getApplicationContext(), ListActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.third_fragment:
                        return true;

                }
                return false;
            }
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(InformationActivity.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
//                        dinhvi1 = Html.fromHtml("<font color='#6200EE'><b>Địa chỉ : </b><br></font>"+addresses.get(0).getAddressLine(0)).toString();
                        Toast.makeText(InformationActivity.this, ""+ Html.fromHtml("<font color='#6200EE'><b>Country Name : </b><br></font>"+addresses.get(0).getCountryName()), Toast.LENGTH_SHORT).show();
                        Toast.makeText(InformationActivity.this, ""+ Html.fromHtml("<font color='#6200EE'><b>Locality : </b><br></font>"+addresses.get(0).getLocality()), Toast.LENGTH_SHORT).show();
                        Toast.makeText(InformationActivity.this, ""+ Html.fromHtml("<font color='#6200EE'><b>Địa Chỉ: </b><br></font>"+addresses.get(0).getAddressLine(0)), Toast.LENGTH_SHORT).show();
//                        openDialogAddProduc();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    protected void onStart() {
        super.onStart();
        FirebaseUser user =mAuth.getCurrentUser();
        if(user==null){
            startActivity(new Intent(InformationActivity.this,MainActivity.class));
        }
    }
//    private void openDialogAddProduc() {
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.layout_vitri);
//        Window window = dialog.getWindow();
//        if (window == null) {
//            return;
//        }
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        WindowManager.LayoutParams windowAttributes = window.getAttributes();
//        windowAttributes.gravity = Gravity.CENTER;
//        window.setAttributes(windowAttributes);
//        TextView diachi = findViewById(R.id.textView5);
//        diachi.setText(dinhvi1);
//        dialog.setCancelable(false);
//        dialog.show();
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                        sau khi chạy hết khoảng thời gian 1s thì tắt
//                dialog.dismiss();
//            }
//        }, 5000);
//    }
}