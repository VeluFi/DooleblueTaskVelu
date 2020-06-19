package com.example.taskdoodlebluevelu;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewActivity extends AppCompatActivity implements OnClickListener, View.OnClickListener {

    private AppCompatTextView totalCost,showHideMore;
    private RecyclerView reviewItem;
    private AppCompatButton placeOrder;
    RadioGroup radioGroup;
    RadioButton radioButton_dinein,radioButton_takway,radioButton;
    private ListCartAdapter adapter=null;
    private DataBaseHelper dataBaseHelper;
    private int count=0;
    private ArrayList<ModelClass> cartlist,classArrayList;
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
        setContentView(R.layout.review_order);
        dataBaseHelper=new DataBaseHelper(this);
        classArrayList=loadDate();
        placeOrder=findViewById(R.id.placeOrder);
        placeOrder.setOnClickListener(this);
        totalCost=findViewById(R.id.totalCost);
        radioGroup = findViewById(R.id.radio_grp);
       // radioButton_dinein = findViewById(R.id.radio_dinein);
       // radioButton_takway = findViewById(R.id.radio_takeway);
        showHideMore=findViewById(R.id.showHideMore);
        showHideMore.setOnClickListener(this);
        reviewItem=findViewById(R.id.recyclerViewReviewItem);
        reviewItem.setHasFixedSize(false);
        reviewItem.setLayoutManager(new LinearLayoutManager(this));

      /*  if(radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radioGroup.getCheckedRadioButtonId() == R.id.radio_dinein){
                    radioButton_takway.setChecked(false);
                }else {
                    radioButton_takway.setChecked(true);
                }
            }
        });*/



    }

    @Override
    public void OnItemClick() {
        totalCost.setText("$ "+dataBaseHelper.getTotalCost());
    }

    private void updateRecyclerView(int count){
        this.count=count;
        cartlist=new ArrayList<>();
        for(int i=0;i<count && i<classArrayList.size();i++){
            cartlist.add(classArrayList.get(i));
        }
        if(adapter==null){
            adapter=new ListCartAdapter(this,cartlist,this);
            reviewItem.setAdapter(adapter);
        }
        else{
           adapter.setCartList(cartlist);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.placeOrder:
                radioButton=findViewById(radioGroup.getCheckedRadioButtonId());
                break;
            case R.id.showHideMore:
                updateRecyclerView(count+2);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateRecyclerView(count+2);
        totalCost.setText("$ "+dataBaseHelper.getTotalCost());
    }
}