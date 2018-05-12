package com.example.admin.sticktree;

import android.*;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.Result;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class online_findqrcode_updategrowth extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private String TAG = MainActivity.class.getSimpleName();
    private static final String URL = "http://192.168.1.5/api/find_tree";
    ProgressDialog prg ;

    private ZXingScannerView mScannerView;
    private static final int REQUEST_CAMERA = 1;

    String  result_scan ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_findqrcode_updategrowth);

        prg = new ProgressDialog(online_findqrcode_updategrowth.this);
        prg.setMessage("รอสักครู่...");
        prg.setCancelable(false); //prg.show();


        if (ActivityCompat.checkSelfPermission(online_findqrcode_updategrowth.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(online_findqrcode_updategrowth.this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
        try{
            if (ActivityCompat.checkSelfPermission(online_findqrcode_updategrowth.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ) {

                ActivityCompat.requestPermissions(online_findqrcode_updategrowth.this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA);
            }
            else{
                mScannerView = new ZXingScannerView(getApplicationContext());
                setContentView(mScannerView);
                mScannerView.setResultHandler(online_findqrcode_updategrowth.this);
                mScannerView.startCamera();
            }
            //Toast.makeText(online_edit_find.this, "click", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(online_findqrcode_updategrowth.this, e.toString(), Toast.LENGTH_LONG).show();
            Log.i("Error BUG ",e.toString());

        }

    }

    @Override
    public void handleResult(Result result) {

        result_scan = result.getText();
      //  Toast.makeText(online_findqrcode_updategrowth.this ,  result_scan = result.getText(), Toast.LENGTH_SHORT).show();
        // Fix issue OpenQR Return don't discover data and Onclick Don't work  ///

        mScannerView.stopCamera();
        setContentView(R.layout.online_findqrcode_updategrowth);
        prg.show();

        RequestQueue requestQueue = Volley.newRequestQueue(online_findqrcode_updategrowth.this);
        //  JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>(){
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            //  JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(String response) {
                // Log.d(TAG, response.toString());
                try {
                    prg.hide();
                    JSONObject j = new JSONObject(response);
                    String result_status = j.getString("status");
                    String result_message = j.getString("status_meesage");
                    switch (result_status) {
                        case "true":
                            prg.hide();
                            Toast.makeText(getApplicationContext(), result_message, Toast.LENGTH_LONG).show();
                            // Toast.makeText(getApplicationContext(), j.getString("treecode"), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(online_findqrcode_updategrowth.this, online_update_growth.class);
                            intent.putExtra("treecode", j.getString("treecode"));
                            intent.putExtra("treename",j.getString("treename"));
                           // intent.putExtra("typetree",j.getString("typetree"));
                            intent.putExtra("tree_id" , j.getString("id"));
                     /*       intent.putExtra("localname",j.getString("localname"));
                            intent.putExtra("character",j.getString("character"));
                            intent.putExtra("lat",j.getString("lat"));
                            intent.putExtra("lng",j.getString("lng"));
                            intent.putExtra("id",j.getString("id"));*/

                            startActivity(intent);
                            finish();
                            break;
                        case "false":
                            prg.hide();
                            Toast.makeText(getApplicationContext(), result_message, Toast.LENGTH_LONG).show();

                            break;
                    }
                    //  for (int i = 0 ; i<myJsonArray.length(); i++) {
                    //     JSONObject json_data = myJsonArray.getJSONObject(i);
                    //  email.setText("-------- "+json_data.getString("tree_name"));
                    //      email.setText("-------- "+json_data.getJSONObject("school").getString("school_name"));

                } catch (Exception e) {
                    // e.printStackTrace();
                    prg.hide();
                    Toast.makeText(online_findqrcode_updategrowth.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prg.hide();
                VolleyLog.d(TAG, error.toString());
                Toast.makeText(online_findqrcode_updategrowth.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("find","qrcode");
                params.put("qrcode_photo", result_scan);


                return params;


            }
        };
        requestQueue.add(request);



    }
}
