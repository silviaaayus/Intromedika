package com.silvia.medical;
 
public class API {
//    private String HOST ="http://192.168.163.78/intromedika/";
//    private String HOST ="http://192.168.100.15/intromedika/";
    private String HOST ="https://apiklinik.vermez.id/";


    public String KATEGORI = HOST + "Api/tampil_poli";
    public String DOKTER = HOST + "Api/tampil_data_dokter";
    public String DOKTER_SEMUA = HOST + "Api/tampil_data_dokter_lain";
    public String LOGIN = HOST + "Api/login";
    public String RIWAYAT = HOST + "Api/tampil_data_rm?id_user=";
    public String PASIENLAMA = HOST + "Api/tampil_detail_pasien?nik=";
    public String DETAIL_JADWAL = HOST + "Api/tampil_detail_jadwal_booking?tgl_kunjungan=";
    public String REGISTER = HOST + "Api/insert_register";
    public String BOOKING = HOST + "Api/insert_booking_pasien";
    public String ANTRIAN  = HOST + "Api/tampil_list_booking_pasien?id_pasien=";
    public String CHANGE  = HOST + "Api/update_password";
    public String NOTIF  = "https://intromedika.com/api/send/message";
    public String URL_CARINIK = HOST + "api/cari_nik_register?nik=";
    public String UPDATE_STATUS = HOST + "api/update_status_booking";
    public String ALLPASIEN = HOST + "api/tampil_all_pasien";
    public String SLIDER = HOST + "api/tampil_slider";
    public String GAMBAR_SLIDER = "https://intromedika.com/assets/img/";
    public String PASIENEDT = HOST + "Api/tampil_pasien_edit?id_pasien=";
    public String EDIT_PASIEN = HOST + "Api/update_pasien_edit";
    public String LUPAPASSWORD  = HOST + "Api/lupa_password";


}
