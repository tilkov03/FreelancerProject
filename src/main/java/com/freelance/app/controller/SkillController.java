package com.freelance.app.controller;

import com.freelance.app.model.Category;
import com.freelance.app.model.Skill;
import com.freelance.app.repository.SkillRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/skill")
public class SkillController {
    @Autowired
    SkillRepository skillRepository;

    @GetMapping
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }
    @PostMapping
    public Skill createSkill(@RequestBody Skill skill) {
        return skillRepository.save(skill);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Skill> getCategoryById(@PathVariable Long id) {
        Optional<Skill> category = skillRepository.findById(id);
        return category.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        if (skillRepository.existsById(id)) {
            skillRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //Метод за извличане на уменията на даден потребител (по неговото ид)
    @GetMapping("/user/{userId}")
    public List<Skill> getSkillsByUser(@PathVariable Long userId) {
        return skillRepository.findSkillsByUser(userId);
    }
}
