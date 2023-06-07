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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfood.sqlite.DBHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddFoodActivity extends AppCompatActivity implements SingleChoiceDialogFragment.SingleChoiceListener{
    ImageView imgAddAnh,imgBack,imgAnhFood;
    TextView txtDanhMuc,txtTenMon,txtTacGia,txtVideo,txtCongThuc,txtAddress,txtMoTa;
    Button btnDanhMuc,btnThemMon;

    private static final int REQUEST_GALLERY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        mapping();
        addAction();
    }

    private void addAction() {
        btnDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment singleChoiceDialog = new SingleChoiceDialogFragment();
                singleChoiceDialog.setCancelable(false);
                singleChoiceDialog.show(getSupportFragmentManager(), "Single Choice Dialog");
            }
        });

        imgAddAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Tille"),REQUEST_GALLERY);
            }
        });

        imgAnhFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Tille"),REQUEST_GALLERY);
            }
        });

        btnThemMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(txtTenMon.getText().toString()) || TextUtils.isEmpty(txtCongThuc.getText().toString()) ||
                        TextUtils.isEmpty(txtVideo.getText().toString()) ||
                        TextUtils.isEmpty(txtAddress.getText().toString()) ||
                        TextUtils.isEmpty(txtTacGia.getText().toString()) ||
                        TextUtils.isEmpty(txtMoTa.getText().toString()) ||
                        TextUtils.isEmpty(txtDanhMuc.getText().toString())|| imgAnhFood.getDrawable()==null){
                    Toast.makeText(AddFoodActivity.this, "Thông tin không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    int i = inSertData();
                    if (i==1){
                        Toast.makeText(AddFoodActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        clear();
                    } else Toast.makeText(AddFoodActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddFoodActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1){
            Uri uri = data.getData();
            imgAnhFood.setImageURI(uri);
        }
    }
//Hàm clear dữ liệu sau khi thêm vào csdl thành công
    public void clear(){
            txtTenMon.setText("");
            txtDanhMuc.setText("");
            txtMoTa.setText("");
            txtCongThuc.setText("");
            txtAddress.setText("");
            txtTacGia.setText("");
            txtVideo.setText("");
        }
//Insert data vào database
    public int inSertData(){
        int categoryID=0;
        switch (txtDanhMuc.getText().toString()){
            case "Đồ Uống":
                categoryID=1;
                break;
            case "Món Thịt":
                categoryID=2;
                break;
            case "Cơm":
                categoryID=3;
                break;
            case "Salad":
                categoryID=4;
                break;
            case "Món Cá":
                categoryID=5;
                break;
            case "Món Nướng":
                categoryID=6;
                break;
        }
        ContentValues values = new ContentValues();
        values.put(FOODCATEGORYID,categoryID);
        values.put(FOOD_NAME,txtTenMon.getText().toString());
        values.put(ANH,imageViewToByte(imgAnhFood));
        values.put(VIDEO,txtVideo.getText().toString());
        values.put(ADDRESS,txtAddress.getText().toString());
        values.put(TACGIA,txtTacGia.getText().toString());
        values.put(CONGTHUC,txtCongThuc.getText().toString());
        values.put(MOTADAI,txtMoTa.getText().toString());
        if(dbHelper.insert(TB_FOOD,null,values)>0){
            return 1;
        }
        return -1;
    }
//Convert image sang byte
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void mapping() {
        btnDanhMuc = findViewById(R.id.btnChonDM);
        btnThemMon = findViewById(R.id.btnThemMon);
        txtDanhMuc = findViewById(R.id.txtDanhMuc);
        txtTenMon = findViewById(R.id.txtTenMon);
        txtVideo = findViewById(R.id.txtVideo);
        txtCongThuc = findViewById(R.id.txtCongThuc);
        txtAddress = findViewById(R.id.txtAddress);
        txtTacGia = findViewById(R.id.txtTacGia);
        txtMoTa = findViewById(R.id.txtMoTa);
        imgAnhFood = findViewById(R.id.imgAnhFood);
        imgAddAnh = findViewById(R.id.imgAddAnhFood);
        imgBack = findViewById(R.id.imgBack);
    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        txtDanhMuc.setText(list[position]);
    }

    @Override
    public void onNegativeButtonClicked() {
        txtDanhMuc.setText("Dialog cancel");
    }
}