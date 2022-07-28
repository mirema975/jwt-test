package com.example.testJWT.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.example.testJWT.entity.Comentario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublicacionDTO {
    
    private Long id;

    @NotEmpty
    @Size(min = 2, message = "El titulo de la publicacion debe tener al menos 2 caracteres")
    private String titulo;
 
    @NotEmpty
    @Size(min = 10, message = "La descripcion de la publicacion debe tener al menos 10 caracteres")
    private String descripcion;
 
    @NotEmpty
    @Size(min = 10, message = "El contenido la publicacion debe tener al menos 10 caracteres")
    private String contenido;
    
    private Set<Comentario> comentarios;

}
