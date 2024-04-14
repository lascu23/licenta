package com.licenta.repository;

import com.licenta.entity.DoctorProfile;
import com.licenta.entity.Medicines;
import com.licenta.entity.PatientProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MedicineRepository extends JpaRepository<Medicines, Integer> {
    @Query("SELECT h from Medicines h WHERE LOWER(h.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Medicines> findByName(@Param("searchTerm") String search, Pageable pageable);

    Medicines findByName(String name);

    Medicines findById(int id);

}
