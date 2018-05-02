package com.example.admin.sticktree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class offline_scan extends AppCompatActivity {

    Button scanqrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_scan);

        scanqrcode = (Button) findViewById(R.id.scanqrcode);

        scanqrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(offline_scan.this,offline_Data.class);
                startActivity(intent);
            }
        });
    }
}
