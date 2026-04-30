package com.shivansh.code.hospitalManagement.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InsuranceResponseDto {
    private String policyNumber;
    private String provider;
    private LocalDate validUntil;
}
