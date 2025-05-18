package com.example.libraryapplication.helper;

import com.example.libraryapplication.model.Sach;

public class BookRank {
    Sach s;
    String rank;
    public BookRank(Sach s,String rank){
        this.s=s;
        this.rank=rank;
    }
    public Sach getSach(){return s;}
    public String toString(){
        return rank+". "+s.toString();
    }
}
