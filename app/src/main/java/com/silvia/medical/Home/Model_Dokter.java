package com.silvia.medical.Home;


public class Model_Dokter  {

        private String id_dokter;
        private String id_poli;
        private String kode_poli;
        private String nama_dokter;
        private String nama_poli;
        private String jam_awal;
        private String jam_akhir;
        private String jenis_kelamin_dokter;

    public Model_Dokter(String id_dokter, String id_poli, String kode_poli, String nama_dokter, String nama_poli, String jam_awal, String jam_akhir, String jenis_kelamin_dokter) {
        this.id_dokter = id_dokter;
        this.id_poli = id_poli;
        this.kode_poli = kode_poli;
        this.nama_dokter = nama_dokter;
        this.nama_poli = nama_poli;
        this.jam_awal = jam_awal;
        this.jam_akhir = jam_akhir;
        this.jenis_kelamin_dokter = jenis_kelamin_dokter;
    }

    public String getId_dokter() {
        return id_dokter;
    }

    public void setId_dokter(String id_dokter) {
        this.id_dokter = id_dokter;
    }

    public String getId_poli() {
        return id_poli;
    }

    public void setId_poli(String id_poli) {
        this.id_poli = id_poli;
    }

    public String getKode_poli() {
        return kode_poli;
    }

    public void setKode_poli(String kode_poli) {
        this.kode_poli = kode_poli;
    }

    public String getNama_dokter() {
        return nama_dokter;
    }

    public void setNama_dokter(String nama_dokter) {
        this.nama_dokter = nama_dokter;
    }

    public String getNama_poli() {
        return nama_poli;
    }

    public void setNama_poli(String nama_poli) {
        this.nama_poli = nama_poli;
    }

    public String getJam_awal() {
        return jam_awal;
    }

    public void setJam_awal(String jam_awal) {
        this.jam_awal = jam_awal;
    }

    public String getJam_akhir() {
        return jam_akhir;
    }

    public void setJam_akhir(String jam_akhir) {
        this.jam_akhir = jam_akhir;
    }

    public String getJenis_kelamin_dokter() {
        return jenis_kelamin_dokter;
    }

    public void setJenis_kelamin_dokter(String jenis_kelamin_dokter) {
        this.jenis_kelamin_dokter = jenis_kelamin_dokter;
    }
}
