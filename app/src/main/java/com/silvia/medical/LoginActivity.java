package com.silvia.medical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.silvia.medical.Profil.ChangePasswordActivity;
import com.silvia.medical.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    API api;
    TinyDB tinyDB;
    String id_user, level;
    String password,username,password1,username1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        api = new API();
        tinyDB = new TinyDB(this);

        AndroidNetworking.initialize(this);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

            }
        });

        binding.lupaPasswrod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, LupaPassActivity.class);
                startActivity(i);

            }
        });




        binding.btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validasiInput()) {
                  getLogin();
                }
            }

        });

    }



    private boolean validasiInput() {
        boolean value= true;

        password = binding.edtpass.getText().toString();
        username = binding.edtusername.getText().toString();
        if (username.equalsIgnoreCase("") ){
            binding.edtusername.setError("Username Kosong");
            binding.edtusername.requestFocus();
            value = false;
        }
        if (password.equalsIgnoreCase("") ){
            binding.edtpass.setError("Password Kosong");
            binding.edtpass.requestFocus();
            value = false;
        }

        return value;
    }

    private void getLogin() {
        Log.d("login",api.LOGIN);
        AndroidNetworking.post(api.LOGIN)
                .addBodyParameter("username", username)
                .addBodyParameter("password",password)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                            alertDialog.hide();
                            int res = response.getInt("response");
                            String message = response.getString("status");
                            Log.d("sukses", "code" + response);

                            if (res == 1) {

                                JSONObject data = response.getJSONObject("res");
                                id_user = data.getString("id_user");

                                String nama = data.getString("nama_pasien");
                                String id_pasien = data.getString("id_pasien");
                                level = data.getString("level");
                                String jekel = data.getString("jenis_kelamin_pasien");
                                String nik = data.getString("nik");
                                String norm = data.getString("no_rekam_medis");
                                String notelp = data.getString("no_hp_pasien");
                                String alamat = data.getString("alamat_pasien");
                                String ttl = data.getString("tgl_lahir_pasien");
                                password1 = data.getString("password");
                                username1 = data.getString("username");


                                tinyDB.putString("keyId",id_user);
                                tinyDB.putString("keyNama",nama);
                                tinyDB.putString("keyJekel",jekel);
                                tinyDB.putString("keyIdPasien",id_pasien);
                                tinyDB.putString("keyNik",nik);
                                tinyDB.putString("keyRM",norm);
                                tinyDB.putString("keyTTL",ttl);
                                tinyDB.putString("keyTelp",notelp);
                                tinyDB.putString("keyAlamat",alamat);
                                Log.e("id",id_user);

                                tinyDB.putBoolean("keyLogin", true);

                                if (level.equalsIgnoreCase("Pasien")){
                                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    finish();
                                }




                            } else {
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("eror", "code :" + anError);
                        Toast.makeText(LoginActivity.this, ""+anError , Toast.LENGTH_SHORT).show();
                        }
                });


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }
}