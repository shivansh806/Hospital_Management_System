package com.shivansh.code.hospitalManagement.service;

import com.shivansh.code.hospitalManagement.dto.PatientResponseDto;
import com.shivansh.code.hospitalManagement.entity.Patient;
import com.shivansh.code.hospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServices {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public PatientResponseDto getPatientById(Long patientId){
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()-> new EntityNotFoundException("Patient Not found with id "+patientId));
        return modelMapper.map(patient, PatientResponseDto.class);
    }

    public Page<PatientResponseDto> getAllPatients(Integer pageNumber, Integer pageSize){
        return patientRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(patient -> modelMapper.map(patient, PatientResponseDto.class));
    }
}
