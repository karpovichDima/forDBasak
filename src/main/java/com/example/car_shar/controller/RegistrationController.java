package com.example.car_shar.controller;

import com.example.car_shar.domain.Role;
import com.example.car_shar.domain.User;
import com.example.car_shar.repos.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
@Slf4j
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration() {
        log.debug("RegistrationController | @GetMapping(\"/registration\").method | return registration.mustache");
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {

        User userFromDb = userRepo.findByUsername(user.getUsername());

        log.debug("RegistrationController |  @PostMapping(\"/registration\").method | User userFromDb = userRepo.findByUsername(user.getUsername())");
        if (userFromDb != null) {
            model.put("message", "User exists!");
            log.debug("RegistrationController |  @PostMapping(\"/registration\").method | userFromDb != null | return registration.mustache");
            return "registration";
        }

        user.setActive(true);
        log.debug("RegistrationController |  @PostMapping(\"/registration\").method | user.setActive(true);");
        user.setRoles(Collections.singleton(Role.USER));
        log.debug("RegistrationController |  @PostMapping(\"/registration\").method | user.setRoles(Collections.singleton(Role.USER));");
        userRepo.save(user);
        log.debug("RegistrationController |  @PostMapping(\"/registration\").method | userRepo.save(user); | return \"redirect:/login\"");

        return "redirect:/login";
    }
}