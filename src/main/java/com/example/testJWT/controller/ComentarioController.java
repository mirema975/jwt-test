package com.example.testJWT.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.testJWT.dto.ComentarioDTO;
import com.example.testJWT.service.ComentarioService;

@RestController
@RequestMapping("/api/")
public class ComentarioController {
    
    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/publicaciones/{publicacionId}/comentarios")
    public List<ComentarioDTO> getAllByPublicacionId(@PathVariable(name = "publicacionId") Long publicacionId){
        return comentarioService.getAllByPublicacionId(publicacionId);
    }

    @GetMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<ComentarioDTO> findById(@PathVariable(name = "publicacionId") Long publicacionId, @PathVariable(name = "id") Long comentarioId){
        ComentarioDTO comentarioDTO = comentarioService.getById(publicacionId, comentarioId);
        return new ResponseEntity<>(comentarioDTO, HttpStatus.OK);
    }

    @PostMapping("/publicaciones/{publicacionId}/comentarios")
    public ResponseEntity<ComentarioDTO> save(@PathVariable(name = "publicacionId") Long publicacionId, @Valid @RequestBody ComentarioDTO dto){
        return new ResponseEntity<>(comentarioService.create(publicacionId, dto), HttpStatus.CREATED);
    }

    @PutMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<ComentarioDTO> update(@PathVariable(name = "publicacionId") Long publicacionId, @PathVariable(name = "id") Long comentarioId,@Valid @RequestBody ComentarioDTO dto){
        ComentarioDTO comentarioActualizado = comentarioService.update(publicacionId, dto, comentarioId);
        return new ResponseEntity<>(comentarioActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "publicacionId") Long publicacionId, @PathVariable(name = "id") Long comentarioId){
        comentarioService.delete(publicacionId, comentarioId);
        return new ResponseEntity<>("Comentario eliminado con exito", HttpStatus.OK);
    }
}
