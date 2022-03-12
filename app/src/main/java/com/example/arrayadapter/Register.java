package com.example.arrayadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private EditText loginInput, passwordInput, confirmInput;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DBHelper(getApplicationContext());

        loginInput = findViewById(R.id.loginInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmInput = findViewById(R.id.confirmPassword);

        Button registerButton = findViewById(R.id.registerButton);
        Button loginButton = findViewById(R.id.goLoginButton);

        loginButton.setOnClickListener(e->{
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        });

        registerButton.setOnClickListener(e-> register());
    }
    private void register(){
        String[] registerData={
                loginInput.getText().toString(),
                passwordInput.getText().toString(),
                confirmInput.getText().toString()
        };
        if (registerData[0].replaceAll("\\s+", "").equals("")){
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.login_empty),Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if (registerData[1].replaceAll("\\s+", "").equals("")){
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.password_empty),Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if (registerData[2].replaceAll("\\s+", "").equals("")){
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.confirm_empty),Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if(!registerData[1].equals(registerData[2])){
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.confirm_fail),Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        if (!dbHelper.checkIfLoginExists(registerData[0])){
            dbHelper.addNewUser(registerData[0],registerData[1]);
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }else {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.register_fail),Toast.LENGTH_LONG);
            toast.show();
        }
    }
}