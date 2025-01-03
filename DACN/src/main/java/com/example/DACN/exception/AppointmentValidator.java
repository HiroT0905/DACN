package com.example.DACN.exception;

import com.example.DACN.model.Appointment;
import com.example.DACN.model.status.AppointmentStatus;
import com.example.DACN.repository.AppointmentRepo;

import java.util.List;

public class AppointmentValidator {

    private AppointmentRepo appointmentRepo;
    public static boolean validAdd(List<Appointment> a1, Appointment a2){
        for (Appointment element: a1){
            if (element.getStatus().equals(a2.getStatus())){
                return false;
            }
        }

        return  true;
    }
    public static Appointment validStatus(List<Appointment> a1){
        for (Appointment e: a1){
            if(e.getStatus()== AppointmentStatus.PENDING){
                return e;
            }
        }
        return null;
    }


}
