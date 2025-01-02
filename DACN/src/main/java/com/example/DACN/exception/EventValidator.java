package com.example.DACN.exception;

import com.example.DACN.model.Event;
import com.example.DACN.model.status.EventStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventValidator {

    public static void validateEvent(Event event) {

        if (!isValidDate(event.getEventDate())){
            throw new OurException("Date is not valid");
        }
        if(!isValidTime(event.getEventStartTime(),event.getEventEndTime())){
            throw new OurException("Time is not valid");
        }
        if (!isValidQuantity(event.getCurrentRegistrations(), event.getMaxRegistrations())){
            throw new OurException("Quantity is not valid");
        }
        if (!isValidDate(event.getEventDate())){
            throw new OurException("Date is not valid");
        }
        if (!isValidStatus(event.getStatus().toString())){
            throw new OurException("Status is not valid");
        }
    }




   public static boolean isValidDate(LocalDate date){
        return date.isAfter(LocalDate.now());
   }
   public static boolean isValidTime(LocalTime startTime, LocalTime endTime){
        return startTime.isBefore(endTime);
   }

   public static boolean isValidQuantity(Long current, Long max){
        return current< max;
   }

   public static boolean isValidStatus(String status){
        return status.equals(EventStatus.ACTIVE.toString());
   }

}
