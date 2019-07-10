package com.example.keerthana.expiration;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class productInfoAdapter extends ArrayAdapter<product> {
    private Activity context;
    private List<product> productList;
    public static int pos;
    //List<product> proList =new ArrayList<product>(productList);

    public productInfoAdapter(Activity context,List<product>productList){
       // List<product> proList =new ArrayList<product>(productList);
        super(context,R.layout.list_view,productList);
        this.context=context;
        this.productList=productList;
    }

    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflator=context.getLayoutInflater();
        View listView=inflator.inflate(R.layout.list_view,null,true);

        TextView pname=listView.findViewById(R.id.name);
        TextView date=listView.findViewById(R.id.date);
        TextView option=listView.findViewById(R.id.op);
      pos=position;
        product p=productList.get(position);
        pname.setText(p.getName());
        date.setText(p.getCdate());
        option.setText(String.valueOf(p.getOption()));
        //option.setText();
        return listView;
    }
}
