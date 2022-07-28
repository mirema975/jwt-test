package com.example.testJWT.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.testJWT.dto.PublicacionDTO;
import com.example.testJWT.service.PublicacionService;
import com.example.testJWT.util.AppConstantes;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {
    
    @Autowired
    private PublicacionService publicacionService;

    @GetMapping
    public ResponseEntity<Page<PublicacionDTO>> getAll(
            @RequestParam(value = "pageNo", defaultValue = AppConstantes.PAGE_NO_DEFAULT, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstantes.PAGE_SIZE_DEFAULT, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstantes.SORT_BY_DEFAULT,required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstantes.SORT_DIR_DEFAULT, required = false) String sortDir) {
                return new ResponseEntity<>(publicacionService.getAll(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionDTO> getById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(publicacionService.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PublicacionDTO> save(@Valid @RequestBody PublicacionDTO dto){
        return new ResponseEntity<>(publicacionService.create(dto),HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PublicacionDTO> update(@PathVariable(name = "id") Long id, @RequestBody PublicacionDTO dto){
        PublicacionDTO entityResponse = publicacionService.update(dto, id);
        return new ResponseEntity<>(entityResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id){
        publicacionService.delete(id);
        return new ResponseEntity<>("Publicacion eliminada con exito", HttpStatus.OK);
    }
}
