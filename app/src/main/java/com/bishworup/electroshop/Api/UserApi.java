package com.bishworup.electroshop.Api;

import com.bishworup.electroshop.Models.LoginClass;
import com.bishworup.electroshop.Models.User;
import com.bishworup.electroshop.Response.NodeResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {
    @POST("users/signup")
    Call<NodeResponse> registerUser(@Body User users);

    @POST("users/login")
    Call<NodeResponse> login(@Body LoginClass login);

}
