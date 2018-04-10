package com.example.admin.sticktree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class online_Insert extends AppCompatActivity {
    Button confirm,open_camera , select_photo ,gps ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online__insert);

        findID();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        open_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        select_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    //Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
    //startActivity(intent);

    public void findID(){
        confirm = (Button) findViewById(R.id.confirm);
        open_camera = (Button) findViewById(R.id.open_camera);
        select_photo = (Button) findViewById(R.id.select_photo);
        gps = (Button) findViewById(R.id.gps);
    }
}
