package com.example.reklam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button login ;
    Button register ;

    EditText userName;
    EditText pass;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         login      = (Button) findViewById(R.id.btnLogin);
         register   = (Button) findViewById(R.id.btnRegister);


         userName   = (EditText) findViewById(R.id.txtName);
         pass       = (EditText) findViewById(R.id.textPass);


         auth = FirebaseAuth.getInstance();

        configureButtons();
    }

    public void configureButtons(){


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addMail = userName.getText().toString()+"@gmail.com";
                //if(isEmpyt()) return;
                inProgress(true);
                // Burasını tekrar düzelt  addMail,pass.getText().toString()
                auth.signInWithEmailAndPassword("furkan@gmail.com","123456")
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginActivity.this,"Giriş yapıldı !",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,LocationActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();return;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        inProgress(false);
                        Toast.makeText(LoginActivity.this,"Giriş Yapılamadı !\n"+e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addMail = userName.getText().toString()+"@gmail.com";
                if(isEmpyt()) return;
                inProgress(true);
                auth.createUserWithEmailAndPassword(addMail,pass.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(LoginActivity.this,"Kayıt işlemi başarıyla gerçekleşti !",
                                        Toast.LENGTH_SHORT).show();
                                inProgress(false);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        inProgress(false);
                        Toast.makeText(LoginActivity.this,"Kayıt işlemi başarısız !\n"+e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    private void inProgress(boolean x){
        if(x){

            login.setEnabled(false);
            register.setEnabled(false);

        }else {

            login.setEnabled(true);
            register.setEnabled(true);

        }
    }

    private boolean isEmpyt(){
        if(TextUtils.isEmpty(userName.getText().toString())){
            userName.setError("Required");
            return true;
        }
        if(TextUtils.isEmpty(pass.getText().toString())){
            pass.setError("Required");
            return true;
        }
        return false;
    }
}
