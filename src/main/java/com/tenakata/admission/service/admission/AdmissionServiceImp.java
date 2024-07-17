package com.tenakata.admission.service.admission;

import com.tenakata.admission.controller.request.AdmissionRequest;
import com.tenakata.admission.exception.AccessDeniedException;
import com.tenakata.admission.exception.Response;
import com.tenakata.admission.model.admission.SaveAdmission;
import com.tenakata.admission.repository.admission.SaveAdmissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Service
@Slf4j
public class AdmissionServiceImp implements AdmissionService{
    @Autowired
    private SaveAdmissionRepository saveAdmissionRepository;
    @Override
    public Response<?> saveAdmission(AdmissionRequest admissionRequest) {
        var name = admissionRequest.getName();
        var age = 0;
        String gender = "";
        var maritalStatus = admissionRequest.getMaritalStatus();
        var location = admissionRequest.getLocation();
        var imageUrl = admissionRequest.getImageUrl();
        String country = "";
        var height = 0.0;
        var iq = 0;
        double score = 0.0;

        if(!Objects.isNull(admissionRequest.getIq())) {
            iq = admissionRequest.getIq();
        }
        if(!Objects.isNull(admissionRequest.getHeight())) {
            height = admissionRequest.getHeight();
        }
        if(!Objects.isNull(admissionRequest.getCountry())) {
            country = admissionRequest.getCountry();
        }
        if(!Objects.isNull(admissionRequest.getGender())) {
            gender = admissionRequest.getGender();
        }
        if (!Objects.isNull(admissionRequest.getAge())) {
             age = admissionRequest.getAge();
        }
        SaveAdmission admission = new SaveAdmission();

        if (iq >= 100 && country.equalsIgnoreCase("kenya")) {
            if (gender.equalsIgnoreCase("male")) {
                score = 43.5;

                if (age < 26) {
                    score = score/2;
                }
            } else if (gender.equalsIgnoreCase("female")) {
                score = 56.5;

                if (age < 26) {
                    score = score /2;
                }
            }
            log.info("score " +score);
            admission.setIq(iq).setAge(age).setGender(gender).setCountry(country).setHeight(height).setLocation(location)
                    .setImageUrl(imageUrl).setMaritalStatus(maritalStatus).setName(name).setScore(score).setTimeStamp(LocalDateTime.now(ZoneId.of("Africa/Nairobi")));
            saveAdmissionRepository.save(admission);


        }
        else if (iq < 100) {
            throw new AccessDeniedException("Minimum IQ Required is 100");

        } else {
            throw new AccessDeniedException("Candidate Does not Reside in Kenya");
        }
         return Response.ok().setPayload(admission);


}
}
