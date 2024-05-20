package com.licenta.service;

import com.licenta.EmbeddedKeys.PrescriptionMedicineId;
import com.licenta.dto.PrescriptionMedicineDto;
import com.licenta.entity.Appointment;
import com.licenta.entity.Medicines;
import com.licenta.entity.Prescription;
import com.licenta.entity.PrescriptionMedicine;
import com.licenta.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class PrescriptionServiceImpl implements PrescriptionService{
    private final MedicineRepository medicineRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final PatientProfileRepository patientProfileRepository;
    private final PrescriptionMedicineRepository prescriptionMedicineRepository;
    private final AppointmentRepository appointmentRepository;

    public PrescriptionServiceImpl(MedicineRepository medicineRepository, PrescriptionRepository prescriptionRepository, DoctorProfileRepository doctorProfileRepository, PatientProfileRepository patientProfileRepository, PrescriptionMedicineRepository prescriptionMedicineRepository, AppointmentRepository appointmentRepository) {
        this.medicineRepository = medicineRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.doctorProfileRepository = doctorProfileRepository;
        this.patientProfileRepository = patientProfileRepository;
        this.prescriptionMedicineRepository = prescriptionMedicineRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public PrescriptionMedicineDto createPrescription() {
        PrescriptionMedicineDto prescriptionMedicineDto = new PrescriptionMedicineDto();
        prescriptionMedicineDto.setMedicineNames(new ArrayList<>());
        prescriptionMedicineDto.getMedicineNames().add("");
        prescriptionMedicineDto.setDetails(new ArrayList<>());
        return prescriptionMedicineDto;
    }

    @Override
    public void savePrescription(PrescriptionMedicineDto prescriptionMedicineDto, int id) {
        Prescription prescription = new Prescription();
        Appointment appointment = appointmentRepository.findById(id);
        prescription.setAppointment(appointment);
        prescription.setCreationDate(LocalDate.now());
        prescriptionRepository.save(prescription);


        appointment.setFulfilled(true);
        appointmentRepository.save(appointment);

        String[] array1 = prescriptionMedicineDto.getMedicineNames().get(0).split(",");
        String[] array2 = prescriptionMedicineDto.getDetails().get(0).split(",");

        for(int i = 0; i < array1.length; i++){
            Medicines medicines = medicineRepository.findByName(array1[i]);
            PrescriptionMedicineId prescriptionMedicineId = new PrescriptionMedicineId();
            prescriptionMedicineId.setPrescription(prescription);
            prescriptionMedicineId.setMedicine(medicines);

            PrescriptionMedicine prescriptionMedicine = new PrescriptionMedicine();
            prescriptionMedicine.setId(prescriptionMedicineId);
            prescriptionMedicine.setDetails(array2[i]);

            prescriptionMedicineRepository.save(prescriptionMedicine);
        }
    }
}
