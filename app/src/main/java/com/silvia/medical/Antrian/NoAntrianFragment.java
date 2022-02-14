package com.silvia.medical.Antrian;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.Display;
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
import com.silvia.medical.Konsultasi.Adapter_Konsultasi;
import com.silvia.medical.Konsultasi.Model_Kosultasi;
import com.silvia.medical.R;
import com.silvia.medical.TinyDB;
import com.silvia.medical.databinding.FragmentNoAntrianBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NoAntrianFragment extends Fragment {
    private FragmentNoAntrianBinding binding;
    TinyDB tinyDB;
    API api;
    private List<Model_Antrian> dataAntrian;
    String id_pasien,id_user;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNoAntrianBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        AndroidNetworking.initialize(getContext());
        tinyDB = new TinyDB(getContext());
        id_pasien = tinyDB.getString("keyIdPasien");
        id_user = tinyDB.getString("keyId");
        api = new API();

        binding.recyclerAntrian.setHasFixedSize(true);
        binding.recyclerAntrian.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        dataAntrian = new ArrayList<>();
        binding.swHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNoAntrian();
            }
        });



        return view;
    }

    private void getNoAntrian(){
        binding.swHome.setRefreshing(false);
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
                                Gson gson = new Gson();
                                dataAntrian.clear();

                                for (int i = 0; i < res.length(); i++) {
                                    JSONObject data = res.getJSONObject(i);
                                    Model_Antrian Isi = gson.fromJson(data + "", Model_Antrian.class);
                                    dataAntrian.add(Isi);

                                }
                                Adapter_Antrian adapter = new Adapter_Antrian(dataAntrian);
                                binding.recyclerAntrian.setAdapter(adapter);
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

    @Override
    public void onResume() {
        super.onResume();
        getNoAntrian();

    }
}