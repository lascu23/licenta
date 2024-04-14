package com.licenta.repository;

import com.licenta.entity.HospitalProfile;
import com.licenta.entity.PatientProfile;
import com.licenta.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientProfileRepository extends JpaRepository<PatientProfile, Integer> {
    PatientProfile findByUserId(int userId);
    PatientProfile findById(int id);

}
