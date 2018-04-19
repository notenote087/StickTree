package com.example.admin.sticktree;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.text.TextUtils.isEmpty;

public class online_Insert extends AppCompatActivity {
    Button confirm, open_camera, select_photo, gps;
    EditText lat, lng;
    String lattitude, longitude;

    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;

    protected LocationListener locationListener;
    protected Context context;

    GPS gpss;
    ProgressDialog pd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online__insert);



        findID();

        pd = new ProgressDialog(online_Insert.this);
        pd.setMessage("loading");


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        open_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        select_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getLocation();


            }
        });
    }
    //Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
    //startActivity(intent);

    void getLocation() {
        findID();

        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            pd.show();
            if (ActivityCompat.checkSelfPermission(online_Insert.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (online_Insert.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //return;
                ActivityCompat.requestPermissions(online_Insert.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                Toast.makeText(online_Insert.this, "checkSelfPermission", Toast.LENGTH_SHORT).show();
            }


            LocationListener locationListener = new LocationListener() {



                @Override
                public void onLocationChanged(Location location) {
                    pd.hide();
                    Toast.makeText(online_Insert.this, "onLocationChanged", Toast.LENGTH_SHORT).show();
                    lat.setText("" + location.getLatitude());
                    lng.setText("" + location.getLongitude());



                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                    Toast.makeText(online_Insert.this, "onStatusChanged", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onProviderEnabled(String s) {
                    Toast.makeText(online_Insert.this, "onProviderEnabled", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onProviderDisabled(String s) {
                    Toast.makeText(online_Insert.this, "onProviderDisabled", Toast.LENGTH_SHORT).show();
                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener); //NETWORK_PROVIDER


        }catch(Exception ex){
            lat.setText(ex.toString());

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }
    public void findID(){
        confirm = (Button) findViewById(R.id.confirm);
        open_camera = (Button) findViewById(R.id.open_camera);
        select_photo = (Button) findViewById(R.id.select_photo);
        gps = (Button) findViewById(R.id.gps);

        lat = (EditText) findViewById(R.id.lat);
        lng = (EditText) findViewById(R.id.lng);
    }

   /* public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(intent, 100);

        //DialogBox("takePicture");
    }*/




}
