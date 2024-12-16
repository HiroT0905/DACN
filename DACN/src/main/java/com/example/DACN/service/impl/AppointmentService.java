package com.example.DACN.service.impl;


import com.example.DACN.dto.ApiResponse;
import com.example.DACN.dto.AppointmentDTO;
import com.example.DACN.exception.EventValidator;
import com.example.DACN.exception.OurException;
import com.example.DACN.model.*;
import com.example.DACN.model.status.AppointmentStatus;
import com.example.DACN.repository.AppointmentRepo;
import com.example.DACN.repository.DonationUnitRepo;
import com.example.DACN.repository.EventRepo;
import com.example.DACN.repository.UsersRepo;
import com.example.DACN.service.interfac.IAppointmentService;
import com.example.DACN.service.utils.Utils;
import jdk.jshell.execution.Util;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.ResolverStyle;
import java.util.List;

@Service
public class AppointmentService implements IAppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final DonationUnitRepo donationUnitRepo;
    private final EventRepo eventRepo;
    private final UsersRepo usersRepo;

    public AppointmentService(AppointmentRepo appointmentRepo, DonationUnitRepo donationUnitRepo, EventRepo eventRepo, UsersRepo usersRepo) {
        this.appointmentRepo = appointmentRepo;
        this.donationUnitRepo = donationUnitRepo;
        this.eventRepo = eventRepo;
        this.usersRepo = usersRepo;
    }

    @Override
    public ApiResponse saveAppointment(String username, Long eventId) {
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
            appointment.setEvent(event);
            appointment.setUser(user);
            appointment.setHealthcheck(healthCheck);
            appointment.setAppointmentDateTime(LocalDateTime.now());
            appointment.setStatus(AppointmentStatus.PENDING);
            appointmentRepo.save(appointment);
            response.setCode(200);
            response.setMessage("Successfully saved appointment");

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

}
