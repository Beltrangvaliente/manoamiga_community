package com.tecnara.manoamiga.doc.dto;

public class ClaveValorDto {
    
    private Long clave;
    private String valor;

    public ClaveValorDto(Long clave, String valor) {
        this.clave = clave;
        this.valor = valor; 
    }

    public ClaveValorDto() {
    }

    
    
    public Long getClave() {
        return clave;
    }

    public void setClave(Long clave) {
        this.clave = clave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    
}
