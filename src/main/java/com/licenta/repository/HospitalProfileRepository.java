package com.licenta.repository;

import com.licenta.entity.HospitalProfile;
import com.licenta.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;


@Repository
public interface HospitalProfileRepository extends JpaRepository<HospitalProfile, Integer> {
    Page<HospitalProfile> findAll(Pageable pageable);

    @Query("SELECT h from HospitalProfile h WHERE LOWER(h.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(h.city) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<HospitalProfile> findByNameOrCity(@Param("searchTerm") String search, Pageable pageable);

//    @Query("SELECT h FROM HospitalProfile h WHERE h.user.id= :userId")
    HospitalProfile findByUserId(@Param("userId") int userId);
}
