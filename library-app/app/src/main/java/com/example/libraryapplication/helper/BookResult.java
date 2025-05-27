package com.example.libraryapplication.helper;

import androidx.annotation.NonNull;

import com.example.libraryapplication.model.Sach;

public class BookResult {
    Sach s;
    String text;
    public BookResult(Sach s,String text){
        this.s=s;
        this.text=text;
    }
    public Sach getSach(){return s;}
    @NonNull
    public String toString(){
        if(s==null)return text;
        else return s+"\n";
    }
}
