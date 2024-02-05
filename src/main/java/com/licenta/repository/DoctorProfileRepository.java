package com.licenta.repository;

import com.licenta.entity.DoctorProfile;
import com.licenta.entity.HospitalProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, Integer> {
    @Query("SELECT h from DoctorProfile h WHERE LOWER(h.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(h.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<DoctorProfile> findByFirstNameOrLastName(@Param("searchTerm") String search, Pageable pageable);
}
