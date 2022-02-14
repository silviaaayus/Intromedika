package com.silvia.medical.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.silvia.medical.Booking.BookingActivity;
import com.silvia.medical.R;
import com.silvia.medical.TinyDB;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Adapter_Poli extends RecyclerView.Adapter<Adapter_Poli.ViewHolder> {

    List<Model_Poli>dataPoli;
     Context context;

    public Adapter_Poli(List<Model_Poli> dataPoli) {
        this.dataPoli = dataPoli;
    }

    @NonNull
    @Override
    public Adapter_Poli.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kategori,parent,false);
        return new ViewHolder(view);
    }

   
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull Adapter_Poli.ViewHolder holder, int position) {
        TinyDB tinyDB = new TinyDB(context);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        String kategori = dataPoli.get(position).getNama_poli();
        String maksimal = dataPoli.get(position).getMaksimal_pasien();
        holder.kategori.setText(dataPoli.get(position).getNama_poli());


            holder.relPoli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, BookingActivity.class);
                    i.putExtra("tgl",formattedDate);
                    i.putExtra("Id Poli",dataPoli.get(position).getId_poli());
                    i.putExtra("Nama Poli", dataPoli.get(position).getNama_poli());
                    i.putExtra("Jam_Buka", dataPoli.get(position).getJam_awal());
                    i.putExtra("Jam_Tutup", dataPoli.get(position).getJam_akhir());
                    i.putExtra("kode_poli", dataPoli.get(position).getKode_poli());
                    i.putExtra("id_jadwal", dataPoli.get(position).getId_jadwal());
                    i.putExtra("maksimal", dataPoli.get(position).getMaksimal_pasien());
                    i.putExtra("pelayanan", dataPoli.get(position).getPelayanan_poli());
                    context.startActivity(i);

                }
            });






//        if(kategori.equalsIgnoreCase("Poli Gigi")||kategori.equalsIgnoreCase("Poli KIA")
//                ||kategori.equalsIgnoreCase("Spesialis THT")){
//            holder.relPoli.getBackground().setTint(context.getResources().getColor(R.color.hijau));
//        }else {
//            holder.relPoli.getBackground().setTint(context.getResources().getColor(R.color.hijau_200));
//        }

        if (kategori.equalsIgnoreCase("Poli Umum")){
            holder.img.setImageResource(R.drawable.umum);
        }else if (kategori.equalsIgnoreCase("Poli Gigi")){
            holder.img.setImageResource(R.drawable.gigi);
        }else if (kategori.equalsIgnoreCase("Poli KIA")){
            holder.img.setImageResource(R.drawable.kia);
        }else if (kategori.equalsIgnoreCase("Spesialis Anak")){
            holder.img.setImageResource(R.drawable.anak);
        }else{
            holder.img.setImageResource(R.drawable.telinga);
        }







    }

    @Override
    public int getItemCount() {
        return dataPoli.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView kategori;
        RelativeLayout  relPoli;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            kategori = itemView.findViewById(R.id.kategori);
            relPoli = itemView.findViewById(R.id.btnPoli);
            img = itemView.findViewById(R.id.imgKategori);
        }
    }
}
