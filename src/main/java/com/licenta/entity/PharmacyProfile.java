package com.licenta.entity;

import com.licenta.common.Location;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "pharmacy_profile")
public class PharmacyProfile extends Location {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "pharmacyProfileSet")
    private Set<Medicines> medicinesSet = new HashSet<>();

    @ManyToMany(mappedBy = "pharmacyProfileSet")
    private Set<Schedule> scheduleSet = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
