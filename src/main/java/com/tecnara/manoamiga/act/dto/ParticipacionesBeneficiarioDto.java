
package com.tecnara.manoamiga.act.dto;


public class ParticipacionesBeneficiarioDto {
    public Long id;
    public String nombreBeneficiario;
    public String apellidoBeneficiario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    public String getApellidoBeneficiario() {
        return apellidoBeneficiario;
    }

    public void setApellidoBeneficiario(String apellidoBeneficiario) {
        this.apellidoBeneficiario = apellidoBeneficiario;
    }
    
    
}
