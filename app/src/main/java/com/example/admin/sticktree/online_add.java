package com.example.admin.sticktree;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class online_add extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {


    Button gps,select_photo,submit;
    EditText tree_name , local_name ,character, lat,lng;
    TextView photo_tree_tv ;
    ImageView imageview1 ;
    Spinner spinner;

    ArrayList<Uri> path = new ArrayList<>();
    ImageView imgMain;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ImageAdapter imageAdapter;
    ImageController mainController;

    ArrayList path_photo ;

    private static final int REQUEST_STORAGE = 1;
    private String TAG = MainActivity.class.getSimpleName();
   // private static final String URL = "http://192.168.1.5/api/add";
  //  private static final String URL2 = "http://192.168.1.5/api/type_tree";
    private static final String URL = "http://10.80.45.10/api/add";
    private static final String URL2 = "http://10.80.45.10/api/type_tree";

    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;
    protected LocationListener locationListener;
    protected Context context;


    ProgressDialog prg;
    String image[];

    String school_id ;

    String typetree_name = null;




    private void findID(){
        tree_name = (EditText) findViewById(R.id.tree_name);
        local_name = (EditText) findViewById(R.id.local_name);
        character = (EditText) findViewById(R.id.charac_ter);
        lng = (EditText) findViewById(R.id.lng);
        lat = (EditText) findViewById(R.id.lat);
        submit = (Button) findViewById(R.id.submit);
        select_photo = (Button) findViewById(R.id.select_photo);
        gps = (Button) findViewById(R.id.gps);
        photo_tree_tv =(TextView) findViewById(R.id.photo_tree_tv);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        imageview1 = (ImageView) findViewById(R.id.imageView1);

        spinner = (Spinner) findViewById(R.id.spinner);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_add);

        String image[] ;

        prg = new ProgressDialog(online_add.this);
        prg.setMessage("รอสักครู่...");
        prg.setCancelable(false); //prg.show();

        findID();
        select_photo.setOnClickListener(this);
        gps.setOnClickListener(this);
        submit.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);

        //imgMain = (ImageView) findViewById(R.id.img_main);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mainController = new ImageController(this, imgMain);
        imageAdapter = new ImageAdapter(this, path);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(imageAdapter);

       // type_tree_query();

        SharedPreferences prefs = getSharedPreferences("login", MODE_PRIVATE);
        school_id = prefs.getString("school_id", null);

        type_tree_query();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.gps:

                try
                {
                    //Toast.makeText(online_edit.this, "GPS CLICK", Toast.LENGTH_SHORT).show();
                    prg.show();
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (ActivityCompat.checkSelfPermission(online_add.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                            (online_add.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(online_add.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                        // Toast.makeText(online_edit.this, "checkSelfPermission", Toast.LENGTH_SHORT).show();
                    }
                    locationListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            prg.hide();
                            // Toast.makeText(online_edit.this, "onLocationChanged", Toast.LENGTH_SHORT).show();
                            lat.setText("" + location.getLatitude());
                            lng.setText("" + location.getLongitude());

                        }
                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {}

                        @Override
                        public void onProviderEnabled(String s) { }

                        @Override
                        public void onProviderDisabled(String s) {}
                    };
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,  locationListener); //NETWORK_PROVIDER


                }catch(Exception ex){
                    lat.setText(ex.toString());
                    prg.hide();
                    // Log.d("Error BUG ",ex.toString());
                }

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
                    if(typetree_name != null){


                    prg.show();
                  /*  Log.d(TAG,"select :  in if "+path_photo  );
                    Log.d(TAG,"select : "+path_photo.toString());*/

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
             //           prg.show();

                        RequestQueue requestQueue = Volley.newRequestQueue(online_add.this);
                        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                prg.hide();
                                try {
                                    JSONObject j = new JSONObject(response);
                                    String result_status = j.getString("status");
                                    Toast.makeText(getApplicationContext(), j.getString("status_meesage"), Toast.LENGTH_SHORT).show();
                                    switch(result_status){
                                        case "true":
                                            prg.hide();
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
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                prg.hide();
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                                Log.d(TAG, "Failed Message onError\t" + error.getMessage());
                                Log.d(TAG, "Failed Message onError \t" + error.getStackTrace().toString());

                                finish();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {

                                Map<String, String> params = new HashMap<String, String>();


                                params.put("treename", tree_name.getText().toString());
                                params.put("localname", local_name.getText().toString());
                                params.put("character", character.getText().toString());

                                params.put("lat", lat.getText().toString());
                                params.put("lng", lng.getText().toString());

                                params.put("school_id",school_id);
                                params.put("typetree_name",typetree_name);

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
                        Toast.makeText(context, ex.toString(), Toast.LENGTH_LONG).show();
                      //  finish();
                    }



                    }else {
                        prg.hide();
                        Toast.makeText(this, "กรุณาเลือกชนิดพรรณไม้", Toast.LENGTH_SHORT).show();
                    }
                }else {
                   // prg.hide();
                    Toast.makeText(this, "กรุณาเลือกรูปภาพอย่างน้อย 1 รูป ", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                prg.hide();
                finish();
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
                  //  Log.d(TAG,"select :" + data.getParcelableArrayListExtra(Define.INTENT_PATH));
                   // Log.d(TAG,"select :" + path_photo);
                 //   Bitmap myBitmap = BitmapFactory.decodeFile(String.valueOf(data.getParcelableArrayListExtra(Define.INTENT_PATH)));
                    imageAdapter.getPath(path_photo);

                    // you can get an image path(ArrayList<Uri>) on 0.6.2 and later
                    break;
                }
                else {
                    //Toast.makeText(this, "คุณต้องเลือกรูปอย่างน้อย 1 รูป", Toast.LENGTH_SHORT).show();
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

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public void type_tree_query(){
        try{
            prg.show();
            findID();


            RequestQueue requestQueue = Volley.newRequestQueue(online_add.this);
            StringRequest request = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    prg.hide();
                    try {
                        JSONObject j = new JSONObject(response);
                        String result_status = j.getString("status");
                    //    Toast.makeText(getApplicationContext(), j.getString("status_meesage"), Toast.LENGTH_SHORT).show();
                        switch(result_status){
                            case "true":
                                prg.hide();

                                List<String> categories = new ArrayList<String>();
                              //  JSONArray jsonArray = new JSONArray(response);
                           //     Log.d(TAG,"JSON TEST "  + j.getJSONArray("data").getJSONObject(0).getString("name"));
                               // Log.d(TAG,"JSON TEST "  + j.getJSONObject("data").getString("name"));
                                Log.d(TAG,"JSON TEST "  + j.getString("data"));
                                JSONArray jsonArray = new JSONArray(j.getString("data"));
                                Log.d(TAG,"JSON TEST "  + jsonArray);
                                  for (int i = 0 ; i<jsonArray.length(); i++) {
                                           JSONObject json_data = jsonArray.getJSONObject(i);
                                      Log.d(TAG,"JSON TEST " +json_data.getString("name"));


                                      categories.add(json_data.getString("name"));
                                  }
                                //  email.setText("-------- "+json_data.getString("tree_name"));
                                //      email.setText("-------- "+json_data.getJSONObject("school").getString("school_name"));

                                // Creating adapter for spinner
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(online_add.this, android.R.layout.simple_spinner_item, categories);
                                // Drop down layout style - list view with radio button
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                // attaching data adapter to spinner
                                spinner.setAdapter(dataAdapter);


                               // finish();
                                break;
                            case "false":
                                prg.hide();
                                Toast.makeText(getApplicationContext(), j.getString("status_meesage"), Toast.LENGTH_SHORT).show();
                               // finish();
                                break;
                        }

                        //Toast.makeText(getApplicationContext(), j.getString("name"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        prg.hide();
                        Log.d(TAG, "Failed JSONException\t" + e.getMessage());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    prg.hide();

                    Log.d(TAG, "Failed Message onError\t" + error.getMessage());
                    Log.d(TAG, "Failed Message onError \t" + error.getStackTrace().toString());

                  //  finish();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("data","");
                    return params;


                }

            };


            requestQueue.add(request);


        }catch(Exception ex){
            Log.d(TAG, "Failed Message\t" + ex.toString());
            Toast.makeText(online_add.this, ex.toString(), Toast.LENGTH_LONG).show();
            //  finish();
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        typetree_name = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(online_add.this, typetree_name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
