package com.qairym.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qairym.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class EmailAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;

//    public EmailAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
//        this.authenticationManager = authenticationManager;
//        this.tokenProvider = tokenProvider;
//    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = null;
        String password = null;
        try {
            LoginDto loginDto = getLoginDto(request);
            username = loginDto.getUsername();
            password = loginDto.getPassword();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Username is: {}", username); log.info("Password is: {}", password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        //String url = request.getRequestURL().toString();
        String token = tokenProvider.createToken(user);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    private LoginDto getLoginDto(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String parsedReq = sb.toString();
        ObjectMapper mapper = new ObjectMapper();
        LoginDto loginDto = mapper.readValue(parsedReq, LoginDto.class);
        return loginDto;
    }
}
