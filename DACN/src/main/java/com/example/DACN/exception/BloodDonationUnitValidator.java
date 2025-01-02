package com.example.DACN.exception;

import com.example.DACN.dto.DonationUnitDTO;
import com.example.DACN.model.DonationUnit;

public class BloodDonationUnitValidator {

    public static void validateUnit(DonationUnit dto ){
        if (!isValidEmail(dto.getEmail())){
            throw new OurException("Email sai định dạng. Vui lòng nhập lại email!!!");

        }
        if (!isValidPhone(dto.getPhone())){
            throw new OurException("SỐ điện thoại sai định dạng. Vui lòng nhập lại số điện thoại!!!");
        }

    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && email.matches(emailRegex);
    }

    private static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }
}
