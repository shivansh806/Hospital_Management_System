package com.shivansh.code.hospitalManagement.service;

import com.shivansh.code.hospitalManagement.dto.PatientResponseDto;
import com.shivansh.code.hospitalManagement.dto.UpdatePatientProfileRequestDto;
import com.shivansh.code.hospitalManagement.entity.Patient;
import com.shivansh.code.hospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServices {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @PreAuthorize("hasRole('PATIENT')")
    @Transactional
    public PatientResponseDto getPatientById(Long userId){
        Patient patient = patientRepository
                .findByUser_Id(userId)
                .orElseThrow(() ->
                        new RuntimeException("Patient not found")
                );
        return modelMapper.map(patient, PatientResponseDto.class);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<PatientResponseDto> getAllPatients(Integer pageNumber, Integer pageSize){
        return patientRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(patient -> modelMapper.map(patient, PatientResponseDto.class));
    }

    @PreAuthorize("hasRole('PATIENT')")
    @Transactional
    public PatientResponseDto updatePatientProfile(
            UpdatePatientProfileRequestDto request,
            Long userId
    ){
        Patient patient = patientRepository.findByUser_Id(userId)
                .orElseThrow(()->new EntityNotFoundException("Patient not found by id "+ userId));

        if(request.getName() != null){
            patient.setName(request.getName());
        }
        if(request.getGender() != null){
            patient.setGender(request.getGender());
        }
        if(request.getBloodGroup() != null){
            patient.setBloodGroup(request.getBloodGroup());
        }
        if(request.getBirthDate() != null){
            patient.setBirthDate(request.getBirthDate());
        }

        Patient savedPatient = patientRepository.save(patient);
        return modelMapper.map(savedPatient, PatientResponseDto.class);
    }
}
