package com.greensphere.userservice.utils;

import com.greensphere.userservice.enums.Status;
import com.greensphere.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PhoneNumberUtil {
    private static final String PHONE_NUMBER_PATTERN = "^[0-9]+$";

    private final UserRepository userRepository;

    public static String formatNumber(String mobile) {
        if (mobile.charAt(0) == '0' && mobile.length() == 10) {
            mobile = mobile.substring(1);
        }
        final String firstTwoDigits = mobile.substring(0, 2);
        if (!firstTwoDigits.equalsIgnoreCase("94")) {
            mobile = "94" + mobile;
        }
        return mobile;
    }

    public static String formatNumberToNormal(String mobile) {

        final String firstTwoDigits = mobile.substring(0, 2);
        if (firstTwoDigits.equalsIgnoreCase("94")) {
            mobile = "0" + mobile.substring(mobile.length() - 9);
        }
        return mobile;
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches(PHONE_NUMBER_PATTERN);
    }

    public boolean isUniquePhoneNumber(String phoneNumber) {
        String formattedPhoneNumber = formatNumber(phoneNumber);
        boolean isCustomerExists = userRepository.existsAppUserByMobileAndStatus(formattedPhoneNumber, Status.ACTIVE.name());
        if (isCustomerExists) {
            log.warn("isUniquePhoneNumber-> phone: {} already exists", formattedPhoneNumber);
            return false;
        }
        log.info("isUniquePhoneNumber-> phone: {} verified", formattedPhoneNumber);
        return true;
    }
}
