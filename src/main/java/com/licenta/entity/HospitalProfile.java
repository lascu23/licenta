package com.licenta.entity;

import com.licenta.common.Location;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.web.multipart.MultipartFile;


import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "hospital_profile")
public class HospitalProfile extends Location {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "hospitalProfile", cascade = CascadeType.ALL, targetEntity = DoctorProfile.class)
    private Set<DoctorProfile> doctorProfileSet;

    @ManyToMany(mappedBy = "hospitalProfilesSet")
    private Set<Schedule> scheduleSet = new HashSet<>();
}
