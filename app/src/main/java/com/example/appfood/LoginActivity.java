package com.example.appfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfood.sqlite.DBHelper;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
TextView txtQuenPW,txtSignup;
Button btnLogin;
EditText txtUserName,txtPassword;
DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new DBHelper(this);
        mapping();
        addAction();
    }

    private void addAction() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();
                if (TextUtils.isEmpty(email) ||TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Bạn chưa nhập thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkuserpass = dbHelper.checkUsernamePassword(email,password);
                    if (checkuserpass==true){
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        String user = txtUserName.getText().toString();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                    }else Toast.makeText(LoginActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

        txtQuenPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,LayLaiMKActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mapping() {
        txtQuenPW = findViewById(R.id.txtQuenPW);
        txtUserName = findViewById(R.id.txtUsername);
        txtSignup = findViewById(R.id.txtSignup);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }
}