package com.example.admin.sticktree;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    Button login  ;
    EditText email , password;

    ProgressDialog prg;

    private String TAG = MainActivity.class.getSimpleName();
   // private static final String URL = "http://192.168.1.5/api/login";
   private static final String URL = "http://10.80.45.10/api/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login = (Button) findViewById(R.id.login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        prg = new ProgressDialog(login.this);
        prg.setMessage("รอสักครู่...");
        prg.setCancelable(false); //prg.show();

        login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               prg.show();

               RequestQueue requestQueue = Volley.newRequestQueue(login.this);
               StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                   //  JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
                   @Override
                   public void onResponse(String response) {
                       // Log.d(TAG, response.toString());
                       try {
                           SharedPreferences pref = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
                           SharedPreferences.Editor editor = pref.edit();
                           JSONObject j = new JSONObject(response);
                           String result_status = j.getString("status");
                           String result_message = j.getString("status_meesage");
                           switch (result_status) {
                                   case "true":
                                       prg.hide();
                                       Toast.makeText(getApplicationContext(), result_message, Toast.LENGTH_LONG).show();
                                       editor.putBoolean("login", true);  // login status check in splash screen
                                       String fullname = j.getString("firstname") + " " + j.getString("lastname");
                                       editor.putString("name", fullname);
                                       editor.putString("school_id", j.getString("school_id"));
                                       editor.putString("school_name", j.getString("school_name"));
                                       editor.commit();

                                       Intent intent = new Intent(login.this, online_main.class);
                                       intent.putExtra("school_name", j.getString("school_name"));
                                       startActivity(intent);
                                       finish();
                                       break;
                                   case "false":
                                       Toast.makeText(getApplicationContext(), result_message, Toast.LENGTH_LONG).show();
                                       break;
                           }
                           //  for (int i = 0 ; i<myJsonArray.length(); i++) {
                           //     JSONObject json_data = myJsonArray.getJSONObject(i);
                           //  email.setText("-------- "+json_data.getString("tree_name"));
                           //      email.setText("-------- "+json_data.getJSONObject("school").getString("school_name"));

                       } catch (Exception e) {
                           // e.printStackTrace();
                           Toast.makeText(login.this, e.toString(), Toast.LENGTH_SHORT).show();
                       }
                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       VolleyLog.d(TAG, error.toString());
                       Toast.makeText(login.this, error.toString(), Toast.LENGTH_SHORT).show();

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
}
