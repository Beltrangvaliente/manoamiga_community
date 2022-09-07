package com.tecnara.manoamiga.doc.controllers;


import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.entities.CentroAtencionEntity;
import com.tecnara.manoamiga.doc.repositories.ICentroAtencionRepository;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/doc/alumno/centro_atencion")
public class AlumnoCentroAtencionController extends TecnaraBaseController{

    @Autowired
    private IUtilidadesService utils;
    
    @Autowired
    private ICentroAtencionRepository repoCentroAtencion;

    @GetMapping("/buscador") 
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoCentroAtencion.findByNombreContainingIgnoreCase(filtro)
                .stream().map(x -> new ClaveValorDto(x.getId(), x.getNombre())).collect(Collectors.toList());

    }
    
    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        CentroAtencionEntity x= utils.findById(repoCentroAtencion, id, CentroAtencionEntity.class);
        return x.getNombre();
    }      
}
