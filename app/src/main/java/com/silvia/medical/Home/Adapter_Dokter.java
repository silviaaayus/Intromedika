package com.silvia.medical.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.silvia.medical.R;

import java.util.List;

public class Adapter_Dokter extends RecyclerView.Adapter<Adapter_Dokter.ViewHolder> {

    List<Model_Dokter>dataDokter;
    Context context;

    public Adapter_Dokter(List<Model_Dokter> dataDokter) {
        this.dataDokter = dataDokter;
    }

    @NonNull
    @Override
    public Adapter_Dokter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dokter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Dokter.ViewHolder holder, int position) {
        holder.namadokter.setText(dataDokter.get(position).getNama_dokter());
        holder.poli.setText(dataDokter.get(position).getNama_poli());
        holder.jam_awal.setText(" "+dataDokter.get(position).getJam_awal()+" - ");
        holder.jam_akhir.setText(dataDokter.get(position).getJam_akhir());

        if (dataDokter.get(position).getJenis_kelamin_dokter().equalsIgnoreCase("Perempuan")){
            holder.img.setImageResource(R.drawable.doctor_female);
        }else{
            holder.img.setImageResource(R.drawable.doctor_male);
        }


    }

    @Override
    public int getItemCount() {
        return dataDokter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namadokter, poli, jam_awal, jam_akhir;
        CircularImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            namadokter = itemView.findViewById(R.id.namaDokter);
            poli = itemView.findViewById(R.id.namaPoli);
            jam_awal = itemView.findViewById(R.id.jamAwal);
            jam_akhir = itemView.findViewById(R.id.jamAkhir);
            img = itemView.findViewById(R.id.imgDokter);
        }
    }
}
