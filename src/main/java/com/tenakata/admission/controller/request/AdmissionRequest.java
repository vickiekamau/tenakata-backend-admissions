package com.tenakata.admission.controller.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
public class AdmissionRequest {
    private String name;
    private Integer age;
    private String gender;
    private String maritalStatus;
    private Double height;
    private Integer iq;
    private String location;
    private String imageUrl;
    private String country;
}
