package com.silvia.medical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.silvia.medical.databinding.ActivityRegisterBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    String[] jekel = {"Laki-Laki","Perempuan"};
    String tempJekel;


    String[] kategoriPasien = {"Bpjs","Umum","Spesialis"};
    String tempKategori;

    boolean flagCari = false;
    int flagAkun = 99;
    API api;

    String carinik,nama_user,username,jekels,pass,alamat,telp,ttl;


    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        api = new API();
        AndroidNetworking.initialize(this);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        binding.ttl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(v);
            }
        });

        binding.LinearRegister.setVisibility(View.GONE);


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        binding.btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validasiInputCariNIK()){
                    getCariNIK();
                }

            }
        });


        ArrayAdapter<String> Adokter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,jekel);
        Adokter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.edtJekelPasienReg.setAdapter(Adokter);

        binding.edtJekelPasienReg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempJekel = jekel[i];
                Log.e("spinner",tempJekel);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        aksiTambah();

//        ArrayAdapter<String> AKategori= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,kategoriPasien);
//        AKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        binding.kategoriPasien.setAdapter(AKategori);
//
//        binding.kategoriPasien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                tempKategori = kategoriPasien[i];
//                Log.e("spinner",tempKategori);
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//
//            }
//        });


    }

    private boolean validasiInputCariNIK() {
        boolean value= true;
        carinik = binding.edtnik.getText().toString();

        int panjangNIK = carinik.length();


        if (carinik.equalsIgnoreCase("") ){
            binding.edtnik.setError("NIK Kosong");
            binding.edtnik.requestFocus();
            value = false;
        }
        else if (panjangNIK != 16){
            binding.edtnik.setError("Format Tidak Sesuai");
            binding.edtnik.requestFocus();
            value = false;
        }

        return value;
    }

    private boolean validasiInputRegister() {
        boolean value= true;
        nama_user= binding.edtpasien.getText().toString();
        username = binding.edtusername.getText().toString();
        pass = binding.edtpass.getText().toString();
        alamat = binding.alamatReg.getText().toString();
        telp = binding.nohpReg.getText().toString();
        ttl = binding.ttl.getText().toString();

        if (nama_user.equalsIgnoreCase("") ){
            binding.edtpasien.setError("Nama Pasien Kosong");
            binding.edtpasien.requestFocus();
            value = false;
        }
        if (username.equalsIgnoreCase("") ){
            binding.edtusername.setError("Username Kosong");
            binding.edtusername.requestFocus();
            value = false;
        }
        if (pass.equalsIgnoreCase("") ){
            binding.edtpass.setError("Password Kosong");
            binding.edtpass.requestFocus();
            value = false;
        }
        if (alamat.equalsIgnoreCase("") ){
            binding.alamatReg.setError("Alamat Kosong");
            binding.alamatReg.requestFocus();
            value = false;
        }
        if (telp.equalsIgnoreCase("") ){
            binding.nohpReg.setError("No HP Kosong");
            binding.nohpReg.requestFocus();
            value = false;
        }
        if (ttl.equalsIgnoreCase("") ){
            binding.ttl.setError("Tanggal Lahir Kosong");
            binding.ttl.requestFocus();
            value = false;
        }



        return value;
    }

    private void showDateDialog(View views){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Log.d("tgl",dateFormat.format(newDate.getTime()));
                binding.ttl.setText(dateFormat.format(newDate.getTime()));


            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.show();
    }


    public void aksiTambah(){
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nik = binding.edtnik.getText().toString();
                int niks = nik.length();

                        if (flagCari) {
                                if (flagAkun > 0) {
                                    if (flagAkun != 99) {
                                        // Data di pasien ada, tpi di akun juga ada
                                        Toast.makeText(RegisterActivity.this, "Akun sudah terdaftar", Toast.LENGTH_SHORT).show();
                                        flagCari = false;
                                            binding.LinearRegister.setVisibility(View.GONE);
                                    } else {
                                        if (validasiInputRegister()) {
                                            // Data pasien nya ada, tpi di akun belum ada
                                            tambahData(nama_user, alamat, telp, username, pass, nik, ttl);
                                        }
                                    }
                                } else {
                                    // Data pasien dan akun tidak ada
                                    if (validasiInputRegister()) {
                                        tambahData(nama_user, alamat, telp, username, pass, nik, ttl);
                                    }

                                }

                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Silahkan tekan tombol cari dahulu", Toast.LENGTH_SHORT).show();
                        }

//                        binding.edtnik.setText("");
//                        binding.edtpasien.setText("");
//                        binding.edtusername.setText("");
//                        binding.edtpass.setText("");
//                        binding.alamatReg.setText("");
//                        binding.btnRegister.setText("");
//                        binding.ttl.setText("");
//                        binding.nohpReg.setText("");

                    }

        });
    }

    public void tambahData(String nama_user,String alamat, String telp,
                           String username, String pass, String nik, String ttl){
        AndroidNetworking.post(api.REGISTER)
                .addBodyParameter("nama_pasien", nama_user)
                .addBodyParameter("jenis_kelamin_pasien", tempJekel)
                .addBodyParameter("alamat_pasien",alamat)
                .addBodyParameter("no_hp_pasien", telp)
                .addBodyParameter("username", username)
                .addBodyParameter("password", pass)
                .addBodyParameter("nik", nik)
                .addBodyParameter("tgl_lahir_pasien", ttl)
                .addBodyParameter("dataAkun", ""+flagAkun)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String code = response.getString("code");
                            String status = response.getString("status");
                            if (code.equalsIgnoreCase("1")){
                                flagCari = false;
                                binding.LinearRegister.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"Data berhasil ditambahkan" , Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(i);
                            }
                            else{
                                flagCari = false;
                                binding.LinearRegister.setVisibility(View.GONE);
                                Toast.makeText(RegisterActivity.this, status, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(getApplicationContext(),"Data gagal ditambahkan", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void getCariNIK(){
        String nik = binding.edtnik.getText().toString();
        Log.e("url",api.URL_CARINIK);
        AndroidNetworking.get(api.URL_CARINIK+nik)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            if (response.getString("response").equalsIgnoreCase("1")) {

                                    JSONArray res = response.getJSONArray("result");
                                    JSONObject data = res.getJSONObject(0);

                                    String nama_user = data.getString("nama_pasien");
                                    String jekel_user = data.getString("jenis_kelamin_pasien");
                                    String alamat_user = data.getString("alamat_pasien");
                                    String telp_user = data.getString("no_hp_pasien");
                                    String nik = data.getString("nik");
                                    String ttl = data.getString("tgl_lahir_pasien");
                                    flagAkun = data.getInt("dataAkun");

                                    binding.edtpasien.setText(nama_user);
                                    if (jekel_user.equalsIgnoreCase("Laki-Laki")) {
                                        binding.edtJekelPasienReg.setSelection(0);
                                    } else {
                                        binding.edtJekelPasienReg.setSelection(1);
                                    }

                                    binding.alamatReg.setText(alamat_user);
                                    binding.nohpReg.setText(telp_user);
                                    binding.edtnik.setText(nik);
                                    binding.ttl.setText(ttl);
                                Toast.makeText(RegisterActivity.this, "Pasien sudah terdaftar, lengkapi atau perbaharui data", Toast.LENGTH_SHORT).show();

                            }else {
                                // Menandakan data berdasarkan NIK suda mempunya akun
                                Toast.makeText(RegisterActivity.this, "Data Tidak Ada, Silahkan lengkapi data", Toast.LENGTH_SHORT).show();
                                flagAkun = 99;

                                binding.edtpasien.setText("");
                                binding.edtusername.setText("");
                                binding.edtpass.setText("");
                                binding.alamatReg.setText("");
                                binding.ttl.setText("");
                                binding.nohpReg.setText("");
                            }
                            flagCari = true;
                           binding.LinearRegister.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("tampil menu","response:"+anError);
                        anError.printStackTrace();
                    }
                });
    }
}