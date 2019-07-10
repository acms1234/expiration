package com.example.keerthana.expiration;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.renderscript.Sampler;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.keerthana.expiration.retrieval;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{
    private int Gallery_intent=2;
    static int i;
    public int eday,emonth,eyear,op;
    public static int d=0,m=0,y=0;
    public int year,month,day;

    private NotificationManagerCompat notificationManager;

    private DatePickerDialog.OnDateSetListener pDateSetListener;

    private TextView p_date;
    public EditText p_name;

    private Button p_save,p_retrieve,bocr;
    private ImageButton calImg;

    static String name;

    private Calendar c;

    product p;

    Spinner spinner;
    String pdate,option1[]={"1","2","3","4","5","6"};
    ArrayAdapter<String> adapter1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calImg=(ImageButton)findViewById(R.id.cal);
        bocr = (Button)findViewById(R.id.ocr);
        p_name = findViewById(R.id.name);
        p_save = findViewById(R.id.save);
        p_date=findViewById(R.id.date);
        p=new product();
        spinner=findViewById(R.id.spinner1);
        p_retrieve=findViewById(R.id.retrieve);
        adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,option1);
        spinner.setAdapter(adapter1);

        if(d==0 && m==0 && y==0) {
            p_date.setText("DD-MM-YYYY");
        }
        else {
            eday=d;
            emonth=m;
            eyear=y;
            String s=Integer.toString(d)+"-"+String.valueOf(m)+"-"+String.valueOf(y);
            p_date.setText(s);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                                          {
                                              @Override
                                              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                                              {
                                                  switch(i)
                                                  {
                                                      case 0:op=1;
                                                          break;
                                                      case 1:op=2;
                                                          break;
                                                      case 2:op=3;
                                                          break;
                                                      case 3:op=4;
                                                          break;
                                                      case 4:op=5;
                                                          break;
                                                      case 5:op=6;
                                                          break;
                                                      case 6:op=7;
                                                          break;

                                                  }

                                              }

                                              @Override
                                              public void onNothingSelected(AdapterView<?> adapterView)
                                              {

                                              }
                                          }
        );

        bocr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,com.example.keerthana.expiration.OcrCaptureActivity.class);
                startActivity(i);
            }
        });

        p_retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,retrieval.class);
                startActivity(i);
            }
        });


        //CHOOSING DATE OF EXPIRY
        //CALENDER
        calImg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Calendar cal=Calendar.getInstance();
                 year=cal.get(Calendar.YEAR);
                 month=cal.get(Calendar.MONTH);
                 day=cal.get(Calendar.DATE);

                DatePickerDialog dialog=new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        pDateSetListener,
                        year,month,day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });


        //STORING DATE IN STRING AND DISPLAYING
        pDateSetListener=new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                String date=day+"-"+(month+1)+"-"+year;
                eday=day;
                emonth=month;
                eyear=year;
                p_date.setText(date);


            }

        };



        //SAVING DATA TO FIREBASE
        p_save.setOnClickListener(new View.OnClickListener()
                                  {
                                      @Override
                                      public void onClick(View view)
                                      {
                                          FirebaseDatabase database=FirebaseDatabase.getInstance();
                                          DatabaseReference myRef = database.getReference(retrieval.name);
                                          name=p_name.getText().toString();
                                          pdate=p_date.getText().toString();

                                          if(name.length()==0)
                                              p_name.setError("Required");
                                          if(pdate.equals("DD-MM-YYYY") || pdate.length()==0)
                                              p_date.setError("Required");
                                          if(name.length()!=0 && pdate.length()!=0 && !pdate.equals("DD-MM-YYYY"))
                                          {
                                              p.setName(name);
                                              p.setCdate(pdate);
                                              p.setOption(op);

                                              //myRef.child("product" + (++i)).setValue(p);
                                              myRef.child(name).setValue(p);

                                              //myRef.push(p);
                                              Toast.makeText(MainActivity.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();

                                              c=Calendar.getInstance();
                                              c.set(Calendar.DAY_OF_MONTH,eday);
                                              c.set(Calendar.MONTH,emonth);
                                              c.set(Calendar.YEAR,eyear);
                                              c.add(Calendar.DATE,-op);

                                              Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
                                              PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100+i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                              AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                              alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


                                              Intent i=new Intent(MainActivity.this,retrieval.class);
                                              startActivity(i);
                                          }
                                      }
                                  }
        );


        p_retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this,retrieval.class);
                startActivity(i);
            }
        });

    }

}


