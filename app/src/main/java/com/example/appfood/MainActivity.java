package com.example.appfood;

import static com.example.appfood.sqlite.DBHelper.TB_USER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfood.model.Food;
import com.example.appfood.sqlite.DBHelper;

public class MainActivity extends AppCompatActivity {
TextView txtNameUser;
ImageView imgAddFood,imgProfile;
Button btnXemAll;

    public static DBHelper dbHelper;
    public static String dbName = "food.sqlite";
    public static final String TB_FOOD="food";
    public static final String FOODCATEGORYID="categoryid";
    public static final String FOOD_NAME="foodname";
    public static final String ANH="anh";
    public static final String VIDEO="video";
    public static final String ADDRESS="address";
    public static final String TACGIA="tacgia";
    public static final String CONGTHUC="congthuc";
    public static final String MOTADAI="motadai";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        mapping();
        addAction();
        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        int accountID=0;

        String sql_select = "select * from "+TB_USER+" where email= '"+user+"'";
        Cursor cs = dbHelper.SelectData(sql_select);
        while (cs.moveToNext()){
            accountID = cs.getInt(0);
            txtNameUser.setText(cs.getString(1));
        }
    }

    //Hàm mở theo từng danh mục
    public void openCategory(View v) {
        switch (v.getId()) {
            case R.id.lnDrink:
                Intent intent = new Intent(MainActivity.this,DrinkActivity.class);
                startActivity(intent);
                break;
            case R.id.lnMeat:
                Intent intent1 = new Intent(MainActivity.this,MeatActivity.class);
                startActivity(intent1);
                break;
            case R.id.lnRice:
                Intent intent2 = new Intent(MainActivity.this,RiceActivity.class);
                startActivity(intent2);
                break;
            case R.id.lnSalad:
                Intent intent3 = new Intent(MainActivity.this,SaladActivity.class);
                startActivity(intent3);
                break;
            case R.id.lnFish:
                Intent intent4 = new Intent(MainActivity.this,FishActivity.class);
                startActivity(intent4);
                break;
            case R.id.lnMonNuong:
                Intent intent5 = new Intent(MainActivity.this,NuongActivity.class);
                startActivity(intent5);
                break;
        }
    }

    private void addAction() {
        imgAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddFoodActivity.class);
                startActivity(intent);
            }
        });

        btnXemAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ListFoodActivity.class);
                startActivity(intent);
            }
        });
    }
    private void mapping() {
        imgAddFood = findViewById(R.id.imgAddFood);
        imgProfile = findViewById(R.id.imgProfile);
        txtNameUser = findViewById(R.id.txtNameUser);
        btnXemAll = findViewById(R.id.btnXemAll);
    }
}