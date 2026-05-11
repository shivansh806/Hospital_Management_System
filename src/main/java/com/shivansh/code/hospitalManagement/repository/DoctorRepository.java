package com.shivansh.code.hospitalManagement.repository;

import com.shivansh.code.hospitalManagement.entity.Doctor;
import com.shivansh.code.hospitalManagement.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUser_Id(Long userId);
}
