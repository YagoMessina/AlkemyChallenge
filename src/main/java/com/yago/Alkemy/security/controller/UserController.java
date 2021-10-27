package com.yago.Alkemy.security.controller;

import com.yago.Alkemy.dto.UserDTO;
import com.yago.Alkemy.security.dto.AuthRequestDTO;
import com.yago.Alkemy.security.dto.AuthResponseDTO;
import com.yago.Alkemy.security.model.User;
import com.yago.Alkemy.security.service.CustomUserDetailsService;
import com.yago.Alkemy.security.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    public UserController(AuthenticationManager authenticationManager,
                          CustomUserDetailsService customUserDetailsService,
                          JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Login")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequestDTO request) {
        try {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
            Authentication auth = authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().build();
        }
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.createToken(userDetails);
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @Operation(summary = "Register")
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserDTO userDTO) {
        User user = customUserDetailsService.save(userDTO);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Activation")
    @GetMapping("/activate/{activationToken}")
    public ResponseEntity<?> activate(@PathVariable String activationToken){
        customUserDetailsService.activate(activationToken);
        return ResponseEntity.ok("Your account was successfully activated!");
    }
}