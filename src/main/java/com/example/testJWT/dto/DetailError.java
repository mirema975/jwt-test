package com.example.testJWT.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailError {
    
    private Date marcaDeTiempo;
    private String mensaje;
    private String detalles;
}
