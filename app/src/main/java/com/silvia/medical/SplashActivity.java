package com.silvia.medical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.silvia.medical.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    private int waktu_loading = 3000;
    TinyDB tinyDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tinyDB = new TinyDB(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!tinyDB.getBoolean("keyLogin")) {
                    Intent home = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(home);

                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }

        }, waktu_loading);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}