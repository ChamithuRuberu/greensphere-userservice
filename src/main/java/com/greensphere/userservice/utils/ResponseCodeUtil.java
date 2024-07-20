package com.greensphere.userservice.utils;

public class ResponseCodeUtil {

    public static final String SUCCESS_CODE = "0000";
    public static final String INTERNAL_SERVER_ERROR_CODE = "1010";
    public static final String FAILED_CODE = "2020";
    public static final String PARAMETER_MISSING = "2026";
    public static final String FAILED = "FAILED";

    //for jwt token
    public static final String JWT_TOKEN_VALIDATE_ERROR_CODE = "4000";
    public static final String JWT_TOKEN_EXPIRED_ERROR_CODE = "4001";
    public static final String INVALID_TOKEN_ERROR_CODE = "4002";
    public static final String OTP_EXPIRED = "4006";


    //for otp
    public static final String OTP_SENT_FAILED_ERROR_CODE = "5000";
    public static final String INVALID_OTP_ERROR_CODE = "5001";
    public static final String OTP_ATTEMPTS_EXCEED_ERROR_CODE = "5002";
    public static final String OTP_NOT_FOUND_ERROR_CODE = "5003";
    public static final String OTP_VERIFICATION_FAILED_ERROR_CODE = "5004";
    public static final String TIME_EXCEED_ERROR_CODE = "5005";


    //
    public static final String USER_EXISTS_ERROR_CODE = "6001";
    public static final String DISABLE_USER_ERROR_CODE = "6002";
    public static final String DEVICE_LIMIT_EXCEED_ERROR_CODE = "6003";
    public static final String INVALID_LOGIN_METHOD_ERROR_CODE = "6004";
    public static final String USER_NOT_ACTIVE_ERROR_CODE = "6005";
    public static final String CANNOT_FIND_USER_ROLES = "6010";
    public static final String USER_DOESNT_HAVE_PERMISSION = "6020";
    public static final String CANNOT_FIND_USER = "6030";
    public static final String EMPTY_PARAMETER = "6050";
    public static final String LOCKED_USER_ERROR_CODE = "6006";
}
