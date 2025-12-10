package org.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.factory.UserFactory;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository repo;

    public AuthController(UserRepository repo) { this.repo = repo; }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        String email = payload.get("email");
        String role = payload.get("role");

        if (name == null || email == null || role == null)
            return ResponseEntity.badRequest().body("Missing fields");

        if (repo.findByEmail(email).isPresent())
            return ResponseEntity.status(409).body("Email exists");

        User user = UserFactory.createUser(role, name, email);
        return ResponseEntity.ok(repo.save(user));
    }

    @GetMapping("/users")
    public List<User> all() { return repo.findAll(); }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public String root() {
        return "Auth service is running!";
    }
}