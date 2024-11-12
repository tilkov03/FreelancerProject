package com.freelance.app.repository;

import com.freelance.app.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Query("SELECT a FROM Application a JOIN Job j ON a.jobId = j.id WHERE j.userId = :userId")
    List<Application> findApplicationsByEmployer(@Param("userId") Long userId);

    List<Application> findByJobId(Long jobId);

}