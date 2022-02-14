package com.silvia.medical.Dokter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.silvia.medical.API;
import com.silvia.medical.Home.Adapter_Dokter;
import com.silvia.medical.Home.Model_Dokter;
import com.silvia.medical.databinding.ActivityDokterBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DokterActivity extends AppCompatActivity {
    private ActivityDokterBinding binding;
    API api;
    private List<Model_Dokter> dataDokter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDokterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        api = new API();
        AndroidNetworking.initialize(this);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.recDokterlain.setHasFixedSize(true);
        binding.recDokterlain.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        dataDokter = new ArrayList<>();
        getDokterLain();


    }

    private void getDokterLain() {
        Log.e("api",api.DOKTER_SEMUA);
        AndroidNetworking.get(api.DOKTER_SEMUA)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equalsIgnoreCase("sukses")) {

                                JSONArray res = response.getJSONArray("res");
                                Gson gson = new Gson();
                                dataDokter.clear();
                                for (int i = 0; i < res.length(); i++) {
                                    JSONObject data = res.getJSONObject(i);
                                    Model_Dokter Isi = gson.fromJson(data + "", Model_Dokter.class);
                                    dataDokter.add(Isi);
                                }
                                Adapter_Dokter adapter = new Adapter_Dokter(dataDokter);
                                binding.recDokterlain.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {

                                Toast.makeText(DokterActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("tampil menu", "response:" + anError);
                    }
                });


    }
}