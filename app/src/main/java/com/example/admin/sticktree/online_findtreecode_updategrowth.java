package com.example.admin.sticktree;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class online_findtreecode_updategrowth extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    //private static final String URL = "http://192.168.1.5/api/find_tree";
    private static final String URL = "http://10.80.45.10/api/find_tree";


    EditText tree_code ;
    Button submit ;
    ProgressDialog prg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_findtreecode_updategrowth);

        prg = new ProgressDialog(online_findtreecode_updategrowth.this);
        prg.setMessage("รอสักครู่...");
        prg.setCancelable(false); //prg.show();


        tree_code = findViewById(R.id.tree_code_find);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prg.show();

                RequestQueue requestQueue = Volley.newRequestQueue(online_findtreecode_updategrowth.this);
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
                                    Intent intent = new Intent(online_findtreecode_updategrowth.this, online_update_growth.class);
                                    intent.putExtra("treecode", j.getString("treecode"));
                                    intent.putExtra("treename",j.getString("treename"));
                                    // intent.putExtra("typetree",j.getString("typetree"));
                                    intent.putExtra("tree_id" , j.getString("id"));
                                    Log.d(TAG,"TEST " + j.getString("treecode")  + " " + j.getString("treename"));
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
                            Toast.makeText(online_findtreecode_updategrowth.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        prg.hide();
                        VolleyLog.d(TAG, error.toString());
                        Toast.makeText(online_findtreecode_updategrowth.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("find","treecode");
                        params.put("treecode", tree_code.getText().toString());


                        return params;


                    }
                };
                requestQueue.add(request);

            }
        });

    }
}
