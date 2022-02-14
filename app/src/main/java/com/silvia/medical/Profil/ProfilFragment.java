package com.silvia.medical.Profil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.silvia.medical.API;
import com.silvia.medical.LoginActivity;
import com.silvia.medical.R;
import com.silvia.medical.TinyDB;
import com.silvia.medical.databinding.FragmentProfilBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfilFragment extends Fragment {
    private FragmentProfilBinding binding;
    TinyDB tinyDB;
    API api;
    String nama_pasien,nik,jekel;



    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfilBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        api = new API();
        tinyDB = new TinyDB(getContext());
        AndroidNetworking.initialize(getContext());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        binding.lineEdtProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),EditProfilActivity.class);
                startActivity(i);
            }
        });



        binding.btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tinyDB.clear();
                Intent i = new Intent(getContext(), LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        binding.linePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ChangePasswordActivity.class);
                startActivity(i);

            }
        });
        getPasien();



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        getPasien();

        binding.namapasienProfil.setText(nama_pasien);
        binding.nikprofile.setText(nik);

    }

    private void getPasien() {
        AndroidNetworking.get(api.PASIENEDT+tinyDB.getString("keyIdPasien"))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String stat = response.getString("status");

                            if (stat.equalsIgnoreCase("Sukses")){
                                JSONArray res = response.getJSONArray("res");
                                JSONObject data = res.getJSONObject(0);
                                nama_pasien = data.getString("nama_pasien");
                                nik = data.getString("nik");
                                jekel = data.getString("jenis_kelamin_pasien");
                                Log.e("jekel",jekel);
                                binding.namapasienProfil.setText(nama_pasien);
                                binding.nikprofile.setText(nik);

                                if (jekel.equalsIgnoreCase("Perempuan")){
                                    binding.imgProfil.setImageResource(R.drawable.female);
                                }else{
                                    binding.imgProfil.setImageResource(R.drawable.male);
                                }

                            }
                            else {
                                Toast.makeText(getContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();

                        Log.d("eror", "code :"+anError);
                        Toast.makeText(getContext(), ""+anError, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}