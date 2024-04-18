package com.licenta.service;

import com.licenta.dto.ShowCalendarDto;
import com.licenta.entity.*;
import com.licenta.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Service
public class CalendarServiceImpl implements CalendarService{

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final PatientProfileRepository patientProfileRepository;
    private final UserServiceImpl userService;
    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionMedicineRepository prescriptionMedicineRepository;

    public CalendarServiceImpl(AppointmentRepository appointmentRepository, UserRepository userRepository, DoctorProfileRepository doctorProfileRepository, PatientProfileRepository patientProfileRepository, UserServiceImpl userService, PrescriptionRepository prescriptionRepository, PrescriptionMedicineRepository prescriptionMedicineRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.doctorProfileRepository = doctorProfileRepository;
        this.patientProfileRepository = patientProfileRepository;
        this.userService = userService;
        this.prescriptionRepository = prescriptionRepository;
        this.prescriptionMedicineRepository = prescriptionMedicineRepository;
    }

    @Override
    public ShowCalendarDto createDoctorCalendar(int year, int month) {
        LocalDate today;
        if (year == 0 || month == 0) {
            today = LocalDate.now();
        } else {
            today = LocalDate.of(year, month, 1);
        }

        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        String currentMonthName = today.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());


        List<String> daysOfWeek = new ArrayList<>();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            daysOfWeek.add(dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()));
        }

        List<List<Integer>> calendar = new ArrayList<>();
        int daysInMonth = today.lengthOfMonth();
        int firstDayOfMonth = today.withDayOfMonth(1).getDayOfWeek().getValue() - 1; // Adjust for zero-based index
        int currentDay = 1;

        for (int i = 0; i < 6; i++) {
            List<Integer> week = new ArrayList<>();
            for (int j = 0; j < 7; j++) {
                if ((i == 0 && j < firstDayOfMonth) || currentDay > daysInMonth) {
                    week.add(null);
                } else {
                    week.add(currentDay++);
                }
            }
            calendar.add(week);
            if (currentDay > daysInMonth) {
                break;
            }
        }

        return new ShowCalendarDto(calendar, currentMonthName, daysOfWeek, currentMonth, currentYear);
    }

    @Override
    public List<Appointment> getAppointments(int day, int currentMonth, int currentYear) {
        DoctorProfile doctorProfile = doctorProfileRepository.findByUserId(userService.getAuthenticationUser().getId());
        LocalDate localDate = LocalDate.of(currentYear, currentMonth, day);
        return appointmentRepository.findAllByDoctorProfileIdAndAppointmentDate(doctorProfile.getId(), localDate);
    }

    @Override
    public ResponseEntity<?> markAppointmentAsFulfilled(int id) {
        try {
            Appointment appointment = appointmentRepository.findById(id);
            appointment.setFulfilled(true);
            appointmentRepository.save(appointment);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to mark appointment as fulfilled: " + e.getMessage());
        }
    }


    @Override
    public List<Appointment> getPatientAppointmentsForCalendar() {
        PatientProfile patientProfile = patientProfileRepository.findByUserId(userService.getAuthenticationUser().getId());
        return appointmentRepository.findAllByPatientProfileId(patientProfile.getId());
    }


    //leaga appointments de porescriptions using creation date
    @Override
    public List<PrescriptionMedicine> getPrescriptionsForPatient() {
        PatientProfile patientProfile = patientProfileRepository.findByUserId(userService.getAuthenticationUser().getId());
        List<Prescription> prescriptions = prescriptionRepository.findAllByPatientProfileId(patientProfile.getId());
        List<Integer> prescriptionsIds = new ArrayList<>(prescriptions.size());
        for(Prescription prescription : prescriptions){
            prescriptionsIds.add(prescription.getId());
        }
        List<PrescriptionMedicine> prescriptionMedicines = prescriptionMedicineRepository.findByPrescriptionIds(prescriptionsIds);
        return prescriptionMedicines;
    }

}





















