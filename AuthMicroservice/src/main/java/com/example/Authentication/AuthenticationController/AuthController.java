package com.example.Authentication.AuthenticationController;

import com.example.Authentication.AuthenticationModel.AuthenticationRequest;
import com.example.Authentication.JWT.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authentication/")
public class AuthController {
    private static final Logger _logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private JwtUtil _jwtUtil;
    @Autowired
    private AuthenticationManager _authenticationManager;

    @PostMapping("generateToken")
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

    @PostMapping("validateToken")
    public ResponseEntity<Boolean> validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader)
    {
        try{
            String token = null;
            boolean isTokenValid = true;
            if(authHeader != null && authHeader.startsWith("Bearer ")){
                token = authHeader.substring(7);
                isTokenValid = _jwtUtil.validateToken(token);

            }
            return ResponseEntity.ok(isTokenValid);
        }
        catch (Exception ex)
        {
            return ResponseEntity.internalServerError().build();
        }

        //return new ResponseEntity<>(isValid, HttpStatus.OK);
    }
    @GetMapping("authentication/get")
    public static ResponseEntity<String> Get(){
        return ResponseEntity.ok().build();
    }
}
