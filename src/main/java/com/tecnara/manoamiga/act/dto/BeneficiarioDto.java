/*

 */
package com.tecnara.manoamiga.act.dto;
 

public class BeneficiarioDto {

    private Long id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String nombreCompletoTutor;
    private String telefono;
    private String usuario;
    private String documento;

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getNombreCompletoTutor() {
        return nombreCompletoTutor;
    }

    public void setNombreCompletoTutor(String nombreCompletoTutor) {
        this.nombreCompletoTutor = nombreCompletoTutor;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getApellido() {
        return apellido1 + " " + apellido2;
    }

    
}
