
package com.tecnara.manoamiga.aaa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name = "aaa_preferencias")
public class PreferenciaEntity {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public Long id;

    public String clave;
    
    @Column(columnDefinition = "text")
    public String valor;
    
    public String tipo; //Texto,Multilinea,HTML,Entero,Decimales,Booleano
    
    private String perfil; //Por si acaso quieren tener varios tipos de aplicación Pruebas,Producción

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
    
    
    
}
