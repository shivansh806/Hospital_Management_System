package com.shivansh.code.hospitalManagement;

import com.shivansh.code.hospitalManagement.dto.CountBloodGroupTypeEntity;
import com.shivansh.code.hospitalManagement.entity.Patient;
import com.shivansh.code.hospitalManagement.entity.type.BloodGroup;
import com.shivansh.code.hospitalManagement.service.PatientServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.shivansh.code.hospitalManagement.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@SpringBootTest
public class PatientTests {

    @Autowired
    private PatientRepository patientRepository;

//    @Autowired
//    private PatientServices patientServices;

    @Test
    public void testPatientRepository(){
       List<Patient> patientList = patientRepository.findPatientWithAppointments();
       System.out.println(patientList);

    }

    @Test
    public void testPatientServices(){
//        Patient patient = patientServices.getPatientById(2l);
//        System.out.println(patient);
//        Patient patient = patientRepository.findByName("Rahul Sharma");
//        Patient patient = patientRepository.findByBirthDate(LocalDate.of(1999,2, 14));
//        List<Patient> patientList = patientRepository.findByBirthDateOrEmail(LocalDate.of(1999,2,14), "anjali.verma@gmail.com");
//        List<Patient> patientList = patientRepository.findByNameLike("%A%");
//        List<Patient> patientList = patientRepository.findByBloodGroup(BloodGroup.valueOf("A_POS"));
//        List<Patient> patientList = patientRepository.findByBornAfterDate(LocalDate.of(1995, 11, 3));

//        List<Object[]> patientList = patientRepository.findAndCountEachBloodGroup();
//        for(Object[] patient : patientList){
//            System.out.println(patient[0]+" "+patient[1]);
//        }

        Page<Patient> patientList = patientRepository.findAllPatient(PageRequest.of(1,2));
        for(Patient patient: patientList){
            System.out.println(patient);
        }

//        List<CountBloodGroupTypeEntity> patientList = patientRepository.findAndCountEachBloodGroup();
//        System.out.println(patientList);

    }
}
