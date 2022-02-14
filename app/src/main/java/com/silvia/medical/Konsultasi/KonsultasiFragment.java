package com.silvia.medical.Konsultasi;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.silvia.medical.API;
import com.silvia.medical.Home.Adapter_Poli;
import com.silvia.medical.Home.Model_Poli;
import com.silvia.medical.R;
import com.silvia.medical.TinyDB;
import com.silvia.medical.databinding.FragmentKonsultasiBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class KonsultasiFragment extends Fragment {

    private FragmentKonsultasiBinding binding;
    TinyDB tinyDB;
    API api;
    String id_user;
    private List<Model_Kosultasi> dataKonsultasi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentKonsultasiBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        tinyDB = new TinyDB(getContext());
        id_user = tinyDB.getString("keyId");
        api = new API();

        binding.recyclerKonsultasi.setHasFixedSize(true);
        binding.recyclerKonsultasi.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        dataKonsultasi = new ArrayList<>();
        getRiwayat();

        return view;
    }
    private void getRiwayat(){
        Log.e("riwayat",api.RIWAYAT+id_user);
        AndroidNetworking.get(api.RIWAYAT+id_user)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equalsIgnoreCase("sukses")) {

                                JSONArray res = response.getJSONArray("res");
                                Gson gson = new Gson();
                                dataKonsultasi.clear();
                                for (int i = 0; i < res.length(); i++) {
                                    JSONObject data = res.getJSONObject(i);
                                    Model_Kosultasi Isi = gson.fromJson(data + "", Model_Kosultasi.class);
                                    dataKonsultasi.add(Isi);
                                }
                                Adapter_Konsultasi adapter = new Adapter_Konsultasi(dataKonsultasi);
                                binding.recyclerKonsultasi.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {

                                Toast.makeText(getContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
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