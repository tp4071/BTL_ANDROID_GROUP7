package com.example.libraryapplication.helper;

import androidx.annotation.NonNull;

import com.example.libraryapplication.model.Sach;

public class BookRank {
    Sach s;
    String rank;
    public BookRank(Sach s,String rank){
        this.s=s;
        this.rank=rank;
    }
    public Sach getSach(){return s;}
    @NonNull
    public String toString(){
        return "\n"+rank+". "+s.getTenSach()+"\n"+"Tác giả: "+s.getTacGia()+"\nNhà xuất bản: "+s.getNxb()+"\n"+"Số lượt mượn: "+s.getSlMuon()+"\n";
    }
}
