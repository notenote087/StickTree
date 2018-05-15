package com.example.admin.sticktree;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.sticktree.offline_Database.SQLiteHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class offline_school extends AppCompatActivity implements  View.OnClickListener,AdapterView.OnItemSelectedListener{

    Button submit ;
    Spinner spinner;

    private String TAG = MainActivity.class.getSimpleName();
  //  private static final String URL = "http://192.168.1.5/api/find_school";
   // private static final String URL2 = "http://192.168.1.5/api/get_tree";
    private static final String URL = "http://10.80.45.10/api/find_school";
    private static final String URL2 = "http://10.80.45.10/api/get_tree";

    String school_name_select = null;

    protected Context context;
    ProgressDialog prg;

    SQLiteHelper sqLiteHelper ;
    SQLiteDatabase sqLiteDatabase;
    private static final String DB_NAME = "tree.db";

    private void findID(){
        spinner = (Spinner) findViewById(R.id.spinner_school);
        submit = (Button) findViewById(R.id.submit_offline_school);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_school);

        findID();



        prg = new ProgressDialog(offline_school.this);
        prg.setMessage("รอสักครู่...");
        prg.setCancelable(false); //prg.show();



        submit.setOnClickListener(this) ;
        spinner.setOnItemSelectedListener(this);

        school_query();

     /*   sqLiteDatabase = openOrCreateDatabase(DB_NAME, MODE_PRIVATE,null);

        SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
        sqLiteHelper.getWritableDatabase();
        for(int i = 0 ; i<= 10 ; i++){
            sqLiteHelper.add_tree("tee_number " + i ,"BASE64","K2","qr_code","localname","science","char","type");
        }

        sqLiteHelper.close();


        sqLiteHelper.getReadableDatabase();
        sqLiteHelper.find_tree();*/
    }

    public void school_query(){
        try{
            findID();
            prg.show();

            RequestQueue requestQueue = Volley.newRequestQueue(offline_school.this);
            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    prg.hide();
                    try {
                        JSONObject j = new JSONObject(response);
                        String result_status = j.getString("status");
                       // Toast.makeText(getApplicationContext(), j.getString("status"), Toast.LENGTH_SHORT).show();
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
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(offline_school.this, android.R.layout.simple_spinner_item, categories);
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

            //  finish();
        }

    }


    public void get_tree(final String school_name){
      //  Toast.makeText(getApplicationContext(), school_name, Toast.LENGTH_SHORT).show();

        try{
            findID();
            prg.show();

            RequestQueue requestQueue = Volley.newRequestQueue(offline_school.this);
            StringRequest request = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    prg.hide();
                    try {
                        JSONObject j = new JSONObject(response);
                        String result_status = j.getString("status");
                       // Toast.makeText(getApplicationContext(), j.getString("status"), Toast.LENGTH_SHORT).show();
                        switch(result_status){
                            case "true":
                                prg.hide();

                                sqLiteDatabase = openOrCreateDatabase(DB_NAME, MODE_PRIVATE,null);
                                SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
                                sqLiteHelper.getWritableDatabase();

                                //  JSONArray jsonArray = new JSONArray(response);
                                //     Log.d(TAG,"JSON TEST "  + j.getJSONArray("data").getJSONObject(0).getString("name"));
                                // Log.d(TAG,"JSON TEST "  + j.getJSONObject("data").getString("name"));
                              //  Log.d(TAG,"JSON TEST "  + j.getString("data"));
                                JSONArray jsonArray = new JSONArray(j.getString("data"));
                            //    Log.d(TAG,"JSON TEST "  + jsonArray);
                                for (int i = 0 ; i<jsonArray.length(); i++) {
                                    JSONObject json_data = jsonArray.getJSONObject(i);
                                    //Log.d(TAG,"JSON TEST " +json_data.getString("lat"));

                                    JSONObject ob = json_data.getJSONObject("typetree");
                                    Log.d(TAG,"JSON TEST tytptree" +ob.getString("typetree_name"));
                                    Log.d(TAG,"JSON TEST BASE64" +json_data.getString("base64"));

//add_tree(String name , String photo_base64,String code, String qr , String localname, String sciencename , String character , String typetree)
                                  //  sqLiteHelper.add_tree("tee_number " + i ,"BASE64","K2","qr_code","localname","science","char","type");
                                    String t_name = json_data.getString("tree_name");
                                    String l_name = json_data.getString("localname");
                                    String s_name = json_data.getString("sciencename");
                                    String charac_ter = json_data.getString("character");
                                    String qr = json_data.getString("qrcode_photo");
                                    String t_code = json_data.getString("treecode") ;
                                    String base_64 = json_data.getString("base64");
                                    String type_tree = ob.getString("typetree_name");

                                    sqLiteHelper.add_tree(t_name,base_64,t_code,qr,l_name,s_name,charac_ter,type_tree);


                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("offline_select_school", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("select_school", "true");
                                    editor.commit();

                                   /* sqLiteHelper.getReadableDatabase();
                                    sqLiteHelper.find_tree();*/

                                }

                                sqLiteHelper.close();

                                Toast.makeText(offline_school.this, "ดึงข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(offline_school.this,offline_scan.class);
                                startActivity(intent);


                                break;
                            case "false":
                                prg.hide();
                                Toast.makeText(offline_school.this, j.getString("status_meesage"), Toast.LENGTH_SHORT).show();
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
                    params.put("school_name",school_name);
                    return params;


                }

            };
            requestQueue.add(request);


        }catch(Exception ex){
            Log.d(TAG, "Failed Message\t" + ex.toString());

            //  finish();
        }
    }

    @Override
    public void onClick(View view) {
     //   Toast.makeText(offline_school.this, school_name, Toast.LENGTH_LONG).show();
        switch (view.getId()){
            case R.id.submit_offline_school:
                if(school_name_select != null){
                    get_tree(school_name_select);
               // Toast.makeText(offline_school.this, school_name_select, Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(offline_school.this, "กรุณาเลือกโรงเรียน", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        school_name_select = adapterView.getItemAtPosition(i).toString();
       // Log.d(TAG,"Bug : onItemSelected ");
      //  Toast.makeText(offline_school.this, school_name_select, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d(TAG,"Bug : onNothingSelected ");
    }


}
