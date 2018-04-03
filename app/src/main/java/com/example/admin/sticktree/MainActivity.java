package com.example.admin.sticktree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button login , offline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findID();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,online_login.class);

             ///   d.finish();
                //  d.onDestroy(); // สัส
                startActivity(intent);

            }
        });

        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,offline_school.class);
                startActivity(intent);
            }
        });
    }
    public void findID(){
            login = (Button) findViewById(R.id.login);
            offline = (Button) findViewById(R.id.offline);
    }
}
