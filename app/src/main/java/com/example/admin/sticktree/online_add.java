package com.example.admin.sticktree;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.sticktree.AdapterImage.ImageAdapter;
import com.example.admin.sticktree.AdapterImage.ImageController;

import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.sangcomz.fishbun.define.Define;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class online_add extends AppCompatActivity implements View.OnClickListener {


    Button gps,select_photo,submit;
    EditText tree_name , localname ,character, lat,lng;
    TextView photo_tree_tv ;

    ArrayList<Uri> path = new ArrayList<>();
    ImageView imgMain;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ImageAdapter imageAdapter;
    ImageController mainController;

    ArrayList path_photo ;

    private static final int REQUEST_STORAGE = 1;
    private String TAG = MainActivity.class.getSimpleName();

    private void findID(){
        tree_name = (EditText) findViewById(R.id.tree_name);
        localname = (EditText) findViewById(R.id.localname);
        character = (EditText) findViewById(R.id.character);
        lng = (EditText) findViewById(R.id.lng);
        lat = (EditText) findViewById(R.id.lat);
        submit = (Button) findViewById(R.id.submit);
        select_photo = (Button) findViewById(R.id.select_photo);
        gps = (Button) findViewById(R.id.gps);
        photo_tree_tv =(TextView) findViewById(R.id.photo_tree_tv);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

    }

    public void setCountSelectPhoto(int size){
        findID();
        photo_tree_tv.setText("รูปภาพพรรณไม้ : เลือกแล้วจำนวน " + size + " รูป");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_add);

        findID();
        select_photo.setOnClickListener(this);
        submit.setOnClickListener(this);

        //imgMain = (ImageView) findViewById(R.id.img_main);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mainController = new ImageController(this, imgMain);
        imageAdapter = new ImageAdapter(this, path);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(imageAdapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.gps:

                break;
            case R.id.select_photo:

                try {

                    if (ActivityCompat.checkSelfPermission(online_add.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                        ActivityCompat.requestPermissions(online_add.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE);
                        //Toast.makeText(online_add.this, "checkSelfPermission", Toast.LENGTH_SHORT).show();
                    }

                    /*Intent imageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(imageIntent, 11);*/
                  /*  Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);*/

                    FishBun.with(online_add.this)
                            .setImageAdapter(new GlideAdapter())
                            .setMaxCount(4)
                            .setMinCount(1)
                            .textOnNothingSelected("กรุณาเลือกรูปอย่าางน้อย 1 รูป")
                            .startAlbum();

                }catch (Exception ex){
                    //lat.setText(ex.toString());
                    Log.d(TAG,"Fail :" + ex.toString());
                }

                break;
            case R.id.submit:

                if(path_photo != null){
                    Log.d(TAG,"select :  in if "  );
                }else {
                    Toast.makeText(this, "กรุณาเลือกรูปภาพอย่างน้อย 1 รูป ", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        switch (requestCode) {
            case Define.ALBUM_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    // path = data.getStringArrayListExtra(Define.INTENT_PATH);
                    // you can get an image path(ArrayList<String>) on <0.6.2
                    path_photo = data.getParcelableArrayListExtra(Define.INTENT_PATH);
                    Log.d(TAG,"select :" + data.getParcelableArrayListExtra(Define.INTENT_PATH));
                    Log.d(TAG,"select :" + path_photo);
                 //   Bitmap myBitmap = BitmapFactory.decodeFile(String.valueOf(data.getParcelableArrayListExtra(Define.INTENT_PATH)));
                    imageAdapter.getPath(path_photo);

                    // you can get an image path(ArrayList<Uri>) on 0.6.2 and later
                    break;
                }
                else {
                    Toast.makeText(this, "มีบางอย่างผิดพลาด", Toast.LENGTH_SHORT).show();
                    break;
                }
        }
    }
}
