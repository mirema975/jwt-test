package com.example.testJWT.service;

import java.util.List;

import com.example.testJWT.dto.ComentarioDTO;

public interface ComentarioService {
    public ComentarioDTO create(Long publicacionId, ComentarioDTO dto);
    public List<ComentarioDTO> getAllByPublicacionId(Long publicacionId);
    public ComentarioDTO getById(Long publicacionId, Long comentarioId);
    public ComentarioDTO update(Long publicacionId, ComentarioDTO solicitudDeComentario, Long comentarioId);
    public void delete(Long publicacionId, Long comentarioId);
}
