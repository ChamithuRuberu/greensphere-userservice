package com.greensphere.userservice.service;

import com.greensphere.userservice.dto.request.GymActivateByAdminRequest;
import com.greensphere.userservice.dto.request.TrainerActivateRequest;
import com.greensphere.userservice.dto.request.UpdateUserDetailsRequest;
import com.greensphere.userservice.dto.request.UserActivateByGymRequest;
import com.greensphere.userservice.dto.request.logOutRequest.LogOutRequest;
import com.greensphere.userservice.dto.request.userLogin.UserLoginRequest;
import com.greensphere.userservice.dto.request.userRegister.GovUserRegisterRequest;
import com.greensphere.userservice.dto.request.userRegister.SetUpDetailsRequest;
import com.greensphere.userservice.dto.request.userRegister.UserRegisterRequestDto;
import com.greensphere.userservice.dto.request.userRegister.UserRegisterVerifyRequest;
import com.greensphere.userservice.dto.response.BaseResponse;
import com.greensphere.userservice.dto.response.UpdateUserDetailsResponse;
import com.greensphere.userservice.dto.response.tokenValidationResponse.UserAuthResponse;
import com.greensphere.userservice.dto.response.userLoginResponse.UserLoginResponse;
import com.greensphere.userservice.entity.AppUser;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;

public interface UserService {
    BaseResponse<HashMap<String, Object>> registerInit(UserRegisterRequestDto registerInitRequest);

    BaseResponse<HashMap<String, Object>> registerVerify(UserRegisterVerifyRequest userRegisterVerifyRequest);

    BaseResponse<HashMap<String, Object>> setUpDetails(SetUpDetailsRequest setUpDetailsRequest);

    BaseResponse<HashMap<String, Object>> govUserSignUp(GovUserRegisterRequest govUserRegisterRequest);

    BaseResponse<UserLoginResponse> login(UserLoginRequest loginRequest);

    BaseResponse<HashMap<String, Object>> activateUser(TrainerActivateRequest request);
    BaseResponse<HashMap<String, Object>> activateUserByGym(UserActivateByGymRequest request);
    BaseResponse<HashMap<String, Object>> activateGymByAdmin(GymActivateByAdminRequest request);

    BaseResponse<?> logOut(LogOutRequest logOutRequest);

    BaseResponse<UserAuthResponse> tokenValidation(String token, HttpServletRequest httpServletRequest);

    BaseResponse<UpdateUserDetailsResponse> updateUserDetails(UpdateUserDetailsRequest updateUserDetailsRequest, AppUser appUser);

}
