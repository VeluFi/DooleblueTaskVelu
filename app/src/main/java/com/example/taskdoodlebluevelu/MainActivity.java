package com.example.taskdoodlebluevelu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnClickListener {


    private RecyclerView recyclerView;
    private AppCompatButton viewCart;
    private DataBaseHelper dataBaseHelper;


    private ArrayList<ModelClass> loadDate() {
        ArrayList<ModelClass> list=new ArrayList<>();
        for(int i=1;i<=7;i++){
            ModelClass modelClass=new ModelClass();
            modelClass.setName("Item "+i);
            modelClass.setDesccrption("Desc "+i);
            modelClass.setRupees("7");
            list.add(modelClass);
        }
        return list;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBaseHelper=new DataBaseHelper(this);
        viewCart=findViewById(R.id.viewCartItems);
        recyclerView=findViewById(R.id.recyclerViewitems);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ListCartAdapter cartAdapter=new ListCartAdapter(this,loadDate(),this);
        recyclerView.setAdapter(cartAdapter);

        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,ReviewActivity.class);
                startActivity(i);
            }
        });

    }


    @Override
    public void OnItemClick() {
        int count=dataBaseHelper.getTotalCount();
        viewCart.setText("VIEW CART ("+count+" ITEMS)");
    }

    @Override
    protected void onResume() {
        super.onResume();
        int count=dataBaseHelper.getTotalCount();
        viewCart.setText("VIEW CART ("+count+" ITEMS)");
    }
}