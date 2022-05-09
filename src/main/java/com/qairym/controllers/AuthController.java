package com.qairym.controllers;

import com.qairym.dto.GoogleAuthDto;
import com.qairym.services.AuthService;
import com.qairym.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Api(value="qairym_api", description="Operations pertaining to users")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @ApiOperation(value = "Authorization with token")
    @GetMapping("/verify")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok("Авторизация успешна");
    }

    @PostMapping("/google")
    public ResponseEntity<?> googleAuth(@RequestParam String username) {
        return ResponseEntity.ok(
                authService.save(username)
        );
    }
}
