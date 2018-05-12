package com.example.admin.sticktree;


import android.*;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUpdateTree extends Fragment {
    View rootView ;
    Button treecode_button,scanqrcode_button;

    private String TAG = MainActivity.class.getSimpleName();
    private static final String URL = "http://192.168.1.6/api/find_tree";
    ProgressDialog prg ;

    String  result_scan ;

    public FragmentUpdateTree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_update_tree, container, false);

        try{

        treecode_button = (Button) rootView.findViewById(R.id.treecode);
        scanqrcode_button = (Button) rootView.findViewById(R.id.scanqrcode);

        treecode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),online_findtreecode_updategrowth.class);
                startActivity(intent);
            }
        });
        scanqrcode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),online_findqrcode_updategrowth.class);
                startActivity(intent);
            }
        });

        }catch (Exception e){
                Log.d(TAG,"close : " + e.getMessage());
        }

        return rootView ;



    }




}
