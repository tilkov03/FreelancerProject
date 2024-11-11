package com.freelance.app.controller;

import com.freelance.app.model.Job;
import com.freelance.app.repository.JobRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    // Метод за създаване на нова обява
    @PostMapping
    public Job createJob(@Valid @RequestBody Job job) {
        return jobRepository.save(job);
    }

    // Метод за получаване на всички обяви
    @GetMapping
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // Метод за търсене на обяви по ключова дума
    @GetMapping("/search")
    public List<Job> searchJobs(@RequestParam String keyword) {
        return jobRepository.searchJobsByKeyword(keyword);
    }

    // Метод за филтриране на обяви по работодател
    @GetMapping("/by-employer")
    public List<Job> getJobsByEmployer(@RequestParam String employer) {
        return jobRepository.findByEmployer(employer);
    }

    //метод за актуализиране на обява по ID
    @PutMapping("/{id}")
    public Job updateJob(@PathVariable Long id, @Valid @RequestBody Job jobDetails) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id " + id));

        job.setTitle(jobDetails.getTitle());
        job.setDescription(jobDetails.getDescription());
        job.setEmployer(jobDetails.getEmployer());

        return jobRepository.save(job);
    }

    // метод за изтриване на обява по ID
    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id " + id));

        jobRepository.delete(job);
    }

}