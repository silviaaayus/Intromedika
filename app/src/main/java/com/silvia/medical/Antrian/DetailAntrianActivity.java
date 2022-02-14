package com.silvia.medical.Antrian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.silvia.medical.API;
import com.silvia.medical.R;
import com.silvia.medical.TinyDB;
import com.silvia.medical.databinding.ActivityDetailAntrianBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailAntrianActivity extends AppCompatActivity {
    private ActivityDetailAntrianBinding binding;
    TinyDB tinyDB;
    API api;
    String id_pasien,id_user;
    String jam_awal,jam_akhir,tgl,namapoli,antrian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailAntrianBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tinyDB = new TinyDB(this);
        api = new API();

        tinyDB = new TinyDB(this);
        id_pasien = tinyDB.getString("keyIdPasien");
        id_user = tinyDB.getString("keyId");

        getNoAntrian();

    }

    private void getNoAntrian(){
        Log.e("antrian",api.ANTRIAN+id_pasien+"&id_user="+id_user);
        AndroidNetworking.get(api.ANTRIAN+id_pasien+"&id_user="+id_user)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equalsIgnoreCase("Sukses")) {
                                JSONArray res = response.getJSONArray("res");
                                for (int i = 0; i < res.length(); i++) {
                                    JSONObject data = res.getJSONObject(i);

                                    binding.jamAwalDetail.setText(data.getString("jam_awal")+" - ");
                                    binding.JamAkhirDetail.setText(data.getString("jam_akhir"));
                                    binding.tglKunjunganDetail.setText(data.getString("tgl_kunjungan"));
                                    binding.namaPoliDetail.setText(data.getString("nama_poli"));
                                    binding.nomorantrian.setText(data.getString("no_antrian"));
                                }

//
                            } else {

                                Toast.makeText(DetailAntrianActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onResume() {
        super.onResume();

    }
}