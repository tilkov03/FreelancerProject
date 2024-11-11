package com.freelance.app.controller;

import com.freelance.app.model.Application;
import com.freelance.app.model.ApplicationStatus;
import com.freelance.app.model.Role;
import com.freelance.app.repository.ApplicationRepository;
import com.freelance.app.repository.JobRepository;
import com.freelance.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    // Метод за кандидатстване по обява
    @PostMapping("/apply")
    public String applyForJob(@RequestParam Long jobId, @RequestParam Long userId, @RequestBody String message) {
        // Проверка дали потребителят е фрийлансър
        var user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getRole().equals(Role.FREELANCER)) {
            return "Only freelancers can apply for jobs.";
        }

        // Проверка дали обявата съществува
        var job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));

        // Създаване на нова кандидатура
        Application application = new Application();
        application.setJobId(jobId);
        application.setUserId(userId);
        application.setMessage(message);
        application.setStatus(ApplicationStatus.PENDING);

        applicationRepository.save(application);
        return "Application submitted successfully.";
    }

    // Метод за преглед на кандидатурите за конкретна обява
    @GetMapping("/job/{jobId}")
    public List<Application> getApplicationsForJob(@PathVariable Long jobId, @RequestParam Long userId) {
        // Проверка дали потребителят е работодател и собственик на обявата
        var user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        var job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));

        if (!user.getRole().equals(Role.EMPLOYER) || !job.getUserId().equals(userId)) {
            throw new RuntimeException("Only the employer who posted the job can view applications.");
        }

        return applicationRepository.findByJobId(jobId);
    }
    // Метод за промяна на статуса на кандидатурата
    @PutMapping("/{applicationId}/status")
    public String updateApplicationStatus(@PathVariable Long applicationId, @RequestParam Long userId, @RequestParam ApplicationStatus status) {
        var user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        var application = applicationRepository.findById(applicationId).orElseThrow(() -> new RuntimeException("Application not found"));

        // Check if user is the employer of the job associated with this application
        var job = jobRepository.findById(application.getJobId()).orElseThrow(() -> new RuntimeException("Job not found"));
        if (!user.getRole().equals(Role.EMPLOYER) || !job.getUserId().equals(userId)) {
            throw new RuntimeException("Only the employer can update the application status.");
        }

        application.setStatus(status);
        applicationRepository.save(application);
        return "Application status updated successfully.";
    }



}