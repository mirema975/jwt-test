package com.example.testJWT.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioDTO {
    
    private Long id;
    
    @NotEmpty(message = "El nombre no debe ser vacio o nulo")
    private String nombre;
    
    @Email
    @NotEmpty(message = "El email no debe ser vacio o nulo")
    private String email;

    @NotEmpty(message = "El cuerpo no debe ser vacio")
    @Size(message = "El cuerpo del comentario debe tener al menos 10 caracteres")
    private String cuerpo;

}
