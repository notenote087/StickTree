package com.example.admin.sticktree;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class online_edit extends AppCompatActivity {

    Button submit , gps ;
    EditText treename,localname,character,lat,lng ;
    TextView treecode ;
    private String TAG = online_edit.class.getSimpleName();
   // private static final String URL = "http://192.168.1.6/api/edit";
    private static final String URL = "http://10.80.45.10/api/edit";

    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;
    protected LocationListener locationListener;
    protected Context context;

    ProgressDialog prg;

    String code,id ;

    private void findID(){
        submit = (Button) findViewById(R.id.submit);
        gps = (Button) findViewById(R.id.gps);
        treecode = (TextView) findViewById(R.id.treecode);
        treename = (EditText) findViewById(R.id.treename);
        localname = (EditText)  findViewById(R.id.localname);
        character = (EditText)  findViewById(R.id.character);
        lat = (EditText)  findViewById(R.id.lat);
        lng = (EditText)  findViewById(R.id.lng);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_edit);

        findID();

        prg = new ProgressDialog(online_edit.this);
        prg.setMessage("รอสักครู่...");
        prg.setCancelable(false); //prg.show();

        Intent intent = getIntent();
        String tree_code =  intent.getStringExtra("treecode");
        treecode.setText("รหัสพรรณไม้ :" + tree_code);
        treename.setText(intent.getStringExtra("treename"));
        localname.setText(intent.getStringExtra("localname"));
        character.setText(intent.getStringExtra("character"));
        lat.setText(intent.getStringExtra("lat"));
        lng.setText(intent.getStringExtra("lng"));

       // code = tree_code;   // sent to web
        id = intent.getStringExtra("id");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(online_edit.this, "Submit", Toast.LENGTH_SHORT).show();
            try{
                prg.show();


                RequestQueue requestQueue = Volley.newRequestQueue(online_edit.this);
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
                                    treecode.setText("รหัสพรรณไม้ :" + j.getString("treecode"));
                                    treename.setText(j.getString("treename"));
                                    localname.setText(j.getString("localname"));
                                    character.setText(j.getString("character"));
                                    lat.setText(j.getString("lat"));
                                    lng.setText(j.getString("lng"));

                                    break;
                                case "false":
                                    Toast.makeText(getApplicationContext(), j.getString("status_meesage"), Toast.LENGTH_SHORT).show();
                                    break;
                            }

                            //Toast.makeText(getApplicationContext(), j.getString("name"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Log.d(TAG, "Failed JSONException\t" + e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        prg.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "Failed Message\t" + error.getMessage());
                        Log.d(TAG, "Failed Message \t" + error.getStackTrace());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {

                        Map<String, String> params = new HashMap<String, String>();

                        //params.put("find","qrcode");
                        params.put("id",id);
                    //    params.put("code",code);
                        params.put("treename", treename.getText().toString());
                        params.put("localname", localname.getText().toString());
                        params.put("character", character.getText().toString());

                        params.put("lat", lat.getText().toString());
                        params.put("lng", lng.getText().toString());

                      //  Toast.makeText(context, "in params", Toast.LENGTH_LONG).show();
                        Log.d(TAG,"Data = " + params);
                        return params;


                    }
                };
                requestQueue.add(request);


            }catch(Exception ex){
                Log.d(TAG, "Failed Message\t" + ex.toString());
                Toast.makeText(context, ex.toString(), Toast.LENGTH_LONG).show();
            }
            }
        });

        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    //Toast.makeText(online_edit.this, "GPS CLICK", Toast.LENGTH_SHORT).show();
                    prg.show();

                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (ActivityCompat.checkSelfPermission(online_edit.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                            (online_edit.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(online_edit.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
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
                            public void onStatusChanged(String s, int i, Bundle bundle) {

                            }

                            @Override
                            public void onProviderEnabled(String s) {

                            }

                            @Override
                            public void onProviderDisabled(String s) {

                            }


                        };
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,  locationListener); //NETWORK_PROVIDER


                }catch(Exception ex){
                    lat.setText(ex.toString());
                    prg.hide();
                   // Log.d("Error BUG ",ex.toString());
                }

            }
        });


    }



}
