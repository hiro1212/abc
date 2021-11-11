package com.example.asm_android_nang_cao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class AddTaskFirebase extends AppCompatActivity {
    public static String NOTIFICATION_CHANNEL = "thong bao app";
    FirebaseDatabase fbdtb;
    FirebaseAuth mAuth;
    DatabaseReference node;
    private static final int REQUEST_CODE_IMGE = 99;
    private static final int REQUEST_IMAGE_OPEN = 1;
    FirebaseStorage storage;
    TextView them;
    TextView sua;
    String id_sua;
    Intent service;
    EditText et_title;
    EditText et_content;
    EditText et_time;
    ImageView imgAvatar;
    ImageView imgChup;
    ArrayList<TaskModel> arrtm;
    RecyclerView rcl_view;
    Recycler_2_Adapter rcl_adt;
    ImageView backpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_firebase);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            boolean emailVerified = user.isEmailVerified();
            String uid = user.getUid();
        }
        backpage =findViewById(R.id.backpage);
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddTaskFirebase.this,CaseActivity.class));
            }
        });
        arrtm = new ArrayList<>();
        imgChup = findViewById(R.id.imgchup);
        imgChup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_IMGE);
            }
        });
        et_time = findViewById(R.id.ed_time);
        et_time.setText("15");
        imgAvatar = findViewById(R.id.imgavatar);
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, REQUEST_IMAGE_OPEN);
            }
        });
        fbdtb = FirebaseDatabase.getInstance("https://asm-android-nang-cao-172de-default-rtdb.asia-southeast1.firebasedatabase.app/");
        node = fbdtb.getReference("Quests");
        storage = FirebaseStorage.getInstance("gs://asm-android-nang-cao-172de.appspot.com");
        them = findViewById(R.id.additem);
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                final StorageReference storageRef = storage.getReference();
                StorageReference mountainsRef = storageRef.child("img" + calendar.getTimeInMillis() + ".png");
                // Get the data from an ImageView as bytes
                imgAvatar.setDrawingCacheEnabled(true);
                imgAvatar.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imgAvatar.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();
                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(AddTaskFirebase.this, "Lỗi!!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                TaskModel taskModel = new TaskModel();
                                TextView nameTV = AddTaskFirebase.this.findViewById(R.id.et_title);
                                String name = nameTV.getText().toString();
                                if (name.equals("")) {
                                    Toast.makeText(AddTaskFirebase.this, "Title đang trống !!!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                taskModel.setTieuDe(name);
                                TextView time = AddTaskFirebase.this.findViewById(R.id.ed_time);
                                int timetv = Integer.parseInt(time.getText().toString());
                                if (time.getText().equals("")) {
                                    Toast.makeText(AddTaskFirebase.this, "Time đang trống !!!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                taskModel.setTime(timetv);
                                TextView calo = AddTaskFirebase.this.findViewById(R.id.et_calo);
                                int calotv = Integer.parseInt(calo.getText().toString());
                                if (calo.getText().equals("")) {
                                    Toast.makeText(AddTaskFirebase.this, "Calo đang trống !!!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                taskModel.setCalo(calotv);
                                TextView contentTV = AddTaskFirebase.this.findViewById(R.id.et_content);
                                String content = contentTV.getText().toString();
                                if (content.equals("")) {
                                    Toast.makeText(AddTaskFirebase.this, "Content đang trống !!!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                taskModel.setHinhAvt(uri.toString().trim());
                                taskModel.setNoiDung(content);
                                UUID id = UUID.randomUUID();
                                taskModel.setId(id.toString());
                                fbdtb = FirebaseDatabase.getInstance("https://asm-android-nang-cao-172de-default-rtdb.asia-southeast1.firebasedatabase.app/");
                                node = fbdtb.getReference("Quests");
                                node.child(id.toString()).setValue(taskModel);
                                service = new Intent(AddTaskFirebase.this, TaskService.class);
                                AddTaskFirebase.this.startService(service);
                                openDialogAddProduc();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                    }
                });


            }
        });

        Query query = node.orderByChild("tieuDe");
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                arrtm.clear();
//                for (DataSnapshot child : dataSnapshot.getChildren()) {
//                    TaskModel task = child.getValue(TaskModel.class);
//                    arrtm.add(task);
//                    listQuests(arrtm);
//
//                }
//                System.out.println(arrtm);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot dataSnapshot, @Nullable @org.jetbrains.annotations.Nullable String s) {
                TaskModel task = dataSnapshot.getValue(TaskModel.class);
                if (task != null) {
                    arrtm.add(task);
                }
                listQuests(arrtm);
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
        sua = findViewById(R.id.edititem);
        sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                final StorageReference storageRef = storage.getReference();
                StorageReference mountainsRef = storageRef.child("img" + calendar.getTimeInMillis() + ".png");

                // Get the data from an ImageView as bytes
                imgAvatar.setDrawingCacheEnabled(true);
                imgAvatar.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imgAvatar.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();
                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(AddTaskFirebase.this, "Lỗi!!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                TaskModel taskModel = new TaskModel();
                                TextView nameTV = AddTaskFirebase.this.findViewById(R.id.et_title);
                                String name = nameTV.getText().toString();
                                if (name.equals("")) {
                                    Toast.makeText(AddTaskFirebase.this, "Title đang trống !!!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                taskModel.setTieuDe(name);
                                TextView time = AddTaskFirebase.this.findViewById(R.id.ed_time);
                                int timetv = Integer.parseInt(time.getText().toString());
                                if (time.equals("")) {
                                    Toast.makeText(AddTaskFirebase.this, "Time đang trống !!!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                taskModel.setTime(timetv);
                                TextView calo = AddTaskFirebase.this.findViewById(R.id.et_calo);
                                int calotv = Integer.parseInt(calo.getText().toString());
                                if (calo.getText().equals("")) {
                                    Toast.makeText(AddTaskFirebase.this, "Calo đang trống !!!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                taskModel.setCalo(calotv);
                                TextView contentTV = AddTaskFirebase.this.findViewById(R.id.et_content);
                                String content = contentTV.getText().toString();
                                if (content.equals("")) {
                                    Toast.makeText(AddTaskFirebase.this, "Content đang trống !!!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                taskModel.setHinhAvt(uri.toString().trim());
                                taskModel.setNoiDung(content);
                                fbdtb = FirebaseDatabase.getInstance("https://asm-android-nang-cao-172de-default-rtdb.asia-southeast1.firebasedatabase.app/");
                                node = fbdtb.getReference("Quests");
//                                Toast.makeText(AddTaskFirebase.this, ""+id_sua, Toast.LENGTH_SHORT).show();
                                node.child(id_sua).updateChildren(taskModel.toMap());
                                openDialogAddProduc();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                    }
                });

            }
        });
    }

    public void xoa_Firebase(String id) {
        fbdtb = FirebaseDatabase.getInstance("https://asm-android-nang-cao-172de-default-rtdb.asia-southeast1.firebasedatabase.app/");
        node = fbdtb.getReference("Quests");
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(AddTaskFirebase.this);
        myBuilder.setMessage("Bạn chắc chắn muốn xóa không ???");
        myBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int j) {
                node.child(id).removeValue();
                openDialogAddProduc();
            }
        });
        myBuilder.setNegativeButton("HỦy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        myBuilder.show();
    }

    public void sua_Firebase(String title, String content, int time, String image, String id, int calo) {
        EditText title_ed = AddTaskFirebase.this.findViewById(R.id.et_title);
        EditText content_ed = AddTaskFirebase.this.findViewById(R.id.et_content);
        EditText time_ed = AddTaskFirebase.this.findViewById(R.id.ed_time);
        ImageView avt_view = AddTaskFirebase.this.findViewById(R.id.imgavatar);
        EditText calo_ed = AddTaskFirebase.this.findViewById(R.id.et_calo);
        id_sua = id;
        title_ed.setText(title);
        content_ed.setText(content);
        time_ed.setText(String.valueOf(time));
        Picasso.get().load(image).into(avt_view);
        calo_ed.setText(String.valueOf(calo));
    }

    ;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_IMGE && resultCode == RESULT_OK) {
            imgAvatar = (ImageView) findViewById(R.id.imgavatar);
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgAvatar.setImageBitmap(bitmap);

        }
        if (requestCode == REQUEST_IMAGE_OPEN && resultCode == RESULT_OK) {
            imgAvatar = (ImageView) findViewById(R.id.imgavatar);
            Uri full = data.getData();
            imgAvatar.setImageURI(full);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Show thông tin ra kiểu dọc
     **/
    private void listQuests(ArrayList<TaskModel> TaskModels) {
        rcl_view = findViewById(R.id.recyclerviewFB);
        rcl_view.setLayoutManager(new GridLayoutManager(this, 1));
        rcl_view.setItemAnimator(new DefaultItemAnimator());

//        Initilize
        rcl_adt = new Recycler_2_Adapter(TaskModels, AddTaskFirebase.this);
        rcl_view.setAdapter(rcl_adt);
        rcl_adt.notifyDataSetChanged();
    }

    private void showNotification() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(AddTaskFirebase.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Kênh Thông Báo";
            String description = "Mô Tả Thông Báo";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL, name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }
//    Intent intent = new Intent(second_fragment.this, second_fragment.class);
//    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//    PendingIntent pendingIntent = PendingIntent.getActivity(second_fragment.this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(AddTaskFirebase.this, NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("Thông Báo")
                .setContentText("Đã thêm 1 hoạt động mới")
//            .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager.notify(22, builder.build());
    }
    protected void onStart() {
        super.onStart();
        FirebaseUser user =mAuth.getCurrentUser();
        if(user==null){
            startActivity(new Intent(AddTaskFirebase.this,MainActivity.class));
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
}