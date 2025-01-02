package com.example.DACN.service.impl;

import com.example.DACN.dto.ApiResponse;
import com.example.DACN.dto.EventDTO;
import com.example.DACN.exception.EventValidator;
import com.example.DACN.exception.OurException;
import com.example.DACN.model.DonationUnit;
import com.example.DACN.model.Event;
import com.example.DACN.model.status.EventStatus;
import com.example.DACN.repository.DonationUnitRepo;
import com.example.DACN.repository.EventRepo;
import com.example.DACN.service.interfac.IEventService;
import com.example.DACN.service.utils.Utils;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;



//Đang handle phần getByUnitName
@Service
public class EventService implements IEventService {
    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private DonationUnitRepo donationUnitRepo;

    @Override
    public ApiResponse addNewEvent(Event event) {

        ApiResponse response = new ApiResponse();

        try{
            EventValidator.validateEvent(event);
            DonationUnit unit = donationUnitRepo.findById(event.getDonationUnit().getId()).orElseThrow(() -> new OurException("Donation Unit not found!!!"));
            List<Event> existedEventList = unit.getEvents();
            for (Event a : existedEventList){
                if (event == a ){
                    throw new IllegalArgumentException("The event is existed. Please check the Date or something!!!");
                }
            }
            event.setDonationUnit(unit);
            event.setName(event.getEventName());
            Event savedEvent = eventRepo.save(event);
            EventDTO eventDTO = Utils.mapEventEntityToEventDTO(savedEvent);
            response.setCode(200);
            response.setMessage("Successful");
            response.setEventDTO(eventDTO);

        }catch(Exception e){
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return  response;
    }

    @Override
    public ApiResponse deleteEvent(Long id) {
        ApiResponse response = new ApiResponse();
        try{
            eventRepo.findById(id).orElseThrow(()-> new OurException("No event found"));
            eventRepo.deleteById(id);
            response.setCode(200);
            response.setMessage("Successful");
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
    public ApiResponse updateEvent(Long id,Event event) {
        ApiResponse response = new ApiResponse();
        try{
            Event existEvent = eventRepo.findById(id).orElseThrow(()-> new OurException("No Event found"));
            EventValidator.validateEvent(event);
            existEvent.setName(event.getName());
            existEvent.setEventDate(event.getEventDate());
            existEvent.setEventStartTime(event.getEventStartTime());
            existEvent.setEventEndTime(event.getEventEndTime());
            existEvent.setDonationUnit(event.getDonationUnit());
            existEvent.setStatus(EventStatus.ACTIVE);
            Event updatedEvent = eventRepo.save(existEvent);
            EventDTO eventDTO = Utils.mapEventEntityToEventDTO(updatedEvent);

            response.setCode(200);
            response.setMessage("Successful");
            response.setEventDTO(eventDTO);
        }catch(OurException e){
            response.setCode(404);
            response.setMessage(e.getMessage());
        }catch(Exception e){
            response.setCode(500);
            response.setMessage(e.getMessage());
        }

        return null;
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
    public ApiResponse getEventByDateAndUnitName(LocalDate eventDate, Long unitId) {

        ApiResponse response = new ApiResponse();
        donationUnitRepo.findById(unitId).orElseThrow(()->new OurException("No event faound"));
        var i = eventRepo.findEventByEventDate(eventDate);
        if(i.isEmpty()){
            response.setCode(404);
            response.setMessage("No events found");
            return response;
        }

        try{
            List<Event> events = eventRepo.findEventByEventDateAndDonationUnitId(eventDate, unitId);
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
