package com.silvia.medical.Home;



public class Model_Poli  {

        private String id_poli;
        private String kode_poli;
        private String nama_poli;
        private String status_poli;
        private String id_jadwal;
        private String jam_awal;
        private String jam_akhir;
        private String id_dokter;
        private String maksimal_pasien;
        private String pelayanan_poli;

    public Model_Poli(String id_poli, String kode_poli, String nama_poli, String status_poli, String id_jadwal, String jam_awal, String jam_akhir, String id_dokter, String maksimal_pasien, String pelayanan_poli) {
        this.id_poli = id_poli;
        this.kode_poli = kode_poli;
        this.nama_poli = nama_poli;
        this.status_poli = status_poli;
        this.id_jadwal = id_jadwal;
        this.jam_awal = jam_awal;
        this.jam_akhir = jam_akhir;
        this.id_dokter = id_dokter;
        this.maksimal_pasien = maksimal_pasien;
        this.pelayanan_poli = pelayanan_poli;
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

    public String getNama_poli() {
        return nama_poli;
    }

    public void setNama_poli(String nama_poli) {
        this.nama_poli = nama_poli;
    }

    public String getStatus_poli() {
        return status_poli;
    }

    public void setStatus_poli(String status_poli) {
        this.status_poli = status_poli;
    }

    public String getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(String id_jadwal) {
        this.id_jadwal = id_jadwal;
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

    public String getId_dokter() {
        return id_dokter;
    }

    public void setId_dokter(String id_dokter) {
        this.id_dokter = id_dokter;
    }

    public String getMaksimal_pasien() {
        return maksimal_pasien;
    }

    public void setMaksimal_pasien(String maksimal_pasien) {
        this.maksimal_pasien = maksimal_pasien;
    }

    public String getPelayanan_poli() {
        return pelayanan_poli;
    }

    public void setPelayanan_poli(String pelayanan_poli) {
        this.pelayanan_poli = pelayanan_poli;
    }
}
