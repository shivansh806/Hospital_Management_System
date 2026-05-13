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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServices {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;


    @PreAuthorize("hasRole('PATIENT')")
    @Transactional
    public AppointmentResponseDto createNewAppointment(CreateAppointmentRequestDto request, Long userId){

        Long doctorId = request.getDoctorId();

        Patient patient = patientRepository.findByUser_Id(userId)
                .orElseThrow(()-> new EntityNotFoundException("Patient Not found by id "+userId));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(()-> new EntityNotFoundException("Doctor Not found by id "+ doctorId));

        Appointment appointment = Appointment.builder()
                .reason(request.getReason())
                .appointmentTime(request.getAppointmentTime())
                .patient(patient)
                .doctor(doctor)
                .build();

        patient.getAppointments().add(appointment);

        appointment = appointmentRepository.save(appointment);

        return modelMapper.map(appointment, AppointmentResponseDto.class);
    }

    @PreAuthorize("hasRole('PATIENT')")
    @Transactional
    public List<AppointmentResponseDto> getMyAppointments(Long userId){
        Patient patient = patientRepository.findByUser_Id(userId)
                .orElseThrow(()-> new EntityNotFoundException("Patient Not Found by id "+ userId));

        List<Appointment> appointments =
                appointmentRepository.findByPatient(patient);

        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .toList();
    }

    @PreAuthorize("hasRole('PATIENT')")
    @Transactional
    public void deleteMyAppointment(Long appointmentId, Long userId){
        Patient patient = patientRepository.findByUser_Id(userId)
                .orElseThrow(()->new EntityNotFoundException("Patient Not Found by id "+ userId));

        Appointment appointment = appointmentRepository
                .findById(appointmentId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Appointment not found"
                        )
                );

        if(!appointment.getPatient().getId().equals(patient.getId())){
            throw new IllegalArgumentException("Appointment does not belong to the patient");
        }
        appointmentRepository.delete(appointment);
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @Transactional
    public AppointmentResponseDto reassignDoctor(Long appointmentId, Long newDoctorId, Long loggedInUserId) throws AccessDeniedException {
        Doctor loggedInDoctor = doctorRepository
                .findByUser_Id(loggedInUserId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Doctor not found"
                        )
                );

        Appointment appointment = appointmentRepository
                .findById(appointmentId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Appointment not found"
                        )
                );

        if(!appointment.getDoctor().getId()
                .equals(loggedInDoctor.getId())){

            throw new AccessDeniedException(
                    "You can only reassign your own appointments"
            );
        }

        Doctor newDoctor = doctorRepository
                .findById(newDoctorId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "New doctor not found"
                        )
                );
        appointment.setDoctor(newDoctor);
        return modelMapper.map(appointment, AppointmentResponseDto.class);
    }

    @PreAuthorize("hasRole('DOCTOR')")
    public List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long userId) {
        Doctor doctor = doctorRepository.findByUser_Id(userId).orElseThrow();

        return doctor.getAppointments()
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .toList();
    }
}
