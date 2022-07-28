package com.example.testJWT.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.testJWT.dto.PublicacionDTO;
import com.example.testJWT.entity.Publicacion;
import com.example.testJWT.exception.ResourceNotFoundException;
import com.example.testJWT.repository.PublicacionRepository;

@Service
public class PublicacionServiceImpl implements PublicacionService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Override
    public PublicacionDTO create(PublicacionDTO dto) {
        
        Publicacion entity = dtoToEntity(dto);

        Publicacion nuevaPublicacion = publicacionRepository.save(entity);

        return entityToDto(nuevaPublicacion);
    }

    @Override
    public Page<PublicacionDTO> getAll(int numeroDePagina, int pageSize, String sortBy, String sortDir) {
        // Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Sort sort = Sort.by(Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(numeroDePagina, pageSize, sort);
        Page<Publicacion> publicaciones = publicacionRepository.findAll(pageable);
        
        // List<Publicacion> listaDePublicaciones = publicaciones.getContent();
        return publicaciones.map(this::entityToDto);
        
        // publicaciones.getPageable();
        // Page<PublicacionDTO> dto = new PageImpl(contenido, publicaciones.getPageable(), publicaciones.getTotalElements());

        // PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
        // publicacionRespuesta.setContenido(contenido);
        // publicacionRespuesta.setNumeroPagina(publicaciones.getNumber());
        // publicacionRespuesta.setMedidaPagina(publicaciones.getSize());
        // publicacionRespuesta.setTotalElementos(publicaciones.getTotalElements());
        // publicacionRespuesta.setTotalPaginas(publicaciones.getTotalPages());
        // publicacionRespuesta.setEsUltima(publicaciones.isLast());
        // return dto;

    }

    @Override
    public PublicacionDTO getById(Long id) {
        Publicacion entity = publicacionRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
        return entityToDto(entity);
    }

    @Override
    public PublicacionDTO update(PublicacionDTO dto, Long id) {
        Publicacion entity = publicacionRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
        
        entity.setTitulo(dto.getTitulo());
        entity.setDescripcion(dto.getDescripcion());
        entity.setContenido(dto.getContenido());

        Publicacion publicacionUpdate = publicacionRepository.save(entity);
    
        return entityToDto(publicacionUpdate);
    }

    @Override
    public void delete(Long id) {
        Publicacion entity = publicacionRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
    
        publicacionRepository.delete(entity);
    }

    // Convierte entity to dto
    private PublicacionDTO entityToDto(Publicacion entity) {
        return modelMapper.map(entity, PublicacionDTO.class);
    }

    // Convierte dto a entity
    private Publicacion dtoToEntity(PublicacionDTO dto) {
        return modelMapper.map(dto, Publicacion.class);
    }
}
