package com.example.admin.sticktree;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.sticktree.AdapterImage.ImageAdapter;
import com.example.admin.sticktree.AdapterImage.ImageController;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.sangcomz.fishbun.define.Define;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class online_update_growth extends AppCompatActivity implements View.OnClickListener{

    private static final String URL = "http://192.168.1.5/api/update_growth";

    Button select_photo,submit;
    EditText detail;
    TextView tree_code,tree_name ;

    ArrayList<Uri> path = new ArrayList<>();
    ImageView imgMain;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ImageAdapter imageAdapter;
    ImageController mainController;

    ProgressDialog prg;
    ArrayList path_photo ;

    String image[];

    String tree_id , name, code ;

    private static final int REQUEST_STORAGE = 1;
    private String TAG = MainActivity.class.getSimpleName();

    private void findID(){
        select_photo = (Button) findViewById(R.id.select_photo);
        submit = (Button) findViewById(R.id.submit);

        detail = (EditText) findViewById(R.id.detail);

        tree_code = (TextView) findViewById(R.id.tree_code);
        tree_name = (TextView) findViewById(R.id.tree_name);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_update_growth);

        findID();
        select_photo.setOnClickListener(this);
        submit.setOnClickListener(this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mainController = new ImageController(this, imgMain);
        imageAdapter = new ImageAdapter(this, path);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(imageAdapter);


        prg = new ProgressDialog(online_update_growth.this);
        prg.setMessage("รอสักครู่...");
        prg.setCancelable(false); //prg.show();

        Intent intent = getIntent();
        tree_id = intent.getStringExtra("tree_id");
        name = intent.getStringExtra("treename");
        code = intent.getStringExtra("treecode");
        Log.d(TAG,"TEST Intent"+ name +"  " + code + " " + tree_id  ) ;
        tree_code.setText("รหัสพรรณไม้ " + code);
        tree_name.setText("ชื่อพรรณไม้ " + name);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.select_photo:
                try {
                    if (ActivityCompat.checkSelfPermission(online_update_growth.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                        ActivityCompat.requestPermissions(online_update_growth.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE);
                        //Toast.makeText(online_add.this, "checkSelfPermission", Toast.LENGTH_SHORT).show();
                    }
                    FishBun.with(online_update_growth.this)
                            .setImageAdapter(new GlideAdapter())
                            .setMaxCount(4)
                            .setMinCount(1)
                            .textOnNothingSelected("กรุณาเลือกรูปอย่าางน้อย 1 รูป")
                            .textOnImagesSelectionLimitReached("เลือกรูปเกินกำหนด")
                            .startAlbum();

                }catch (Exception ex){
                    //lat.setText(ex.toString());
                    Log.d(TAG,"Fail :" + ex.toString());
                }

                break;
            case R.id.submit:
                prg.show();
                if(path_photo != null){ //prg.show();

                    //Log.d(TAG,"select :  in if "  );
                    image = new String[path_photo.size()] ;
                    //  Log.d(TAG,"select : size "+path_photo.size());
                    for(int i = 0 ; i < path_photo.size();i++){
                        Log.d(TAG,"for : " + path_photo.get(i));
                        // getRealPathFromURI(path_photo.get(i));
                        Uri myUri = Uri.parse(String.valueOf(path_photo.get(i)));
                        Log.d(TAG,"for : Uri" + myUri);
                        String res = getRealPathFromURI(myUri);
                        Log.d(TAG,"for : getReal: " + res);

                        Bitmap bm = BitmapFactory.decodeFile(res);
                        String getBase = getStringImage(bm);
                        //  Log.d(TAG,"for : getBase: \n" + getBase);
                        image[i] = getBase;
                        // Bitmap bitmap = decodeBase64(image[i]);
                        // imageview1.setImageBitmap(bitmap);
                    }

                    try{
                        //

                        RequestQueue requestQueue = Volley.newRequestQueue(online_update_growth.this);
                        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                prg.hide();
                                try {
                                    JSONObject j = new JSONObject(response);
                                    String result_status = j.getString("status");

                                    switch(result_status){
                                        case "true":
                                            prg.hide();
                                            Toast.makeText(getApplicationContext(), j.getString("status_meesage"), Toast.LENGTH_SHORT).show();
                                            finish();
                                            break;
                                        case "false":
                                            prg.hide();
                                            Toast.makeText(getApplicationContext(), j.getString("status_meesage"), Toast.LENGTH_SHORT).show();
                                            finish();
                                            break;
                                    }

                                    //Toast.makeText(getApplicationContext(), j.getString("name"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    prg.hide();
                                    Log.d(TAG, "Failed JSONException\t" + e.getMessage());
                                    Toast.makeText(getApplicationContext(), "ผิดพลาด", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                prg.hide();
                                Toast.makeText(online_update_growth.this,"สำเร็จ..", Toast.LENGTH_LONG).show();
                                //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                                Log.d(TAG, "Failed Message onError\t" + error.getMessage());
                                Log.d(TAG, "Failed Message onError \t" + error.getStackTrace().toString());

                                finish();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {

                                Map<String, String> params = new HashMap<String, String>();




                                params.put("detail",detail.getText().toString());
                                params.put("tree_id",tree_id);

                                JSONObject jsonObjectImage = new JSONObject();

                                for(int i = 0 ; i < image.length; i++){
                                    Log.d(TAG,"i value " + i);
                                    try {
                                        jsonObjectImage.put("slot_"+i,image[i]);
                                        // jsonObjectImage.put("slot_"+i, "this is data [" + i + "]");
                                        Log.d(TAG,"Data jsonobject = " + image[i]);

                                    } catch (JSONException e) {
                                        Log.d(TAG,"for : JSON " + e.getMessage());
                                    }

                                }
                                params.put("image",jsonObjectImage.toString());
                                //   params.put("image2",image[0]);
                                //  Toast.makeText(context, "in params", Toast.LENGTH_LONG).show();
                                Log.d(TAG,"Data = " + params);

                                path_photo.clear();
                                image = null ;
                                //jsonObjectImage = null ;

                                return params;


                            }

                        };


                        requestQueue.add(request);


                    }catch(Exception ex){
                        Log.d(TAG, "Failed Message\t" + ex.toString());
                      //  Toast.makeText(online_update_growth.this, ex.toString(), Toast.LENGTH_LONG).show();
                        Toast.makeText(online_update_growth.this,"สำเร็จ..", Toast.LENGTH_LONG).show();
                        //  finish();
                    }


                }else {
                    Toast.makeText(this, "กรุณาเลือกรูปภาพอย่างน้อย 1 รูป ", Toast.LENGTH_SHORT).show();
                }

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
                   // Toast.makeText(this, "มีบางอย่างผิดพลาด", Toast.LENGTH_SHORT).show();
                    break;
                }
        }
    }


    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();


        return path;
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }
}
