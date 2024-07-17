package com.tenakata.admission.model.admission;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Accessors(chain = true)
public class SaveAdmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    private String gender;
    private String maritalStatus;
    private Double height;
    private Integer iq;
    private String location;
    private String imageUrl;
    private Double score;
    private String country;
    private LocalDateTime timeStamp;
}
