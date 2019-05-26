package com.example.reklam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PassChangeActivity extends AppCompatActivity {

    EditText pass ;
    Button button;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_change);

        pass = (EditText) findViewById(R.id.textPass);
        button   = (Button) findViewById(R.id.btnChange);

        auth = FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser usr = auth.getCurrentUser();

                if(isEmpyt()) return;
                if(usr != null){
                    usr.updatePassword(pass.getText().toString()).
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PassChangeActivity.this,"Parola Başarıyla Değiştirildi !",
                                        Toast.LENGTH_SHORT).show();
                                auth.signOut();
                                finish();
                                startActivity(new Intent(PassChangeActivity.this,LoginActivity.class));
                            }else{
                                Toast.makeText(PassChangeActivity.this,"Parola Değiştirme Başarısız !",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });
    }

    private void inProgress(boolean x){
        if(x){
            button.setEnabled(false);
        }else {
            button.setEnabled(true);
        }
    }
    private boolean isEmpyt(){

        if(TextUtils.isEmpty(pass.getText().toString())){
            pass.setError("Required");
            return true;
        }
        return false;
    }
}
