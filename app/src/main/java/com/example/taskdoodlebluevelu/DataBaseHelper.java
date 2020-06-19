package com.example.taskdoodlebluevelu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    private final String cardItem="CREATE TABLE IF NOT EXISTS ITEM_TABLE(NAME TEXT,DESCRPTION TEXT,COUNT TEXT,RUPEES TEXT)";
    private final String NAME="NAME";
    private final String DESCRPTION="DESCRPTION";
    private final String COUNT="COUNT";
    private final String RUPEES="RUPEES";
    public DataBaseHelper(@Nullable Context context) {
        super(context, "cart.db", null,2 );
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
     database.execSQL(cardItem);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ITEM_TABLE");
        onCreate(sqLiteDatabase);
    }

    public void insertItem(String name,String desc,String count,String rupees){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(NAME,name);
        values.put(DESCRPTION,desc);
        values.put(COUNT,count);
        values.put(RUPEES,rupees);
        int result=database.update("ITEM_TABLE",values,NAME+"='"+name+"'",null);
        if(result==0)
            database.insert("ITEM_TABLE",null,values);
    }

    public int getItemCount(String name){
        SQLiteDatabase database=this.getReadableDatabase();
        Cursor cursor=database.rawQuery("SELECT * FROM ITEM_TABLE WHERE NAME='"+name+"'",null);
        if(cursor==null || cursor.getCount()==0)
            return 0;
        cursor.moveToFirst();
        String count=cursor.getString(cursor.getColumnIndex(COUNT));
        return Integer.parseInt(count);
    }

    public int getTotalCount(){
        SQLiteDatabase database=this.getReadableDatabase();
        Cursor cursor=database.rawQuery("SELECT * FROM ITEM_TABLE",null);
        if(cursor==null || cursor.getCount()==0)
            return 0;
        cursor.moveToFirst();
        int count=0;
        while(!cursor.isAfterLast()){
            count+=Integer.parseInt(cursor.getString(cursor.getColumnIndex(COUNT)));
            cursor.moveToNext();
        }
        cursor.close();
        return count;
    }

    public float getTotalCost(){
        SQLiteDatabase database=this.getReadableDatabase();
        Cursor cursor=database.rawQuery("SELECT * FROM ITEM_TABLE",null);
        if(cursor==null || cursor.getCount()==0)
            return 0;
        cursor.moveToFirst();
        int count;
        float cost=0;
        while(!cursor.isAfterLast()){
            count=Integer.parseInt(cursor.getString(cursor.getColumnIndex(COUNT)));
            for (int i=0;i<count;i++)
                cost+=Float.parseFloat(cursor.getString(cursor.getColumnIndex(RUPEES)));
            cursor.moveToNext();
        }
        cursor.close();
        return cost;
    }

}
