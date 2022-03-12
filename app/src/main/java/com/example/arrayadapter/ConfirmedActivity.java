package com.example.arrayadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;

public class ConfirmedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed);
        Intent intent = getIntent();

        Button smsButton = findViewById(R.id.sendSMSButton);
        Button emailButton = findViewById(R.id.sendEmailButton);
        Button returnButton = findViewById(R.id.returnButton);

        returnButton.setOnClickListener(event->{
            Intent returnIntent = new Intent(this, MainActivity.class);
            startActivity(returnIntent);
        });
        emailButton.setOnClickListener(event->{
            sendEmail(intent.getStringExtra("ORDER_STRING"));
        });
        smsButton.setOnClickListener(event->{
            sendSMS(intent.getStringExtra("ORDER_STRING"));
        });
    }
    public void sendEmail(String emailBody){
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(Uri.parse("mailto:"));
        sendIntent.putExtra(Intent.EXTRA_EMAIL, "");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order));
        sendIntent.putExtra(Intent.EXTRA_TEXT,emailBody);
        startActivity(sendIntent);
    }
    public void sendSMS(String smsBody){
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"));
        sendIntent.putExtra("sms_body", smsBody);
        startActivity(sendIntent);
    }
}