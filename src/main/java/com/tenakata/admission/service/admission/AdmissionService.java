package com.tenakata.admission.service.admission;

import com.tenakata.admission.controller.request.AdmissionRequest;
import com.tenakata.admission.exception.Response;

public interface AdmissionService {

    Response<?> saveAdmission(AdmissionRequest admissionRequest);
}
