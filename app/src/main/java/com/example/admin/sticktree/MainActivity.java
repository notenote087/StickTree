package com.example.admin.sticktree;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.admin.sticktree.offline_Database.SQLiteHelper;

public class MainActivity extends AppCompatActivity {

    Button login,offline ;
    SQLiteHelper sqLiteHelper ;
    SQLiteDatabase sqLiteDatabase;
    private static final String DB_NAME = "tree.db";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,login.class);
                startActivity(intent);
            }
        });

        offline = (Button) findViewById(R.id.offline);
        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences prefs = getSharedPreferences("offline_select_school", MODE_PRIVATE);
                String select_school = prefs.getString("select_school", "false");

                switch (select_school){
                    case "true":
                        Intent intent = new Intent(MainActivity.this,offline_scan.class);
                        startActivity(intent);
                        break;
                    case "false":
                        Intent intent2 = new Intent(MainActivity.this,offline_school.class);
                        startActivity(intent2);
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "default", Toast.LENGTH_SHORT).show();
                        break;
                }



               // sqLiteDatabase.close();
            }
        });
     //   sqLiteHelper = new SQLiteHelper(this);

        sqLiteDatabase = openOrCreateDatabase(DB_NAME, MODE_PRIVATE,null);

        SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
        sqLiteHelper.getWritableDatabase();
    }


}
