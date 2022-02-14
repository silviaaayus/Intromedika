package com.silvia.medical.Antrian;


public class Model_Antrian {

        private String id_pasien;
        private String tgl_kunjungan;
        private String tanggal_sistem;
        private String id_jadwal;
        private String id_user;
        private String nama_pasien;
        private String tgl_lahir_pasien;
        private String jenis_kelamin_pasien;
        private String alamat_pasien;
        private String nik;
        private String status_booking;
        private String kode_poli;
        private String jam_awal;
        private String jam_akhir;
        private String nama_poli;
        private String kode_booking;
        private String no_antrian;

    public Model_Antrian(String id_pasien, String tgl_kunjungan, String tanggal_sistem, String id_jadwal, String id_user, String nama_pasien, String tgl_lahir_pasien, String jenis_kelamin_pasien, String alamat_pasien, String nik, String status_booking, String kode_poli, String jam_awal, String jam_akhir, String nama_poli, String kode_booking, String no_antrian) {
        this.id_pasien = id_pasien;
        this.tgl_kunjungan = tgl_kunjungan;
        this.tanggal_sistem = tanggal_sistem;
        this.id_jadwal = id_jadwal;
        this.id_user = id_user;
        this.nama_pasien = nama_pasien;
        this.tgl_lahir_pasien = tgl_lahir_pasien;
        this.jenis_kelamin_pasien = jenis_kelamin_pasien;
        this.alamat_pasien = alamat_pasien;
        this.nik = nik;
        this.status_booking = status_booking;
        this.kode_poli = kode_poli;
        this.jam_awal = jam_awal;
        this.jam_akhir = jam_akhir;
        this.nama_poli = nama_poli;
        this.kode_booking = kode_booking;
        this.no_antrian = no_antrian;
    }

    public String getId_pasien() {
        return id_pasien;
    }

    public void setId_pasien(String id_pasien) {
        this.id_pasien = id_pasien;
    }

    public String getTgl_kunjungan() {
        return tgl_kunjungan;
    }

    public void setTgl_kunjungan(String tgl_kunjungan) {
        this.tgl_kunjungan = tgl_kunjungan;
    }

    public String getTanggal_sistem() {
        return tanggal_sistem;
    }

    public void setTanggal_sistem(String tanggal_sistem) {
        this.tanggal_sistem = tanggal_sistem;
    }

    public String getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(String id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNama_pasien() {
        return nama_pasien;
    }

    public void setNama_pasien(String nama_pasien) {
        this.nama_pasien = nama_pasien;
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

    public String getAlamat_pasien() {
        return alamat_pasien;
    }

    public void setAlamat_pasien(String alamat_pasien) {
        this.alamat_pasien = alamat_pasien;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getStatus_booking() {
        return status_booking;
    }

    public void setStatus_booking(String status_booking) {
        this.status_booking = status_booking;
    }

    public String getKode_poli() {
        return kode_poli;
    }

    public void setKode_poli(String kode_poli) {
        this.kode_poli = kode_poli;
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

    public String getNama_poli() {
        return nama_poli;
    }

    public void setNama_poli(String nama_poli) {
        this.nama_poli = nama_poli;
    }

    public String getKode_booking() {
        return kode_booking;
    }

    public void setKode_booking(String kode_booking) {
        this.kode_booking = kode_booking;
    }

    public String getNo_antrian() {
        return no_antrian;
    }

    public void setNo_antrian(String no_antrian) {
        this.no_antrian = no_antrian;
    }
}
