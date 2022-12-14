package com.controller;


import com.model.AppUser;
import com.model.DTO.ICountRole;
import com.model.DTO.JwtResponse;
import com.service.appuser.IAppUserService;
import com.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IAppUserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AppUser user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        AppUser currentUser = userService.findByName(user.getName());
        return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @GetMapping("/hello")
    public ResponseEntity<Iterable<ICountRole>> hello() {
        Iterable<ICountRole> iCountRoles = userService.getRoleNumber();
        return new ResponseEntity<>(iCountRoles, HttpStatus.OK);
    }
    @GetMapping("/admin")
    public ResponseEntity<String> admin() {
        return new ResponseEntity<>("Admin", HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity<String> user() {
        return new ResponseEntity<>("User", HttpStatus.OK);
    }
}
