package com.qairym.services;

import com.qairym.dto.TokenResponse;
import com.qairym.entities.City;
import com.qairym.repositories.UserRepository;
import com.qairym.security.TokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    public TokenResponse save(String username) {
        if (userRepository.existsByUsername(username)) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            "password"
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = (User) authentication.getPrincipal();
            String token = tokenProvider.createToken(user);
            return new TokenResponse(token);
        }

        com.qairym.entities.user.User user = new com.qairym.entities.user.User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode("password"));
        City location = new City();
        location.setCityId(3L);
        user.setLocation(location);
        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        "password"
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User securityUser = (User) authentication.getPrincipal();
        String token = tokenProvider.createToken(securityUser);
        return new TokenResponse(token);
    }
}
