package com.silvia.medical.Profil;

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
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.silvia.medical.API;
import com.silvia.medical.LoginActivity;
import com.silvia.medical.MainActivity;
import com.silvia.medical.TinyDB;
import com.silvia.medical.databinding.ActivityEditProfilBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditProfilActivity extends AppCompatActivity {
    private ActivityEditProfilBinding binding;
    API api;
    TinyDB tinyDB;

    String[] jekel = {"Laki-Laki","Perempuan"};
    String tempJekel;


    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        api = new API();
        tinyDB = new TinyDB(this);
        AndroidNetworking.initialize(this);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getPasien();

        binding.edtProfilTtl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        binding.btnEdtProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfil();
            }
        });


        ArrayAdapter<String> AJekel = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, jekel);
        AJekel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.edtProfilJekel.setAdapter(AJekel);

        binding.edtProfilJekel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempJekel = jekel[i];
                Log.e("spinner", tempJekel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


    }
    private void showDateDialog(){

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                binding.edtProfilTtl.setText(dateFormatter.format(newDate.getTime()));

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.show();
    }



    private void getPasien() {
        Toast.makeText(this, "Loading . . .", Toast.LENGTH_SHORT).show();
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


                                binding.edtProfilNik.setText(data.getString("nik"));
                                binding.edtProfilNama.setText(data.getString("nama_pasien"));
                                binding.edtProfilAlamat.setText(data.getString("alamat_pasien"));
                                binding.edtProfilTtl.setText(data.getString("tgl_lahir_pasien"));
                                binding.edtProfilNohp.setText(data.getString("no_hp_pasien"));

                                binding.edtProfilNik.setEnabled(false);


                                String jekels = data.getString("jenis_kelamin_pasien");
                                if (jekels.equalsIgnoreCase("Laki-Laki")){
                                    binding.edtProfilJekel.setSelection(0);
                                }else{
                                    binding.edtProfilJekel.setSelection(1);
                                }

                            }
                            else {
//                                Toast.makeText(EditProfilActivity.this, stat, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();

                        Log.d("eror", "code :"+anError);
                        Toast.makeText(EditProfilActivity.this, ""+anError, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void editProfil(){
        AndroidNetworking.post(api.EDIT_PASIEN)
                .addBodyParameter("id_pasien", tinyDB.getString("keyIdPasien"))
                .addBodyParameter("id_user", tinyDB.getString("keyId"))
                .addBodyParameter("nama_pasien", binding.edtProfilNama.getText().toString())
                .addBodyParameter("alamat_pasien", binding.edtProfilAlamat.getText().toString())
                .addBodyParameter("tgl_lahir_pasien", binding.edtProfilTtl.getText().toString())
                .addBodyParameter("jenis_kelamin_pasien", tempJekel)
                .addBodyParameter("no_hp_pasien", binding.edtProfilNohp.getText().toString())
                .addBodyParameter("nik", binding.edtProfilNik.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String respon = response.getString("status");
                            if (respon.equalsIgnoreCase("Sukses")){
                                getPasien();
                                Intent i = new Intent(EditProfilActivity.this, LoginActivity.class);
                                startActivity(i);
                                Toast.makeText(EditProfilActivity.this, "Data Berhasil di Update", Toast.LENGTH_SHORT).show();


                            }
                            else{
                                Toast.makeText(EditProfilActivity.this, respon, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Kesalahan update, Kode 2"
                                ,Toast.LENGTH_LONG).show();
                        anError.printStackTrace();

                    }


                });
    }
}