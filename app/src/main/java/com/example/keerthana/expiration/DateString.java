package com.example.keerthana.expiration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.keerthana.expiration.OcrCaptureActivity;
import com.example.keerthana.expiration.MainActivity;
import com.joestelmach.natty.Parser;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

public class DateString extends AppCompatActivity {

    private TextView ds,d;
    private Button bcancel;
    private Button bconfirm;
    private String dstring,pstring;
    int dat,mon,yr;
    List<Date> dates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_string);

        ds=findViewById(R.id.text_date);
        d=findViewById(R.id.dataparse);
        ds.setText(OcrCaptureActivity.dstring);
        dstring=OcrCaptureActivity.dstring;
        dates =new Parser().parse(dstring).get(0).getDates();
        pstring=dates.get(0).toString();
        d.setText(pstring);

        bcancel=findViewById(R.id.bcancel);
        bcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DateString.this,OcrCaptureActivity.class);
                startActivity(i);
            }
        });

        bconfirm=findViewById(R.id.bconfirm);
        bconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.d=22;
                MainActivity.m=3;
                MainActivity.y=3;
                Intent i=new Intent(DateString.this,MainActivity.class);
                startActivity(i);

            }
        });


    }
}
