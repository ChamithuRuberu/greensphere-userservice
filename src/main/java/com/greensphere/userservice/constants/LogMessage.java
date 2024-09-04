package com.greensphere.userservice.constants;

public class LogMessage {
    //log prefix
    public static final String USER_ALREADY_EXIST="AppUser Already Exist";
    public static final String USER_REGISTERED_SUCCESSFULLY="AppUser Registered Successfully";
    public static final String EXCEPTION = "Exception -> {}";

    public static final String OTP_VERIFY = "otpVerify -> {}";
    public static final String OTP_SEND = "sendOtpByEmailAndSms -> ";
    public static final String SAVE_OTP = "saveOtp -> {}";
    public static final String UPDATE_OTP = "updateOtp -> {}";
    public static final String SEND_OTP = "sendOtp -> {}";
    public static final String FORGOT_OTP = "forgotOtp -> {}";

//    public static final String OTP_ATTEMPTS_BALANCE = "Otp Attempts remaining -> {}";
//    public static final String FOREIGN_NUMBER = "foreign number -> {}";
//    public static final String REQUEST = "Request -> {}";
//    public static final String RESPONSE = "Response -> {}";
//    public static final String URL = "Url -> {}";


    public static final String REGISTER = "Register  -> {}";

    public static final String LOGIN = "Login mobile -> {}";


    //log content
    public static final String INVALID_CREDENTIAL = "Invalid credentials.";
    public static final String USER_DISABLED = "AppUser Disabled";
    public static final String USER_LOCKED = "AppUser Locked";
    public static final String USER_NOT_ACTIVE = "AppUser not active";
    public static final String BAD_REQUEST = "Bad request";
    public static final String ERROR_OCCURRED = "Error occurred in the process";
    public static final String CAN_NOT_FIND_USER = "Cannot find AppUser";
    public static final String INPUT_VALIDATION_ERROR = "input validation error";

    // SEND OTP
    public static final String IS_OTP_SENT = "OTP email sent: {}";
    public static final String SEND_OTP_TO_EMAIL = "Trying to send an OTP to the email: {}";
    public static final String SEND_OTP_TO_MOBILE = "Trying to send an OTP to the mobile: {}";


    //ReturnResponseUtil response specification
    public static final String RETURN_RESPONSE_UTIL = "ReturnResponseUtil -> {}";
    public static final String SUCCESS_RESPONSE = "success response";
    public static final String FAILED_RESPONSE = "failed response";
    public static final String INTERNAL_SERVER_ERROR_RESPONSE = "internal server error response";

}
