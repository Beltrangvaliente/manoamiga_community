/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecnara.manoamiga.emp.entities;

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
@Table(name = "emp_horarios_trabajados")
public class HorarioTrabajadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idTrabajador;  //Imputación de horas, para trabajadores que no son tecnicos
    private Long idTecnico;   //Imputación de horas para técnicos. Si no es trabajador.
    
    @DateTimeFormat (pattern = "yyyy-MM-dd") 
    @Column(columnDefinition ="date")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    private String horaInicio;
    private String horaFin;
    private Integer tiempoMinutos;  //Almacenaremos el tiempo en minutos para facilitar las consultas.
    private String tipoRegistro; //Presencial,Desplazamiento,Teletrabajo,Permiso,Descanso,Ausencia,Vacaciones,
    private String modoFichaje; //Inmediato,Retrospectivo

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(Long idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public Long getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(Long idTecnico) {
        this.idTecnico = idTecnico;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public Integer getTiempoMinutos() {
        return tiempoMinutos;
    }

    public void setTiempoMinutos(Integer tiempoMinutos) {
        this.tiempoMinutos = tiempoMinutos;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getModoFichaje() {
        return modoFichaje;
    }

    public void setModoFichaje(String modoFichaje) {
        this.modoFichaje = modoFichaje;
    }

}
