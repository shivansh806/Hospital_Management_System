package com.shivansh.code.hospitalManagement.repository;

import com.shivansh.code.hospitalManagement.dto.CountBloodGroupTypeEntity;
import com.shivansh.code.hospitalManagement.entity.Patient;
import com.shivansh.code.hospitalManagement.entity.type.BloodGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByName(String name);
    Patient findByBirthDate(LocalDate localDate);

    Optional<Patient> findByUser_Id(Long userId);

    List<Patient> findByBirthDateOrEmail(LocalDate localDate, String email);

    List<Patient> findByNameLike(String str);

    @Query("Select p from Patient p where p.bloodGroup =?1")
    List<Patient> findByBloodGroup(BloodGroup bloodGroup);

    @Query("Select p from Patient p where p.birthDate > :birthDate")
    List<Patient> findByBornAfterDate(@Param("birthDate") LocalDate localDate);

    @Query("Select new com.shivansh.code.hospitalManagement.dto.CountBloodGroupTypeEntity(p.bloodGroup, count(p)) " +
            "from Patient p group by p.bloodGroup")
//    List<Object[]> findAndCountEachBloodGroup();
    List<CountBloodGroupTypeEntity> findAndCountEachBloodGroup();

//    @NativeQuery("Select * from Patient")
    @Query(value = "Select * from Patient", nativeQuery = true)
    Page<Patient> findAllPatient(Pageable pageable);

    @Query("select p from Patient p left join fetch p.appointments")
    List<Patient> findPatientWithAppointments();

}

