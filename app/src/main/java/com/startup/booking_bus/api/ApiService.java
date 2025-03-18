package com.startup.booking_bus.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/user/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("api/user/register")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);
}
