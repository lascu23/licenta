package com.licenta.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/register/**").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/appointmentReview/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/logout").permitAll()
                        .requestMatchers("/addHospitalAccount/save").hasRole("ADMIN")
                        .requestMatchers("/addHospitalAccount").hasRole("ADMIN")
                        .requestMatchers("/addPharmacyAccount").hasRole("ADMIN")
                        .requestMatchers("/addPharmacyAccount/save").hasRole("ADMIN")
                        .requestMatchers("/addDoctorAccount").hasAnyRole("ADMIN","HOSPITAL")
                        .requestMatchers("/addDoctorAccount/save").hasAnyRole("ADMIN","HOSPITAL")
                        .requestMatchers("/deleteHospital").hasRole("ADMIN")
                        .requestMatchers("/doctors").permitAll()
                        .requestMatchers("/deleteDoctor").hasRole("ADMIN")
                        .requestMatchers("/deleteHospital").hasRole("ADMIN")
                        .requestMatchers("/deleteMedicine").hasRole("ADMIN")
                        .requestMatchers("/deletePharmacy").hasRole("ADMIN")
                        .requestMatchers("/manageDoctors").hasAnyRole("ADMIN","HOSPITAL")
                        .requestMatchers("/hospitals").permitAll()
                        .requestMatchers("/pharmacies").permitAll()
                        .requestMatchers("/makeAppointment").permitAll()
                        .requestMatchers("/makeAppointment/save").permitAll()
                        .requestMatchers("/getAvailableHours").permitAll()
                        .requestMatchers("/seeMedicinesForOnePharmacy").permitAll()
                        .requestMatchers("/schedule/save").permitAll()
                        .requestMatchers("/deleteSchedule").permitAll()
                        .requestMatchers("/getMedicines").permitAll()
                        .requestMatchers("/profileHospital").permitAll()
                        .requestMatchers("/profileDoctor").permitAll()
                        .requestMatchers("/profilePharmacy").permitAll()
                        .requestMatchers("/profile").permitAll()
                        .requestMatchers("/calendarDoctor").hasRole("DOCTOR")
                        .requestMatchers("/getAppointments").hasRole("DOCTOR")
                        .requestMatchers("/markAppointmentFulfilled/{id}").hasRole("DOCTOR")
                        .requestMatchers("/createPrescription").hasRole("DOCTOR")
                        .requestMatchers("/savePrescription").hasRole("DOCTOR")
                        .requestMatchers("/calendarPatient").hasRole("PATIENT")
                        .requestMatchers("/profilePatient").hasRole("PATIENT")
                        .requestMatchers("/profilePatient/save").hasRole("PATIENT")
                        .requestMatchers("/deleteAccount").hasRole("PATIENT")
                        .requestMatchers("/addMedicineToPharmacy").hasRole("PHARMACY")

                        .anyRequest().authenticated()

                ).formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/home?login=true").failureUrl("/login?error=true").permitAll()
                ).logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll());

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
