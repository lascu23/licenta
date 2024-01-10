package com.licenta.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


import java.util.Set;

@Data
@Entity
@Table(name = "hospital_profile")
public class HospitalProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String address;

    private String city;

    private String country;

    @Lob
    @Column(name = "profile_picture")
    private byte[] profileImage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "hospitalProfile", cascade = CascadeType.ALL, targetEntity = DoctorProfile.class)
    private Set<DoctorProfile> doctorProfileSet;
}
