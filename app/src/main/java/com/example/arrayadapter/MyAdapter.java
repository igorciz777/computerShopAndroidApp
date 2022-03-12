package com.example.arrayadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
    Context context;
    int[] img;
    String[] desc;
    LayoutInflater layoutInflater;
    ImageView imageView;
    TextView textView;

    public MyAdapter(Context context, int[] img, String[] desc){
        super();
        this.context = context;
        this.img = img;
        this.desc = desc;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (img[i]==-1){return new View(context);}
        view = layoutInflater.inflate(R.layout.my_spinner_items, null);
        imageView = view.findViewById(R.id.imageView);
        textView = view.findViewById(R.id.textView);
        imageView.setImageResource(img[i]);
        textView.setText(desc[i]);
        return view;
    }
}
