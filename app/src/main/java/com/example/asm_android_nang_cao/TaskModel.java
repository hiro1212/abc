package com.example.asm_android_nang_cao;

import java.util.HashMap;
import java.util.Map;

public class TaskModel {
    private String id;
    private String tieuDe;
    private String noiDung;
    private String hinhAvt;
    private int time;
    private int calo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getHinhAvt() {
        return hinhAvt;
    }

    public void setHinhAvt(String hinhAvt) {
        this.hinhAvt = hinhAvt;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCalo() {
        return calo;
    }

    public void setCalo(int calo) {
        this.calo = calo;
    }

    public TaskModel(String id, String tieuDe, String noiDung, String hinhAvt, int time, int calo) {
        this.id = id;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.hinhAvt = hinhAvt;
        this.time = time;
        this.calo = calo;
    }

    public TaskModel() {
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> resuft = new HashMap<>();
        resuft.put("tieuDe", tieuDe);
        resuft.put("noiDung", noiDung);
        resuft.put("hinhAvt", hinhAvt);
        resuft.put("time", time);
        resuft.put("calo", calo);
        return resuft;
    }
}
