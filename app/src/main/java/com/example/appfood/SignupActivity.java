package com.example.appfood;

import static com.example.appfood.MainActivity.ADDRESS;
import static com.example.appfood.MainActivity.ANH;
import static com.example.appfood.MainActivity.CONGTHUC;
import static com.example.appfood.MainActivity.FOODCATEGORYID;
import static com.example.appfood.MainActivity.FOOD_NAME;
import static com.example.appfood.MainActivity.MOTADAI;
import static com.example.appfood.MainActivity.TACGIA;
import static com.example.appfood.MainActivity.TB_FOOD;
import static com.example.appfood.MainActivity.VIDEO;
import static com.example.appfood.MainActivity.dbHelper;
import static com.example.appfood.sqlite.DBHelper.TB_USER;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfood.sqlite.DBHelper;

import java.io.ByteArrayOutputStream;

public class SignupActivity extends AppCompatActivity {
    EditText edtPass, edtConfirmPass;
    TextView txtEmail, txtFullName, txtPhone, txtDiaChi;
    Button btnDangKy;
    ImageView imgAvatar, imgAddAvatar;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        dbHelper = new DBHelper(this);

        mapping();
        addAction();
    }

    private void addAction() {
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString();
                String password = edtPass.getText().toString();
                String repassword = edtConfirmPass.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                        TextUtils.isEmpty(txtFullName.getText().toString()) ||
                        TextUtils.isEmpty(txtDiaChi.getText().toString()) ||
                        TextUtils.isEmpty(txtPhone.getText().toString()) ||
                        TextUtils.isEmpty(repassword)) {
                    Toast.makeText(SignupActivity.this, "Bạn chưa nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(repassword)) {
                        Boolean checkEmail = dbHelper.checkUserName(email);
                        if (checkEmail == false) {
                            ContentValues values = new ContentValues();
                            values.put("fullName", txtFullName.getText().toString());
                            values.put("email", txtEmail.getText().toString());
                            values.put("password", edtPass.getText().toString());
                            values.put("phone", txtPhone.getText().toString());
                            values.put("address", txtDiaChi.getText().toString());
                            Boolean insertUser;
                            if (dbHelper.insert(TB_USER, null, values) > 0) {
                                insertUser = true;
                            } else insertUser = false;
                            if (insertUser == true) {
                                Toast.makeText(SignupActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else
                                Toast.makeText(SignupActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(SignupActivity.this, "Email này đã tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void mapping() {
        txtEmail = findViewById(R.id.txtEmail);
        txtDiaChi = findViewById(R.id.txtAddress);
        txtFullName = findViewById(R.id.txtFullName);
        txtPhone = findViewById(R.id.txtPhone);
        edtPass = findViewById(R.id.edtPassWord);
        edtConfirmPass = findViewById(R.id.edtConfirmPassword);
        btnDangKy = findViewById(R.id.btnDangKy);
        imgAvatar = findViewById(R.id.imgAvatar);
        imgAddAvatar = findViewById(R.id.imgAddAvatar);
    }
}