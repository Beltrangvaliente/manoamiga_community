package com.tecnara.manoamiga.expo.entities;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
 
@Entity
@Table(name = "expo_imagenes")
public class ImagenEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public Long id;
    
    @Lob
    @Column(columnDefinition="LONGBLOB")    
    public byte[] imagen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }



}
