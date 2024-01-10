package com.licenta.repository;

import com.licenta.entity.PharmacyProfile;
import com.licenta.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyProfileRepository extends JpaRepository<PharmacyProfile, Integer> {
}
