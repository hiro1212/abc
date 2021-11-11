package com.example.asm_android_nang_cao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    ImageView Backtohome;
    FirebaseAuth mAuth;
    TextView signIn;
    Button register;
    EditText email_edt;
    EditText password_edt;
    EditText password_edt_2;
    String email;
    String password;
    String re_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Backtohome = findViewById(R.id.backGoLogin);
        Backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        signIn = findViewById(R.id.tvOnGoFromSignIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dangKyFB();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        email_edt = findViewById(R.id.newgmail);
        password_edt = findViewById(R.id.newPassword);
        password_edt_2 = findViewById(R.id.reps);

    }
    private void dangKyFB(){
        email = email_edt.getText().toString();
        password = password_edt.getText().toString();
        re_password = password_edt_2.getText().toString();
        if(TextUtils.isEmpty(email)){
            email_edt.setError("Email không được để trống hoặc không đúng định dạng");
            email_edt.requestFocus();
            return;
        }else if(TextUtils.isEmpty(password) && password.length()<6){
            password_edt.setError("Password không được để trống và không được bé hơn 6 ký tự");
            password_edt.requestFocus();
            return;
        }else if(TextUtils.isEmpty(re_password)&&!re_password.equals(password)){
            password_edt_2.setError("RE-Password không được để trống và phải trung với Password");
            password_edt_2.requestFocus();
            return;
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(RegisterActivity.this, "Tài khoản được tạo thành công", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                openDialogAddProduc();
                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegisterActivity.this, "Tạo tài khoản không thành công",
                                        Toast.LENGTH_SHORT).show();
                                openDialogAddProducError();
                            }
                        }
                    });
        }

    }
    private void openDialogAddProduc() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_show_animation);
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
    private void openDialogAddProducError() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_thatbai);
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
//    private void re(){
//        public class Login_Activity extends AppCompatActivity {
//            private TextView
//                    Login_tv_btn_SignUp,
//                    Login_tv_btn_SignUpText,
//                    Login_tv_username,
//                    Login_tv_password;
//            private EditText
//                    Login_edt_username,
//                    Login_edt_password;
//            private LinearLayout
//                    Login_llout_btn_submid;
//
//            FirebaseDatabase databaseAccout;
//            DatabaseReference databaseRef;
//
//
//            String userName, password;
//            //modelaccout
//            ArrayList<accout_user_Model> arrayListAcoutUsser;
//            accout_user_Model arrAcoutUser;
//            SharedPreferences shareAcout;
//
//            @Override
//            protected void onCreate(Bundle savedInstanceState) {
//                super.onCreate(savedInstanceState);
//                setContentView(R.layout.activity_login);
//                anhXa();
//                firebaseDataAccout();
//                nhanDuLieuIntent();
//                batSuKien();
//            }
//
//
//            private void nhanDuLieuIntent() {
//                Intent intent = getIntent();
//                Bundle bundle = intent.getExtras();
//                if (bundle != null) {
//                    userName = bundle.getString("key_userName", "");
//                    password = bundle.getString("key_password", "");
//                    Login_edt_username.setText(userName);
//                    Login_edt_password.setText(password);
//                }
//            }
//
//            private void batSuKien() {
////        chuyển sang trang đăng kí tài khoản
//                Login_tv_btn_SignUp.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(Login_Activity.this, SignUp_Activity.class);
//                        startActivity(intent);
//                    }
//                });
//                Login_tv_btn_SignUpText.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(Login_Activity.this, SignUp_Activity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putBoolean("key_checkLoading", true);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                    }
//                });
//
//                //        check tài khoản
//                Login_llout_btn_submid.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //      gọi hàm check tài khoản
//                        checkValidateSet();
//                    }
//                });
//            }
//
//            /********************Kiểm tra tài khoản
//             * @return**********************/
//            public void checkAccout(String username, String password) {
//                anhXa();
//                int vitri = 0;
//                boolean check = false;
//                if (arrayListAcoutUsser != null) {
//                    for (int i = 0; i < arrayListAcoutUsser.size(); i = i + 1) {
//
//                        String userName = arrayListAcoutUsser.get(i).getUserName();
//                        String passwords = arrayListAcoutUsser.get(i).getPassword();
//                        if (username.equals(userName)) {
//                            setNullBackgourd(Login_edt_username, Login_tv_username);
//                            if (passwords.equals(password)) {
//                                setNullBackgourd(Login_edt_password, Login_tv_password);
//                                check = true;
//                                vitri = i;
//                            }
//                        }
//                    }
//                }
//                if (!check) {
//                    error(Login_edt_username, "Tài khoản không tồn tại!", Login_tv_username);
//                    error(Login_edt_password, "Sai Mật khẩu!", Login_tv_password);
//                } else {
//                    SharedPreferences.Editor editor = shareAcout.edit();
//                    editor.putString("key_idUserName", arrayListAcoutUsser.get(vitri).getIdUser());
//                    editor.putInt("key_quyen", arrayListAcoutUsser.get(vitri).getQuyen());
//                    editor.putString("key_idGioHang", arrayListAcoutUsser.get(vitri).getIdGioHang());
//                    editor.putString("key_avatar", arrayListAcoutUsser.get(vitri).getAvatar());
//                    editor.putString("key_name", arrayListAcoutUsser.get(vitri).getName());
//                    editor.putString("key_userName", arrayListAcoutUsser.get(vitri).getUserName());
//                    editor.putString("key_password", arrayListAcoutUsser.get(vitri).getPassword());
//                    editor.putString("key_diaChi", arrayListAcoutUsser.get(vitri).getDiaChi());
//                    editor.putInt("key_soDienThoai", arrayListAcoutUsser.get(vitri).getSoDienThoai());
//                    editor.putString("key_gmail", arrayListAcoutUsser.get(vitri).getGmail());
//                    editor.apply();
//                    Intent intent = new Intent(Login_Activity.this, sanPham_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putBoolean("key_checkLoading", true);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }
//            }
//
//
//            /********************lấy dữ liệu từ firebase gán vào arrList**********************/
//            private void firebaseDataAccout() {
//
//                //         Gán giá trị trong firebase
//                databaseRef = databaseAccout.getReference().child("Users");
//                databaseRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        arrayListAcoutUsser = new ArrayList<>();
//                        for (DataSnapshot child : snapshot.getChildren()) {
//                            // gọi dữ liệu trên firebase về
//                            arrAcoutUser = child.getValue(accout_user_Model.class);
//
//                            // add vào listItemSanpham
//                            if (arrAcoutUser.getIdUser() != null) {
//                                arrayListAcoutUsser.add(new accout_user_Model(
//                                        arrAcoutUser.getIdUser(),
//                                        arrAcoutUser.getQuyen(),
//                                        arrAcoutUser.getName(),
//                                        arrAcoutUser.getUserName(),
//                                        arrAcoutUser.getPassword(),
//                                        arrAcoutUser.getDiaChi(),
//                                        arrAcoutUser.getGmail(),
//                                        arrAcoutUser.getSoDienThoai(),
//                                        arrAcoutUser.getNgayTao(),
//                                        arrAcoutUser.getTrangThai(),
//                                        arrAcoutUser.getIdGioHang(),
//                                        arrAcoutUser.getAvatar()));
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError error) {
//                    }
//                });
//            }
//
//            private void checkValidateSet() {
//                String username = Login_edt_username.getText().toString().trim();
//                String password = Login_edt_password.getText().toString().trim();
//                if (!username.equals("")) {
//                    setNullBackgourd(Login_edt_username, Login_tv_username);
//                    if (!password.equals("")) {
//                        setNullBackgourd(Login_edt_password, Login_tv_password);
//                        //kiểm tra tài khoản
//                        checkAccout(username, password);
//                    } else {
//                        error(Login_edt_password, "Mật khẩu không được bỏ trống!", Login_tv_password);
//                    }
//                } else {
//                    error(Login_edt_username, "Tên đăng nhập không được bỏng trống!", Login_tv_username);
//                }
//
//            }
//
//            private void setNullBackgourd(EditText edt, TextView tv) {
//                edt.setBackgroundResource(R.drawable.broder_stroke_black);
//                tv.setTextColor(Color.parseColor("#616161"));
//
//            }
//
//            private void error(EditText edt, String tv, TextView tv2) {
//                edt.setError(tv);
//                edt.setBackgroundResource(R.drawable.broder_stroke_red_error);
//                edt.requestFocus();
//                tv2.setTextColor(Color.parseColor("#E85243"));
//            }
//
//            private void anhXa() {
//                shareAcout = getSharedPreferences("AccountSharedPreferences", MODE_PRIVATE);
//                databaseAccout = FirebaseDatabase.getInstance("https://asmandroid-duan1-default-rtdb.asia-southeast1.firebasedatabase.app/");
//
//
////        TextView
//                Login_tv_btn_SignUp = findViewById(R.id.login_tv_btn_SignUp);
//                Login_tv_btn_SignUpText = findViewById(R.id.login_tv_btn_SignUpText);
//                Login_tv_username = findViewById(R.id.login_tv_username);
//                Login_tv_password = findViewById(R.id.login_tv_password);
//
////        EditText
//                Login_edt_username = findViewById(R.id.login_edt_username);
//                Login_edt_password = findViewById(R.id.login_edt_password);
//
////        LinearLayout
//                Login_llout_btn_submid = findViewById(R.id.login_llout_btn_submid);
//            }
//        }
//    }
}