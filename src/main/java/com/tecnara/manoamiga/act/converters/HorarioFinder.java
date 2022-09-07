/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.act.converters;

import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.entities.HorarioEntity;
import com.tecnara.manoamiga.act.repositories.IHorarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HorarioFinder {
    
    @Autowired
    private IHorarioRepository repoHorario;
    
    @Autowired
    private IGeneral general;
    
    public String findHorarioFromActivity(Long idActividad){
        List<HorarioEntity> horario=repoHorario.findByIdActividad(idActividad);
        String res="";
        for (HorarioEntity h:horario){
            res+=getHorarioText(h);
        }
        return res;
    }
    
    public String getHorarioText(HorarioEntity h){
        if ("Puntual".equals(h.tipoActividad)){
            return "Día " + general.concat(h.getFechaActividad(),"(", h.getHorarioInicio(),"a",h.getHorarioFin(),");");
        }else if ("Periodica".equals(h.tipoActividad)){
            return general.concat(general.getDiaSemana(h.getDiaSemana()),"(", h.getHorarioInicio(),"a",h.getHorarioFin(),");");
        }else{
            return "Horario no disponible";
        }
    }    
    
}
