package com.licenta.repository;

import com.licenta.entity.MedicinePharmacyProfile;
import com.licenta.entity.Medicines;
import com.licenta.entity.PharmacyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicinePharmacyProfileRepository extends JpaRepository<MedicinePharmacyProfile, Integer> {
    MedicinePharmacyProfile findByMedicinesAndPharmacyProfile(Medicines medicines, PharmacyProfile pharmacyProfile);

    List<MedicinePharmacyProfile> getMedicinePharmacyProfileByPharmacyProfile(PharmacyProfile pharmacyProfile);
    List<MedicinePharmacyProfile> getMedicinePharmacyProfileByMedicinesName( String name);

    @Query("SELECT mpp FROM MedicinePharmacyProfile mpp " +
            "JOIN mpp.medicines m " +
            "WHERE mpp.pharmacyProfile = :pharmacyProfile " +
            "AND m.name LIKE %:medicineName%")
    List<MedicinePharmacyProfile> getMedicinePharmacyProfileByPharmacyProfileAndMedicineName(@Param("pharmacyProfile") PharmacyProfile pharmacyProfile,@Param("medicineName") String search);
}
