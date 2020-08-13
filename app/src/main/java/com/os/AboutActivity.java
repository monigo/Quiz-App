package com.os;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {

    Button ytbtn ;
    Button web_btn;
    Button wh_btn;
    Button tele_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ytbtn = findViewById(R.id.yt_btn);
        ytbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://chat.whatsapp.com/KSuJzJynl4W9k9Jogs7rU3")));

            }
        });
        wh_btn = findViewById(R.id.wh_btn);
        wh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/onlinesortout")));

            }
        });
        tele_btn = findViewById(R.id.tele_btn);
        tele_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/onlinesortout")));

            }
        });
        web_btn = findViewById(R.id.web_btn);
        web_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://onlinesortout.blogspot.com/")));

            }
        });
    }
}
