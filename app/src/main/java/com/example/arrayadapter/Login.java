package com.example.arrayadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText loginInput, passwordInput;
    private DBHelper dbHelper;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DBHelper(getApplicationContext());
        sharedPreferences = this.getSharedPreferences("PREF",Context.MODE_PRIVATE);

        Button loginButton = findViewById(R.id.loginButton);
        Button registerButton = findViewById(R.id.goRegisterButton);
        loginInput = findViewById(R.id.loginInput);
        passwordInput = findViewById(R.id.passwordInput);
        loadPreferences();

        registerButton.setOnClickListener(e->{
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        });
        loginButton.setOnClickListener(e-> login());
    }
    private boolean checkLoginCorrect(){
        return passwordInput.getText().toString().equals(dbHelper.getPasswordByLogin(loginInput.getText().toString()));
    }
    private void login(){
        if (loginInput.getText().toString().replaceAll("\\s+", "").equals("")){
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.login_empty),Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if (passwordInput.getText().toString().replaceAll("\\s+", "").equals("")){
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.password_empty),Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if (checkLoginCorrect()){
            sharedPreferences.edit().putBoolean("isLoggedIn", true).apply();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("login",loginInput.getText().toString());
            intent.putExtra("password",passwordInput.getText().toString());
            startActivity(intent);
        }else {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.login_fail),Toast.LENGTH_LONG);
            toast.show();
        }
    }
    private void loadPreferences(){
        loginInput.setText(sharedPreferences.getString("loginInput",null));
        passwordInput.setText(sharedPreferences.getString("passwordInput",null));
    }
}