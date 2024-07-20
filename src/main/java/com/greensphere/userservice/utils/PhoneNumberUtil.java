package com.greensphere.userservice.utils;

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
}
