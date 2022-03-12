package com.example.arrayadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] pcDesc;
    String[] kbDesc;
    String[] mouseDesc;
    String[] monitorDesc;
    int[] pcImg={
        R.drawable.zestaw1,
        R.drawable.zestaw2,
        R.drawable.zestaw3
    };
    int[] kbImg={
            R.drawable.keyboard3,
            R.drawable.keyboard2,
            R.drawable.keyboard1
    };
    int[] mouseImg={
            R.drawable.mouse3,
            R.drawable.mouse2,
            R.drawable.mouse1
    };
    int[] monitorImg={
            R.drawable.monitor2,
            R.drawable.monitor3,
            R.drawable.monitor1
    };
    float orderTotal = 0;
    Spinner spinner, spinnerKeyboard, spinnerMouse, spinnerMonitor;
    Button pcAdd, pcSubtract, orderButton;
    EditText pcAmount, orderName;
    CheckBox keyboardCheck, mouseCheck, monitorCheck;
    TextView priceText;
    MyAdapter pcAdapter, kbAdapter, mouseAdapter, monitorAdapter;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pcDesc= new String[]{
                "Intel Core i5-11400F / 16GB RAM / 512GB HDD / RTX3060Ti, " + getString(R.string.price) + " 5500zł",
                "AMD Ryzen 3-4300G / 8GB RAM / 256GB HDD / Radeon RX6500 XT," + getString(R.string.price) + " 2800zł",
                "Intel Celeron 2GHz / 128MB RAM / 20GB HDD / 3dfx Voodoo 2 12MB, " + getString(R.string.price) + "500zł"};
        kbDesc= new String[]{
                "Logitech K270, " + getString(R.string.price) + " 120zł",
                "SteelSeries 6GV2, " + getString(R.string.price) + " 300zł",
                "IBM Model M, " + getString(R.string.price) + " 500zł"
        };
        mouseDesc= new String[]{
                "Logitech B100, " + getString(R.string.price) + " 40zł",
                "SteelSeries Rival 3, " + getString(R.string.price) + " 120zł",
                "Microsoft Intellimouse 3.0, " + getString(R.string.price) + " 220zł"
        };
        monitorDesc= new String[]{
                "Samsung F24T356FHRX, " + getString(R.string.price) + " 600zł",
                "iiyama G-Master G2740QSU, " + getString(R.string.price) + " 1140zł",
                "Sony GDM-FW900, " + getString(R.string.price) + " 2800zł"
        };
        sharedPreferences = this.getSharedPreferences("PREF",Context.MODE_PRIVATE);

        /*DBHelper dbHelper = new DBHelper(getApplicationContext());
        dbHelper.deleteDB(getApplicationContext());
        sharedPreferences.edit().clear().apply();*/

        if(!sharedPreferences.getBoolean("isLoggedIn",false)){
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }


        spinner = findViewById(R.id.spinner);
        spinnerKeyboard = findViewById(R.id.spinnerKb);
        spinnerMouse = findViewById(R.id.spinnerMouse);
        spinnerMonitor = findViewById(R.id.spinnerMonitor);
        spinner.setOnItemSelectedListener(this);
        spinnerKeyboard.setOnItemSelectedListener(this);
        spinnerMonitor.setOnItemSelectedListener(this);
        spinnerMouse.setOnItemSelectedListener(this);

        pcAdd = findViewById(R.id.addPc);
        pcSubtract = findViewById(R.id.minusPc);
        pcAmount = findViewById(R.id.pcNumInput);

        orderName = findViewById(R.id.nameInput);

        keyboardCheck = findViewById(R.id.keyboardCheck);
        mouseCheck = findViewById(R.id.mouseCheck);
        monitorCheck = findViewById(R.id.monitorCheck);

        priceText = findViewById(R.id.priceValue);

        pcAdapter = new MyAdapter(getApplicationContext(), pcImg, pcDesc);
        kbAdapter = new MyAdapter(getApplicationContext(), kbImg, kbDesc);
        mouseAdapter = new MyAdapter(getApplicationContext(), mouseImg, mouseDesc);
        monitorAdapter = new MyAdapter(getApplicationContext(), monitorImg, monitorDesc);
        spinner.setAdapter(pcAdapter);
        spinnerKeyboard.setAdapter(kbAdapter);
        spinnerMouse.setAdapter(mouseAdapter);
        spinnerMonitor.setAdapter(monitorAdapter);

        orderButton = findViewById(R.id.orderButton);
        orderButton.setOnClickListener(view -> {
            if(orderName.getText() == null || orderName.getText().toString().equals("")){
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.edittexterror),Toast.LENGTH_LONG);
                toast.show();
            }else if(getPcAmount() <= 0){
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.pcamounterror),Toast.LENGTH_LONG);
                toast.show();
            }else {
                Order newOrder = getOrder();
                Intent intent = new Intent(this, OrderConfirm.class);
                intent.putExtra("ORDER_STRING", new String[]{
                        newOrder.getPcSet(), newOrder.getKeyboard(), newOrder.getMouse(), newOrder.getMonitor(), newOrder.getUsername()
                });
                intent.putExtra("ORDER_INT", new int[]{
                        newOrder.getPcImage(), newOrder.getKbImage(), newOrder.getMsImage(), newOrder.getMrImage(), newOrder.getPcAmount()
                });
                intent.putExtra("ORDER_PRICE", newOrder.getPrice());
                startActivity(intent);
            }
        });


        addonCheck();
        setPcAmount();
        loadPreference(sharedPreferences);
    }

    @Override
    protected void onDestroy() {
        //sharedPreferences.edit().putBoolean("isLoggedIn",false).apply();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        //sharedPreferences.edit().putBoolean("isLoggedIn",false).apply();
        super.onStop();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        setPrice();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void addonCheck(){
        keyboardCheck.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                spinnerKeyboard.setVisibility(View.VISIBLE);
            }else{
                spinnerKeyboard.setVisibility(View.GONE);
            }
            setPrice();
        });
        mouseCheck.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                spinnerMouse.setVisibility(View.VISIBLE);
            }else{
                spinnerMouse.setVisibility(View.GONE);
            }
            setPrice();
        });
        monitorCheck.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                spinnerMonitor.setVisibility(View.VISIBLE);
            }else{
                spinnerMonitor.setVisibility(View.GONE);
            }
            setPrice();
        });
    }
    public void setPcAmount(){
        AtomicInteger amount = new AtomicInteger(1);
        pcAmount.setText(String.valueOf(amount.get()));
        pcAdd.setOnClickListener(view -> {
            amount.set(Integer.parseInt(pcAmount.getText().toString()));
            amount.getAndIncrement();
            pcAmount.setText(String.valueOf(amount.get()));
            setPrice();
        });
        pcSubtract.setOnClickListener(view -> {
            amount.set(Integer.parseInt(pcAmount.getText().toString()));
            if (amount.get() > 1){
                amount.getAndDecrement();
                pcAmount.setText(String.valueOf(amount.get()));
            }
            setPrice();
        });
    }
    public void setPrice(){
        orderTotal=0;
        switch(spinner.getSelectedItemPosition()){
            case 0: orderTotal += 5500 ;break;
            case 1: orderTotal += 2800 ;break;
            case 2: orderTotal += 500 ;break;
        }
        if(keyboardCheck.isChecked()){
            switch(spinnerKeyboard.getSelectedItemPosition()){
                case 0:
                    orderTotal+=120;break;
                case 1:
                    orderTotal+=300;break;
                case 2:
                    orderTotal+=500;break;
            }
        }
        if(mouseCheck.isChecked()){
            switch(spinnerMouse.getSelectedItemPosition()){
                case 0:
                    orderTotal+=40;break;
                case 1:
                    orderTotal+=120;break;
                case 2:
                    orderTotal+=220;break;
            }
        }
        if(monitorCheck.isChecked()){
            switch (spinnerMonitor.getSelectedItemPosition()){
                case 0:
                    orderTotal+=600;break;
                case 1:
                    orderTotal+=1140;break;
                case 2:
                    orderTotal+=2800;break;
            }
        }
        orderTotal *= getPcAmount();
        priceText.setText(String.format("%s zł", orderTotal));
    }
    public int getPcAmount(){
        return Integer.parseInt(pcAmount.getText().toString());
    }
    public Order getOrder(){
        int kbImage, mouseImage, monitorImage;
        String keyboard, mouse, monitor;
        if(keyboardCheck.isChecked()) {
            kbImage = kbImg[spinnerKeyboard.getSelectedItemPosition()];
            keyboard = kbDesc[spinnerKeyboard.getSelectedItemPosition()];
        }else{
            kbImage = -1;
            keyboard = null;
        }
        if(mouseCheck.isChecked()) {
            mouseImage = mouseImg[spinnerMouse.getSelectedItemPosition()];
            mouse = mouseDesc[spinnerMouse.getSelectedItemPosition()];
        }else{
            mouseImage = -1;
            mouse = null;
        }
        if(monitorCheck.isChecked()) {
            monitorImage = monitorImg[spinnerMonitor.getSelectedItemPosition()];
            monitor = monitorDesc[spinnerMonitor.getSelectedItemPosition()];
        }else{
            monitorImage = -1;
            monitor = null;
        }
        return new Order(
                pcDesc[spinner.getSelectedItemPosition()],keyboard,mouse,monitor,
                orderName.getText().toString(),
                pcImg[spinner.getSelectedItemPosition()],
                kbImage,mouseImage,monitorImage,
                Integer.parseInt(pcAmount.getText().toString()),
                (double) orderTotal
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.viewOrders:
                intent = new Intent(this,OrderList.class);
                startActivity(intent);
                return true;
            case R.id.shareOrder:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                StringBuilder orderString = new StringBuilder()
                        .append(getOrder().getPcSet()).append("\n");

                if(getOrder().getKeyboard() != null){
                    orderString.append(getOrder().getKeyboard()).append("\n");
                }
                if(getOrder().getMouse() != null){
                    orderString.append(getOrder().getMouse()).append("\n");
                }
                if(getOrder().getMonitor() != null){
                    orderString.append(getOrder().getMonitor()).append("\n");
                }
                intent.putExtra(Intent.EXTRA_TEXT,orderString.toString());
                startActivity(Intent.createChooser(intent,null));
                return true;
            case R.id.saveLogin:
                sharedPreferences.edit().putString("loginInput",getIntent().getStringExtra("login")).apply();
                sharedPreferences.edit().putString("passwordInput",getIntent().getStringExtra("password")).apply();
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.saved_login),Toast.LENGTH_LONG);
                toast.show();
                return true;
            case R.id.about:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setMessage(R.string.about)
                        .setPositiveButton(R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss()).create();
                dialog.show();
                return true;
            case R.id.logOut:
                sharedPreferences.edit().putBoolean("isLoggedIn",false).apply();
                Intent login = new Intent(this, Login.class);
                startActivity(login);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void savePreference(SharedPreferences.Editor editor){
        editor.putInt("spinnerPC",spinner.getSelectedItemPosition());
        editor.putBoolean("keyboardCheck", keyboardCheck.isChecked());
        editor.putInt("spinnerKeyboard",spinnerKeyboard.getSelectedItemPosition());
        editor.putBoolean("mouseCheck",mouseCheck.isChecked());
        editor.putInt("spinnerMouse",spinnerMouse.getSelectedItemPosition());
        editor.putBoolean("monitorCheck",monitorCheck.isChecked());
        editor.putInt("spinnerMonitor",spinnerMonitor.getSelectedItemPosition());
        editor.putString("orderName",orderName.getText().toString());
        editor.apply();
    }
    public void loadPreference(SharedPreferences preferences){
        spinner.setSelection(preferences.getInt("spinnerPC",0));
        keyboardCheck.setChecked(preferences.getBoolean("keyboardCheck",false));
        spinnerKeyboard.setSelection(preferences.getInt("spinnerKeyboard",0));
        mouseCheck.setChecked(preferences.getBoolean("mouseCheck",false));
        spinnerMouse.setSelection(preferences.getInt("spinnerMouse",0));
        monitorCheck.setChecked(preferences.getBoolean("monitorCheck",false));
        spinnerMonitor.setSelection(preferences.getInt("spinnerMonitor",0));
        orderName.setText(preferences.getString("orderName", null));
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        savePreference(sharedPreferences.edit());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        loadPreference(sharedPreferences);
        super.onRestoreInstanceState(savedInstanceState);
    }
}