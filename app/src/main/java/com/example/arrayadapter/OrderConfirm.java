package com.example.arrayadapter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OrderConfirm extends AppCompatActivity {
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);

        Intent intent = getIntent();
        dbHelper = new DBHelper(getApplicationContext());
        //dbHelper.deleteDB(getApplicationContext());

        ListView listView = findViewById(R.id.orderList);
        TextView textAmount = findViewById(R.id.textAmount);
        TextView textPrice = findViewById(R.id.textPrice);
        Button confirmButton = findViewById(R.id.confirmButton);


        String[] orderStrings = intent.getStringArrayExtra("ORDER_STRING");
        int[] orderInts = intent.getIntArrayExtra("ORDER_INT");
        Double orderPrice = intent.getDoubleExtra("ORDER_PRICE",0);

        Order confirmedOrder = new Order(
                orderStrings[0],orderStrings[1],orderStrings[2],orderStrings[3],orderStrings[4],
                orderInts[0],orderInts[1],orderInts[2],orderInts[3],orderInts[4],
                orderPrice
        );
        int[] itemImg={
                confirmedOrder.getPcImage(),
                confirmedOrder.getKbImage(),
                confirmedOrder.getMsImage(),
                confirmedOrder.getMrImage()
        };
        String[] itemDesc={
                confirmedOrder.getPcSet(),
                confirmedOrder.getKeyboard(),
                confirmedOrder.getMouse(),
                confirmedOrder.getMonitor()
        };

        MyAdapter myAdapter = new MyAdapter(getApplicationContext(),itemImg,itemDesc);
        listView.setAdapter(myAdapter);

        textAmount.setText(getString(R.string.pcamount) + " " + confirmedOrder.getPcAmount());
        textPrice.setText(getString(R.string.price) + " " + confirmedOrder.getPrice());


        confirmButton.setOnClickListener(event->{
            sendOrderToDB(confirmedOrder);
            Intent confirmed = new Intent(this,ConfirmedActivity.class);

            StringBuilder orderString = new StringBuilder()
                    .append(getString(R.string.ordered)).append("\n")
                    .append(confirmedOrder.getPcSet()).append("\n");

            if(confirmedOrder.getKeyboard() != null){
                orderString.append(confirmedOrder.getKeyboard()).append("\n");
            }
            if(confirmedOrder.getMouse() != null){
                orderString.append(confirmedOrder.getMouse()).append("\n");
            }
            if(confirmedOrder.getMonitor() != null){
                orderString.append(confirmedOrder.getMonitor()).append("\n");
            }

            orderString.append(getString(R.string.at)).append(": ").append(getDate(confirmedOrder.getOrderDate(),"dd/MM/yyyy hh:mm:ss")).append("\n")
                    .append(getString(R.string.orderedfor)).append(": ").append(confirmedOrder.getUsername());

            confirmed.putExtra("ORDER_STRING", orderString.toString());
            startActivity(confirmed);
        });
    }
    public void sendOrderToDB(Order order){
        dbHelper.addOrder(order);
    }
    public String getDate(long milliSeconds, String dateFormat)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}