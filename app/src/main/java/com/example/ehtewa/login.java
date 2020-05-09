package com.example.ehtewa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class login extends AppCompatActivity {

    private ProgressBar progressBar;
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private Button btnLogin, forgetPass;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
        // go back to the main activity
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back2));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.pwdInp);
        btnLogin = findViewById(R.id.logInBtn);
        forgetPass = findViewById(R.id.forgetPass);
        progressBar = findViewById(R.id.progressBar);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    inputEmail.setError("ادخل عنوان البريد الإلكتروني!");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    inputPassword.setError("ادخل كلمة المرور!");
                    return;
                }

                //authenticate user
                 if(checkInternetConnection()){
                progressBar.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                              progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // error message will be displayed depending on the kind of the error
                                        Toast.makeText(login.this, "فشل المصادقة" +task.getException(), Toast.LENGTH_LONG).show();
                                } else {
                                    user =auth.getCurrentUser();
                                    Intent intent = new Intent(login.this, Home.class);
                                    Toast.makeText(login.this, "مرحباً "+user.getEmail(), Toast.LENGTH_LONG).show();
                                    startActivity(intent);
                                    finish();


                                }
                            }
                        });
            }
                else {
                    Toast.makeText(login.this, "تحقق من اتصال الانترنت ", Toast.LENGTH_LONG).show();
                }
            }
        });
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, resetPassword.class));
            }
        });
    }

    //يتحقق من اتصال الانترنت
    public boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected());
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

}
