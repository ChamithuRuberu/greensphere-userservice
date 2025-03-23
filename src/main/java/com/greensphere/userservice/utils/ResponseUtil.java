package com.greensphere.userservice.utils;

public class ResponseUtil {

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL SERVER ERROR";
    //for jwt token
    public static final String JWT_TOKEN_VALIDATE_ERROR_CODE = "4000";
    public static final String JWT_TOKEN_EXPIRED_ERROR_CODE = "4001";
    public static final String INVALID_TOKEN_ERROR_CODE = "4002";
    public static final String DEVICE_ID_MISMATCH_ERROR_CODE = "402";
    public static final String INVALID_CREDENTIAL = "Invalid credentials.";
    public static final String USER_ALREADY_LOGGED_IN_ANOTHER_DEVICE = "User already logged in another device..";

    //Gym Controller
    public static final String GYM_REGISTERED = "Gym Registration Successfully.";
    public static final String GYM_REGISTERED_FAILED = "Gym Registration Failed.";



}
