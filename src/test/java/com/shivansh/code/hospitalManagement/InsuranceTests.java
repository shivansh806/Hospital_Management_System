package com.shivansh.code.hospitalManagement;

import com.shivansh.code.hospitalManagement.entity.Appointment;
import com.shivansh.code.hospitalManagement.entity.Insurance;
import com.shivansh.code.hospitalManagement.entity.Patient;
import com.shivansh.code.hospitalManagement.service.AppointmentServices;
import com.shivansh.code.hospitalManagement.service.InsuranceServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootTest
public class InsuranceTests {

    @Autowired
    private InsuranceServices insuranceServices;

    @Autowired
    private AppointmentServices appointmentServices;

//    @Test
//    public void testInsuranceServ(){
//        Insurance insurance = Insurance.builder()
//                .policyNumber("BOB_864hy64")
//                .provider("BOB")
//                .validUntil(LocalDate.of(2035, 12, 12))
//                .build();
//
//        Patient patient = insuranceServices.createInsurance(insurance, 1L);
//        System.out.println(patient);
//
//        Patient newPatient = insuranceServices.deleteInsurance(patient.getId());
//        System.out.println(newPatient);
//    }

//    @Test
//    public void testAppointmentServ(){
//        Appointment appointment = Appointment.builder()
//                .appointmentTime(LocalDateTime.of(2026,04,30,1, 50))
//                .reason("Heart issues")
//                .build();
//        var appointment1 = appointmentServices.createNewAppointment(appointment, 1L, 1L);
//        System.out.println(appointment1);
//
//        var newAppointment = appointmentServices.reassignAppointment(appointment1.getId(), 3L);
//        System.out.println(newAppointment);
//    }
}
