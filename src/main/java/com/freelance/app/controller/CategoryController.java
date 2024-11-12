package com.freelance.app.controller;

import com.freelance.app.model.Category;
import com.freelance.app.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    //Метод за извличане на всички категории
    @GetMapping
    List<Category> GetCategories(){
        return categoryRepository.findAll();
    }
    //Метод за създаване на нова категория
    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }
    //Метод за преглед на категория спрямо нейното ид
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    //Промяна на вече съшествуваща категория
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setName(categoryDetails.getName());
            Category updatedCategory = categoryRepository.save(category);
            return ResponseEntity.ok(updatedCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //Изтриване на категория спрямо нейното ид
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/job-counts")
    public ResponseEntity<List<Object[]>> getCategoryJobCounts() {
        List<Object[]> categoryJobCounts = categoryRepository.findCategoryJobCounts();

        if (categoryJobCounts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(categoryJobCounts);
    }
}
