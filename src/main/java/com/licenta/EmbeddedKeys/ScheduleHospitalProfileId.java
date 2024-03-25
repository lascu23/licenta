package com.licenta.EmbeddedKeys;

import com.licenta.entity.HospitalProfile;
import com.licenta.entity.Schedule;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class ScheduleHospitalProfileId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private HospitalProfile hospitalProfile;
}
