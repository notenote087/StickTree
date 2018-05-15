package com.example.admin.sticktree.offline_Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by admin on 13/5/2561.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private final String TAG = getClass().getSimpleName();

    private static final String DB_NAME = "tree.db";
    private static final String TABLE_NAME = "tree_data" ;
    private static final int DB_VERSION = 1;

    //private SQLiteDatabase sqLiteDatabase;

    public SQLiteHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
        Log.d(TAG,"DB CONSTRUC ");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTable = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME +"(id INTEGER PRIMARY KEY AUTOINCREMENT,photo_base64 TEXT ,QRCODE TEXT , TREECODE TEXT ,TREENAME TEXT ,LOCALNAME TEXT , SCIENCENAME TEXT ,CHARACTER TEXT ,TYPETREE TEXT )";
        sqLiteDatabase.execSQL(createTable);

        Log.d(TAG,"TABLE CREATE ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }

    public void add_tree(String name , String photo_base64,String code, String qr , String localname, String sciencename , String character , String typetree){
        SQLiteDatabase db = getWritableDatabase();

        String sql = "INSERT INTO tree_data (TREENAME,TREECODE,QRCODE,photo_base64,SCIENCENAME,LOCALNAME,TYPETREE,CHARACTER) VALUES (?,?,?,?,?,?,?,?)";
        String[] args ={name,code,qr,photo_base64,sciencename,localname,typetree,character};

        db.execSQL(sql,args);
        Log.d(TAG,"DB INSERT ");
    }

    public String[] find_tree(String qr){
        Log.d(TAG,"DB find_tree " );
        String result[] = new String[7];

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
       // String select = "SELECT TREENAME,SCIENCENAME,LOCALNAME,TYPETREE,CHARACTER FROM tree_data" ;
    //    Cursor c = sqLiteDatabase.rawQuery(select,null) ;
      //  String select = "SELECT TREENAME,SCIENCENAME  FROM tree_data " ;
        String select = "SELECT TREENAME,TREECODE,photo_base64,SCIENCENAME,LOCALNAME,TYPETREE,CHARACTER  FROM tree_data WHERE QRCODE =" + "'" + qr +"'"  ;
        Cursor c = sqLiteDatabase.rawQuery(select,null);
        String data = "";

        while (c.moveToNext()){
       //     data += c.getString(0) + " " +c.getString(1)+ " " +c.getString(2)+ " " +c.getString(3)+ " " +c.getString(4);
            data += c.getString(0) + " " +c.getString(1)+" "+c.getString(2) + " " +c.getString(3)+" "+c.getString(4) + " " +c.getString(5)+" "+c.getString(6) ;
            Log.d(TAG,"DB find_tree " + data);
            result[0] = c.getString(0);
            result[1] = c.getString(1);
            result[2] = c.getString(2);
            result[3] = c.getString(3);
            result[4] = c.getString(4);
            result[5] = c.getString(5);
            result[6] = c.getString(6);

            if(!c.isLast()) data += "\n" ; //Log.d(TAG,"DB find_tree Break " );  break   ;
        }

        sqLiteDatabase.close();
        c.close();

        return result;
    }
}
