package com.tecnara.manoamiga.aaa.services.adapters;

import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class UserServiceAdapter implements UserDetails{

    private UsuarioEntity usuario;
    private Long idValidado = 1L;

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }
    
    public String getRole(){
        return usuario.getRol();
    }
    
   
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles=new ArrayList<>();
        roles.add( new GrantedAuthorityAdapter(usuario.getRol()) );
        return roles;
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getUsuario();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return usuario.estado.equalsIgnoreCase("Bloqueado")==false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return usuario.estado.equalsIgnoreCase("Aceptado");
    }
    
    public Long getIdValidado(){
        return idValidado;
    }

    public void setIdValidado(Long idValidado) {
        this.idValidado = idValidado;
    }
    
}
