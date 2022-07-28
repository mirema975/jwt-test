package com.example.testJWT.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.testJWT.dto.LoginDTO;
import com.example.testJWT.dto.RegistroDTO;
import com.example.testJWT.entity.Rol;
import com.example.testJWT.entity.User;
import com.example.testJWT.repository.RolRepository;
import com.example.testJWT.repository.UserRepository;
import com.example.testJWT.security.JWTAuthResponseDTO;
import com.example.testJWT.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Obtenemos el token del jwtTokenProvider
        String token = jwtTokenProvider.generarTokenDeAcceso(authentication);

        return ResponseEntity.ok(new JWTAuthResponseDTO(token));
    }
 
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody RegistroDTO registroDTO){
        if(userRepository.existsByUsername(registroDTO.getUsername())){
            return new ResponseEntity<>("Ese nombre de usuario ya existe", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(registroDTO.getEmail())){
            return new ResponseEntity<>("Ese email de usuario ya existe", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setNombre(registroDTO.getNombre());
        user.setUsername(registroDTO.getUsername());
        user.setEmail(registroDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registroDTO.getPassword()));

        Rol roles = rolRepository.findByNombre("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("Usuario registrado Existosamente", HttpStatus.OK);
    }
}