package com.example.asm_android_nang_cao;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.animation.AnimationUtils;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class Recycler_Adapter extends RecyclerView.Adapter<Recycler_Adapter.ViewHolder> {

    ArrayList<TaskModel> TaskModels;
    CaseActivity context;

    public Recycler_Adapter(ArrayList<TaskModel> taskModels, CaseActivity context) {
        TaskModels = taskModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_layout_1_dong, parent, false);
//        Animation animation = AnimationUtils.loadAnimation(context, R.anim.list_item_anim);
//        view.startAnimation(animation);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Recycler_Adapter.ViewHolder holder, int position) {
        holder.title_item.setText(TaskModels.get(position).getTieuDe());
        holder.more_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            context.chuyen_Trang(TaskModels.get(position).getId(),TaskModels.get(position).getTieuDe(),TaskModels.get(position).getNoiDung(),TaskModels.get(position).getHinhAvt(),TaskModels.get(position).getTime(),TaskModels.get(position).getCalo());
            }
        });
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.anim_listview);
        holder.itemView.startAnimation(animation);
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

        private TextView title_item;
        private TextView more_info;


        public ViewHolder(View itemView) {
            super(itemView);
//ÁNh xạ
        title_item = itemView.findViewById(R.id.title_1_d);
        more_info = itemView.findViewById(R.id.xemthem);
        }
    }
}
