package com.freelance.app.repository;

import com.freelance.app.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    @Query("SELECT s FROM Skill s JOIN s.users u WHERE u.id = :userId")
    List<Skill> findSkillsByUser(@Param("userId") Long userId);



}