package com.shivansh.code.hospitalManagement.service;

import com.shivansh.code.hospitalManagement.dto.DoctorResponseDto;
import com.shivansh.code.hospitalManagement.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorServices {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    public List<DoctorResponseDto> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
                .toList();
    }

    @PreAuthorize("hasRole('DOCTOR')")
    public DoctorResponseDto getDoctorProfile(Long doctorId){
        return doctorRepository.findByUser_Id(doctorId)
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
                .orElseThrow(()-> new RuntimeException("Doctor not found by id "+ doctorId));
    }
}
