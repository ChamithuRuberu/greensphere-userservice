package com.greensphere.userservice.controller;

import com.greensphere.userservice.dto.request.*;
import com.greensphere.userservice.dto.request.logOutRequest.LogOutRequest;
import com.greensphere.userservice.dto.request.userLogin.UserLoginRequest;
import com.greensphere.userservice.dto.request.userRegister.GovUserRegisterRequest;
import com.greensphere.userservice.dto.request.userRegister.AdminCreateUserRequest;
import com.greensphere.userservice.dto.request.userRegister.AdminCreateTrainerRequest;
import com.greensphere.userservice.dto.request.userRegister.SetUpDetailsRequest;
import com.greensphere.userservice.dto.request.userRegister.UserRegisterRequestDto;
import com.greensphere.userservice.dto.request.userRegister.UserRegisterVerifyRequest;
import com.greensphere.userservice.dto.response.BaseResponse;
import com.greensphere.userservice.dto.response.DefaultResponse;
import com.greensphere.userservice.dto.response.UpdateUserDetailsResponse;
import com.greensphere.userservice.dto.response.tokenValidationResponse.UserAuthResponse;
import com.greensphere.userservice.dto.response.userLoginResponse.UserLoginResponse;
import com.greensphere.userservice.dto.response.user.UserHealthDetailsResponse;
import com.greensphere.userservice.entity.AppUser;
import com.greensphere.userservice.entity.TrainerIncome;
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
import java.util.List;

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
    public ResponseEntity<DefaultResponse> registerVerify(@RequestBody UserRegisterVerifyRequest userRegisterVerifyRequest) {
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
    public ResponseEntity<DefaultResponse> govUserRegister(@Valid @RequestBody GovUserRegisterRequest govUserRegisterRequest) {
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

    // SUPER_ADMIN: create a new APP USER directly
    @PostMapping(path = "/admin/create-user")
    public ResponseEntity<DefaultResponse> adminCreateUser(@RequestAttribute("user") AppUser appUser, @Valid @RequestBody AdminCreateUserRequest req) {
        BaseResponse<HashMap<String, Object>> response = userService.adminCreateUser(appUser, req);
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

    // SUPER_ADMIN: create a new TRAINER directly (sets ROLE_TRAINER and Trainer entity)
    @PostMapping(path = "/admin/create-trainer")
    public ResponseEntity<DefaultResponse> adminCreateTrainer(@RequestAttribute("user") AppUser appUser, @Valid @RequestBody AdminCreateTrainerRequest req) {
        BaseResponse<HashMap<String, Object>> response = userService.adminCreateTrainer(appUser, req);
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
    public ResponseEntity<DefaultResponse> login(@Valid @RequestBody UserLoginRequest loginRequest) {
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

    @GetMapping(path = "/me/health")
    public ResponseEntity<DefaultResponse> getMyHealth(@RequestAttribute("user") AppUser appUser) {
        BaseResponse<UserHealthDetailsResponse> response = userService.getMyHealthDetails(appUser);
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

    @GetMapping(path = "/me/payments")
    public ResponseEntity<DefaultResponse> getMyPayments(@RequestAttribute("user") AppUser appUser) {
        BaseResponse<java.util.List<com.greensphere.userservice.entity.TrainerIncome>> response = userService.getMyPaymentHistory(appUser);
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

    // ADMIN: get all users with role ROLE_USER
    @GetMapping(path = "/admin/users")
    public ResponseEntity<DefaultResponse> getAllRoleUsers() {
        BaseResponse<java.util.List<AppUser>> response = userService.getAllRoleUsers();
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

    // USER: get total payments summary (count, total)
    @GetMapping(path = "/me/payments/total")
    public ResponseEntity<DefaultResponse> getMyPaymentsTotal(@RequestAttribute("user") AppUser appUser) {
        BaseResponse<java.math.BigDecimal> response = userService.getMyPaymentsTotalAmount(appUser);
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


    //trainer
    @PostMapping(path = "/trainer-activate")
    public ResponseEntity<DefaultResponse> trainerActivate(@Valid @RequestBody TrainerActivateRequest request) {
        BaseResponse<HashMap<String, Object>> response = userService.activateUser(request);
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

    //gym
    @PostMapping(path = "/gym-activate")
    public ResponseEntity<DefaultResponse> gymActivate(@Valid @RequestBody UserActivateByGymRequest request) {
        BaseResponse<HashMap<String, Object>> response = userService.activateUserByGym(request);
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

    //admin
    @PostMapping(path = "/admin-activate")
    public ResponseEntity<DefaultResponse> adminActivate(@Valid @RequestBody GymActivateByAdminRequest request) {
        BaseResponse<HashMap<String, Object>> response = userService.activateGymByAdmin(request);
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

    // GET: upcoming payments for a specific trainer
    @GetMapping("/upcoming/{trainerId}")
    public ResponseEntity<List<TrainerIncome>> getUpcomingPayments(@PathVariable Long trainerId) {
        List<TrainerIncome> upcoming = userService.getUpcomingPaymentsByTrainer(trainerId);
        return ResponseEntity.ok(upcoming);
    }

    // Create/save a payment record (auto-calculates nextPaymentDate)
    @PostMapping("/save")
    public ResponseEntity<TrainerIncome> saveIncome(@RequestBody TrainerIncome income) {
        return ResponseEntity.ok(userService.saveIncome(income));
    }

    @PutMapping("/{id}/disable")
    public ResponseEntity<String> disableUser(@PathVariable Long id) {
        userService.disableUser(id);
        return ResponseEntity.ok("User " + id + " disabled successfully.");
    }

    @PutMapping("/{id}/enable")
    public ResponseEntity<String> enableUser(@PathVariable Long id) {
        userService.enableUser(id);
        return ResponseEntity.ok("User " + id + " enabled successfully.");
    }

    // Upcoming for all trainers (admin)
    @GetMapping("/upcoming")
    public ResponseEntity<List<TrainerIncome>> getAllUpcoming() {
        return ResponseEntity.ok(userService.getAllUpcomingPayments());
    }

    // Due soon (within next N days) for a specific trainer
    @GetMapping("/due-soon/{trainerId}")
    public ResponseEntity<List<TrainerIncome>> getDueSoonByTrainer(
            @PathVariable Long trainerId,
            @RequestParam(defaultValue = "7") int days) {
        return ResponseEntity.ok(userService.getDueSoonByTrainer(trainerId, days));
    }

    // Due soon (within next N days) for all trainers (admin)
    @GetMapping("/due-soon")
    public ResponseEntity<List<TrainerIncome>> getAllDueSoon(@RequestParam(defaultValue = "7") int days) {
        return ResponseEntity.ok(userService.getAllDueSoon(days));
    }

    // Renew a payment cycle for an existing record (sets lastPaymentDate=today, recomputes nextPaymentDate)
    @PutMapping("/{incomeId}/renew")
    public ResponseEntity<TrainerIncome> renewPayment(@PathVariable Long incomeId) {
        return ResponseEntity.ok(userService.renewPayment(incomeId));
    }

}
