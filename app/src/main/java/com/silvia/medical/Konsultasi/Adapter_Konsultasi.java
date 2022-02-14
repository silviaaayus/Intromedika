package com.silvia.medical.Konsultasi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.internal.$Gson$Preconditions;
import com.silvia.medical.Home.Adapter_Dokter;
import com.silvia.medical.R;

import org.w3c.dom.Text;

import java.util.List;

public class Adapter_Konsultasi extends RecyclerView.Adapter<Adapter_Konsultasi.ViewHolder> {
    List<Model_Kosultasi> dataKonsultasi;
    Context context;

    public Adapter_Konsultasi(List<Model_Kosultasi> dataKonsultasi) {
        this.dataKonsultasi = dataKonsultasi;
    }

    @NonNull
    @Override
    public Adapter_Konsultasi.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riwayat,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Konsultasi.ViewHolder holder, int position) {
        holder.tanggal_pemeriksaan.setText(dataKonsultasi.get(position).getTgl_kunjungan());
        holder.nama_pasien.setText(dataKonsultasi.get(position).getNama_pasien());
        holder.nama_dokter.setText(dataKonsultasi.get(position).getNama_dokter());
        holder.tarif.setText("Rp."+dataKonsultasi.get(position).getTotal());

    }

    @Override
    public int getItemCount() {
        return dataKonsultasi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tanggal_pemeriksaan, nama_pasien, tarif, nama_dokter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            tanggal_pemeriksaan = itemView.findViewById(R.id.tanggal_pemeriksaan);
            nama_pasien = itemView.findViewById(R.id.nama_pasien);
            tarif = itemView.findViewById(R.id.tarif);
            nama_dokter = itemView.findViewById(R.id.dokter);
        }
    }
}
