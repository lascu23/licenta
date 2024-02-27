package com.licenta.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "pharmacy_profile")
public class PharmacyProfile {
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

    @ManyToMany(mappedBy = "pharmacyProfileSet")
    private Set<Medicines> medicinesSet = new HashSet<>();

    //override ul de mai jos este pentru stackoverflow error
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
