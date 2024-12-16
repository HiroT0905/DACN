package com.example.DACN.exception;

import com.example.DACN.model.User;

import java.util.regex.Pattern;

public class UserValidator {

    // Hàm validate toàn bộ các trường hợp
    public static void validateUserInput(User user) {
        if (!isValidCccd(user.getUsername())) {
            throw new OurException("CCCD phải là số và có 12 ký tự");
        }

        if (!isValidPassword(user.getPassword())) {
            throw new OurException("Mật khẩu phải có ít nhất 6 ký tự, bao gồm ít nhất một chữ cái viết hoa và một chữ số");
        }

        if (!isValidEmail(user.getEmail())) {
            throw new OurException("Email không hợp lệ");
        }

        if (!isValidPhone(user.getPhone())) {
            throw new OurException("Số điện thoại phải có đúng 10 chữ số");
        }
    }

    // Kiểm tra CCCD (phải là số và có đúng 12 ký tự)
    private static boolean isValidCccd(String cccd) {
        return cccd != null && cccd.matches("\\d{12}");
    }

    // Kiểm tra mật khẩu (phải có ít nhất 6 ký tự, một số và một chữ cái viết hoa)
    private static boolean isValidPassword(String password) {
        // Kiểm tra mật khẩu có ít nhất một chữ cái viết hoa, một số và dài hơn 6 ký tự
        return password != null && password.length() >= 6 &&
                password.matches(".*[A-Z].*") &&  // Có ít nhất một chữ cái viết hoa
                password.matches(".*\\d.*");    // Có ít nhất một chữ số
    }

    // Kiểm tra email (đúng định dạng email)
    private static boolean isValidEmail(String email) {
        // Định dạng email chuẩn (có thể dùng regex đơn giản)
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && email.matches(emailRegex);
    }

    // Kiểm tra số điện thoại (phải có đúng 10 chữ số)
    private static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }
}
