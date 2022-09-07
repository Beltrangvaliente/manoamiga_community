package com.tecnara.manoamiga.aaa.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "aaa_email")
public class EmailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    public Date fecha;
    public String hora;
    public String titulo;
    public String mailDestinatario;   
    public String textoEnviado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMailDestinatario() {
        return mailDestinatario;
    }

    public void setMailDestinatario(String mailDestinatario) {
        this.mailDestinatario = mailDestinatario;
    }

    public String getTextoEnviado() {
        return textoEnviado;
    }

    public void setTextoEnviado(String textoEnviado) {
        this.textoEnviado = textoEnviado;
    }


}
