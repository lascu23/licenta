package com.licenta.controller;

import com.licenta.entity.Appointment;
import com.licenta.entity.Review;
import com.licenta.repository.AppointmentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ReviewController {

    private final AppointmentRepository appointmentRepository;

    public ReviewController(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/appointmentReview/{appointmentId}/{activeStars}")
    public String getReviewPage(@PathVariable int appointmentId, @PathVariable int activeStars){
        Appointment appointment = appointmentRepository.findById(appointmentId);
        Review review = new Review();
        review.setId(activeStars);
        appointment.setReview(review);
        appointmentRepository.save(appointment);
        return "redirect:/calendarPatient";
    }

}
