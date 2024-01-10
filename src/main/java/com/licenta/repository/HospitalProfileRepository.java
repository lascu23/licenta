package com.licenta.repository;

import com.licenta.entity.HospitalProfile;
import com.licenta.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HospitalProfileRepository extends JpaRepository<HospitalProfile, Integer> {
    @Query("SELECT profileImage FROM HospitalProfile WHERE id = :id")
    byte[] getProfileImageById(@Param("id")int id);


}
