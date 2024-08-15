package com.example.apkdatamhs;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText etUsername, etFullname, etRegEmail, etRegPassword;
    Button btnRegRegister;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etFullname = findViewById(R.id.etFullname);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPassword);
        btnRegRegister = findViewById(R.id.btnRegRegister);
        db = new DatabaseHelper(this);

        btnRegRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String fullname = etFullname.getText().toString();
                String email = etRegEmail.getText().toString();
                String password = etRegPassword.getText().toString();
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Boolean checkEmail = db.checkEmail(email);
                    if (checkEmail == false) {
                        String hashedPassword = Utils.hashMD5(password);

                        Boolean insert = db.insert(username, fullname, email, hashedPassword);
                        if (insert == true) {
                            Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Email Already Exists", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);
                    }
                } else {
                    etRegEmail.setError("Invalid email address");
                }
            }
        });
    }
}