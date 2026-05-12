package com.shivansh.code.hospitalManagement.dto;

import com.shivansh.code.hospitalManagement.entity.type.BloodGroup;
import com.shivansh.code.hospitalManagement.entity.type.GenderType;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdatePatientProfileRequestDto {
    private String name;
    private GenderType gender;
    private BloodGroup bloodGroup;
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;
}
