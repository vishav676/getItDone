package com.vishavlakhtia.nearby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class register extends AppCompatActivity {

    EditText register_email, register_password, register_name;
    Button bt_go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_email  = findViewById(R.id.et_register_email);
        register_password = findViewById(R.id.et_register_password);
        register_name  = findViewById(R.id.et_register_name);

        bt_go = findViewById(R.id.bt_Go);
        bt_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = register_email.getText().toString();
                String password = register_password.getText().toString();
                final String name = register_name.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Please Enter Name...",Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Please Enter Password...",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(name)){
                    Toast.makeText(getApplicationContext(),"Please Enter name...",Toast.LENGTH_SHORT).show();
                }else {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull final Task<AuthResult> task) {
                                    if (!task.isSuccessful())
                                    {
                                        Toast.makeText(getApplicationContext(),""+task.getException(),Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                                        final DatabaseReference databaseReference;
                                        databaseReference = FirebaseDatabase.getInstance().getReference();
                                        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(name).build();
                                        currentUser.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }
}
