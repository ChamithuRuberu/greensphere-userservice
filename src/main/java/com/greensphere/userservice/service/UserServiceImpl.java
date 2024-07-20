package com.greensphere.userservice.service;

import com.greensphere.userservice.dto.request.userRegister.UserRegisterRequestDto;
import com.greensphere.userservice.dto.response.BaseResponse;
import com.greensphere.userservice.dto.response.notificationServiceResponse.SmsResponse;
import com.greensphere.userservice.entity.AppUser;
import com.greensphere.userservice.entity.Parameter;
import com.greensphere.userservice.entity.Role;
import com.greensphere.userservice.enums.Status;
import com.greensphere.userservice.exceptions.MissingParameterException;
import com.greensphere.userservice.repository.ParameterRepository;
import com.greensphere.userservice.repository.UserRepository;
import com.greensphere.userservice.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final ApiConnector apiConnector;
    private final RoleServiceImpl roleService;
    private final ParameterRepository parameterRepository;

    public void persistUser(AppUser appUser) {
        try {
            userRepository.save(appUser);
        } catch (Exception e) {
            log.error("persistUser-> Exception: {}", e.getMessage(), e);
        }
    }
    public BaseResponse<HashMap<String, Object>> registerInit(UserRegisterRequestDto registerInitRequest) {
        try {
            String mobile = PhoneNumberUtil.formatNumber(registerInitRequest.getMobile());
            String email = registerInitRequest.getEmail();
            String nic = registerInitRequest.getNic();

            AppUser appUser;
            List<AppUser> appUsers = userRepository.findAppUsersByNicOrMobileOrEmail(nic, mobile, email);
            if (!appUsers.isEmpty()) {
                if (appUsers.size() > 1) {
                    AppUser isExist = appUsers.stream().
                            filter(a ->
                                    nic.equals(a.getNic()) &&
                                            email.equals(a.getEmail()) &&
                                            mobile.equals(a.getMobile()))
                            .findFirst()
                            .orElse(null);
                    if (isExist == null) {
                        log.error("registerInit-> AppUser entered details already exists in the database, but not in the same appUser");
                        return BaseResponse.<HashMap<String, Object>>builder()
                                .code(ResponseCodeUtil.FAILED_CODE)
                                .title(ResponseUtil.FAILED)
                                .message("AppUser entered details already exists for another customer, Please recheck the details you entered.")
                                .build();
                    }
                    appUser = isExist;
                } else {
                    appUser = appUsers.get(0);

                }
            } else {
                // save app appUser in INITIALIZED status
                appUser = AppUser.builder()
                        .mobile(mobile)
                        .email(email)
                        .nic(nic)
                        .build();
                Role roleByName = roleService.getRoleByName(registerInitRequest.getRoleType());
                Set<Role> objects = new HashSet<>();
                objects.add(roleByName);
                appUser.setRoles(objects);
                persistUser(appUser);
                log.info("registerInit -> user saved in INITIATED status, mobile: {}, email: {}, nic: {}", mobile, email, nic);

            }
            log.info("registerInit -> sending registration otp to user");
            Parameter otpLengthParameter = parameterRepository.findParameterByName(AppConstants.OTP_LENGTH);
            if (otpLengthParameter==null) {
                log.warn("registerInit -> OTP_LENGTH parameter is missing from database");
                throw new MissingParameterException("OTP_LENGTH parameter is missing from database, Please add missing OTP_LENGTH parameter");
            }

            Parameter otpMessageParameter = parameterRepository.findParameterByName(AppConstants.OTP_MESSAGE);
            if (otpMessageParameter==null) {
                log.warn("registerInit -> OTP_MESSAGE parameter is missing from database");
                throw new MissingParameterException("OTP_MESSAGE parameter is missing from database, Please add missing OTP_MESSAGE parameter");
            }
            String otp = RandomNumberGenerator.createRandomReference(Integer.parseInt(otpLengthParameter.getValue()));
            String otpMessage = otpMessageParameter.getValue().replace("<otp>", otp);

            //API CALL NOTIFICATION SERVICE ->
            SmsResponse smsResponse = apiConnector.sendSms(mobile, otpMessage);
            if (!smsResponse.getCode().equals(ResponseCodeUtil.SUCCESS_CODE)){
                throw new NullPointerException("sms response is null " + smsResponse.getMessage());
            }
            appUser.setOtp(otp);
            appUser.setOtpStatus(smsResponse.getMessage());
            appUser.setOtpAttempts(appUser.getOtpAttempts() + 1);
            appUser.setOtpSentAt(LocalDateTime.now());

            appUser.setStatus(Status.PENDING.name());
            persistUser(appUser);

            HashMap<String, Object> data = new HashMap<>();
            data.put("app_user_id", appUser.getUsername());
            data.put("mobile", mobile);

            return BaseResponse.<HashMap<String, Object>>builder()
                    .code(ResponseCodeUtil.SUCCESS_CODE)
                    .title(ResponseUtil.SUCCESS)
                    .message("AppUser initiated successfully")
                    .data(data)
                    .build();
        } catch (Exception e) {
            log.error("registerInit -> Exception : {}", e.getMessage(), e);
            return BaseResponse.<HashMap<String, Object>>builder()
                    .code(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE)
                    .title(ResponseUtil.INTERNAL_SERVER_ERROR)
                    .message("Error occurred while initializing appUser")
                    .build();
        }
    }
}
