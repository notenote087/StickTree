package com.example.admin.sticktree;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class offline_Data extends AppCompatActivity {

    ImageView imageview ;
    TextView tree_name_offline ,localname_offline,character_offline,typetree_offline,sciencename_offline;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_data);
        findID();
//add_tree(String name , String photo_base64,String code, String qr , String localname, String sciencename , String character , String typetree)
        // Bitmap bitmap = decodeBase64(image[i]);
        // imageview1.setImageBitmap(bitmap);

        /*
           intent.putExtra("TREENAME",data[0]);
        intent.putExtra("TREECODE",data[1]);
        intent.putExtra("photo_base64",data[2]);
        intent.putExtra("SCIENCENAME",data[3]);
        intent.putExtra("LOCALNAME",data[4]);
        intent.putExtra("TYPETREE",data[5]);
        intent.putExtra("CHARACTER",data[6]);
        * */
        Intent intent = getIntent();

        tree_name_offline.setText("ชื่อพรรณไม้ :" + intent.getStringExtra("TREENAME"));
        localname_offline.setText("ชื่อท้องถิ่น :" +intent.getStringExtra("LOCALNAME"));
        character_offline.setText("ลักษณะ :" +intent.getStringExtra("CHARACTER"));
        typetree_offline.setText("ชนิดพรรณไม้ :" +intent.getStringExtra("TYPETREE"));
        sciencename_offline.setText("ชื่อวิทยาศาสตร์ :" +intent.getStringExtra("SCIENCENAME"));

         Bitmap bitmap = decodeBase64(intent.getStringExtra("photo_base64"));
        imageview.setImageBitmap(bitmap);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }


    private void findID(){
        imageview = findViewById(R.id.imageView1);
        tree_name_offline = findViewById(R.id.tree_name_offline);
        localname_offline= findViewById(R.id.localname_offline);
        character_offline = findViewById(R.id.character_offline);
        typetree_offline = findViewById(R.id.typetree_offline);
        sciencename_offline = findViewById(R.id.sciencename_offline);
        back = findViewById(R.id.back);



    }
}
