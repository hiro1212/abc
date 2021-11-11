package com.example.asm_android_nang_cao;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Recycler_2_Adapter extends RecyclerView.Adapter<Recycler_2_Adapter.ViewHolder> {

    ArrayList<TaskModel> TaskModels;
    AddTaskFirebase context;

    public Recycler_2_Adapter(ArrayList<TaskModel> taskModels, AddTaskFirebase context) {
        TaskModels = taskModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_layout_1_dong_add, parent, false);
//        Animation animation = AnimationUtils.loadAnimation(context, R.anim.list_item_anim);
//        view.startAnimation(animation);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Recycler_2_Adapter.ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.anim_listview);
        holder.itemView.startAnimation(animation);
        holder.title_add.setText(TaskModels.get(position).getTieuDe());
        holder.content_add.setText(TaskModels.get(position).getNoiDung());
        holder.time_add.setText(TaskModels.get(position).getTime()+"s");
        holder.calo_add.setText(TaskModels.get(position).getCalo()+"calo");
        Picasso.get().load(TaskModels.get(position).getHinhAvt()).into(holder.image_add);
        holder.xoa_dtb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.xoa_Firebase(TaskModels.get(position).getId());
            }
        });
        holder.sua_dtb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = TaskModels.get(position).getTieuDe();
                String content =TaskModels.get(position).getNoiDung();
                int time = TaskModels.get(position).getTime();
                String avatar = TaskModels.get(position).getHinhAvt();
                String id_sua = TaskModels.get(position).getId();
                int calo_sua = TaskModels.get(position).getCalo();
                context.sua_Firebase(title,content,time,avatar,id_sua,calo_sua);
            }
        });
//        //gán hình ảnh bằng link
////        holder.ItemGioHang_tv_tenSanPham.setText(mainModelsKhoHang.get(position).getTenSanPham().toString());
////        holder.ItemGioHang_tv_giaSanPham.setText(mainModelsKhoHang.get(position).getGiaSanPham().toString() + "vnđ");
//        holder.ItemGioHang_tv_soLuongSanPham.setText("");
//
//        holder.ItemGioHang_imgBtn_xoaItem.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                //                bắt sự kiện khi nhấn vào nút xóa
////                context.setXoaItem_gioHang(mainModelsKhoHang.get(position).getIdSanPham(), position);
////                gửi id sản phẩm
//                return false;
//            }
//        });
//
//        holder.ItemQuanLyKho_llout_btnInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////             chuyển dữ liệu quá trang kho hàng add vô bule
////                String idSanPham = mainModelsKhoHang.get(position).getIdSanPham();
////                String anhSanPham = mainModelsKhoHang.get(position).getHinhAnh();
////                String tenSanPham = mainModelsKhoHang.get(position).getTenSanPham();
////                String giaSanPham = mainModelsKhoHang.get(position).getGiaSanPham();
////                String xuatXu = mainModelsKhoHang.get(position).getXuatXu();
////                String ngayNhap = mainModelsKhoHang.get(position).getNgayNhap();
////                int loaiSanPham = mainModelsKhoHang.get(position).getLoaiSanPham();
////
////                context.setShowItem_gioHang(idSanPham, anhSanPham, tenSanPham, giaSanPham, xuatXu, ngayNhap, loaiSanPham);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (TaskModels != null) {
            return TaskModels.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title_add;
        private TextView content_add;
        private TextView time_add;
        private TextView calo_add;
        private ImageView image_add;
        private ImageView xoa_dtb;
        private ImageView sua_dtb;


        public ViewHolder(View itemView) {
            super(itemView);
//ÁNh xạ
            calo_add = itemView.findViewById(R.id.calo_add);
            time_add = itemView.findViewById(R.id.time_add);
            title_add = itemView.findViewById(R.id.title_add);
            content_add = itemView.findViewById(R.id.content_add);
            image_add = itemView.findViewById(R.id.image_add);
            xoa_dtb = itemView.findViewById(R.id.xoa_dtb);
            sua_dtb = itemView.findViewById(R.id.sua_dtb);
        }
    }
}
