package com.lastfidler.myapKLK.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.lastfidler.myapKLK.R;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

import com.lastfidler.myapKLK.list.list;

public class database extends SQLiteAssetHelper {
    public static String name = "msgDb_13533.db";
    public static int version = 1;
    Context mcontext;

    public database(Context context) {
        super(context, name, null, version);
        this.mcontext = context;
    }

    public ArrayList gettitle() {
        try {
            SQLiteDatabase db = getReadableDatabase();
            ArrayList arrayList = new ArrayList();
            Cursor cursor = db.rawQuery("select * from MSG_CAT", null);
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                arrayList.add(cursor.getString(cursor.getColumnIndex("NAME")));
                cursor.moveToNext();

            }
            return arrayList;
        } catch (Exception e) {
            Toast.makeText(mcontext, String.valueOf(e), Toast.LENGTH_SHORT).show();
            return null;
        }

    }

    public ArrayList gettext(int id) {
        try {

            SQLiteDatabase db = getReadableDatabase();
          ArrayList arrayList = new ArrayList<>();
            Cursor cursor = db.rawQuery("select * from MESSAGES where MSG_CAT ='" + id + "'", null);
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                arrayList.add(cursor.getString(cursor.getColumnIndex("MESSAGE")));
                cursor.moveToNext();
            }
            return arrayList;
        } catch (Exception e) {
            Toast.makeText(mcontext, String.valueOf(e), Toast.LENGTH_SHORT).show();
            return null;
        }

    }
    public ArrayList randome(int id) {
        try {

            SQLiteDatabase db = getReadableDatabase();
            ArrayList arrayList = new ArrayList();
            for (int i = 0 ; i<100;i++)
            {


            Cursor cursor = db.rawQuery("select * from MESSAGES where ID ='" + id + "'", null);
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                arrayList.add(cursor.getString(cursor.getColumnIndex("MESSAGE")));
                cursor.moveToNext();
            }
            id++;
            }
            return arrayList;
        } catch (Exception e) {
            Toast.makeText(mcontext, String.valueOf(e), Toast.LENGTH_SHORT).show();
            return null;
        }

    }

//not working geting images//
  /*  public Bitmap getimages(Integer id)
    {
        try
        {
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap image = null;
        Cursor cursor = db.rawQuery("Select * from MSG_CAT where ID = ? "+new String[] {String.valueOf(id)},null);
        if (cursor.moveToFirst()) {
            byte[] bt = cursor.getBlob(cursor.getColumnIndex("images"));
           image = BitmapFactory.decodeByteArray(bt,0,bt.length);

        }
        return image;

        } catch (Exception e) {
            Toast.makeText(mcontext, String.valueOf(e), Toast.LENGTH_LONG).show();
            return null;
        }
    }*/

}
