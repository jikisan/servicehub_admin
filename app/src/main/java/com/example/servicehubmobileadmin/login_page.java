package com.example.servicehubmobileadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class login_page extends AppCompatActivity {

    private EditText et_username, et_password;
    private CheckBox checkBox_rememberMe;
    private TextView tv_forgotPassword, tv_signUp;
    private Button btn_login, btn_guest;
    private FirebaseAuth fAuth;
    private DatabaseReference userDatabase;
    private String userType, projId;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        setRef();

//        Intent intent;
//        if(user == null)
//        {
//            intent = new Intent(login_page.this, login_page.class);
//        }
//        else
//        {
//            intent = new Intent(login_page.this, MainActivity.class);
//        }
//        startActivity(intent);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                if (TextUtils.isEmpty(email))
                {
                    et_username.setError("Email is Required");
                    return;
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    et_username.setError("Incorrect Email Format");
                }
                else if (TextUtils.isEmpty(password))
                {

                    et_password.setError("Password is Required");
                    return;
                }
                else
                {
                    fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(login_page.this, "Logged in succesfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));


                            } else {
                                Toast.makeText(login_page.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });
    }

    private void setRef() {

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        checkBox_rememberMe = findViewById(R.id.cb_rememberMe);

        btn_login = findViewById(R.id.btn_login);

        fAuth = FirebaseAuth.getInstance();
    }
}