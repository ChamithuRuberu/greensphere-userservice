package com.greensphere.userservice.service;

import com.greensphere.userservice.dto.request.logOutRequest.LogOutRequest;
import com.greensphere.userservice.dto.request.userLogin.UserLoginRequest;
import com.greensphere.userservice.dto.request.userRegister.GovUserRegisterRequest;
import com.greensphere.userservice.dto.request.userRegister.SetUpDetailsRequest;
import com.greensphere.userservice.dto.request.userRegister.UserRegisterRequestDto;
import com.greensphere.userservice.dto.request.userRegister.UserRegisterVerifyRequest;
import com.greensphere.userservice.dto.response.BaseResponse;
import com.greensphere.userservice.dto.response.userLoginResponse.UserLoginResponse;

import java.util.HashMap;

public interface UserService {
    BaseResponse<HashMap<String, Object>> registerInit(UserRegisterRequestDto registerInitRequest);

    BaseResponse<HashMap<String, Object>> registerVerify(UserRegisterVerifyRequest userRegisterVerifyRequest);

    BaseResponse<HashMap<String, Object>> setUpDetails(SetUpDetailsRequest setUpDetailsRequest);

    BaseResponse<HashMap<String, Object>> govUserSignUp(GovUserRegisterRequest govUserRegisterRequest);

    BaseResponse<UserLoginResponse> login(UserLoginRequest loginRequest);

    BaseResponse<?> logOut(LogOutRequest logOutRequest);
}
