package com.example.taskdoodlebluevelu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class ListCartAdapter extends RecyclerView.Adapter<ListCartAdapter.CustomViewHolder> {
    private ArrayList<ModelClass> cartList;
    private DataBaseHelper dataBaseHelper;
    private OnClickListener listener;
    public ListCartAdapter(Context context,ArrayList<ModelClass> cartList,OnClickListener listener){
        this.cartList=cartList;
        dataBaseHelper=new DataBaseHelper(context);
        this.listener=listener;
    }

    public void setCartList(ArrayList<ModelClass> cartList) {
        this.cartList = cartList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
        ModelClass modelClass=cartList.get(position);
        final String name=modelClass.getName();
        final String desc=modelClass.getDesccrption();
        final String rup=modelClass.getRupees();
        holder.name.setText(name);
        holder.descrption.setText(desc);
        holder.itemPrice.setText("$"+modelClass.getRupees());
        holder.itemCount.setText(""+dataBaseHelper.getItemCount(name));
        holder.itemDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count=dataBaseHelper.getItemCount(name);
               if(count>0){
                   dataBaseHelper.insertItem(name,desc,""+(count-1),rup);
                   holder.itemCount.setText(""+dataBaseHelper.getItemCount(name));
               }
               listener.OnItemClick();
            }
        });

        holder.iteminc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count=dataBaseHelper.getItemCount(name);
                dataBaseHelper.insertItem(name,desc,""+(count+1),rup);
                holder.itemCount.setText(""+dataBaseHelper.getItemCount(name));
                listener.OnItemClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView name,descrption,itemCount,itemPrice;
        private AppCompatImageView iteminc,itemDec;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.itemName);
            descrption=itemView.findViewById(R.id.itemDescription);
            itemCount=itemView.findViewById(R.id.itemCount);
            iteminc=itemView.findViewById(R.id.itemAdd);
            itemDec=itemView.findViewById(R.id.itemSub);
            itemPrice=itemView.findViewById(R.id.itemprice);
        }
    }
}
