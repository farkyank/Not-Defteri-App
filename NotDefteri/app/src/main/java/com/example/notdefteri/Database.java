package com.example.notdefteri;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.notdefteri.model.Not;

import java.lang.reflect.Array;
import java.util.ArrayList;

//20190305011 - ABDULHAMİT TUNÇ
//SQLite Database bağlama kalıtım(extends) yaptık
public class Database extends SQLiteOpenHelper {

    final static String DATABASE_NAME="Notlar";
    final static int  DATABASE_VERSION=1;
    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Database Table oluşturma, tablo başlıkları
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOT="CREATE TABLE Notlar ("+"id INTEGER PRIMARY KEY AUTOINCREMENT , "+
                "baslik VARCHAR , "+
                "notMetin VARCHAR , "+
                "webUrl VARCHAR , "+
                "imageUrl VARCHAR ," +
                "color VARCHAR , "+
                "tarih VARCHAR )";

        db.execSQL(CREATE_NOT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXITS Notlar");
    }

    //Tabloya INSERT INTO, atama yapma
    public void YeniNot(Not not){
        SQLiteDatabase db=this.getWritableDatabase();
        String INSERT_NOT="INSERT INTO Notlar(baslik,notMetin,webUrl,imageUrl,color,tarih)"+
                "VALUES('"+not.getBaslik()+"','"+not.getNotMetin()+"','"+not.getWebUrl()+"','"+not.getImageUrl()+"','"+not.getRenk()+"','"+not.getTarih()+"')";

        db.execSQL(INSERT_NOT);

    }

    public ArrayList<Not> getNotlarim(){
        SQLiteDatabase db=this.getReadableDatabase();
        String SELECT_NOTLAR="SELECT * FROM Notlar ORDER BY id DESC";

        Cursor cursor=db.rawQuery(SELECT_NOTLAR, null);
        ArrayList<Not> notlar = new ArrayList<>();

        if (cursor!=null){
            cursor.moveToFirst();
            try{//Do While kullanıldığı için - database boş ise hata almamak için .
                do{
                    Not not = new Not();
                    not.setId(cursor.getInt(0));
                    not.setBaslik(cursor.getString(1));
                    not.setNotMetin(cursor.getString(2));
                    not.setWebUrl(cursor.getString(3));
                    not.setImageUrl(cursor.getString(4));
                    not.setRenk(cursor.getString(5));
                    not.setTarih(cursor.getString(6));
                    notlar.add(not);
                }while (cursor.moveToNext());
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return notlar;
    }
    //NOT GUNCELLEME
    public void NotGuncelle(Not not){
        SQLiteDatabase db=this.getWritableDatabase();
        String UPDATE_NOT="UPDATE Notlar SET baslik='"
                +not.getBaslik()
                +"', notMetin='"+not.getNotMetin()
                +"' , webUrl='"+not.getWebUrl()
                +"' , imageUrl='"+not.getImageUrl()
                +"' , color='"+not.getRenk()
                +"', tarih='"+not.getTarih()+"' WHERE id="+not.getId();
        db.execSQL(UPDATE_NOT);
        db.close();
    }
    //NOT SİLME
    public void NotSil(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String DELETE_NOT="DELETE FROM Notlar WHERE id="+id;
        db.execSQL(DELETE_NOT);
        db.close();


    }
}
