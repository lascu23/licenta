package com.licenta.entity;

import com.licenta.EmbeddedKeys.ScheduleHospitalProfileId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "hospital_schedule")
@Entity
@Getter
@Setter
public class ScheduleHospitalProfile {
    @EmbeddedId
    private ScheduleHospitalProfileId id;

    @MapsId("schedule")
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @MapsId("hospitalProfile")
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private HospitalProfile hospitalProfile;
}
