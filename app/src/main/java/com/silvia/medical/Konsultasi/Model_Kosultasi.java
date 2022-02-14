package com.silvia.medical.Konsultasi;



public class Model_Kosultasi  {

        private String id_rm;
        private String id_dokter;
        private String kode_booking;
        private String nama_pasien;
        private String no_rekam_medis;
        private String tgl_lahir_pasien;
        private String jenis_kelamin_pasien;
        private String no_hp_pasien;
        private String alamat_pasien;
        private String nama_dokter;
        private String tgl_transaksi;
        private String tgl_kunjungan;
        private String total;

    public Model_Kosultasi(String id_rm, String id_dokter, String kode_booking, String nama_pasien, String no_rekam_medis, String tgl_lahir_pasien, String jenis_kelamin_pasien, String no_hp_pasien, String alamat_pasien, String nama_dokter, String tgl_transaksi, String tgl_kunjungan, String total) {
        this.id_rm = id_rm;
        this.id_dokter = id_dokter;
        this.kode_booking = kode_booking;
        this.nama_pasien = nama_pasien;
        this.no_rekam_medis = no_rekam_medis;
        this.tgl_lahir_pasien = tgl_lahir_pasien;
        this.jenis_kelamin_pasien = jenis_kelamin_pasien;
        this.no_hp_pasien = no_hp_pasien;
        this.alamat_pasien = alamat_pasien;
        this.nama_dokter = nama_dokter;
        this.tgl_transaksi = tgl_transaksi;
        this.tgl_kunjungan = tgl_kunjungan;
        this.total = total;
    }

    public String getId_rm() {
        return id_rm;
    }

    public void setId_rm(String id_rm) {
        this.id_rm = id_rm;
    }

    public String getId_dokter() {
        return id_dokter;
    }

    public void setId_dokter(String id_dokter) {
        this.id_dokter = id_dokter;
    }

    public String getKode_booking() {
        return kode_booking;
    }

    public void setKode_booking(String kode_booking) {
        this.kode_booking = kode_booking;
    }

    public String getNama_pasien() {
        return nama_pasien;
    }

    public void setNama_pasien(String nama_pasien) {
        this.nama_pasien = nama_pasien;
    }

    public String getNo_rekam_medis() {
        return no_rekam_medis;
    }

    public void setNo_rekam_medis(String no_rekam_medis) {
        this.no_rekam_medis = no_rekam_medis;
    }

    public String getTgl_lahir_pasien() {
        return tgl_lahir_pasien;
    }

    public void setTgl_lahir_pasien(String tgl_lahir_pasien) {
        this.tgl_lahir_pasien = tgl_lahir_pasien;
    }

    public String getJenis_kelamin_pasien() {
        return jenis_kelamin_pasien;
    }

    public void setJenis_kelamin_pasien(String jenis_kelamin_pasien) {
        this.jenis_kelamin_pasien = jenis_kelamin_pasien;
    }

    public String getNo_hp_pasien() {
        return no_hp_pasien;
    }

    public void setNo_hp_pasien(String no_hp_pasien) {
        this.no_hp_pasien = no_hp_pasien;
    }

    public String getAlamat_pasien() {
        return alamat_pasien;
    }

    public void setAlamat_pasien(String alamat_pasien) {
        this.alamat_pasien = alamat_pasien;
    }

    public String getNama_dokter() {
        return nama_dokter;
    }

    public void setNama_dokter(String nama_dokter) {
        this.nama_dokter = nama_dokter;
    }

    public String getTgl_transaksi() {
        return tgl_transaksi;
    }

    public void setTgl_transaksi(String tgl_transaksi) {
        this.tgl_transaksi = tgl_transaksi;
    }

    public String getTgl_kunjungan() {
        return tgl_kunjungan;
    }

    public void setTgl_kunjungan(String tgl_kunjungan) {
        this.tgl_kunjungan = tgl_kunjungan;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}

