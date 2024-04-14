package com.licenta.service;

import com.licenta.entity.*;
import com.licenta.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService{

    private final UserRepository userRepository;
    private final PatientProfileRepository patientProfileRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final AppointmentRepository appointmentRepository;
    private final ScheduleDoctorProfileRepository scheduleDoctorProfileRepository;

    public AppointmentServiceImpl(UserRepository userRepository, PatientProfileRepository patientProfileRepository, DoctorProfileRepository doctorProfileRepository, AppointmentRepository appointmentRepository, ScheduleDoctorProfileRepository scheduleDoctorProfileRepository) {
        this.userRepository = userRepository;
        this.patientProfileRepository = patientProfileRepository;
        this.doctorProfileRepository = doctorProfileRepository;
        this.appointmentRepository = appointmentRepository;
        this.scheduleDoctorProfileRepository = scheduleDoctorProfileRepository;
    }

    @Override //poti crea o programare la doctor
    public Appointment makeAppointment(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String specialty = request.getParameter("specialty");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userLoggedIn = userRepository.findByEmail(authentication.getName());

        PatientProfile patientProfile = patientProfileRepository.findByUserId(userLoggedIn.getId());
        DoctorProfile doctorProfile = doctorProfileRepository.findByFirstNameAndLastNameAndSpecialty(firstName, lastName, specialty);
        return new Appointment(doctorProfile, patientProfile);
    }

    @Override //obtine orele disponibile
    public List<LocalTime> getAvailableHours(int doctorId, LocalDate date) {
        String appointmentDay = date.getDayOfWeek().name();
        Schedule schedule= scheduleDoctorProfileRepository.findByDoctorProfileIdAndScheduleDay(doctorId, appointmentDay);
        LocalTime startHour = schedule.getStartHour();
        LocalTime endHour = schedule.getEndHour();
        //LocalTime currentHour = startHour;
        List<LocalTime> availableHours = new ArrayList<>();

        List<Appointment> appointmentList = appointmentRepository.findAllByDoctorProfileIdAndAppointmentDate(doctorId, date);
        Collections.sort(appointmentList, Comparator.comparing(Appointment::getAppointmentHour));

        LocalTime currentHour = startHour;
        int index = 0;
        while (currentHour.isBefore(endHour) && index < appointmentList.size()) {
            LocalTime nextBusyTime = appointmentList.get(index).getAppointmentHour();
            if (currentHour.isBefore(nextBusyTime.minusMinutes(30))) {
                availableHours.add(currentHour);
            }
            currentHour = nextBusyTime.plusMinutes(30);
            index++;
        }

        // Adăugăm orarul disponibil până la sfârșitul intervalului, dacă este cazul
        while (currentHour.isBefore(endHour)) {
            availableHours.add(currentHour);
            currentHour = currentHour.plusMinutes(30);
        }

        return availableHours;
    }

    @Override//salveaza programarea
    public void saveAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

}
