package com.example.appfood;

import static com.example.appfood.MainActivity.TB_FOOD;
import static com.example.appfood.MainActivity.dbHelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.appfood.adapter.FoodAdapter;
import com.example.appfood.model.Food;

import java.util.ArrayList;
import java.util.List;

public class ListFoodActivity extends AppCompatActivity {
    GridView gridFood;
    ArrayList<Food> list;
    FoodAdapter adapter = null;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);

        gridFood = findViewById(R.id.grFood);
        imgBack = findViewById(R.id.imgBack);
        list = new ArrayList<>();
        adapter = new FoodAdapter(this, R.layout.row_food, list);
        gridFood.setAdapter(adapter);
        String sql_select = "select * from "+TB_FOOD;
        Cursor cs = dbHelper.SelectData(sql_select);
        while (cs.moveToNext()){
            int foodid = cs.getInt(0);
            int categoryid = cs.getInt(1);
            String foodname = cs.getString(2);
            byte[] image = cs.getBlob(3);
            String video = cs.getString(4);
            String address = cs.getString(5);
            String tacgia = cs.getString(6);
            String congthuc = cs.getString(7);
            String motadai = cs.getString(8);
            Food food = new Food(foodid,categoryid,foodname,image,video,address,tacgia,congthuc,motadai);
            list.add(food);
        }
        adapter.notifyDataSetChanged();
        addAction();
    }

    private void addAction() {
        gridFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Food food = (Food) adapter.getItem(i);
                int foodid = food.getFoodID();
                System.out.println(foodid);
                Intent intent = new Intent(ListFoodActivity.this, DetailFoodActivity.class);
                intent.putExtra("foodid",foodid);
                startActivity(intent);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ListFoodActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}