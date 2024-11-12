package com.freelance.app.repository;

import com.freelance.app.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c.name, COUNT(j) FROM Category c LEFT JOIN c.jobs j GROUP BY c.name")
    List<Object[]> findCategoryJobCounts();
}
