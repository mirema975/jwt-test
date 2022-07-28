package com.example.testJWT.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageDTO <T>{
    
    private List<T> content;
    private int nroPaginaActual;
    private int maxElementos;
    private int cantidadPaginas;
    private Integer ultimaPagina;
    private Integer primeraPagina;

}
