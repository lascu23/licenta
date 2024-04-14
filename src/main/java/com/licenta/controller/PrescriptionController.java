package com.licenta.controller;

import com.licenta.dto.PrescriptionMedicineDto;
import com.licenta.service.PrescriptionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PrescriptionController {

    @Autowired
    private PrescriptionServiceImpl prescriptionService;

    @GetMapping("/createPrescription")
    public String createPrescription(Model model){
        model.addAttribute("prescriptionMedicineDto", prescriptionService.createPrescription());
        return "prescriptionForm";
    }

    @PostMapping("/savePrescription")
    public String savePrescription(@ModelAttribute PrescriptionMedicineDto prescriptionMedicineDto,@RequestParam int doctorId, @RequestParam int patientId){
        prescriptionService.savePrescription(prescriptionMedicineDto, doctorId, patientId);
        return "redirect:/calendar";
    }
}
