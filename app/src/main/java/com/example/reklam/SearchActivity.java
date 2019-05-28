package com.example.reklam;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import static com.example.reklam.App.CHANNEL_1_ID;
import static com.example.reklam.App.CHANNEL_2_ID;
import static com.example.reklam.App.CHANNEL_3_ID;
import static com.example.reklam.App.CHANNEL_4_ID;

public class SearchActivity extends AppCompatActivity {

    //private NotificationManagerCompat notificationManager;

    private Location myLocation;
    public List<Firmalar> comp;

    public Button btnSearch;
    public EditText txtSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        txtSearch = (EditText) findViewById(R.id.textSearch);

        Bundle extras = getIntent().getExtras();
        String value = extras.getString("send_string");

        myLocation = new Location("");
        String[] s = value.split(",");
        myLocation.setLatitude(Double.parseDouble(s[0]));
        myLocation.setLongitude(Double.parseDouble(s[1]));

        comp = new ArrayList<>();


        new DatabaseHelper().readFirma(new DatabaseHelper.VeriDurumu() {
            @Override
            public void VeriAlındı(List<Firmalar> firmalar, List<String> keys) {
                comp = firmalar;




            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = txtSearch.getText().toString();
                int i=1;

                for(Firmalar fr : comp){
                    if(s.toLowerCase().equals(fr.getKategori().toLowerCase()) || s.toLowerCase().equals(fr.getFirmaAdi().toLowerCase())){

                        float distanceInMeters = myLocation.distanceTo(fr.getLocation());

                        if(distanceInMeters<=100){

                            addNotification(fr,i,distanceInMeters);
                        }
                        i++;
                    }
                }
            }
        });








    }

    // Creates and displays a notification
    private void addNotification(Firmalar list , int i,float x) {
        // Builds your notification

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_"+i+"_ID")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(list.getFirmaAdi())
                .setContentText(list.getKampanyaIcerik()+"\n Uzaklık : "+x)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);



        // Creates the intent needed to show the notification

        Intent notificationIntent = new Intent(this, NotificationActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(i, builder.build());
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