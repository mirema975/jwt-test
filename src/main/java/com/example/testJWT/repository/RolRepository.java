package com.example.testJWT.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.testJWT.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Long>{
    
    public Optional<Rol> findByNombre(String nombre);
    
}
