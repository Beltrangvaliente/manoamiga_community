/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/monitor/usuario")
public class MonitorUsuarioController {
    
    @Autowired
    private IUsuarioRepository repoUsuario;    
    
    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador (String filtro){
        return repoUsuario.findByUsuarioContainingIgnoreCase(filtro).stream().map(x->new ClaveValorDto(x.getId(), x.getUsuario())).collect(Collectors.toList());
    }
    
    
    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id){
        UsuarioEntity x= repoUsuario.findById(id).orElse(new UsuarioEntity());
        return x.getUsuario();
    }   
}
