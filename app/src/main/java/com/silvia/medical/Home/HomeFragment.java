package com.silvia.medical.Home;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.silvia.medical.API;
import com.silvia.medical.Dokter.DokterActivity;
import com.silvia.medical.LoginActivity;
import com.silvia.medical.MainActivity;
import com.silvia.medical.Profil.EditProfilActivity;
import com.silvia.medical.R;
import com.silvia.medical.TinyDB;
import com.silvia.medical.databinding.FragmentHomeBinding;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    API api;

    private List<Model_Poli> dataPoli;
    private List<Model_Dokter> dataDokter;
    TinyDB tinyDB;
    String jekel,img,nama_pasien;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentHomeBinding.inflate(inflater,container,false);
       View view = binding.getRoot();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


//       setSliderViews();

        AndroidNetworking.initialize(getContext());
        api = new API();
        tinyDB = new TinyDB(getContext());






        binding.lainnyaDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), DokterActivity.class);
                startActivity(i);

            }
        });



        binding.recyclerPoli.setHasFixedSize(true);
        binding.recyclerPoli.setLayoutManager(new GridLayoutManager(getContext(),5));
        dataPoli = new ArrayList<>();
        getPoli();

        binding.recyclerDokter.setHasFixedSize(true);
        binding.recyclerDokter.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        dataDokter = new ArrayList<>();
        getDokter();

//        if (binding.nama.equals("Hi,"+"null")){
//            Intent i = new Intent(getContext(),LoginActivity.class);
//            startActivity(i);
//        }

        getSlider();



        getPasien();


       return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        binding.nama.setText("Hi,"+nama_pasien);


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
                                jekel = data.getString("jenis_kelamin_pasien");
                                Log.e("nama",nama_pasien);

                                if (jekel.equalsIgnoreCase("perempuan")){
                                    binding.imgProfil.setImageResource(R.drawable.female);
                                }else{
                                    binding.imgProfil.setImageResource(R.drawable.male);
                                }
                                binding.nama.setText("Hi,"+nama_pasien);
                            }
                            else {
                                Toast.makeText(getContext(), "Ups! Data Anda Ada yang Kosong, Silahkan Edit Profil Anda", Toast.LENGTH_LONG).show();
//                                Intent i = new Intent(getContext(), EditProfilActivity.class);
//                                startActivity(i);
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
    private void getSlider() {
        Log.e("apiSLider",api.SLIDER);
        AndroidNetworking.get(api.SLIDER)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("totalJalan", "data : "+response);
                            if (response.getString("status").equalsIgnoreCase("sukses")){
                                JSONArray data = response.getJSONArray("res");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject res = data.getJSONObject(i);
                                    img = res.getString("gambar_slider");
                                    Log.d("totalJalan", "img : " + img);
                                    setSliderViews();

                                }
                            }else {
                                Log.d("totalJalan", "kosong : "+response);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("totalJalan", "eror : "+anError);
                    }
                });
    }

    private void setSliderViews() {
        SliderView sliderView = new SliderView(getContext());
        sliderView.setImageUrl(api.GAMBAR_SLIDER+img);
        Log.e("slider",api.GAMBAR_SLIDER+img);
        sliderView.setImageScaleType(ImageView.ScaleType.FIT_CENTER);


        binding.imageSlider.addSliderView(sliderView);
        binding.imageSlider.setIndicatorAnimation(SliderLayout.Animations.FILL);
        binding.imageSlider.setScrollTimeInSec(2);

    }

    private void getPoli(){
        Log.e("api",api.KATEGORI);
        AndroidNetworking.get(api.KATEGORI)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equalsIgnoreCase("sukses")) {

                                JSONArray res = response.getJSONArray("res");
                                Gson gson = new Gson();
                                dataPoli.clear();
                                for (int i = 0; i < res.length(); i++) {
                                    JSONObject data = res.getJSONObject(i);
                                    Model_Poli Isi = gson.fromJson(data + "", Model_Poli.class);
                                    dataPoli.add(Isi);
                                }
                                Adapter_Poli adapter = new Adapter_Poli(dataPoli);
                                binding.recyclerPoli.setAdapter(adapter);
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

    private void getDokter(){
        Log.e("api",api.DOKTER);
        AndroidNetworking.get(api.DOKTER)
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
                                binding.recyclerDokter.setAdapter(adapter);
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