package com.greensphere.userservice.controller;

import com.greensphere.userservice.dto.request.TokenValidationRequest;
import com.greensphere.userservice.dto.request.logOutRequest.LogOutRequest;
import com.greensphere.userservice.dto.request.UpdateUserDetailsRequest;
import com.greensphere.userservice.dto.request.userLogin.UserLoginRequest;
import com.greensphere.userservice.dto.request.userRegister.GovUserRegisterRequest;
import com.greensphere.userservice.dto.request.userRegister.SetUpDetailsRequest;
import com.greensphere.userservice.dto.request.userRegister.UserRegisterRequestDto;
import com.greensphere.userservice.dto.request.userRegister.UserRegisterVerifyRequest;
import com.greensphere.userservice.dto.response.BaseResponse;
import com.greensphere.userservice.dto.response.DefaultResponse;
import com.greensphere.userservice.dto.response.UpdateUserDetailsResponse;
import com.greensphere.userservice.dto.response.tokenValidationResponse.UserAuthResponse;
import com.greensphere.userservice.dto.response.userLoginResponse.UserLoginResponse;
import com.greensphere.userservice.entity.AppUser;
import com.greensphere.userservice.service.UserService;
import com.greensphere.userservice.utils.ResponseCodeUtil;
import com.greensphere.userservice.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/register-init")
    public ResponseEntity<DefaultResponse> registerInit(@Valid @RequestBody UserRegisterRequestDto registerInitRequest) {
        BaseResponse<HashMap<String, Object>> response = userService.registerInit(registerInitRequest);
        if (response.getCode().equals(ResponseCodeUtil.SUCCESS_CODE)) {
            return ResponseEntity.ok(DefaultResponse.success(ResponseUtil.SUCCESS, response.getMessage(), response.getData()));
        } else if (response.getCode().equals(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE)) {
            return ResponseEntity.internalServerError()
                    .body(DefaultResponse.internalServerError(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE, response.getMessage()));
        } else {
            return ResponseEntity.badRequest()
                    .body(DefaultResponse.error(ResponseUtil.FAILED, response.getMessage(), response.getData()));
        }
    }

    @PostMapping(value = "/register-verify")
    public ResponseEntity<DefaultResponse> registerVerify( @RequestBody UserRegisterVerifyRequest userRegisterVerifyRequest) {
        BaseResponse<HashMap<String, Object>> response = userService.registerVerify(userRegisterVerifyRequest);
        if (response.getCode().equals(ResponseCodeUtil.SUCCESS_CODE)) {
            return ResponseEntity.ok(DefaultResponse.success(ResponseUtil.SUCCESS, response.getMessage(), response.getData()));
        } else if (response.getCode().equals(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE)) {
            return ResponseEntity.internalServerError()
                    .body(DefaultResponse.internalServerError(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE, response.getMessage()));
        } else {
            return ResponseEntity.badRequest()
                    .body(DefaultResponse.error(ResponseUtil.FAILED, response.getMessage(), response.getData()));
        }
    }

    @PostMapping(path = "/client-register")
//    @PreAuthorize("hasAuthority('APP_USER')")
    public ResponseEntity<DefaultResponse> appUserSetUpDetails(@Valid @RequestBody SetUpDetailsRequest setUpDetailsRequest) {
        BaseResponse<HashMap<String, Object>> response = userService.setUpDetails(setUpDetailsRequest);
        if (response.getCode().equals(ResponseCodeUtil.SUCCESS_CODE)) {
            return ResponseEntity.ok(DefaultResponse.success(ResponseUtil.SUCCESS, response.getMessage(), response.getData()));
        } else if (response.getCode().equals(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE)) {
            return ResponseEntity.internalServerError()
                    .body(DefaultResponse.internalServerError(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE, response.getMessage()));
        } else {
            return ResponseEntity.badRequest()
                    .body(DefaultResponse.error(ResponseUtil.FAILED, response.getMessage(), response.getData()));
        }
    }

    @PostMapping(path = "/gov-user/register")
//    @PreAuthorize("hasAuthority('GOVERNMENT_USER')")
    public ResponseEntity<DefaultResponse>govUserRegister(@Valid @RequestBody GovUserRegisterRequest govUserRegisterRequest){
        BaseResponse<HashMap<String, Object>> response = userService.govUserSignUp(govUserRegisterRequest);
        if (response.getCode().equals(ResponseCodeUtil.SUCCESS_CODE)) {
            return ResponseEntity.ok(DefaultResponse.success(ResponseUtil.SUCCESS, response.getMessage(), response.getData()));
        } else if (response.getCode().equals(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE)) {
            return ResponseEntity.internalServerError()
                    .body(DefaultResponse.internalServerError(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE, response.getMessage()));
        } else {
            return ResponseEntity.badRequest()
                    .body(DefaultResponse.error(ResponseUtil.FAILED, response.getMessage(), response.getData()));
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<DefaultResponse>login(@Valid @RequestBody UserLoginRequest loginRequest){
        BaseResponse<UserLoginResponse> response = userService.login(loginRequest);
        if (response.getCode().equals(ResponseCodeUtil.SUCCESS_CODE)) {
            return ResponseEntity.ok(DefaultResponse.success(ResponseUtil.SUCCESS, response.getMessage(), response.getData()));
        } else if (response.getCode().equals(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE)) {
            return ResponseEntity.internalServerError()
                    .body(DefaultResponse.internalServerError(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE, response.getMessage()));
        } else {
            return ResponseEntity.badRequest()
                    .body(DefaultResponse.error(ResponseUtil.FAILED, response.getMessage(), response.getData()));
        }
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<DefaultResponse> logOut(@RequestBody LogOutRequest logOutRequest) {
        BaseResponse<?> response = userService.logOut(logOutRequest);
        if (response.getCode().equals(ResponseCodeUtil.SUCCESS_CODE)) {
            return ResponseEntity.ok(DefaultResponse.success(ResponseUtil.SUCCESS, response.getMessage(), response.getData()));
        } else if (response.getCode().equals(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE)) {
            return ResponseEntity.internalServerError()
                    .body(DefaultResponse.internalServerError(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE, response.getMessage()));
        } else {
            return ResponseEntity.badRequest()
                    .body(DefaultResponse.error(ResponseUtil.FAILED, response.getMessage()));
        }
    }

    @PostMapping(path = "/token-validation")
    public ResponseEntity<DefaultResponse> tokenValidation(@RequestBody TokenValidationRequest validationRequest, HttpServletRequest httpServletRequest) {
        BaseResponse<UserAuthResponse> response = userService.tokenValidation(validationRequest.getToken(), httpServletRequest);
        if (response.getCode().equals(ResponseCodeUtil.SUCCESS_CODE)) {
            return ResponseEntity.ok(DefaultResponse.success(ResponseUtil.SUCCESS, response.getMessage(), response.getData()));
        } else if (response.getCode().equals(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE)) {
            return ResponseEntity.internalServerError()
                    .body(DefaultResponse.internalServerError(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE, response.getMessage()));
        } else {
            return ResponseEntity.badRequest()
                    .body(DefaultResponse.error(ResponseUtil.FAILED, response.getMessage()));
        }
    }

    @PutMapping(path = "/settings/update-user-details")
    public ResponseEntity<DefaultResponse> updateUserDetails(
            @RequestBody UpdateUserDetailsRequest updateUserDetailsRequest,
            @RequestAttribute("user") AppUser appUser
    ) {
        BaseResponse<UpdateUserDetailsResponse> response = userService.updateUserDetails(updateUserDetailsRequest, appUser);

        if (response.getCode().equals(ResponseCodeUtil.SUCCESS_CODE)) {
            return ResponseEntity.ok(DefaultResponse.success(ResponseUtil.SUCCESS, response.getMessage(), response.getData()));
        } else if (response.getCode().equals(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE)) {
            return ResponseEntity.internalServerError()
                    .body(DefaultResponse.internalServerError(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE, response.getMessage()));
        } else {
            return ResponseEntity.badRequest()
                    .body(DefaultResponse.error(ResponseUtil.FAILED, response.getMessage()));
        }
    }

}
