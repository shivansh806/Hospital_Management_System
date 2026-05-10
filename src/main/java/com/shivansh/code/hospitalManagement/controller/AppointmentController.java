package com.shivansh.code.hospitalManagement.controller;

import com.shivansh.code.hospitalManagement.dto.AppointmentResponseDto;
import com.shivansh.code.hospitalManagement.dto.CreateAppointmentRequestDto;
import com.shivansh.code.hospitalManagement.response.ApiResponse;
import com.shivansh.code.hospitalManagement.service.AppointmentServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentServices appointmentServices;

    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> createNewAppointment(@Valid @RequestBody CreateAppointmentRequestDto createAppointmentRequestDto){
        AppointmentResponseDto appointment = appointmentServices.createNewAppointment(createAppointmentRequestDto);
        ApiResponse<AppointmentResponseDto> apiResponse =
                new ApiResponse<>("Appointment Created Sucessfully", appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PatchMapping("/{appointmentId}/doctor/{doctorId}")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> reassignAppointment(
            @PathVariable Long appointmentId,
            @PathVariable Long doctorId
    ){
        AppointmentResponseDto appointment = appointmentServices.reassignAppointment(appointmentId, doctorId);
        ApiResponse<AppointmentResponseDto> apiResponse =
                new ApiResponse<>("Appointment reassigned Successfully..", appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
