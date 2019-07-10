package com.example.keerthana.expiration;

import android.content.Intent;

import java.util.Date;

public class product
{
    private String name;
    //private String pdate;
    private String cdate;
    private int option;

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }



    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public product(){

    }

    public product(String name,String cdate,int option)
    {
        this.name=name;
        this.cdate=cdate;
        this.option=option;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
