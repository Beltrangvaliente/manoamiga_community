/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tecnara.manoamiga.expo.services;

import com.tecnara.manoamiga.expo.dto.AgendaDiaDto;
import com.tecnara.manoamiga.expo.entities.AspectoEntity;
import java.util.List;


public interface IEventosService {
    
    public List<AgendaDiaDto> getAgenda();
    public AspectoEntity getAspecto();

}
