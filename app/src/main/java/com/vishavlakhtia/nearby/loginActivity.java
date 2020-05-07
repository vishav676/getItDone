package com.vishavlakhtia.nearby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vishavlakhtia.nearby.outline.Users;

import io.paperdb.Paper;

public class loginActivity extends AppCompatActivity {

    EditText sign_email,sign_password;
    Button sign_go;
    DataSnapshot dataSnapshot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sign_email = findViewById(R.id.et_email);
        sign_password = findViewById(R.id.et_password);
        sign_go = findViewById(R.id.sign_go);

        Paper.init(this);

        sign_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = sign_email.getText().toString();
                final String password = sign_password.getText().toString();
                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(getApplicationContext(),"Enter email",Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(),"Enter password",Toast.LENGTH_SHORT).show();
                }else {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful())
                                    {
                                        Toast.makeText(getApplicationContext(),""+task.getException(),Toast.LENGTH_SHORT).show();
                                    }else {
                                        Paper.book().write(userDb.UserEmail,email);

                                        Paper.book().write(userDb.UserPasswordKey,password);
                                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),homeActivity.class));
                                    }
                                }
                            });
                }
            }
        });

    }
}
