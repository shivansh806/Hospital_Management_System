package com.shivansh.code.hospitalManagement.service;

import com.shivansh.code.hospitalManagement.dto.InsuranceRequestDto;
import com.shivansh.code.hospitalManagement.dto.InsuranceResponseDto;
import com.shivansh.code.hospitalManagement.dto.PatientResponseDto;
import com.shivansh.code.hospitalManagement.entity.Insurance;
import com.shivansh.code.hospitalManagement.entity.Patient;
import com.shivansh.code.hospitalManagement.repository.InsuranceRepository;
import com.shivansh.code.hospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceServices {

    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;


    @PreAuthorize("hasRole('PATIENT')")
    @Transactional
    public InsuranceResponseDto createInsurance(InsuranceRequestDto insurance, Long userId){
        Patient patient = patientRepository.findByUser_Id(userId)
                .orElseThrow(()-> new EntityNotFoundException("Patient not found by id"+userId));

        Insurance newInsurance = Insurance.builder()
                        .policyNumber(insurance.getPolicyNumber())
                                .provider(insurance.getProvider())
                                        .validUntil(insurance.getValidUntil())
                                                .build();

        patient.setInsurance(newInsurance);
        newInsurance.setPatient(patient);  // bidirectional mapping

        return modelMapper.map(newInsurance, InsuranceResponseDto.class);
    }

    @PreAuthorize("hasRole('PATIENT')")
    @Transactional
    public InsuranceResponseDto deleteInsurance(Long userId){
        Patient patient = patientRepository.findByUser_Id(userId)
                .orElseThrow();
        Insurance insurance = patient.getInsurance();
        patient.setInsurance(null);
        return modelMapper.map(insurance, InsuranceResponseDto.class);
    }

}
