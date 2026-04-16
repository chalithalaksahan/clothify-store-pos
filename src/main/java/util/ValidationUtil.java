package util;


import java.time.LocalDate;

public class ValidationUtil {

    // The Custom Exception remains the same
    public static class ValidationException extends RuntimeException {
        public ValidationException(String message) {
            super(message);
        }
    }

    // 1. Validates ANY String (No JavaFX TextField required)
    public static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " is required.");
        }
        return value.trim();
    }

    // 2. Validates ANY Number String
    public static String requireNumber(String value, String fieldName) {
        String text = requireText(value, fieldName);
        if (!text.matches("\\d+(\\.\\d+)?")) {
            throw new ValidationException(fieldName + " must be a valid positive number.");
        }
        return text;
    }

    // 3. Validates ANY Object (No JavaFX ComboBox required)
    public static <T> T requireNotNull(T value, String fieldName) {
        if (value == null) {
            throw new ValidationException("Please select or provide a " + fieldName + ".");
        }
        return value;
    }

    // Validates ANY selection (Dropdowns, Radio Buttons, Checkboxes)
    public static <T> T requireSelection(T value, String fieldName) {
        // We check if it's null.
        // We also check if it's a String that happens to be empty (like an empty prompt text)
        if (value == null || (value instanceof String && ((String) value).trim().isEmpty())) {
            throw new ValidationException("Please select a " + fieldName + ".");
        }
        return value;
    }
    // 1. Email Validation (Using a standard industry Regex)
    public static String requireValidEmail(String value, String fieldName) {
        String text = requireText(value, fieldName);

        // This regex guarantees it has text, an '@' symbol, text, a dot, and a valid domain ending.
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        if (!text.matches(emailRegex)) {
            throw new ValidationException("Please enter a valid " + fieldName + " (e.g., name@example.com).");
        }
        return text;
    }

    // 2. Password Validation (Enforces length, can easily be upgraded)
    public static String requireValidPassword(String value, String fieldName, int minLength) {
        String text = requireText(value, fieldName);

        if (text.length() < minLength) {
            throw new ValidationException(fieldName + " must be at least " + minLength + " characters long.");
        }
        if (!text.matches(".*\\d.*")) {
             throw new ValidationException(fieldName + " must contain at least one number.");
        }
        return text;
    }

    // 3. Date Validation (Perfect for Hire Dates or Birthdays)
    public static LocalDate requireDate(LocalDate date, String fieldName) {
        if (date == null) {
            throw new ValidationException("Please select a " + fieldName + ".");
        }
        return date;
    }

    // 4. Advanced Date Validation (Prevents users from setting a hire date in the future)
    public static LocalDate requirePastOrPresentDate(LocalDate date, String fieldName) {
        LocalDate validDate = requireDate(date, fieldName);

        if (validDate.isAfter(LocalDate.now())) {
            throw new ValidationException(fieldName + " cannot be a future date.");
        }
        return validDate;
    }
    // 4. NEW: Universal Regex Matcher (Great for Emails, Passwords, etc.)
    public static String requirePattern(String value, String regex, String errorMessage) {
        String text = requireText(value, "Field");
        if (!text.matches(regex)) {
            throw new ValidationException(errorMessage);
        }
        return text;
    }
}