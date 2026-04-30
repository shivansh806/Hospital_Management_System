package com.shivansh.code.hospitalManagement.controller;

import com.shivansh.code.hospitalManagement.dto.PatientResponseDto;
import com.shivansh.code.hospitalManagement.service.PatientServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PatientServices patientServices;

    @GetMapping("/patients")
    public ResponseEntity<Page<PatientResponseDto>> findAllPatients(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "Size", defaultValue = "2") Integer pageSize
    ){
        return ResponseEntity.status(HttpStatus.OK).body(patientServices.getAllPatients(pageNumber, pageSize));
    }

}
