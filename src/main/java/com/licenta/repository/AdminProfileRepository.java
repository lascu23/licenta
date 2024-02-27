package com.licenta.repository;

import com.licenta.entity.AdminProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminProfileRepository extends JpaRepository<AdminProfile, Integer> {
}
