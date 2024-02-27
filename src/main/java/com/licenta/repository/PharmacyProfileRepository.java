package com.licenta.repository;

import com.licenta.entity.HospitalProfile;
import com.licenta.entity.PatientProfile;
import com.licenta.entity.PharmacyProfile;
import com.licenta.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyProfileRepository extends JpaRepository<PharmacyProfile, Integer> {
    @Query("SELECT h from PharmacyProfile h WHERE LOWER(h.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(h.city) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<PharmacyProfile> findByNameOrCity(@Param("searchTerm") String search, Pageable pageable);

    PharmacyProfile findByUserId(int userId);
    PharmacyProfile findById(int id);
    PharmacyProfile findByNameAndAddress(String name, String address);

}
