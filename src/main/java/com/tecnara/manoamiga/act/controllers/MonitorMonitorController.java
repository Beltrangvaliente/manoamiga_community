/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.dto.MonitoresDeLaActividadDto;
import com.tecnara.manoamiga.act.entities.ActividadEntity;
import com.tecnara.manoamiga.act.entities.MonitorEntity;
import com.tecnara.manoamiga.act.repositories.IActividadRepository;
import com.tecnara.manoamiga.act.repositories.IMonitorRepository;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/act/monitor/monitor")
public class MonitorMonitorController extends TecnaraBaseController{

    @Autowired
    private IMonitorRepository repoMonitor;
    
    @Autowired
    private IGeneral general;
    
    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro){
        return repoMonitor.findByNombreContainingIgnoreCaseOrApellido1ContainingOrApellido2Containing(filtro, filtro, filtro)
                         .stream()
                         .map(x-> new ClaveValorDto(x.getId(), general.concat(x.getNombre(),x.getApellido1(),x.getApellido2()) ))
                         .collect(Collectors.toList());
    }   
    
    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id){
        MonitorEntity x= repoMonitor.findById(id).orElse(new MonitorEntity());
        return general.concat(x.getNombre(),x.getApellido1(),x.getApellido2());
    }  
}
