package com.example.reklam;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private Location myLocation;
    public List<Firmalar> comp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Bundle extras = getIntent().getExtras();
        String value = extras.getString("send_string");
        Log.d("ATKAFASI", "///"+value);
        myLocation = new Location("");
        String[] s = value.split(",");
        myLocation.setLatitude(Double.parseDouble(s[0]));
        myLocation.setLongitude(Double.parseDouble(s[1]));

        comp = new ArrayList<>();


        new DatabaseHelper().readFirma(new DatabaseHelper.VeriDurumu() {
            @Override
            public void VeriAlındı(List<Firmalar> firmalar, List<String> keys) {
                for(Firmalar firma: firmalar){
                    comp.add(firma);
                }

                float distanceInMeters = myLocation.distanceTo(comp.get(0).getLocation());
                Toast.makeText(SearchActivity.this,"Uzaklık \n !"+distanceInMeters,
                        Toast.LENGTH_SHORT).show();
                Log.d("ATKAFASI", "///"+comp.get(0).getKampanyaIcerik());

            }
        });
        //Log.d("ATKAFASI", "///"+comp.get(0).getKampanyaIcerik());







    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            Intent intent = new Intent(SearchActivity.this,LocationActivity.class);
            startActivity(intent);

        }
        return super.onKeyDown(keyCode, event);
    }
}