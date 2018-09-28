package com.atwa.taxineum.data.network;


import com.atwa.taxineum.data.network.model.LoginRequest;
import com.atwa.taxineum.data.network.model.LoginResponse;
import com.atwa.taxineum.data.network.model.LogoutResponse;
import com.atwa.taxineum.data.network.model.RegisterRequest;
import com.atwa.taxineum.data.network.model.RegisterResponse;

import io.reactivex.Single;


public interface ApiHelper {

    ApiHeader getApiHeader();

    Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request);

    Single<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest request);

    Single<RegisterResponse> doRegisterApiCall(RegisterRequest request);

    Single<LogoutResponse> doLogoutApiCall();

}
