package com.example.testJWT.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.testJWT.entity.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long>{
    public List<Comentario> findByPublicacionId(Long publicacionId);
}
