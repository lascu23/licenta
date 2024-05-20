package com.licenta.entity;

import com.licenta.EmbeddedKeys.ScheduleDoctorProfileId;
import com.licenta.EmbeddedKeys.ScheduleHospitalProfileId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.print.Doc;

@Table(name = "doctor_schedule")
@Entity
@Getter
@Setter
public class ScheduleDoctorProfile {
    @EmbeddedId
    private ScheduleDoctorProfileId id;

    @MapsId("schedule")
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @MapsId("doctorProfile")
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorProfile doctorProfile;
}
