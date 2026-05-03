package com.shivansh.code.hospitalManagement.controller;

import com.shivansh.code.hospitalManagement.dto.DoctorResponseDto;
import com.shivansh.code.hospitalManagement.service.DoctorServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class HospitalController {

    private final DoctorServices doctorServices;

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorServices.getAllDoctors());
    }

}
