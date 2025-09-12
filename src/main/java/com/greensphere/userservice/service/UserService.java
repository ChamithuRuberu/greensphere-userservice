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
import com.greensphere.userservice.entity.TrainerIncome;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

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

    // Trainer Income Management
    TrainerIncome saveIncome(TrainerIncome income);

    /**
     * Upcoming payments for a specific trainer (nextPaymentDate > today).
     */
    List<TrainerIncome> getUpcomingPaymentsByTrainer(Long trainerId);

    /**
     * Upcoming payments across all trainers (nextPaymentDate > today).
     */
    List<TrainerIncome> getAllUpcomingPayments();

    /**
     * Payments due soon for a specific trainer within the next N days (inclusive).
     * @param trainerId trainer id
     * @param days number of days from today (e.g., 7)
     */
    List<TrainerIncome> getDueSoonByTrainer(Long trainerId, int days);

    /**
     * Payments due soon across all trainers within the next N days (inclusive).
     * @param days number of days from today (e.g., 7)
     */
    List<TrainerIncome> getAllDueSoon(int days);

    /**
     * Renew a payment cycle:
     * - Sets lastPaymentDate = today
     * - Recomputes nextPaymentDate = lastPaymentDate + month(s)
     */
    TrainerIncome renewPayment(Long incomeId);}
