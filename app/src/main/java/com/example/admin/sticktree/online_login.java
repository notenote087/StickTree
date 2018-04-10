package com.example.admin.sticktree;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class online_login extends AppCompatActivity {

    EditText email,password ;
    Button login ;

    private String TAG = MainActivity.class.getSimpleName();
    private static final String URL = "http://10.80.45.10/api/login";
    // private static final String URL = "http://notestickburn.epizy.com/stickburn/register.php";

    private  ProgressDialog prg ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_login);

        findID();



        prg = new ProgressDialog(online_login.this);
        prg.setMessage("รอสักครู่...");
        prg.setCancelable(false); //prg.show();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_text = email.getText().toString();
                String password_text = password.getText().toString();

               // Toast.makeText(online_login.this, email_text +" +++ " + password_text, Toast.LENGTH_LONG).show();
                prg.show();
                RequestQueue requestQueue = Volley.newRequestQueue(online_login.this);
              //  JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>(){
                StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                    //  JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(String response) {
                       // Log.d(TAG, response.toString());
                        try {

                            prg.hide();
                           /* h.setText("");
                            w.setText("");*/
                            JSONObject j = new JSONObject(response);
                           // JSONArray j = new JSONArray(response);
                            //   JSONObject j = response.getJSONObject("dataUser");
                            String result = j.getString("status");
                            // j.getJSONObject("dataUser");
                            String result2 = j.getString("status_meesage");
                           // Toast.makeText(getApplicationContext(), result + " " +result2 , Toast.LENGTH_LONG).show();

                            switch (result){
                                case "true":
                                    Toast.makeText(getApplicationContext(), j.getString("status_meesage") + j.getString("school_id"), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(online_login.this,online_updateTree.class);
                                    intent.putExtra("school_name", j.getString("school_name"));
                                    startActivity(intent);

                                    break;
                                case "false":
                                    Toast.makeText(getApplicationContext(), j.getString("status_meesage"), Toast.LENGTH_LONG).show();
                                    break;
                            }
                          //  for (int i = 0 ; i<myJsonArray.length(); i++) {
                           //     JSONObject json_data = myJsonArray.getJSONObject(i);
                              //  email.setText("-------- "+json_data.getString("tree_name"));
                          //      email.setText("-------- "+json_data.getJSONObject("school").getString("school_name"));



                          //  }

                  //          Toast.makeText(online_login.this, "aaa " +j.getString("email"), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            prg.hide();
                            //  textviewShow.setText(e.getMessage());
                                  Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            Log.d("Whats wrong?", e.toString());
                            Log.e("JSON Parser", "Error parsing data " + e.toString());
                            // textviewShow.setText(e.toString());

                            //intent
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, error.toString());

                        prg.hide();
                       // alertDetail("ผิดพลาด " + error.toString());
                      /*  h.setText("");
                        w.setText("");
                        name.setText("");
                        age.setText("");
                        */
                        Toast.makeText(online_login.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("email", email.getText().toString());
                        params.put("password", password.getText().toString());
                        return params;


                    }
                };
                requestQueue.add(request);


            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void findID(){
        login = (Button) findViewById(R.id.login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
    }
}
