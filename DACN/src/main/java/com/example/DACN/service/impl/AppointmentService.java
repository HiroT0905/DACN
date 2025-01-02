package com.example.DACN.service.impl;

import com.example.DACN.dto.HealthMetrics;
import com.example.DACN.exception.AppointmentValidator;
import com.example.DACN.exception.UserValidator;

import com.example.DACN.dto.ApiResponse;
import com.example.DACN.dto.AppointmentDTO;
import com.example.DACN.exception.EventValidator;
import com.example.DACN.exception.OurException;
import com.example.DACN.model.*;
import com.example.DACN.model.status.AppointmentStatus;
import com.example.DACN.repository.*;
import com.example.DACN.service.interfac.IAppointmentService;
import com.example.DACN.service.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.ResolverStyle;
import java.util.Comparator;
import java.util.List;

@Service
public class AppointmentService implements IAppointmentService {
    @Autowired
    private  AppointmentRepo appointmentRepo;
    @Autowired
    private  DonationUnitRepo donationUnitRepo;
    @Autowired
    private  EventRepo eventRepo;
    @Autowired
    private  UsersRepo usersRepo;
    @Autowired
    private  HealthCheckRepo healthCheckRepo;;



    @Override
    public ApiResponse saveAppointment(String username, Long eventId, HealthMetrics healthMetrics ) {
        ApiResponse response = new ApiResponse();
        Appointment appointment = new Appointment();
        Healthcheck healthCheck  = new Healthcheck();
        try{
            Event event = eventRepo.findById(eventId).orElseThrow(()->new OurException("Event not found"));
            User user = usersRepo.findUserByUsername(username).orElseThrow(()->new OurException("User not found"));
            if(!EventValidator.isValidQuantity(event.getCurrentRegistrations(),event.getMaxRegistrations())){
                response.setCode(404);
                response.setMessage("Event is full");
                return response;
            }
            if(event.getEventDate().isBefore(LocalDate.now())){
                response.setCode(404);
                response.setMessage("Sự kiện đã diễn ra. Vui lòng chọn sự kiện hiến máu khác");
                return response;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String healthMetricsJson = objectMapper.writeValueAsString(healthMetrics);
            healthCheck.setHealthMetrics(healthMetricsJson);
            healthCheck.setAppointment(appointment);
            if (!healthCheck.isValidHealthCheck()) {
                response.setCode(404);
                response.setMessage("Healthcheck failed. Cannot book appointment.");
                healthCheckRepo.save(healthCheck); // Lưu trạng thái FAIL
                return response;
            }
            List<Appointment> a1 = appointmentRepo.findByUser_Username(username).stream().toList();


            appointment.setEvent(event);
            appointment.setUser(user);
            appointment.setHealthcheck(healthCheck);
            appointment.setAppointmentDateTime(LocalDateTime.now());
            appointment.setStatus(AppointmentStatus.PENDING);

            Appointment latestCompletedAppointment = a1.stream()
                    .filter(a -> a.getStatus() == AppointmentStatus.COMPLETED)
                    .max(Comparator.comparing(Appointment::getAppointmentDateTime))
                    .orElse(null);

            if (latestCompletedAppointment != null) {
                LocalDateTime nextEligibleDate = latestCompletedAppointment.getNextDonationEligibleDate();
                if (nextEligibleDate != null && LocalDateTime.now().isBefore(nextEligibleDate)) {
                    response.setCode(404);
                    response.setMessage("Bạn không đủ điều kiện để đặt một cuộc hẹn khác. Vui lòng quay lại vào: " + nextEligibleDate);
                    return response;
                }
            }
            if (!AppointmentValidator.validAdd(a1, appointment)){
                response.setCode(404);
                response.setMessage("Không thể đặt lịch nữa vì đã có lịch được đăng ký trước đó!!!");
                return response;
            }


            appointmentRepo.save(appointment);
            response.setCode(200);
            response.setMessage("Successfully saved appointment");
            event.setCurrentRegistrations(event.getCurrentRegistrations()+1);
            eventRepo.save(event);
        }catch(OurException e){
            response.setCode(404);
            response.setMessage(e.getMessage());
        }catch(Exception e){
            response.setCode(500);
            response.setMessage("Error Saving a booking"+e.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse getAllAppointment() {
      ApiResponse response = new ApiResponse();
      try{
          List<Appointment> appointmentList = appointmentRepo.findAll();
          List<AppointmentDTO> appointmentDTOList = Utils.mapAppointmentListToDTO(appointmentList);
          if (!appointmentList.isEmpty()){
              response.setCode(200);
              response.setMessage("Successful");
              response.setAppointmentDTOList(appointmentDTOList);
              return response;
          }
          response.setCode(401);
          response.setMessage("No appointment found");
          return response;
      }catch(OurException e){
          response.setCode(404);
          response.setMessage(e.getMessage());
      }catch(Exception e){
          response.setCode(500);
          response.setMessage(e.getMessage());
      }
      return response;
    }

    @Override
    public ApiResponse getById(Long id) {
        ApiResponse response = new ApiResponse();
        Appointment appointment = appointmentRepo.findById(id).orElseThrow(()->new OurException("No appointment found"));

        try{
            AppointmentDTO appointmentDTO = Utils.mapAppointmentEntityToDTO(appointment);
            response.setCode(200);
            response.setMessage("Successful");
            response.setAppointmentDTO(appointmentDTO);

        } catch (Exception e ){
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public  ApiResponse getAppointmentPendingUser(String username){
        ApiResponse response = new ApiResponse();
        try{
            var a1=AppointmentValidator.validStatus(appointmentRepo.findByUser_Username(username));
            if (a1 != null){
                AppointmentDTO a2 = Utils.mapAppointmentEntityToDTO(a1);
                response.setCode(200);
                response.setMessage("Successful");
                response.setAppointmentDTO(a2);
            }
            if(a1 == null){
                AppointmentDTO a2 = Utils.mapAppointmentEntityToDTO(a1);
                response.setCode(404);
                response.setMessage("BadRequest");
                response.setAppointmentDTO(a2);
                return response;
            }
        }catch(OurException e){
            response.setCode(404);
            response.setMessage(e.getMessage());
        }catch(Exception e){
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;

    }
    @Override
    public ApiResponse getUserAppointments(String username) {
        ApiResponse response = new ApiResponse();
        try{
            var appointment= appointmentRepo.findByUser_Username(username);
            if (!appointment.isEmpty()){
                List<AppointmentDTO> appointmentDTOList = Utils.mapAppointmentListToDTO(appointment);
                response.setCode(200);
                response.setMessage("Successful");
                response.setAppointmentDTOList(appointmentDTOList);
            }
        }catch(OurException e){
            response.setCode(404);
            response.setMessage(e.getMessage());
        }catch(Exception e){
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
            return response;

    }

    @Override
    public ApiResponse updateAppointmentStatus(Long appointmentId, AppointmentStatus status) {
       ApiResponse response = new ApiResponse();
       try{
            Appointment appointment = appointmentRepo.findById(appointmentId).orElseThrow(()->new OurException("No appointment found"));
            appointment.setStatus(status);
            if (appointment.getStatus().equals(AppointmentStatus.COMPLETED)){
                appointment.setNextDonationEligibleDate(LocalDateTime.now().plusMonths(3));
            }
            else
                appointment.setNextDonationEligibleDate(null);

            appointmentRepo.save(appointment);
            AppointmentDTO appointmentDTO = Utils.mapAppointmentEntityToDTO(appointment);
            response.setCode(200);
            response.setMessage("Successful");
            response.setAppointmentDTO(appointmentDTO);
            return response;
       }catch(OurException e){
           response.setCode(404);
           response.setMessage(e.getMessage());
       }catch (Exception e){
           response.setCode(500);
           response.setMessage(e.getMessage());
       }

       return response;
    }

    @Transactional
    public ApiResponse deleteAppointment(Long id){
        // Tìm cuộc hẹn của người dùng theo tên
        ApiResponse api = new ApiResponse();
        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(() -> new OurException("Appointment not found or already processed"));

        try{
            appointmentRepo.delete(appointment);
            api.setCode(200);
            api.setMessage("Successfully");
            return api;
        }catch(Exception e){
            api.setCode(500);
            api.setMessage("Loi he thong");
            return api;
        }
        // Xóa cuộc hẹn

        // Trả về phản hồi thành công
    }

}
