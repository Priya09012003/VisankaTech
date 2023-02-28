package com.example.visankatech;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    TextView alreadyhaveregistered;
    EditText inputEmail,inputPassword,inputConfirmPassword;
    Button btnRegister;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;


    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        alreadyhaveregistered=findViewById(R.id.AlreadyHaveRegistered);
        inputEmail=findViewById(R.id.EnterEmailreg);
        inputPassword=findViewById(R.id.EnterPasswordreg);
        inputConfirmPassword=findViewById(R.id.confirmpassword);
        btnRegister=findViewById(R.id.btnreg);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();




        alreadyhaveregistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,loginpage.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerforAuth();
            }
        });

    }

    private void PerforAuth() {
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();
        String Confirmpassword=inputConfirmPassword.getText().toString();

        if(!email.matches(emailPattern)){
            inputEmail.setError("Enter correct Email");
        } else if (password.isEmpty() || password.length()<6) {
            inputPassword.setError("you have entered wrong password");
        } else if (!password.equals(Confirmpassword)) {
            inputConfirmPassword.setError("Password Not Matched Both failed");
        } else
        {
            progressDialog.setMessage("Please Wait while Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(Register.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                    }else{

                        progressDialog.dismiss();
                        Toast.makeText(Register.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

            

    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(Register.this,home.class);
        startActivity(intent);
    }
}