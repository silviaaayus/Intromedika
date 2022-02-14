 package com.silvia.medical.Booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.silvia.medical.API;
import com.silvia.medical.Antrian.DetailAntrianActivity;
import com.silvia.medical.Antrian.NoAntrianFragment;
import com.silvia.medical.R;
import com.silvia.medical.TinyDB;
import com.silvia.medical.databinding.ActivityBookingBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static android.view.View.GONE;

 public class BookingActivity extends AppCompatActivity {

    private ActivityBookingBinding binding;

    API api;
    TinyDB tinyDB;
    String id_poli,tgl,id_jadwal,kode_poli,id_user,maksimal,pelayanan,jambuka, jamtutup;
    View view;
    String ttl=" ";
    Spinner spinjekel,spinKategori,spinKategoriLama;
    String tempJekel,tempKategori,tempKategoriLama;
    int total=0;
    EditText nik,norm;
    ArrayList<String> pasien = new ArrayList<>();
    ArrayList<String> nikpasien = new ArrayList<>();
    HashMap<String,String> hashPasien = new HashMap<>();
    ArrayAdapter<String> adapter;

    String nama_pasien,tgl_lahir_pasien, jenis_kelamin_pasien, kategori_pasien,no_hp_pasien, alamat_pasien,nikk,id_pasien,no_rm;
     TextView nama, niks, jenis_kelamin, nohp , alamat ,ttl_cari ;



     String pasienbaru,nikbaru,alamatbaru,nohpbaru,ttl_baru;

    BottomSheetBehavior sheetBehavior;
    BottomSheetDialog sheetDialog;
    View bottom_sheet;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        tinyDB = new TinyDB(this);

        id_user = tinyDB.getString("keyId");

        Intent i = getIntent();
        tgl = i.getStringExtra("tgl");
        id_jadwal = i.getStringExtra("id_jadwal");
        kode_poli = i.getStringExtra("kode_poli");
        id_poli = i.getStringExtra("Id Poli");
        maksimal = i.getStringExtra("maksimal");
        pelayanan = i.getStringExtra("pelayanan");

        Log.e("tglbooking",tgl);
        Log.e("maksimal",maksimal);

        binding.jambukapoli.setText(i.getStringExtra("Jam_Buka")+" - ");
        binding.jamtutuppoli.setText(i.getStringExtra("Jam_Tutup"));





        bottom_sheet = findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);


        DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        try {
            String tglBaru= dateFormat.format(df.parse(tgl));
            binding.tanggalAntrian.setText(tglBaru);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        api = new API();
        AndroidNetworking.initialize(this);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Log.e("total",""+total);
        binding.rdPilihPasien.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (binding.rdPasienBaru.isChecked()){
                    showBottomSheetDialogPasienLama();
                }else{
                    showBottomSheetDialogDataSendiri();
                }
            }
        });

        detailAntrian();

    }

    private void showDateDialog(View views){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Log.d("tgl",dateFormatter.format(newDate.getTime()));
                ttl = dateFormatter.format(newDate.getTime());
                TextView ttl_new = views.findViewById(R.id.ttl_new);
                ttl_new.setText(ttl);

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.show();
    }

    private void showBottomSheetDialogDataSendiri() {
        view = getLayoutInflater().inflate(R.layout.detail_caripasien, null);

        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        String[] layanan = pelayanan.split(",");

        ArrayAdapter<String> AKategoriLama= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,layanan);
        AKategoriLama.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKategoriLama= view.findViewById(R.id.kategori_pasien_lama);
        spinKategoriLama.setAdapter(AKategoriLama);

        spinKategoriLama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempKategoriLama = layanan[i];
                Log.e("spinner",tempKategoriLama);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
         nama = view.findViewById(R.id.nama);
         niks = view.findViewById(R.id.nikCari);
         jenis_kelamin = view.findViewById(R.id.jekel_cari);
         nohp = view.findViewById(R.id.nohp);
         alamat = view.findViewById(R.id.alamat_cari);
         ttl_cari = view.findViewById(R.id.ttl_new_cari);

         getPasien();




        view.findViewById(R.id.btnpasienlama).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pasien = nama.getText().toString();
                String nikpasienlama = niks.getText().toString();
                String jekelpasienlama = jenis_kelamin.getText().toString();
                String hplama = nohp.getText().toString();
                String alamatlama = alamat.getText().toString();
                String ttllama = ttl_cari.getText().toString();

                getNoAntrianLama( pasien, nikpasienlama, jekelpasienlama, hplama, alamatlama, ttllama);
                finish();

            }
        });

        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sheetDialog = new BottomSheetDialog(this);
        sheetDialog.setContentView(view);
        sheetDialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            sheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        sheetDialog.show();
        sheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                sheetDialog.dismiss();
                sheetDialog = null;

            }
        });
    }

    private void showBottomSheetDialogPasienBaru() {
        View view = getLayoutInflater().inflate(R.layout.form_pendaftaranpasienbaru, null);

        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        (view.findViewById(R.id.back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });

        view.findViewById(R.id.ttl_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(view);
            }
        });

        String[] jekels = {"Laki-Laki","Perempuan"};

//        String[] kategori = {"BPJS","Umum","Spesialis"};
        String[] layanan = pelayanan.split(",");

        ArrayAdapter<String> AKategori= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,layanan);
        AKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKategori= view.findViewById(R.id.kategori_pasien_new);
        spinKategori.setAdapter(AKategori);

        spinKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempKategori = layanan[i];
                Log.e("spinner",tempKategori);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        ArrayAdapter<String> Adokter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,jekels);
        Adokter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinjekel= view.findViewById(R.id.edtJekel_new);
        spinjekel.setAdapter(Adokter);

        spinjekel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempJekel = jekels[i];
                Log.e("spinner",tempJekel);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        EditText nama = view.findViewById(R.id.edt_namaPasienbaru);
        EditText nikpasien = view.findViewById(R.id.edt_nikPasienbaru);
        EditText alamatpasien = view.findViewById(R.id.edt_alamatpasien);
        EditText hpbaru = view.findViewById(R.id.edt_nohp);
        TextView ttlbaru = view.findViewById(R.id.ttl_new);



        view.findViewById(R.id.btnpasienbaru).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                pasienbaru = nama.getText().toString();
                nikbaru = nikpasien.getText().toString();
                alamatbaru = alamatpasien.getText().toString();
                nohpbaru = hpbaru.getText().toString();
                ttl_baru = ttlbaru.getText().toString();


                if (pasienbaru.equalsIgnoreCase("")||nikbaru.equalsIgnoreCase("")||
                        alamatbaru.equalsIgnoreCase("")||nohpbaru.equalsIgnoreCase("")||
                ttl_baru.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"Semua data harus diisi" , Toast.LENGTH_SHORT).show();

                }else{
                    if (nikbaru.length()!=16){
                        Toast.makeText(BookingActivity.this, "Format NIK Tidak Sesuai", Toast.LENGTH_SHORT).show();
                    }else{
                        getNoAntrianBaru();
                        finish();
                    }

                }
            }
        });

        sheetDialog = new BottomSheetDialog(this);
        sheetDialog.setContentView(view);
        sheetDialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            sheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        sheetDialog.show();
        sheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                sheetDialog = null;
            }
        });
    }

    private void showBottomSheetDialogPasienLama() {
        getAllPasien();

        view = getLayoutInflater().inflate(R.layout.form_caripasien, null);

        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        (view.findViewById(R.id.back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        ImageView cari = view.findViewById(R.id.btn_caripasien);


            cari.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showBottomSheetDialogPasienBaru();

                }
            });




        sheetDialog = new BottomSheetDialog(this);
        sheetDialog.setContentView(view);
        sheetDialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            sheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        sheetDialog.show();
        sheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                sheetDialog = null;
            }
        });
    }

    private void showBottomSheetDialogCariPasienLama() {
        view = getLayoutInflater().inflate(R.layout.detail_caripasien, null);

        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        String[] layanan = pelayanan.split(",");

        ArrayAdapter<String> AKategoriLama= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,layanan);
        AKategoriLama.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKategoriLama= view.findViewById(R.id.kategori_pasien_lama);
        spinKategoriLama.setAdapter(AKategoriLama);

        spinKategoriLama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempKategoriLama = layanan[i];
                Log.e("spinner",tempKategoriLama);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        TextView nama = view.findViewById(R.id.nama);
        TextView niks = view.findViewById(R.id.nikCari);
        TextView jenis_kelamin = view.findViewById(R.id.jekel_cari);
        TextView nohp = view.findViewById(R.id.nohp);
        TextView alamat = view.findViewById(R.id.alamat_cari);
        TextView ttl_cari = view.findViewById(R.id.ttl_new_cari);

        nama.setText(nama_pasien);
        niks.setText(nikk);
        jenis_kelamin.setText(jenis_kelamin_pasien);
        nohp.setText(no_hp_pasien);
        alamat.setText(alamat_pasien);
        ttl_cari.setText(tgl_lahir_pasien);

        view.findViewById(R.id.btnpasienlama).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pasien = nama.getText().toString();
                String nikpasienlama = niks.getText().toString();
                String jekelpasienlama = jenis_kelamin.getText().toString();
                String hplama = nohp.getText().toString();
                String alamatlama = alamat.getText().toString();
                String ttllama = ttl_cari.getText().toString();

                getNoAntrianLama( pasien, nikpasienlama, jekelpasienlama, hplama, alamatlama, ttllama);
                finish();

            }
        });

        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sheetDialog = new BottomSheetDialog(this);
        sheetDialog.setContentView(view);
        sheetDialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            sheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        sheetDialog.show();
        sheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                sheetDialog = null;
            }
        });
    }


    public void getNotif(){
        Log.e("notif",api.NOTIF);
        AndroidNetworking.post(api.NOTIF)
                .addBodyParameter("level","Admin")
                .addBodyParameter("title","Booking Baru!")
                .addBodyParameter("message","Booking Baru Masuk")
                .addBodyParameter("url","booking")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(BookingActivity.this, "Notif Berhasil", Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void onError(ANError error) {
                     error.printStackTrace();
//                        Toast.makeText(getApplicationContext(), "Notif salah"
//                                ,Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void getNotifApoteker(){
        Log.e("notif",api.NOTIF);
        AndroidNetworking.post(api.NOTIF)
                .addBodyParameter("level","Apoteker")
                .addBodyParameter("title","Booking Baru!")
                .addBodyParameter("message","Booking Baru Masuk")
                .addBodyParameter("url","booking")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(BookingActivity.this, "Notif Berhasil", Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void onError(ANError error) {
                        error.printStackTrace();
//                        Toast.makeText(getApplicationContext(), "Notif salah"
//                                ,Toast.LENGTH_LONG).show();
                    }
                });
    }


    public void getNoAntrianBaru(){

        AndroidNetworking.post(api.BOOKING)
                .addBodyParameter("nik", nikbaru)
                .addBodyParameter("nama_pasien", pasienbaru)
                .addBodyParameter("jenis_kelamin_pasien",tempJekel)
                .addBodyParameter("kategori_pasien",tempKategori)
                .addBodyParameter("id_jadwal",id_jadwal)
                .addBodyParameter("tgl_kunjungan",tgl)
                .addBodyParameter("alamat_pasien",alamatbaru)
                .addBodyParameter("no_hp_pasien",nohpbaru)
                .addBodyParameter("tgl_lahir_pasien",ttl_baru)
                .addBodyParameter("id_user",id_user)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        try {
                            String message = null;
                            message = response.getString("status");
                            String respon = response.getString("response");
                            if(respon.equalsIgnoreCase("1")){
//                                Intent j = new Intent(BookingActivity.this, DetailAntrianActivity.class);
//                                startActivity(j);
                                getNotif();
                                getNotifApoteker();
                                Toast.makeText(getApplicationContext(), message
                                        ,Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(getApplicationContext(), message
                                        ,Toast.LENGTH_LONG).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        Log.e("salah",""+error);
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Kesalahan tambah, Kode 2"
                                ,Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void getNoAntrianLama(String pasien, String nikpasienlama, String jekelpasienlama, String hplama, String alamatlama, String ttllama){
        Log.e("update",api.BOOKING);
        Log.e("kategori",tempKategoriLama);

        AndroidNetworking.post(api.BOOKING)
                .addBodyParameter("nik", nikpasienlama)
                .addBodyParameter("nama_pasien", pasien)
                .addBodyParameter("jenis_kelamin_pasien", jekelpasienlama)
                .addBodyParameter("no_hp_pasien", hplama)
                .addBodyParameter("kategori_pasien",tempKategoriLama)
                .addBodyParameter("id_jadwal",id_jadwal)
                .addBodyParameter("tgl_kunjungan",tgl)
                .addBodyParameter("alamat_pasien",alamatlama)
                .addBodyParameter("tgl_lahir_pasien",ttllama)
                .addBodyParameter("id_user",id_user)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try {
                            String respo = response.getString("response");
                            String status = response.getString("status");

                            if(respo.equalsIgnoreCase("1")){
                                Toast.makeText(getApplicationContext(), status
                                        ,Toast.LENGTH_LONG).show();
//                                Intent j = new Intent(BookingActivity.this, DetailAntrianActivity.class);
//                                startActivity(j);
                                getNotifApoteker();
                                getNotif();

                            }else{
                                Toast.makeText(getApplicationContext(), status
                                        ,Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Log.e("salah",""+error);
                        Toast.makeText(getApplicationContext(), "Kesalahan"
                                ,Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void getAllPasien(){
        AndroidNetworking.get(api.ALLPASIEN)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            if (response.getString("status").equalsIgnoreCase("sukses")) {

                                JSONArray res = response.getJSONArray("res");
                                for(int i = 0; i<res.length();i++){
                                    JSONObject data = res.getJSONObject(i);
                                    pasien.add(data.getString("nama_pasien")+" | "+data.getString("nik"));
                                    nikpasien.add(data.getString("nik"));
                                    hashPasien.put(
                                            data.getString("nama_pasien")+" | "+data.getString("nik"),
                                            data.getString("nik")
                                            );
                                }
                                    setAdapter();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("tampil menu","response:"+anError);
                    }
                });
    }

    public void getCariPasien(String snik){
        Log.e("cari",api.PASIENLAMA+snik);
        AndroidNetworking.get(api.PASIENLAMA+snik)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            if (response.getString("status").equalsIgnoreCase("sukses")) {

                                JSONArray res = response.getJSONArray("res");
                                JSONObject data = res.getJSONObject(0);

                                id_pasien = data.getString("id_pasien");
                                no_rm = data.getString("no_rekam_medis");
                                nikk = data.getString("nik");
                                nama_pasien = data.getString("nama_pasien");
                                tgl_lahir_pasien = data.getString("tgl_lahir_pasien");
                                jenis_kelamin_pasien = data.getString("jenis_kelamin_pasien");
                                kategori_pasien = data.getString("kategori_pasien");
                                no_hp_pasien = data.getString("no_hp_pasien");
                                alamat_pasien = data.getString("alamat_pasien");

                                showBottomSheetDialogCariPasienLama();

                                Toast.makeText(BookingActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();

                            }else {

                                 Toast.makeText(BookingActivity.this, "Data Kosong, Silahkan Isi Data Anda", Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("tampil menu","response:"+anError);
                    }
                });
    }

    public void detailAntrian(){
        Log.e("url",api.DETAIL_JADWAL+tgl+"&id_jadwal="+id_jadwal);
        AndroidNetworking.get(api.DETAIL_JADWAL+tgl+"&id_jadwal="+id_jadwal)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            if (response.getString("status").equalsIgnoreCase("sukses")) {

                                JSONArray res = response.getJSONArray("res");
                                JSONObject data = res.getJSONObject(0);

                                int menunggu = data.getInt("antrian");
                                int terpanggil = data.getInt("proses");
                                total = data.getInt("total");

                                if (total==Integer.parseInt(maksimal)){
                                    binding.rdPilihPasien.setEnabled(false);
                                    Toast.makeText(BookingActivity.this, "Pasien Penuh", Toast.LENGTH_SHORT).show();
                                }

                                binding.antrian.setText(""+menunggu);
                                binding.proses.setText(""+terpanggil);
                                binding.total.setText(""+total);

                            }else {
                                Toast.makeText(BookingActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.e("tampil menu","response:"+anError);
                    }
                });
    }

    private void setAdapter() {
        adapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, pasien);

        AutoCompleteTextView viewpasien = view.findViewById(R.id.cari_nama);
        viewpasien.setAdapter(adapter);

        viewpasien.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (viewpasien.isPopupShowing()){
                    view.findViewById(R.id.btn_caripasien).setVisibility(GONE);
                }
                else{
                    view.findViewById(R.id.btn_caripasien).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        viewpasien.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                Toast.makeText(BookingActivity.this, "Editor", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });

//        viewpasien.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                Toast.makeText(BookingActivity.this, "Key", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });

        Log.d("data", "hasil : " + viewpasien);
         viewpasien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selection = (String)parent.getItemAtPosition(position);
                String niks = hashPasien.get(selection);
//                Toast.makeText(BookingActivity.this, selection, Toast.LENGTH_SHORT).show();
                getCariPasien(niks);
            }
        });

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


                                 nama.setText(data.getString("nama_pasien"));
                                 niks.setText(data.getString("nik"));
                                 jenis_kelamin.setText(data.getString("jenis_kelamin_pasien"));
                                 nohp.setText(data.getString("no_hp_pasien"));
                                 alamat.setText(data.getString("alamat_pasien"));
                                 ttl_cari.setText(data.getString("tgl_lahir_pasien"));




                             }
                             else {
                                 Toast.makeText(BookingActivity.this, stat, Toast.LENGTH_SHORT).show();
                             }
                         } catch (JSONException e) {
                             e.printStackTrace();
                         }
                     }

                     @Override
                     public void onError(ANError anError) {
                         anError.printStackTrace();

                         Log.d("eror", "code :"+anError);
                         Toast.makeText(BookingActivity.this, ""+anError, Toast.LENGTH_SHORT).show();
                     }
                 });
     }


}