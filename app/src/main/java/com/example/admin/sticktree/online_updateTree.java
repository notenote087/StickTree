package com.example.admin.sticktree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class online_updateTree extends AppCompatActivity {

    TextView school_name ;
    Button manage_tree , update_tree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_update_tree);

        findID();

        school_name.setText("โรงเรียน "+ getIntent().getExtras().getString("school_name"));

        update_tree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(online_updateTree.this,online_selectTree.class);
                startActivity(intent);
            }
        });

        manage_tree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
            }
        });
    }

    public void findID(){
        school_name = (TextView) findViewById(R.id.school_name);
        manage_tree = (Button) findViewById(R.id.manage_tree);
        update_tree = (Button) findViewById(R.id.update_tree);
    }
}
