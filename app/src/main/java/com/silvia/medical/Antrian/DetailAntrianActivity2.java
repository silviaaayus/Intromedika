package com.silvia.medical.Antrian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.silvia.medical.API;
import com.silvia.medical.R;
import com.silvia.medical.TinyDB;
import com.silvia.medical.databinding.ActivityDetailAntrian2Binding;

public class DetailAntrianActivity2 extends AppCompatActivity {
    private ActivityDetailAntrian2Binding binding;
    TinyDB tinyDB;
    API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailAntrian2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent i = getIntent();
        binding.jamAwalDetail.setText(i.getStringExtra("jamawal")+" - ");
        binding.JamAkhirDetail.setText(i.getStringExtra("jamakhir"));
        binding.namaPoliDetail.setText(i.getStringExtra("nama_poli"));
        binding.nomorantrian.setText(i.getStringExtra("nomorantrian"));
        binding.tglKunjunganDetail.setText(i.getStringExtra("tgl_kunjungan"));
    }
}