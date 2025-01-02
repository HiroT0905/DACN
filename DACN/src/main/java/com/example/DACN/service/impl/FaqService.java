package com.example.DACN.service.impl;


import com.example.DACN.dto.ApiResponse;
import com.example.DACN.dto.FaqDTO;
import com.example.DACN.exception.OurException;
import com.example.DACN.model.Faq;
import com.example.DACN.repository.FaqRepo;
import com.example.DACN.service.interfac.IFaqService;
import com.example.DACN.service.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class FaqService implements IFaqService {

    private final FaqRepo faqRepo;

    public FaqService(FaqRepo faqRepo) {
        this.faqRepo = faqRepo;
    }

    @Override
    public ApiResponse addFaq(String title, String description) {
        ApiResponse response = new ApiResponse();
        try{
            Faq faq = new Faq();
            faq.setTitle(title);
            faq.setDescription(description);
            faq.setTimestamp(LocalDateTime.now());

            faqRepo.save(faq);
            FaqDTO dto = Utils.mapFaqEntityToFaqDTO(faq);

            response.setCode(200);
            response.setMessage("Successful");
            response.setFaqDTO(dto);
            return response;

        }catch(OurException e){
            response.setCode(404);
            response.setMessage(e.getMessage());
            return response;
        }
        catch(Exception e){
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse getAllFaq() {
        ApiResponse response = new ApiResponse();
        try {
            List<Faq> faqs = faqRepo.findAll();
            if(!faqs.isEmpty()){
                List<FaqDTO> dto = Utils.mapFaqListEntityToDTO(faqs);
                response.setCode(200);
                response.setMessage("Successful");
                response.setFaqDTOList(dto);
                return response;
            }

        }catch(OurException e){
            response.setCode(404);
            response.setMessage(e.getMessage());
            return response;
        }catch (Exception e){
            response.setCode(500);
            response.setMessage(e.getMessage());
            return response;

        }
        return response;

    }

    @Override
    public ApiResponse deleteFaq(Long id) {
        ApiResponse response = new ApiResponse();
        try{
            faqRepo.deleteById(id);
            response.setCode(200);
            response.setMessage("Successful");
            return response;
        }catch(OurException e){
            response.setCode(404);
            response.setMessage(e.getMessage());
            return response;
        }
        catch(Exception e){
            response.setCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }

    @Override
    public ApiResponse updateFaq(Long id, String title, String description) {
        ApiResponse response   = new ApiResponse();
        try{
            var faq = faqRepo.findById(id).orElseThrow(()-> new OurException("Faq not found"));
            faq.setTitle(title);
            faq.setDescription(description);
            faq.setTimestamp(LocalDateTime.now());
            faqRepo.save(faq);
            var dto = Utils.mapFaqEntityToFaqDTO(faq);
            response.setCode(200);
            response.setMessage("Successful");
            response.setFaqDTO(dto);
            return response;
        }catch(OurException e){
            response.setCode(404);
            response.setMessage(e.getMessage());
            return response;
        }catch(Exception e){
            response.setCode(500);
            response.setMessage(e.getMessage());
            return response;

        }
    }

    @Override
    public ApiResponse getFaqById(Long id) {
        ApiResponse response = new ApiResponse();
        try{
            var faq = faqRepo.findById(id).orElseThrow(()-> new OurException("Faq not found"));
            response.setCode(200);
            response.setMessage("Successful");
            response.setFaqDTO(Utils.mapFaqEntityToFaqDTO(faq));
            return response;
        }catch(OurException e){
            response.setCode(404);
            response.setMessage(e.getMessage());
            return response;
        }catch(Exception e){
            response.setCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }
}
