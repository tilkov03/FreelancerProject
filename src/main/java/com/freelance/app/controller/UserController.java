package com.freelance.app.controller;

import com.freelance.app.model.Skill;
import com.freelance.app.model.User;
import com.freelance.app.repository.SkillRepository;
import com.freelance.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SkillRepository skillRepository;
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
    @PostMapping("/{userId}/skills/{skillId}")
    public User addSkillToUser(@PathVariable Long userId, @PathVariable Long skillId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found with id " + skillId));

        user.getSkills().add(skill);
        return userRepository.save(user);
    }
}