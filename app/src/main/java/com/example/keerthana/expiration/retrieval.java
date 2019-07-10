package com.example.keerthana.expiration;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.MissingFormatArgumentException;


public class retrieval extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private ListView listView;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private ImageButton maddBtn;
    public static String name,name2;
     product p;
    List<product> productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieval);

        mAuth= FirebaseAuth.getInstance();

        listView=findViewById(R.id.list);
        productList=new ArrayList<>();
        maddBtn=findViewById(R.id.addBtn);
    }


    @Override
    protected void onStart() {
        super.onStart();

        currentUser=mAuth.getCurrentUser();

        if(currentUser==null)
        {
            Intent authIntent =new Intent(retrieval.this,Login.class);
            startActivity(authIntent);
            finish();
        }
        else
            {
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                name = currentUser.getPhoneNumber();
                DatabaseReference myRef = database.getReference(name);
                myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot productSnapshot: dataSnapshot.getChildren()){
                        product p=productSnapshot.getValue(product.class);
                        productList.add(p);
                    }
                    List<product> proList=new ArrayList<product>(productList);
                    productInfoAdapter InfoAdapter=new productInfoAdapter(retrieval.this,proList);
                    listView.setAdapter(InfoAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            }

            );
                maddBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i= new Intent(retrieval.this, MainActivity.class);
                        startActivity(i);
                    }
                });
            }

    }
    public void showPopup(View v){
        PopupMenu popup =new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }
    @Override
    public boolean onMenuItemClick(MenuItem item){
        switch(item.getItemId()){
            case R.id.item1:
                Toast.makeText(this,"Item 1 clicked",Toast.LENGTH_SHORT).show();
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                name = currentUser.getPhoneNumber();
               // int n=listView.getSelectedItemPosition();
               // long s= listView.getItemIdAtPosition(n);
                //DatabaseReference myRef = database.getReference(name);
                //String n=p.getName();
               // myRef.removeValue();
             // int posi=  productInfoAdapter.pos;
                //product p=productList.get(posi);
                //String n=p.getName();
                DatabaseReference myRef = database.getReference(name);
                myRef.removeValue();
            //    myRef.removeEventListener();
                //myRef.child(name).setValue(null).addOnSuccessListener()

                break;
            case R.id.item2: {
                Toast.makeText(this, "Item 1 clicked", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(retrieval.this,MainActivity.class);
                startActivity(intent);
            }

            default:
                return false;
        }
        return false;
    }
}