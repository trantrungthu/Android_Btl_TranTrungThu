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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class UpdateFoodActivity extends AppCompatActivity implements SingleChoiceDialogFragment.SingleChoiceListener{
    int foodid;
    ImageView imgAddAnh,imgBack,imgAnhFood;
    TextView txtDanhMuc,txtTenMon,txtTacGia,txtVideo,txtCongThuc,txtAddress,txtMoTa;
    Button btnDanhMuc,btnSuaMon;

    private static final int REQUEST_GALLERY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);
        mapping();
        addAction();

        Intent intent = getIntent();
        foodid = intent.getIntExtra("foodid",0);
        String sql_select = "select * from "+TB_FOOD+" where foodid ="+foodid;
        Cursor cs = dbHelper.SelectData(sql_select);
        while (cs.moveToNext()){
            int id = cs.getInt(0);
            int categoryid = cs.getInt(1);
            switch (categoryid){
                case 1:
                    txtDanhMuc.setText("Đồ Uống");
                    break;
                case 2:
                    txtDanhMuc.setText("Món Thịt");
                    break;
                case 3:
                    txtDanhMuc.setText("Cơm");
                    break;
                case 4:
                    txtDanhMuc.setText("Salad");
                    break;
                case 5:
                    txtDanhMuc.setText("Món Cá");
                    break;
                case 6:
                    txtDanhMuc.setText("Món Nướng");
                    break;
            }
            txtTenMon.setText(cs.getString(2));
            byte[] image = cs.getBlob(3);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            imgAnhFood.setImageBitmap(bitmap);
            txtVideo.setText("Link video: "+cs.getString(4));
            txtAddress.setText(cs.getString(5));
            txtTacGia.setText(cs.getString(6));
            txtCongThuc.setText("Công thức: "+cs.getString(7));
            txtMoTa.setText(cs.getString(8));
        }
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

        btnSuaMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(txtTenMon.getText().toString()) || TextUtils.isEmpty(txtCongThuc.getText().toString()) ||
                        TextUtils.isEmpty(txtVideo.getText().toString()) ||
                        TextUtils.isEmpty(txtAddress.getText().toString()) ||
                        TextUtils.isEmpty(txtTacGia.getText().toString()) ||
                        TextUtils.isEmpty(txtMoTa.getText().toString()) ||
                        TextUtils.isEmpty(txtDanhMuc.getText().toString())|| imgAnhFood.getDrawable()==null){
                    Toast.makeText(UpdateFoodActivity.this, "Thông tin không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    String id = String.valueOf(foodid);
                    int kq = updateFood(id);
                    if (kq==1){
                        Toast.makeText(UpdateFoodActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(UpdateFoodActivity.this, DetailFoodActivity.class);
                        intent1.putExtra("foodid",foodid);
                        startActivity(intent1);
                    } else Toast.makeText(UpdateFoodActivity.this, "Sửa không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(UpdateFoodActivity.this, DetailFoodActivity.class);
                intent1.putExtra("foodid",foodid);
                startActivity(intent1);
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

    }

    public int updateFood(String foodid){
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
        if(dbHelper.update(TB_FOOD,values,"foodid=?",new String[]{foodid})>0){
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
        btnSuaMon = findViewById(R.id.btnSuaMon);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1){
            Uri uri = data.getData();
            imgAnhFood.setImageURI(uri);
        }
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