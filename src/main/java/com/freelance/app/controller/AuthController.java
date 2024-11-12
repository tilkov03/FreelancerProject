//package com.freelance.app.controller;
//
//import com.freelance.app.model.User;
//import com.freelance.app.repository.UserRepository;
//import com.freelance.app.util.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//    // Метод за регистрация на потребител
//    @PostMapping("/register")
//    public Map<String, String> register(@RequestBody User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword())); // Хеширане на паролата
//        userRepository.save(user);
//
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "User registered successfully");
//        return response;
//    }
//
//    // Метод за вход
//    @PostMapping("/login")
//    public Map<String, String> login(@RequestBody User loginUser) {
//        Optional<User> userOptional = userRepository.findByEmail(loginUser.getEmail());
//
//        Map<String, String> response = new HashMap<>();
//        if (userOptional.isPresent() && passwordEncoder.matches(loginUser.getPassword(), userOptional.get().getPassword())) {
//            String token = JwtUtil.generateToken(loginUser.getEmail());
//            response.put("token", token);
//        } else {
//            response.put("error", "Invalid email or password");
//        }
//        return response;
//    }
//}