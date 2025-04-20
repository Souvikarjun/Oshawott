package com.college.Oshawott.controller;

import com.college.Oshawott.UserRepository;
import com.college.Oshawott.model.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/add")
    public @ResponseBody User addNewUser(
        String name,
        String email,
        String role
    ) {
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setRole(role);
        userRepository.save(newUser);
        return newUser;
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody Map<Object, Object> deleteUser(
        @PathVariable Integer id
    ) {
        Map<Object, Object> response = new HashMap<>();

        if (!userRepository.existsById(id)) {
            response.put("message", "User not found.");
            response.put("success", false);
            return response;
        }
        userRepository.deleteById(id);
        response.put(
            "message",
            "User with id " + id + " deleted successfully."
        );
        response.put("success", true);
        return response;
    }

    @PutMapping("/update/{id}")
    public @ResponseBody Map<Object, Object> updateUser(
        @PathVariable Integer id,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String role
    ) {
        Map<Object, Object> response = new HashMap<>();
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            response.put("message", "User not found.");
            response.put("success", false);
            return response;
        }

        User user = optionalUser.get();
        if (name != null) user.setName(name);
        if (email != null) user.setEmail(email);
        if (role != null) user.setRole(role);
        userRepository.save(user);
        response.put("success", true);
        response.put("message", "User found.");
        return response;
    }

    @GetMapping("/find/{id}")
    public @ResponseBody Map<Object, Object> findUser(
        @PathVariable Integer id
    ) {
        Map<Object, Object> response = new HashMap<>();
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            response.put("success", false);
            response.put("message", "User not found.");
            return response;
        }

        User user = optionalUser.get();
        response.put("success", true);
        response.put("message", "User found.");
        response.put("user", user);
        return response;
    }
}
