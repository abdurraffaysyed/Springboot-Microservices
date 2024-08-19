package com.example.demo.Authentication;

import com.example.demo.Authentication.Model.AuthenticationRequest;
import com.example.demo.JWT.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authenticate")
@Tag(name = "Authentication API Documentation")
public class AuthenticationController {
    private static final Logger _logger = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    private JWTUtil _jwtUtil;
    @Autowired
    private AuthenticationManager _authenticationManager;
    @Operation(summary = "Authenticate user")
    @SecurityRequirements
    @PostMapping
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest user){
        try{
            Authentication auth = _authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            SecurityContext context = SecurityContextHolder.getContext();
            String token = _jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(token);
        }
        catch(BadCredentialsException ex){
            _logger.error("Invalid Credentials");
            return new ResponseEntity<>("Authentication failed", HttpStatus.UNAUTHORIZED);
        }
        catch(Exception ex)
        {
            _logger.error("Exception on Authentication Controller", ex);
            return new ResponseEntity<>("Authentication failed", HttpStatus.UNAUTHORIZED);
        }
    }
}
