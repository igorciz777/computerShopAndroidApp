package com.example.arrayadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class OrderAdapter extends BaseAdapter {
    Context context;
    List<Order> orderList;
    ImageView imageViewPc, imageViewKeyboard, imageViewMouse, imageViewMonitor;
    TextView textViewPc, textViewKeyboard, textViewMouse, textViewMonitor, textViewName, textViewPrice, textViewDate, textViewAmount;
    LayoutInflater layoutInflater;

    public OrderAdapter(Context context, List<Order> orderList){
        super();
        this.context = context;
        this.orderList = orderList;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int i) {
        return orderList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.order_items, null);

        imageViewPc = view.findViewById(R.id.imageViewPc);
        textViewPc = view.findViewById(R.id.textViewPc);
        imageViewPc.setImageResource(orderList.get(i).getPcImage());
        textViewPc.setText(orderList.get(i).getPcSet());

        imageViewKeyboard = view.findViewById(R.id.imageViewKeyboard);
        textViewKeyboard = view.findViewById(R.id.textViewKeyboard);
        imageViewKeyboard.setImageResource(orderList.get(i).getKbImage());
        textViewKeyboard.setText(orderList.get(i).getKeyboard());

        imageViewMouse = view.findViewById(R.id.imageViewMouse);
        textViewMouse = view.findViewById(R.id.textViewMouse);
        imageViewMouse.setImageResource(orderList.get(i).getMsImage());
        textViewMouse.setText(orderList.get(i).getMouse());

        imageViewMonitor = view.findViewById(R.id.imageViewMonitor);
        textViewMonitor = view.findViewById(R.id.textViewMonitor);
        imageViewMonitor.setImageResource(orderList.get(i).getMrImage());
        textViewMonitor.setText(orderList.get(i).getMonitor());

        textViewName = view.findViewById(R.id.orderName);
        textViewName.setText(orderList.get(i).getUsername());
        textViewPrice = view.findViewById(R.id.textViewPrice);
        textViewPrice.setText(String.valueOf(orderList.get(i).getPrice()));
        textViewDate = view.findViewById(R.id.textViewDate);
        textViewDate.setText(getDate(orderList.get(i).getOrderDate(),"dd/MM/yyyy hh:mm:ss"));

        textViewAmount = view.findViewById(R.id.textViewAmount);
        textViewAmount.setText(context.getString(R.string.amount) + orderList.get(i).getPcAmount());

        return view;
    }
    public String getDate(long milliSeconds, String dateFormat)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
