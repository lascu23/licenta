package com.licenta.entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.print.Doc;
import java.sql.Blob;

@Data
@Entity
@Table(name = "doctor_profile")
public class DoctorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    private String lastName;

    private String birthday;

    @Column(name = "profile_picture")
    private byte[] profileImage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private HospitalProfile hospitalProfile;
}
