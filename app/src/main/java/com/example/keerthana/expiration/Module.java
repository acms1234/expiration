package com.example.keerthana.expiration;

import android.app.Application;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class Module extends Application {
    public ArrayList<String> garrList=new ArrayList<>();
    public ArrayAdapter<String> garrAdp;
    public String gvalue_id;
    public  String gvalue_Name;



    public String getGvalue_id(){
        return gvalue_id;
    }
    public void setGvalue_id(String gvalue_id){
        this.gvalue_id=gvalue_id;
    }
    public String getGvalue_Name(){
        return gvalue_Name;
    }
    public void setGvalue_Name(String gvalue_Name){
        this.gvalue_id=gvalue_Name;
    }
}
