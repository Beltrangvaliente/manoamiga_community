/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.expo.controllers.*;
import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.expo.dto.AgendaDiaDto;
import com.tecnara.manoamiga.expo.dto.EventoDto;
import com.tecnara.manoamiga.expo.dto.NoticiaDto;
import com.tecnara.manoamiga.expo.entities.AreaEntity;
import com.tecnara.manoamiga.expo.entities.EspacioEntity;
import com.tecnara.manoamiga.expo.entities.EventoEntity;
import com.tecnara.manoamiga.expo.entities.NoticiaEntity;
import com.tecnara.manoamiga.expo.repositories.IAreaRepository;
import com.tecnara.manoamiga.expo.repositories.IEspacioRepository;
import com.tecnara.manoamiga.expo.repositories.IEventoRepository;
import com.tecnara.manoamiga.expo.repositories.IImagenRepository;
import com.tecnara.manoamiga.expo.repositories.INoticiaRepository;
import com.tecnara.manoamiga.expo.repositories.IServicioRepository;
import com.tecnara.manoamiga.expo.services.IEventosService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("tecnico_emp")
@RequestMapping("/emp/tecnico")
public class TecnicoController extends TecnaraBaseController {

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/index")
    public String index() {
        return "emp/tecnico/index";
    }

}
