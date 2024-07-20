package com.greensphere.userservice.utils;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@NoArgsConstructor
public class RandomNumberGenerator {

    private static final List<Integer> digits =
            new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

    public static String createRandomReference(int length) {
        Collections.shuffle(digits);
        final StringBuilder sb = new StringBuilder(length);
        for (Integer digit : digits.subList(0, length)) {
            sb.append(digit);
        }
        return sb.toString();
    }
}
