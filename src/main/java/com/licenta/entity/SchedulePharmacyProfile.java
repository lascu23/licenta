package com.licenta.entity;

import com.licenta.EmbeddedKeys.SchedulePharmacyProfileId;
import jakarta.persistence.*;
import lombok.Data;

@Table(name = "pharmacy_schedule")
@Entity
@Data
public class SchedulePharmacyProfile {
    @EmbeddedId
    private SchedulePharmacyProfileId id;

    @MapsId("schedule")
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @MapsId("pharmacyProfile")
    @ManyToOne
    @JoinColumn(name = "pharmacy_id")
    private PharmacyProfile pharmacyProfile;
}
