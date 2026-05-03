package com.shivansh.code.hospitalManagement.service;

import com.shivansh.code.hospitalManagement.dto.AppointmentResponseDto;
import com.shivansh.code.hospitalManagement.dto.CreateAppointmentRequestDto;
import com.shivansh.code.hospitalManagement.entity.Appointment;
import com.shivansh.code.hospitalManagement.entity.Doctor;
import com.shivansh.code.hospitalManagement.entity.Patient;
import com.shivansh.code.hospitalManagement.repository.AppointmentRepository;
import com.shivansh.code.hospitalManagement.repository.DoctorRepository;
import com.shivansh.code.hospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServices {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;


    @Transactional
    public AppointmentResponseDto createNewAppointment(CreateAppointmentRequestDto createAppointmentRequestDto){

        Long patientId = createAppointmentRequestDto.getPatientId();
        Long doctorId = createAppointmentRequestDto.getDoctorId();

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()-> new EntityNotFoundException("Patient Not found by id "+patientId));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(()-> new EntityNotFoundException("Doctor Not found by id "+ doctorId));

        Appointment appointment = Appointment.builder()
                        .reason(createAppointmentRequestDto.getReason())
                                .appointmentTime(createAppointmentRequestDto.getAppointmentTime())
                                        .build();

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        patient.getAppointments().add(appointment);

        appointment = appointmentRepository.save(appointment);

        AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDto();
        appointmentResponseDto.setAppointmentTime(appointment.getAppointmentTime());
        appointmentResponseDto.setId(appointment.getId());
        appointmentResponseDto.setReason(appointment.getReason());

        return appointmentResponseDto;
    }

    @Transactional
    public AppointmentResponseDto reassignAppointment(Long appointment_id, Long doctor_id){
        Appointment appointment = appointmentRepository.findById(appointment_id)
                .orElseThrow();
        Doctor doctor = doctorRepository.findById(doctor_id)
                .orElseThrow();

        appointment.setDoctor(doctor);

        return modelMapper.map(appointment, AppointmentResponseDto.class);
    }

    public List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        return doctor.getAppointments()
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .toList();
    }
}
