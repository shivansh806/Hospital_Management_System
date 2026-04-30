package com.shivansh.code.hospitalManagement.dto;

import com.shivansh.code.hospitalManagement.entity.type.BloodGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponseDto {
    private Long id;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private BloodGroup bloodGroup;
    private InsuranceResponseDto insurance;
}
