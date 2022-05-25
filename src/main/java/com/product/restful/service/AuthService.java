package com.product.restful.service;

import com.product.restful.dto.auth.LoginRequest;
import com.product.restful.dto.auth.SignUpRequest;
import com.product.restful.dto.jwt.JwtAuthenticationResponse;
import com.product.restful.entity.User;

public interface AuthService {

    // signup
    // kalo di contoh reddit-clone, setelah signup maka kita akan kirim email verification
    // untuk saat ini kita tidak ingin kirim verifikasi
    // kita hanya ingin setelah signup, maka user akan menerima token untuk request selanjutnya
    User signUp(SignUpRequest signUpRequest);

    // verifyAccount
    // ini membutuhkan verificationToken

    // login
    // kita hanya akan mengembalikan response token jika berhasil autentikasi user
    JwtAuthenticationResponse signIn(LoginRequest loginRequest);

    // refresh token

    // logout
}
