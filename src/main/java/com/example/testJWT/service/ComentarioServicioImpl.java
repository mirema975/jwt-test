package com.example.testJWT.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.testJWT.dto.ComentarioDTO;
import com.example.testJWT.entity.Comentario;
import com.example.testJWT.entity.Publicacion;
import com.example.testJWT.exception.BlogAppException;
import com.example.testJWT.exception.ResourceNotFoundException;
import com.example.testJWT.repository.ComentarioRepository;
import com.example.testJWT.repository.PublicacionRepository;

@Service
public class ComentarioServicioImpl implements ComentarioService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Override
    public ComentarioDTO create(Long publicacionId, ComentarioDTO dto) {
        Comentario comentario = dtoToEntity(dto);
        Publicacion entityPublicacion = publicacionRepository
            .findById(publicacionId)
            .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        
        comentario.setPublicacion(entityPublicacion);
        Comentario nuevoComentario = comentarioRepository.save(comentario);

        return entityToDto(nuevoComentario);
    }

    @Override
    public List<ComentarioDTO> getAllByPublicacionId(Long publicacionId) {
        List<Comentario> comentarios = comentarioRepository.findByPublicacionId(publicacionId);
        return comentarios.stream().map(comentario -> entityToDto(comentario)).collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO getById(Long publicacionId, Long comentarioId) {
        Publicacion entityPublicacion = publicacionRepository
            .findById(publicacionId)
            .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        
        Comentario entityComentario = comentarioRepository
            .findById(comentarioId)
            .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

        if(!entityComentario.getPublicacion().getId().equals(entityPublicacion.getId())){
                throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación");
        }

        return entityToDto(entityComentario);
    }

    @Override
    public ComentarioDTO update(Long publicacionId, ComentarioDTO solicitudDeComentario, Long comentarioId) {
        Publicacion entityPublicacion = publicacionRepository
            .findById(publicacionId)
            .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        
        Comentario entityComentario = comentarioRepository
            .findById(comentarioId)
            .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

        if(!entityComentario.getPublicacion().getId().equals(entityPublicacion.getId())){
                throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación");
        }

        entityComentario.setNombre(solicitudDeComentario.getNombre());
        entityComentario.setEmail(solicitudDeComentario.getEmail());
        entityComentario.setCuerpo(solicitudDeComentario.getCuerpo());

        Comentario comentarioActualizado = comentarioRepository.save(entityComentario);
        return entityToDto(comentarioActualizado);
    }

    @Override
    public void delete(Long publicacionId, Long comentarioId) {
        Publicacion entityPublicacion = publicacionRepository
            .findById(publicacionId)
            .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        
        Comentario entityComentario = comentarioRepository
            .findById(comentarioId)
            .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

        if(!entityComentario.getPublicacion().getId().equals(entityPublicacion.getId())){
                throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación");
        }
        
        comentarioRepository.delete(entityComentario);
    }

    private ComentarioDTO entityToDto(Comentario entity){
        return modelMapper.map(entity, ComentarioDTO.class);
    }

    private Comentario dtoToEntity(ComentarioDTO dto){
        return modelMapper.map(dto, Comentario.class);
    }

}
