package com.freelance.app.repository;

import com.freelance.app.model.Job; // Увери се, че този импорт е тук
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("SELECT j FROM Job j WHERE j.title LIKE %:keyword% OR j.description LIKE %:keyword%")
    List<Job> searchJobsByKeyword(@Param("keyword") String keyword);
}
