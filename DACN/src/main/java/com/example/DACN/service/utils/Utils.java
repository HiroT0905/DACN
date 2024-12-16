//package com.example.DACN.service;
//
//import com.example.DACN.dto.DonationUnitDTO;
//import com.example.DACN.dto.EventDTO;
//import com.example.DACN.dto.UserDTO;
//import com.example.DACN.dto.UserInfoDTO;
//import com.example.DACN.model.DonationUnit;
//import com.example.DACN.model.Event;
//import com.example.DACN.model.User;
//import com.example.DACN.model.UserInfo;
//
//import java.security.SecureRandom;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class Utils {
//    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//    private static final SecureRandom secureRandom = new SecureRandom();
//    public static String generateRandomConfirmationCode(int length) {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 0; i < length; i++) {
//            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
//            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
//            stringBuilder.append(randomChar);
//        }
//        return stringBuilder.toString();
//    }
//
//    public static UserDTO mapUserEntityToUserDTO(User user) {
//        UserDTO userDTO = new UserDTO();
//
//        userDTO.setUsername(user.getUsername());
//
//        userDTO.setEmail(user.getEmail());
//        userDTO.setPhone(user.getPhone());
//        userDTO.setRole(user.getRole());
//        userDTO.setUserInfoDTO(mapUserInfoEntityToUserInfoDTO(user.getUserInfo()));
//        return userDTO;
//    }
//    public static UserInfoDTO mapUserInfoEntityToUserInfoDTO(UserInfo userInfo) {
//        UserInfoDTO dto = new UserInfoDTO();
//        dto.setId(userInfo.getId());
//        dto.setFullName(userInfo.getFullName());
//        dto.setDob(userInfo.getDob());
//        dto.setSex(userInfo.getSex());
//        dto.setAddress(userInfo.getAddress());
//        return dto;
//    }
//    //
//
//    public static DonationUnitDTO mapUnitEntityTOUnitDTO (DonationUnit unit){
//        DonationUnitDTO dto = new DonationUnitDTO();
//        dto.setId(unit.getId());
//        dto.setName(unit.getName());
//        dto.setPhone(unit.getPhone());
//        dto.setEmail(unit.getEmail());
//        dto.setLocation(unit.getLocation());
//        dto.setUnitPhotoUrl(unit.getUnitPhotoUrl());
//        return dto;
//    }
//
//    public static EventDTO mapEventEntityToEventDTO(Event event){
//        EventDTO dto = new EventDTO();
//        dto.setId(event.getId());
//        dto.setEventDate(event.getEventDate());
//        dto.setEventStartTime(event.getEventStartTime());
//        dto.setEventEndTime(event.getEventEndTime());
//        dto.setCurrentRegistrations(event.getCurrentRegistrations());
//        dto.setMaxRegistrations(event.getMaxRegistrations());
//
//        return dto ;
//    }
//
//
//
//    public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList) {
//        return userList.stream().map(Utils::mapUserEntityToUserDTO).collect(Collectors.toList());
//    }
//    public static List<DonationUnitDTO> mapRoomListEntityToRoomListDTO(List<DonationUnit> roomList) {
//        return roomList.stream().map(Utils::mapUnitEntityTOUnitDTO).collect(Collectors.toList());
//    }
//
//    // Ánh xạ danh sách Event Entity sang danh sách EventDTO
//    public static List<EventDTO> mapBookingListEntityToBookingListDTO(List<Event> bookingList) {
//        return bookingList.stream().map(Utils::mapEventEntityToEventDTO).collect(Collectors.toList());
//    }
//}
package com.example.DACN.service.utils;
import com.example.DACN.dto.UserDTO;
import com.example.DACN.dto.*;
import com.example.DACN.model.*;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Utils {
    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateRandomConfirmationCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    public static UserDTO mapUserEntityToUserDTO(User user) {
//        return UserDTO.builder()
//                .username(user.getUsername())
//                .phone(user.getPhone())
//                .email(user.getEmail())
//                .role(user.getRole())
//                .userInfoDTO(mapUserInfoEntityToUserInfoDTO(user.getUserInfo()))
//                .appointments(user.getAppointments().stream().map(Utils::mapAppointmentEntityToDTO).collect(Collectors.toList()))
//                .bloodDonationHistories(user.getBloodDonationHistories().stream().map(Utils::mapBloodDonationHistoryEntityToDTO).collect(Collectors.toSet()))
//                .build();

        UserDTO userDTO = new UserDTO();

        userDTO.setUsername(user.getUsername());

        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setRole(user.getRole());
        userDTO.setUserInfoDTO(mapUserInfoEntityToUserInfoDTO(user.getUserInfo()));
        if (user.getAppointments() != null){
            userDTO.setAppointments(user.getAppointments().stream().map(Utils::mapAppointmentEntityToSimpleDTO).collect(Collectors.toList()));

        }
        if (user.getBloodDonationHistories() != null){
            userDTO.setBloodDonationHistories(user.getBloodDonationHistories().stream().map(Utils::mapBloodDonationHistoryEntityToSimpleDTO).collect(Collectors.toSet()));
        }

        return userDTO;
    }
    public static BloodDonationHistoryDTO mapBloodDonationHistoryEntityToSimpleDTO(BloodDonationHistory history) {
        return BloodDonationHistoryDTO.builder()
                .id(history.getId())
                .donationDate(history.getDonationDateTime())
                .bloodAmount(history.getBloodAmount())
                .donationLocation(history.getDonationLocation())
                .notes(history.getNotes())
                .build(); // Simplified to avoid nested references
    }
    public static AppointmentDTO mapAppointmentEntityToSimpleDTO(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .appointmentDateTime(appointment.getAppointmentDateTime())
                .bloodAmount(appointment.getBloodAmount())
//                .eventName(appointment.getEvent().getName())
                .build(); // Simplified to avoid nested references
    }
    public static UserInfoDTO mapUserInfoEntityToUserInfoDTO(UserInfo userInfo) {
        return UserInfoDTO.builder()
                .id(userInfo.getId())
                .fullName(userInfo.getFullName())
                .dob(userInfo.getDob())
                .sex(userInfo.getSex())
                .address(userInfo.getAddress())
                .build();
    }

    public static DonationUnitDTO mapUnitEntityToUnitDTO(DonationUnit unit) {
        List<EventDTO> eventDTOList = Optional.ofNullable(unit.getEvents())
                .orElse(Collections.emptyList())  // Nếu getEvents() trả về null, sử dụng danh sách rỗng
                .stream()
                .map(Utils::mapEventEntityToEventDTO)
                .collect(Collectors.toList());
        return DonationUnitDTO.builder()
                .id(unit.getId())
                .name(unit.getName())
                .phone(unit.getPhone())
                .email(unit.getEmail())
                .location(unit.getLocation())

                .unitPhotoUrl(unit.getUnitPhotoUrl())
                .events(eventDTOList)
                .build();
    }
    public static DonationUnitDTO mapUnitEntityToBasicDTO(DonationUnit unit) {
        return DonationUnitDTO.builder()
                .id(unit.getId())
                .name(unit.getName())
                .phone(unit.getPhone())
                .email(unit.getEmail())
                .unitPhotoUrl(unit.getUnitPhotoUrl())
                .build(); // Chỉ ánh xạ thông tin cơ bản để tránh vòng lặp
    }

    public static EventDTO mapEventEntityToEventDTO(Event event) {
       List<AppointmentDTO> appointmentDTOList = Optional.ofNullable(event.getAppointments())
               .orElse(Collections.emptyList())
               .stream()
               .map(Utils::mapAppointmentEntityToDTO)
               .collect(Collectors.toList());

        return EventDTO.builder()
                .id(event.getId())
                .eventDate(event.getEventDate())
                .eventStartTime(event.getEventStartTime())
                .eventEndTime(event.getEventEndTime())
                .maxRegistrations(event.getMaxRegistrations())
                .currentRegistrations(event.getCurrentRegistrations())
                .status(event.getStatus().toString())
                .donationUnitDTO(mapUnitEntityToBasicDTO(event.getDonationUnit()))
                .build();
    }

    public static AppointmentDTO mapAppointmentEntityToDTO(Appointment appointment) {
        if(appointment.getBloodDonationHistory() == null){

        }
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .appointmentDateTime(appointment.getAppointmentDateTime())
                .bloodAmount(appointment.getBloodAmount())
                .eventId(appointment.getEvent() != null ? appointment.getEvent().getId() : null) // Xử lý null
                .userId(appointment.getUser() != null ? appointment.getUser().getUsername() : "Unknown") // Trả về "Unknown" nếu null
                .healthCheckId(appointment.getHealthcheck() != null ? appointment.getHealthcheck().getId() : null) // Xử lý null
                .bloodDonationHistoryId(appointment.getBloodDonationHistory() != null ? appointment.getBloodDonationHistory().getId() : null) // Xử lý null
                .status(appointment.getStatus().toString())
                .build();
    }

    public static HealthCheckDTO mapHealthcheckEntityToDTO(Healthcheck healthcheck) {
        String result = null;
        if (healthcheck.getResult() != null) {
            try {
                result = healthcheck.getResult().name();
            } catch (IllegalArgumentException e) {
                result = "UNKNOWN"; // Hoặc giá trị mặc định bạn muốn
            }
        }
        return HealthCheckDTO.builder()
                .id(healthcheck.getId())
                .healthMetrics(healthcheck.getHealthMetrics())
                .notes(healthcheck.getNotes())
                .appointmentId(healthcheck.getAppointment().getId())
                .build();
    }

    public static BloodDonationHistoryDTO mapBloodDonationHistoryEntityToDTO(BloodDonationHistory history) {
        return BloodDonationHistoryDTO.builder()
                .id(history.getId())
                .donationDate(history.getDonationDateTime())
                .bloodAmount(history.getBloodAmount())
                .donationLocation(history.getDonationLocation())
                .notes(history.getNotes())
                .appointmentId(history.getAppointment().getId())
                .build();
    }

    public static List<UserDTO> mapUserListEntityToDTO(List<User> userList) {
        if (userList == null) return List.of();
        return userList.stream().map(Utils::mapUserEntityToUserDTO).collect(Collectors.toList());
    }

    public static List<DonationUnitDTO> mapUnitListEntityToDTO(List<DonationUnit> unitList) {
        if (unitList == null) return List.of();
        return unitList.stream().map(Utils::mapUnitEntityToUnitDTO).collect(Collectors.toList());
    }

    public static List<EventDTO> mapEventListEntityToDTO(List<Event> eventList) {
        if (eventList == null) return List.of();
        return eventList.stream().map(Utils::mapEventEntityToEventDTO).collect(Collectors.toList());
    }
    public static List<AppointmentDTO> mapAppointmentListToDTO(List<Appointment> appointmentList) {
        if (appointmentList == null) return List.of();
        return appointmentList.stream().map(Utils::mapAppointmentEntityToDTO).collect(Collectors.toList());
    }
}
