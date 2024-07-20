/**
 * Created By Dilsha Prasanna
 * Date : 3/29/2024
 * Time : 5:22 PM
 * Project Name : com-credit-backend
 */

package com.greensphere.userservice.utils;

public class AppConstants {

    public static final String LOGIN_ATTEMPTS = "LOGIN_ATTEMPTS";
    public static final String CHANGE_NOTIFICATION_STATUS = "CHANGE_NOTIFICATION_STATUS";
    public static final String LOGIN_ATTEMPTS_EXCEEDED = "User account is disabled due to  login attempts exceeded.";
    public static final String LOGIN_ATTEMPTS_EXCEED_MESSAGE = "LOGIN_ATTEMPTS_EXCEED_MESSAGE";
    public static final String PASSWORD_RESET_MESSAGE_PORTAL = "PASSWORD_RESET_MESSAGE_PORTAL";

    private AppConstants(){

    }
    public static final String BYPASS_APP_VALIDATION_FILTER = "BY_PASS_APP_VALIDATION_FILTER";
    public static final String OWN_BANK_CODE = "OWN_BANK_CODE";
    public static final String QR_GL_ACCOUNT = "QR_GL_ACCOUNT";
    public static final String USER_NOT_REGISTERED_CODE = "UserNotRegistered";
    public static final String ROLE_APP_USER = "ROLE_APP_USER";

    // Parameter table
    public static final String OTP_SENDER_NAME = "OTP_SENDER_NAME";
    public static final String SSL_FINGERPRINTS = "SSL_FINGERPRINTS";
    public static final String BILLER="BILLER";
    public static final String CREDIT_CARD="CREDIT_CARD";
    public static final String FCM_PRIORITY="FCM_PRIORITY";
    public static final String PAYEE="PAYEE";
    public static final String OWN_BANK_CEFT_CODE = "OWN_BANK_CEFT_CODE";
    public static final String FT_OWN_GL_ACCOUNT = "FT_OWN_GL_ACCOUNT";
    public static final String OTP_LENGTH = "OTP_LENGTH";
    public static final String OTP_MESSAGE = "OTP_MESSAGE";
    public static final String OTP_EXPIRED_TIME = "OTP_EXPIRED_TIME";
    public static final String OTP_VERIFY_ATTEMPTS = "OTP_VERIFY_ATTEMPTS";
    public static final String LOGIN_ATTEMPTS_EXCEEDED_MESSAGE = "LOGIN_ATTEMPTS_EXCEEDED_MESSAGE";
    public static final String CC_JWT_TOKEN = "CC_JWT_TOKEN";

    public static final String USER_TO_INTERMEDIATE_ACCOUNT = "USER_TO_INTERMEDIATE_ACCOUNT";
    public static final String INTERMEDIATE_ACCOUNT_TO_BENEFICIARY = "INTERMEDIATE_ACCOUNT_TO_BENEFICIARY";

    public static final String CHANGE_MOBILE_SEND_OTP="CHANGE_MOBILE_SEND_OTP";
    public static final String CHANGE_EMAIL_SEND_OTP="CHANGE_EMAIL_SEND_OTP";
    public static final String RE_SEND_OTP="RE_SEND_OTP";

    // Module Codes
    public static final String SAVING_ACCOUNT_MODULE_CODE="SV";

    public static final String OWN_CEFT_BANK_CODE = "6870";

}
