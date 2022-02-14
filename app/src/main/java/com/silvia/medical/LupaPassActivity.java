package com.silvia.medical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.silvia.medical.Profil.ChangePasswordActivity;
import com.silvia.medical.databinding.ActivityLupaPassBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class LupaPassActivity extends AppCompatActivity {

    private ActivityLupaPassBinding binding;
    API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLupaPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        api = new API();

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.newPass.getText().toString().equals(binding.confirm.getText().toString())){
                    editPassword();
                    Intent i = new Intent(LupaPassActivity.this, LoginActivity.class);
                    startActivity(i);
                    Toast.makeText(LupaPassActivity.this, "Password Telah DiGanti", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LupaPassActivity.this, "Password Tidak Valid!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void editPassword(){
        Log.e("api",api.LUPAPASSWORD);
        AndroidNetworking.post(api.LUPAPASSWORD)
                .addBodyParameter("username", binding.lupaUsername.getText().toString())
                .addBodyParameter("password", binding.newPass.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String respon = response.getString("status");
                            if (respon.equalsIgnoreCase("Sukses")){

                                finish();


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Kesalahan update, Kode 2"
                                ,Toast.LENGTH_LONG).show();

                    }


                });
    }



}