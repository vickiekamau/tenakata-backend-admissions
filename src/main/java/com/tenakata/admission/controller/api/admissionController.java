package com.tenakata.admission.controller.api;

import com.tenakata.admission.controller.request.AdmissionRequest;
import com.tenakata.admission.exception.Response;
import com.tenakata.admission.service.admission.AdmissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tenakata")
@CrossOrigin(origins = "*", maxAge = 3600)
public class admissionController {
    @Autowired
    private AdmissionService admissionService;

    @PostMapping("/admission")
    public Response<?> filterTargetsExport(@Valid @RequestBody AdmissionRequest request){
        return admissionService.saveAdmission(request);
    }

}
