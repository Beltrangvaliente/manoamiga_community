package com.tecnara.manoamiga.aaa.services.adapters;

import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityAdapter implements GrantedAuthority{

    private String rol;

    public GrantedAuthorityAdapter(String rol) {
        this.rol = rol;
    }
    
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    @Override
    public String getAuthority() {
        return rol;
    }
    
}
