package com.example.DACN.service.impl;

import com.example.DACN.dto.ApiResponse;
import com.example.DACN.dto.EventDTO;
import com.example.DACN.exception.EventValidator;
import com.example.DACN.exception.OurException;
import com.example.DACN.model.Appointment;
import com.example.DACN.model.DonationUnit;
import com.example.DACN.model.Event;
import com.example.DACN.model.status.EventStatus;
import com.example.DACN.repository.AppointmentRepo;
import com.example.DACN.repository.BloodInventoryRepo;
import com.example.DACN.repository.DonationUnitRepo;
import com.example.DACN.repository.EventRepo;
import com.example.DACN.service.interfac.IEventService;
import com.example.DACN.service.utils.Utils;
import jakarta.persistence.Transient;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


//Đang handle phần getByUnitName
@Service
public class EventService implements IEventService {
    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private DonationUnitRepo donationUnitRepo;
    @Autowired
    private AppointmentRepo appointmentRepo;
    @Autowired
    private BloodInventoryRepo bloodInventoryRepo;

    public ApiResponse addNewEvent(Map<String, String> eventData) {
        ApiResponse response = new ApiResponse();

        try {
            // Lấy các giá trị từ map
            String eventDate = eventData.get("eventDate");
            String eventStartTime = eventData.get("eventStartTime");
            String eventEndTime = eventData.get("eventEndTime");
            Long maxRegistrations = Long.parseLong(eventData.get("maxRegistrations"));
            Long unitId = Long.parseLong(eventData.get("unitId"));
            String status = eventData.get("status");

            // Kiểm tra đơn vị máu có tồn tại hay không
            DonationUnit unit = donationUnitRepo.findById(unitId)
                    .orElseThrow(() -> new OurException("Donation Unit not found!"));



            // Tạo sự kiện
            Event event = new Event();
            event.setEventDate(LocalDate.parse(eventDate));  // Chuyển đổi từ String sang LocalDate
            event.setEventStartTime(LocalTime.parse(eventStartTime));  // Chuyển đổi từ String sang LocalTime
            event.setEventEndTime(LocalTime.parse(eventEndTime));  // Chuyển đổi từ String sang LocalTime
            event.setMaxRegistrations(maxRegistrations);
            event.setStatus(status != null ? EventStatus.valueOf(status) : EventStatus.ACTIVE);  // Set mặc định là ACTIVE nếu không có status
            event.setDonationUnit(unit);
            event.setCurrentRegistrations(0L);
            event.setName(event.getEventName());  //
            // Lưu sự kiện
            EventDTO eventDTO = Utils.mapEventEntityToEventDTO(event);
            EventValidator.validateEvent(eventDTO);
            response.setCode(200);
            response.setMessage("Event added successfully!");
            response.setEventDTO(eventDTO);  // Hoặc có thể tạo EventDTO và trả về
            Event savedEvent = eventRepo.save(event);

        }catch(OurException e){
            response.setCode(404);
            response.setMessage(e.getMessage());
        }
        catch (Exception e) {
            response.setCode(500);
            response.setMessage("Error: " + e.getMessage());
        }

        return response;
    }


    @Transient
    @Override
    public ApiResponse deleteEvent(Long id) {
        ApiResponse response = new ApiResponse();
        try{
            List<Appointment> appointments = appointmentRepo.findByEventId(id);
            List<Long> appointmentIds = appointments.stream()
                    .map(Appointment::getId)
                    .collect(Collectors.toList());
            appointmentRepo.deleteByIdIn(appointmentIds);
            bloodInventoryRepo.deleteByAppointmentIdIn(appointmentIds);
            eventRepo.deleteById(id);
            response.setCode(200);
            response.setMessage("Xóa thành công");
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

//    @Override
//    public ApiResponse updateEvent(Long id,Event event) {
//        ApiResponse response = new ApiResponse();
//        try{
//            Event existEvent = eventRepo.findById(id).orElseThrow(()-> new OurException("No Event found"));
//            EventValidator.validateEvent(event);
//            existEvent.setName(event.getName());
//            existEvent.setEventDate(event.getEventDate());
//            existEvent.setEventStartTime(event.getEventStartTime());
//            existEvent.setEventEndTime(event.getEventEndTime());
//            existEvent.setDonationUnit(event.getDonationUnit());
//            existEvent.setStatus(EventStatus.ACTIVE);
//            Event updatedEvent = eventRepo.save(existEvent);
//            EventDTO eventDTO = Utils.mapEventEntityToEventDTO(updatedEvent);
//
//            response.setCode(200);
//            response.setMessage("Successful");
//            response.setEventDTO(eventDTO);
//        }catch(OurException e){
//            response.setCode(404);
//            response.setMessage(e.getMessage());
//        }catch(Exception e){
//            response.setCode(500);
//            response.setMessage(e.getMessage());
//        }
//
//        return null;
//    }

    @Override
    public ApiResponse updateEvent(Long id, EventDTO eventDTO) {
        ApiResponse response = new ApiResponse();
        try {
            // Kiểm tra sự tồn tại của sự kiện
            Event existEvent = eventRepo.findById(id)
                    .orElseThrow(() -> new OurException("No Event found with ID: " + id));

            // Validate các trường dữ liệu từ DTO
//            EventValidator.validateEventDTO(eventDTO);

            // Cập nhật thông tin sự kiện từ DTO
            existEvent.setEventDate(eventDTO.getEventDate());
            existEvent.setEventStartTime(eventDTO.getEventStartTime());
            existEvent.setEventEndTime(eventDTO.getEventEndTime());
            existEvent.setMaxRegistrations(eventDTO.getMaxRegistrations());
            existEvent.setStatus(EventStatus.valueOf(eventDTO.getStatus()));

            // Cập nhật đơn vị hiến máu nếu được cung cấp
            if (eventDTO.getDonationUnitDTO() != null) {
                DonationUnit donationUnit = donationUnitRepo.findById(eventDTO.getDonationUnitDTO().getId())
                        .orElseThrow(() -> new OurException("No Donation Unit found with ID: "
                                + eventDTO.getDonationUnitDTO().getId()));
                existEvent.setDonationUnit(donationUnit);
            }

            EventValidator.validateEvent(eventDTO);

            existEvent.setName(eventDTO.getName());
            // Lưu sự kiện đã cập nhật
            Event updatedEvent = eventRepo.save(existEvent);

            // Ánh xạ Entity -> DTO để trả về
            EventDTO updatedEventDTO = Utils.mapEventEntityToEventDTO(updatedEvent);

            // Thiết lập phản hồi thành công
            response.setCode(200);
            response.setMessage("Event updated successfully.");
            response.setEventDTO(updatedEventDTO);
            return response; // Trả về phản hồi

        } catch (OurException e) {
            response.setCode(404);
            response.setMessage(e.getMessage());
            return response; // Trả về phản hồi

        } catch (Exception e) {
            response.setCode(500);
            response.setMessage("An unexpected error occurred: " + e.getMessage());
            return response; // Trả về phản hồi

        }

    }



    @Override
    public ApiResponse getAllEvents() {
        ApiResponse response = new ApiResponse();
        try {
            List<Event> eventList = eventRepo.findAll();
            List<EventDTO> eventDTOList = Utils.mapEventListEntityToDTO(eventList);
            if (!eventList.isEmpty()){
                response.setCode(200);
                response.setMessage("Successful");
                response.setEventDTOList(eventDTOList);
            }else{
                response.setCode(401);
                response.setMessage("No events found");
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
    public ApiResponse getEvent(Long id) {
        ApiResponse response = new ApiResponse();
        try{
            Event event = eventRepo.findById(id).orElseThrow(()-> new OurException("No event found"));
            EventDTO eventDTO = Utils.mapEventEntityToEventDTO(event);
            response.setCode(200);
            response.setMessage("Successfully");
            response.setEventDTO(eventDTO);
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
    public ApiResponse getEventByDate(LocalDate eventDate) {
        ApiResponse response = new ApiResponse();
        try{
            List<Event> event = eventRepo.findEventByEventDate(eventDate);
            if (event == null) {
                response.setCode(404);
                response.setMessage("No event found for the given date.");
                return response;
            }
            List<EventDTO> eventDTO = Utils.mapEventListEntityToDTO(event);
                response.setCode(200);
                response.setMessage("Successful");
                response.setEventDTOList(eventDTO);

        }catch(OurException e){
            response.setCode(404);
            response.setMessage(e.getMessage());
        }catch(Exception e){
            response.setCode(500);
            response.setMessage(e.getMessage());
        }

        return response;

    }



    public ApiResponse getEventsByDateRange(LocalDate startDate, LocalDate endDate) {
        ApiResponse response = new ApiResponse();
        try {
            // Kiểm tra dữ liệu đầu vào
            if (startDate == null || endDate == null) {
                throw new IllegalArgumentException("Start date and end date must not be null.");
            }

            if (startDate.isAfter(endDate)) {
                throw new IllegalArgumentException("Start date cannot be after end date.");
            }

            // Tìm các sự kiện trong khoảng ngày
            List<Event> events = eventRepo.findEventsByEventDateBetween(startDate, endDate);
            events.sort(Comparator.comparing(Event::getEventDate));
            // Nếu không tìm thấy sự kiện nào
            if (events.isEmpty()) {
                response.setCode(200); // Trả về 200 nhưng danh sách trống
                response.setMessage("No events found for the given date range.");
                response.setNewsDTOList(Collections.emptyList());
                return response;
            }

            // Chuyển đổi danh sách sự kiện sang DTO
            List<EventDTO> eventDTOList = Utils.mapEventListEntityToDTO(events);
            response.setCode(200);
            response.setMessage("Successful");
            response.setEventDTOList(eventDTOList);

        } catch (IllegalArgumentException e) {
            response.setCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage("Internal server error: " + e.getMessage());
        }

        return response;
    }
   @Override
    public ApiResponse getEventByUnit(Long unitId) {
        ApiResponse response = new ApiResponse();
        try{
            DonationUnit unit = donationUnitRepo.findById(unitId).orElseThrow(() -> new OurException("No unit found"));
            if(unit.getEvents() == null ){
                response.setCode(404);
                response.setMessage("No event found for this unit .");
            }
            List<Event> events = unit.getEvents();
            List<EventDTO> eventDTO = Utils.mapEventListEntityToDTO(events);
            response.setCode(200);
            response.setMessage("Successful");
            response.setEventDTOList(eventDTO);

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
    public ApiResponse getEventByDateAndUnitName(LocalDate startDate,LocalDate endDate, Long unitId) {

        ApiResponse response = new ApiResponse();
        donationUnitRepo.findById(unitId).orElseThrow(()->new OurException("No event faound"));
        var i = eventRepo.findEventsByEventDateBetween(startDate,endDate);
        if(i.isEmpty()){
            response.setCode(404);
            response.setMessage("No events found");
            return response;
        }

        try{
            List<Event> events = eventRepo.findEventByEventDateBetweenAndDonationUnit_Id(startDate,endDate, unitId);
            if (events.isEmpty()){
                response.setCode(404);
                response.setMessage("No events found");
            }
            List<EventDTO> eventDTO = Utils.mapEventListEntityToDTO(events);
            response.setCode(200);
            response.setMessage("Successful");
            response.setEventDTOList(eventDTO);

        }catch(OurException e){
                response.setCode(404);
                response.setMessage(e.getMessage());
        }catch(Exception e){
                response.setCode(500);
                response.setMessage(e.getMessage());
        }
           return  response;
    }




}
