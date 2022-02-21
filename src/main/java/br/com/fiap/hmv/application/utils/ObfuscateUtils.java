package br.com.fiap.hmv.application.utils;

import lombok.NoArgsConstructor;

import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ObfuscateUtils {

    public static final String DEFAULT_MASK = "******";

    public static String obfuscateTaxId(String taxId) {
        if (nonNull(taxId)) {
            String onlyNumbers = taxId.replaceAll("\\D", "");
            if (11 == onlyNumbers.length()) {
                return replaceWithMask(3, 9, onlyNumbers, "******");
            } else if (14 == onlyNumbers.length()) {
                return replaceWithMask(3, 12, onlyNumbers, "*********");
            } else {
                return DEFAULT_MASK;
            }
        }
        return null;
    }

    public static String obfuscateToken(String token) {
        if (nonNull(token)) {
            String onlyAlphanumeric = token.replaceAll("[^0-9A-Za-z]", "");
            int onlyAlphanumericLength = onlyAlphanumeric.length();
            if (onlyAlphanumericLength >= 24) {
                int end = onlyAlphanumericLength - 10;
                return replaceWithMask(4, end, onlyAlphanumeric, DEFAULT_MASK);
            } else {
                return DEFAULT_MASK;
            }
        }
        return null;
    }

    private static String replaceWithMask(int start, int end, String value, String mask) {
        return new StringBuilder(value).replace(start, end, mask).toString();
    }

}