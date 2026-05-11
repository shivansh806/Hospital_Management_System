package com.shivansh.code.hospitalManagement.controller;

import com.shivansh.code.hospitalManagement.dto.AppointmentResponseDto;
import com.shivansh.code.hospitalManagement.dto.CreateAppointmentRequestDto;
import com.shivansh.code.hospitalManagement.entity.User;
import com.shivansh.code.hospitalManagement.response.ApiResponse;
import com.shivansh.code.hospitalManagement.service.AppointmentServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentServices appointmentServices;

    @PostMapping("/me")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> createNewAppointment(
            @Valid
            @RequestBody CreateAppointmentRequestDto request,
            Authentication authentication){
        User user = (User) authentication.getPrincipal();
        AppointmentResponseDto appointment = appointmentServices.createNewAppointment(request, user.getId());
        ApiResponse<AppointmentResponseDto> apiResponse =
                new ApiResponse<>("Appointment Created Sucessfully", appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDto>>> getMyAppointments(
            Authentication authentication
    ){
        User user = (User) authentication.getPrincipal();
        List<AppointmentResponseDto> appointment = appointmentServices.getMyAppointments(user.getId());
        ApiResponse<List<AppointmentResponseDto>> apiResponse =
                new ApiResponse<>("Appointments fetched Sucessfully", appointment);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<ApiResponse<String>> deleteAppointment(
            @PathVariable Long appointmentId,
            Authentication authentication
    ){
        User user = (User) authentication.getPrincipal();
        appointmentServices.deleteMyAppointment(appointmentId, user.getId());
        ApiResponse<String> apiResponse =
                new ApiResponse<>("Appointment deleted Sucessfully", null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PatchMapping("/{appointmentId}/reassign-doctor/{doctorId}")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> reassignAppointment(
            @PathVariable Long appointmentId,
            @PathVariable Long doctorId,
            Authentication authentication
    ) throws AccessDeniedException {
        User user = (User) authentication.getPrincipal();
        AppointmentResponseDto appointment = appointmentServices.reassignDoctor(appointmentId, doctorId, user.getId());
        ApiResponse<AppointmentResponseDto> apiResponse =
                new ApiResponse<>("Appointment reassigned Successfully..", appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
