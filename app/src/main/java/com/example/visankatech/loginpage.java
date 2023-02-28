package com.example.visankatech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginpage extends AppCompatActivity {

    EditText inputEmail,inputPassword;


    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_loginpage);
        TextView DontHaveAnAccount=findViewById(R.id.DontHaveAnAccount);
        Button btnlogin = findViewById(R.id.btnLogin);

        inputEmail=findViewById(R.id.EnterEmail);
        inputPassword=findViewById(R.id.EnterPassword);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();




        DontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(loginpage.this,Register.class);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });
    }

    private void performLogin() {
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();


        if(!email.matches(emailPattern)){
            inputEmail.setError("Enter correct Email");
        } else if (password.isEmpty() || password.length()<6) {
            inputPassword.setError("you have entered wrong password");
        }  else
        {
            progressDialog.setMessage("Please Wait while login...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(loginpage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    }else{

                        progressDialog.dismiss();
                        Toast.makeText(loginpage.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }

                }
            });


        }



    }

    private void sendUserToNextActivity() {

        Intent intent =new Intent(loginpage.this,home.class);
        startActivity(intent);
    }


}
