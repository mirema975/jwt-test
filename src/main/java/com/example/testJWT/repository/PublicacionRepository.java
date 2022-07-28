package com.example.testJWT.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.testJWT.entity.Publicacion;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long>{
    
}
