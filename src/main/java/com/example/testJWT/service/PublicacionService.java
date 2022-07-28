package com.example.testJWT.service;

import org.springframework.data.domain.Page;

import com.example.testJWT.dto.PublicacionDTO;

public interface PublicacionService {
    
    public PublicacionDTO create(PublicacionDTO dto);

    public Page<PublicacionDTO> getAll(int numeroDePagina, int pageSize, String sortBy, String sortDir);

    public PublicacionDTO getById(Long id);

    public PublicacionDTO update(PublicacionDTO dto, Long id);

    public void delete(Long id);
    
}
