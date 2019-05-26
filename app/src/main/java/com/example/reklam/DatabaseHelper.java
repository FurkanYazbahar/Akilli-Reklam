package com.example.reklam;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceFirma;
    private List<Firmalar> firmalar = new ArrayList<>();

    public interface VeriDurumu{
        void VeriAlındı(List<Firmalar> firmalar, List<String> keys);
    }

    public DatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceFirma = mDatabase.getReference("Firmalar");
    }

    public void readFirma(final VeriDurumu veriDurumu){
        mReferenceFirma.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                firmalar.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    //Log.d("ATKAFASI", "///"+keyNode);
                    Firmalar firma = keyNode.getValue(Firmalar.class);
                    firmalar.add(firma);
                }
                veriDurumu.VeriAlındı(firmalar,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
