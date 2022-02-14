package com.silvia.medical.Antrian;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.silvia.medical.API;
import com.silvia.medical.Home.Model_Dokter;
import com.silvia.medical.MainActivity;
import com.silvia.medical.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Adapter_Antrian extends RecyclerView.Adapter<Adapter_Antrian.ViewHolder> {


    List<Model_Antrian> dataAntrian;
    Context context;

    API api;
    String kode;

    public Adapter_Antrian(List<Model_Antrian> dataAntrian) {
        this.dataAntrian = dataAntrian;
    }

    @NonNull
    @Override
    public Adapter_Antrian.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_antrian,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_Antrian.ViewHolder holder, int position) {
        kode = dataAntrian.get(position).getKode_booking();
        String status = dataAntrian.get(position).getStatus_booking();
        holder.tglkunjungan.setText(dataAntrian.get(position).getTgl_kunjungan());
        holder.noantrian.setText(dataAntrian.get(position).getNo_antrian());
        holder.status.setText(dataAntrian.get(position).getStatus_booking());
        holder.namapoli.setText(dataAntrian.get(position).getNama_poli());
        holder.namapasien.setText(dataAntrian.get(position).getNama_pasien());


        if(status.equalsIgnoreCase("Expired")){
            holder.batal.setVisibility(View.GONE);
        }else if (status.equalsIgnoreCase("Selesai")){
            holder.batal.setVisibility(View.GONE);
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,DetailAntrianActivity2.class);
                    i.putExtra("tgl_kunjungan",dataAntrian.get(position).getTgl_kunjungan());
                    i.putExtra("nama_poli",dataAntrian.get(position).getNama_poli());
                    i.putExtra("jamawal",dataAntrian.get(position).getJam_awal());
                    i.putExtra("jamakhir",dataAntrian.get(position).getJam_akhir());
                    i.putExtra("nomorantrian",dataAntrian.get(position).getNo_antrian());
                    context.startActivity(i);
                }
            });
        }else{
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,DetailAntrianActivity2.class);
                    i.putExtra("tgl_kunjungan",dataAntrian.get(position).getTgl_kunjungan());
                    i.putExtra("nama_poli",dataAntrian.get(position).getNama_poli());
                    i.putExtra("jamawal",dataAntrian.get(position).getJam_awal());
                    i.putExtra("jamakhir",dataAntrian.get(position).getJam_akhir());
                    i.putExtra("nomorantrian",dataAntrian.get(position).getNo_antrian());
                    context.startActivity(i);
                }
            });
            holder.batal.setVisibility(View.VISIBLE);

        }




        holder.batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDialog();
            }
        });


    }

    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Yakin Ingin Membatalkan?");
        alertDialogBuilder.setPositiveButton("Yakin",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                       editStatus();
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak", null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }




    public void editStatus(){
        AndroidNetworking.initialize(context);
        api = new API();
        Log.e("kode",kode);
        Log.e("api",api.UPDATE_STATUS);
        AndroidNetworking.post(api.UPDATE_STATUS)
                .addBodyParameter("kode_booking", kode)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String respon = response.getString("status");
                            if (respon.equalsIgnoreCase("Sukses")){
                                Toast.makeText(context, respon, Toast.LENGTH_SHORT).show();


                            }
                            else{
                                Toast.makeText(context, respon, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(context, "Kesalahan update"
                                ,Toast.LENGTH_LONG).show();

                    }


                });
    }

    @Override
    public int getItemCount() {
        return dataAntrian.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tglkunjungan,namapoli,namapasien,noantrian,status,batal;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            tglkunjungan = itemView.findViewById(R.id.tglantrianAntrian);
            namapoli = itemView.findViewById(R.id.namaPoliAntrian);
            namapasien = itemView.findViewById(R.id.nPasienAntrian);
            card = itemView.findViewById(R.id.card_antrian);
            noantrian = itemView.findViewById(R.id.noantrian);
            status = itemView.findViewById(R.id.status);
            batal = itemView.findViewById(R.id.batal);



        }
    }
}
