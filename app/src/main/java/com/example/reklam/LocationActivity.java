package com.example.reklam;

import android.annotation.TargetApi;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)

public class LocationActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{



    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;

// -------------------------------------------------------------------
    private Button btnOto,btnManual;
    private TextView txtlat, txtlongi;
    private EditText edtlat, edtlongi;
    private GoogleApiClient mGoogleApiClient = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        btnOto = (Button) findViewById(R.id.btnOto);
        btnManual = (Button) findViewById(R.id.btnManual);
        txtlat = (TextView) findViewById(R.id.txtlat);
        txtlongi = (TextView) findViewById(R.id.txtlongi);
        edtlat = (EditText) findViewById(R.id.editLat);
        edtlongi = (EditText) findViewById(R.id.editLongi);




        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }


        btnOto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnManual.setEnabled(false);
                edtlat.setEnabled(false);
                edtlongi.setEnabled(false);


                String s = mLastLocation.getLatitude()+","+mLastLocation.getLongitude();
                Log.d("ATKAFASI", "/// "+s);
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                i.putExtra("send_string",s);
                Log.d("ATKAFASI", "///btnOTO 1");
                startActivity(i);
                Log.d("ATKAFASI", "///btnOTO 2");
            }
        });

        btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOto.setEnabled(false);
                isEmpyt();

                String s = edtlat.getText().toString()+","+edtlongi.getText().toString();
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                i.putExtra("send_string",s);
                startActivity(i);
            }
        });
    }


    private boolean isEmpyt(){
        if(TextUtils.isEmpty(edtlat.getText().toString())){
            edtlat.setError("Required");
            return true;
        }
        if(TextUtils.isEmpty(edtlongi.getText().toString())){
            edtlongi.setError("Required");
            return true;
        }
        return false;
    }


    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


        @Override
        public void onConnected(@Nullable Bundle bundle) {
            try {
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
                //Log.d("ATKAFASI", "///1");
                if (mLastLocation != null) {
                 //   Log.d("ATKAFASI", "///");
                //lattitude.append("Latitude : "+ mLastLocation.getLatitude());
                //longitude.append("Longitude : "+ mLastLocation.getLongitude());
                    //Log.d("MAMUT", "///"+mLastLocation.getLatitude()+"///"+mLastLocation.getLongitude());
                }
            } catch (SecurityException e) {}
        }

        @Override
        public void onConnectionSuspended(int i) {

        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            switch (id) {
                case R.id.pass:
                    startActivity(new Intent(this, PassChangeActivity.class));
                    return true;

                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    startActivity(new Intent(this, LoginActivity.class));

                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(LocationActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }


}
