package com.example.libraryapplication.network;
import com.example.libraryapplication.model.*;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.*;
public interface SupabaseApi {
    // === Đăng nhập ===
    @POST("thuthu")
    Call<List<ThuThu>> login(@Body ThuThu credentials);  // Supabase sẽ lọc với row-level security

    // === Phiếu mượn ===
    @POST("phieumuon")
    Call<PhieuMuon> createPhieuMuon(@Body PhieuMuon pm);

    @GET("phieumuon")
    Call<List<PhieuMuon>> getAllPhieuMuon();

    @PUT("phieumuon")
    Call<List<PhieuMuon>> updateTrangThaiPM(
            @Query("maPM") String maPM,
            @Body PhieuMuon pm
    );

    @GET("phieumuon")
    Call<List<PhieuMuon>> getLatestPhieuMuon(@Query("order")String order,@Query("limit")int limit);

    @POST("rpc/kiem_tra_muon_sach")
    Call<String> kiemTraMuonSach(@Body Map<String,Object> body);

    // === Sách ===
    @GET("sach")
    Call<List<Sach>> getAllSach();

    @POST("rpc/tim_kiem_sach")
    Call<List<Sach>> searchSach(
            @Body Map<String, String> body
    );

    @GET("sach")
    Call<List<Sach>> getSachById(@Query("maSach") String id);

    @POST("sach")
    Call<Sach> createSach(@Body Sach sach);

    @PATCH("sach")
    Call<List<Sach>> updateSach(@Query("maSach") String maSachFilter, @Body Sach sach);

    @DELETE("sach")
    Call<Void> deleteSach(@Query("maSach") String id);
    @GET("sach")
    Call<List<Sach>> getLatestBook(@Query("order")String order,@Query("limit")int limit);

    // === Phiếu vi phạm ===
    @GET("phieuvipham")
    Call<List<PhieuViPham>> getAllPhieuViPham();

    @Headers("Prefer: return=representation")
    @PATCH("phieuvipham")
    Call<List<PhieuViPham>> updateTrangThaiViPham(@Query("maPhieuVP") String filter, @Body PhieuViPham vp);

    @POST("phieuvipham")
    Call<PhieuViPham> createPhieuViPham(@Body PhieuViPham vp);

    @GET("phieuvipham")
    Call<List<PhieuViPham>> searchPhieuViPham(@Query("maPhieuVP") String keyword);


    // === Quản lý tài khoản thủ thư ===
    @PUT("thuthu")
    Call<ThuThu> updateThuThu(
            @Query("maTK") String maTK,
            @Body ThuThu thuThu
    );

    // === Đăng nhập ===
    @GET("thuthu")
    Call<List<ThuThu>> dangNhap(
            @Query("tenTK") String tenTK,
            @Query("matKhau") String matKhau
    );

    // === Thống kê ===
    @POST("rpc/top_5_sach_muon_nhieu")
    Call<List<Sach>> getTopSach(@Body Map<String,String> params);
    @POST("rpc/thong_ke_phieu_muon_theo_ngay")
    Call<List<Map<String, Object>>> thongKePM(@Body Map<String, String> params);
    @POST("rpc/thong_ke_phieu_vi_pham_theo_ngay")
    Call<List<Map<String, Object>>> thongKePhieuViPhamTheoNgay(@Body Map<String, String> params);

    // ===The loai ===
    @GET("theloai")
    Call<List<TheLoai>> getTheLoai();
    @GET("theloai")
    Call<List<TheLoai>> getTLByID(@Query("maTL") String maTL);

}