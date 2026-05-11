package com.shivansh.code.hospitalManagement.repository;

import com.shivansh.code.hospitalManagement.entity.Appointment;
import com.shivansh.code.hospitalManagement.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient(Patient patient);
}
