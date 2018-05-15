package com.example.admin.sticktree;

import android.*;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.admin.sticktree.offline_Database.SQLiteHelper;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class offline_scan extends AppCompatActivity implements ZXingScannerView.ResultHandler , View.OnClickListener{

    Button scanqrcode;

    ProgressDialog prg ;

    private ZXingScannerView mScannerView;
    private static final int REQUEST_CAMERA = 1;

    private String TAG = MainActivity.class.getSimpleName();
    String  result_scan ;

    private void findID(){
        scanqrcode = (Button) findViewById(R.id.scanqrcode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_scan);

        prg = new ProgressDialog(offline_scan.this);
        prg.setMessage("รอสักครู่...");
        prg.setCancelable(false); //prg.show();

        findID();
        scanqrcode.setOnClickListener(this);
    }

    @Override
    public void handleResult(Result result) {
        setContentView(R.layout.offline_scan);

        prg.show();
        //       Fix               //
        findID();
        scanqrcode.setOnClickListener(this);
        result_scan = result.getText();
        prg.hide();
        //Toast.makeText(offline_scan.this, result_scan, Toast.LENGTH_SHORT).show();

        SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
        sqLiteHelper.getReadableDatabase();
        //String data[] = new String[6];

        String data[] = sqLiteHelper.find_tree(result_scan);
      //  String test = data[0];
        Log.d(TAG," test Scan "+data[0] );

        //   String select = "SELECT TREENAME,TREECODE,photo_base64,SCIENCENAME,LOCALNAME,TYPETREE,CHARACTER  FROM tree_data WHERE QRCODE =" + "'" + qr +"'"  ;

        if(data[0] != null){


        Intent intent = new Intent(offline_scan.this, offline_Data.class);
        intent.putExtra("TREENAME",data[0]);
        intent.putExtra("TREECODE",data[1]);
        intent.putExtra("photo_base64",data[2]);
        intent.putExtra("SCIENCENAME",data[3]);
        intent.putExtra("LOCALNAME",data[4]);
        intent.putExtra("TYPETREE",data[5]);
        intent.putExtra("CHARACTER",data[6]);


        startActivity(intent);
        } else {
            Toast.makeText(offline_scan.this, "ไม่พบข้อมูล", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.scanqrcode:

                try{
                    if (ActivityCompat.checkSelfPermission(offline_scan.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ) {
                        ActivityCompat.requestPermissions(offline_scan.this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA);
                    }else{
                        mScannerView = new ZXingScannerView(getApplicationContext());
                        setContentView(mScannerView);
                        mScannerView.setResultHandler(offline_scan.this);
                        mScannerView.startCamera();
                    }
                    //Toast.makeText(online_edit_find.this, "click", Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    Toast.makeText(offline_scan.this, e.toString(), Toast.LENGTH_LONG).show();
                    Log.i("Error BUG ",e.toString());

                }

             /*   Intent intent = new Intent(offline_scan.this,offline_Data.class);
                startActivity(intent);*/


                break;
        }
    }
}
