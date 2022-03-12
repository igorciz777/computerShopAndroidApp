package com.example.arrayadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

public class OrderList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        DBHelper dbHelper = new DBHelper(getApplicationContext());
        List<Order> orderList = dbHelper.getOrdersFromDB();
        ListView listView = findViewById(R.id.orderList);
        OrderAdapter orderAdapter = new OrderAdapter(getApplicationContext(), orderList);
        listView.setAdapter(orderAdapter);

    }
}