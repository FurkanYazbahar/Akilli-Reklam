package com.example.reklam;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity {
    ArrayList<User> users;
    public DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        configureEquipment();
    }
    public void configureEquipment(){
        Button signup     = findViewById(R.id.btnSignup);
        final EditText userName = findViewById(R.id.txtName);
        final EditText pass     = findViewById(R.id.textPass);
        final EditText rePass   = findViewById(R.id.textRePass);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmpUser = userName.getText().toString().trim();
                String tmpPass = pass.getText().toString().trim();
                String tmpRepass = rePass.getText().toString().trim();
                if(!tmpPass.equals(tmpRepass)){
                    Toast.makeText(SignupActivity.this,"Uyuşmayan parola! Kontrol ediniz ",Toast.LENGTH_SHORT).show();
                    Log.d("TAG",tmpPass+"///"+tmpRepass);
                    return;
                }
                Log.d("TAG","//Sıkıntı burdamı/");
                final List<User> users = new ArrayList<>();
                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        User tmp = dataSnapshot.getValue(User.class);
                        Log.d("TAG","//Sıkıntı burdamı/"+tmp.getUsername());
                        Log.d("TAG","//Sıkıntı burdamı/"+tmp.getPass());
                        users.add(tmp);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                       // Log.d("TAG","//Sıkıntı burdamı/");
                    }
                };
                mDatabase.addValueEventListener(postListener);

                for(User usr : users){
                    Log.d("TAG",usr.getUsername()+"///"+usr.getPass());
                }

                Toast.makeText(SignupActivity.this, "Böyle bir kullanıcı mevcut ! ", Toast.LENGTH_SHORT).show();

                                /*
                                else{
                                    writeNewUser(tmpUser, tmpPass);
                                    finish();
                                }*/



               // Log.d("TAG","/// Buradayım");
            }
        });
    }

    public   boolean isCurrentUser(final String userName){

        return false;
    }

    private void writeNewUser(String name, String pass) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        User usr = new User(name,pass);
        String userId = mDatabase.push().getKey();
        mDatabase.child("Users").child(userId).setValue(usr);
        usr = null;
    }
}
