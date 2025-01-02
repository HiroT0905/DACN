package com.example.DACN.service.impl;

import com.example.DACN.dto.ApiResponse;
import com.example.DACN.dto.BloodInventoryDTO;
import com.example.DACN.exception.OurException;
import com.example.DACN.model.Appointment;
import com.example.DACN.model.BloodInventory;
import com.example.DACN.repository.AppointmentRepo;
import com.example.DACN.repository.BloodInventoryRepo;
import com.example.DACN.service.interfac.IBloodInventoryService;
import com.example.DACN.service.utils.Utils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class BloodInventoryService implements IBloodInventoryService {
    @Autowired
    private BloodInventoryRepo bloodInventoryRepo;

    @Autowired
    private AppointmentRepo appointmentRepo;
    @Override
    public ApiResponse addNew(String donationType, int quantity, LocalDateTime lastUpdate, LocalDateTime expirationTime, Long appointmentId){
        ApiResponse response = new ApiResponse();
        try{

            var app = appointmentRepo.findById(appointmentId);
            if (app.get().getBloodInventory() != null){
                response.setCode(404);
                response.setMessage("Dữ liệu đã tồn tại!!");
                return response;
            }else if ("COMPLETED".equalsIgnoreCase(app.get().getStatus().name())){
                BloodInventory bI = new BloodInventory();
                bI.setBloodType(donationType);
                bI.setQuantity(quantity);
                bI.setLastUpdated(lastUpdate);
                bI.setExpirationDate(expirationTime);

                bI.setAppointment(app.get());
                bloodInventoryRepo.save(bI);
                BloodInventoryDTO dto = Utils.mapBloodInventoryToDTO(bI);
                var app1= app.get();
                app1.setBloodInventory(bI);
                appointmentRepo.save(app1);
                response.setCode(200);
                response.setMessage("Successful");
                response.setBloodInventoryDTO(dto);

            }
            else {
                response.setCode(404);
                response.setMessage("Bạn không có quyền thêm vì trạng thái cuộc hẹn không phù hợp");
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
    public  ApiResponse getAll(){
        ApiResponse response = new ApiResponse();
        try{
            List<BloodInventory> bloodInventories = bloodInventoryRepo.findAll();
            if (!bloodInventories.isEmpty()){
                List<BloodInventoryDTO> dto = Utils.mapListBloodInventoryToDTO(bloodInventories);
                response.setCode(200);
                response.setMessage("Successful");
                response.setBloodInventoryDTOList(dto);
                return response;
            }
            else{
                response.setCode(404);
                response.setMessage("Danh sach rong");
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
    public ApiResponse get(Long id){
        ApiResponse response = new ApiResponse();
        try{
            var bI = bloodInventoryRepo.findById(id).orElseThrow(()->new OurException("Không tìm thấy thông tin hiến máu "));
            BloodInventoryDTO dto = Utils.mapBloodInventoryToDTO(bI);
            response.setCode(200);
            response.setMessage("Successful");
            response.setBloodInventoryDTO(dto);
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

    public  ApiResponse update(Long id,String bloodType, int quantity, LocalDateTime lastUpdated, LocalDateTime expirationDate, Long appointmentId){
        ApiResponse response = new ApiResponse();
        try{
            var existed = bloodInventoryRepo.findById(id).orElseThrow(()->new OurException("Không tìm thấy thông tin hiến máu "));
            existed.setBloodType(bloodType);
            existed.setQuantity(quantity);
            existed.setExpirationDate(expirationDate);
            existed.setLastUpdated(lastUpdated);
            existed.setAppointment(appointmentRepo.findById(appointmentId).get());
            var dto = Utils.mapBloodInventoryToDTO(existed);
            response.setCode(200);
            response.setMessage("Successful");
            response.setBloodInventoryDTO(dto);

        }catch(OurException e){
            response.setCode(404);
            response.setMessage(e.getMessage());
        }catch(Exception e){
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

}
