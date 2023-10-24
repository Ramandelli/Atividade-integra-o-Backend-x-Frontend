package br.edu.umfg.secaudit.ordermanagement.validation;

import java.time.LocalDate;

public class Preconditions {

    public static void checkCondition(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkNotBlank(String argument, String message) {
        if (!(argument != null && !argument.isBlank())) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkNotNull(LocalDate argument, String message) {
        if (argument == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkSize(String argument, int min, int max, String message) {
        if (argument.length() < min || argument.length() > max) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkAge(LocalDate birthday, int anos, String message) {
        int age = LocalDate.now().getYear() - birthday.getYear();
        if (age < anos) {
            throw new IllegalArgumentException(message);
        }
    }

}
