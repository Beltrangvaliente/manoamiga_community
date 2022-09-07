package com.tecnara.manoamiga.doc.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.dto.CitaDto;
import com.tecnara.manoamiga.doc.entities.CentroAtencionEntity;
import com.tecnara.manoamiga.doc.entities.CitaEntity;
import com.tecnara.manoamiga.doc.repositories.IAlumnoRepository;
import com.tecnara.manoamiga.doc.repositories.ICentroAtencionRepository;
import com.tecnara.manoamiga.doc.repositories.ICitaRepository;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/doc/alumno/cita")
public class AlumnoCitaController extends TecnaraBaseController {

    
 
    @Autowired
    private ICitaRepository repoCita;

    @Autowired
    private IAlumnoRepository repoAlumno;

    @Autowired
    private ICentroAtencionRepository repoCentroAtencion;

    @Autowired
    private IUtilidadesService utils;   //Informacion general de la aplicación

   @GetMapping("/list")
   public String list(Model m) {
     m.addAttribute("lista", repoCita.alumnoCitaListCalendario(general.getIdValidado()));
       return "doc/alumno/cita_list"; //HTML que queremos mostrar
    }

   // @GetMapping("/list")
   // public String list(Model m) {
     //   return "doc/alumno/cita_list";
   // }
    
    
    
    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new CitaEntity());
        } else {
            m.addAttribute("registro", repoCita.findByIdAndIdAlumno(id, general.getIdValidado()).get());
        }
        return "doc/alumno/cita_form";
    }

    @Autowired
    private IGeneral general;

    @GetMapping("/list_data")
    @ResponseBody // para que devuelva un JSON
    public List<Map> listData() {
        return repoCita.alumnoCitaList(general.getIdValidado()); // nos devuelve todos los datos de la tabla; sin filtrar

    }
 
    @GetMapping("/list_data_calendario")
    @ResponseBody // para que devuelva un JSON
    public List<Map> listDataCalendario() {
        return repoCita.alumnoCitaListCalendario(general.getIdValidado()); // nos devuelve todos los datos de la tabla; sin filtrar

    }

    @GetMapping("/list_data_fecha_disponible")
    @ResponseBody
    public List<Map> listDataFechaDisponible(Long idCentroDeAtencion) {
        return repoCita.fechaDisponibleCitaForm(idCentroDeAtencion);

    }

    @GetMapping("/list_data_hora_disponible")
    @ResponseBody
    public List<Map> listDataHoraDisponible(Long idCentroDeAtencion, @DateTimeFormat(pattern =  "yyyy-MM-dd") Date fecha) {
        return repoCita.horaDisponibleCitaForm(idCentroDeAtencion, fecha); 

    }

    @PostMapping("/guardar")
    
    public String guardar(CitaEntity nuevo) {
        repoCita.alumnoCitaUpdate(general.getIdValidado(), nuevo.getIdCentroDeAtencion(), nuevo.getFechaDeCita() , nuevo.getHoraDeCita());
        return "redirect:list";
    
    }
       //update 
        //set id_alumno = ???
        //where id_centro ... and fecha .... and hora ..... and id_alumno is null
//}

    @GetMapping("/borrar")
    public String borrar(Long id) {
        if (repoCita.findByIdAndIdAlumno(id, general.getIdValidado()).isEmpty()) {
            return "redirect:list?error=No se puede borrar";
        }
        repoCita.deleteById(id);
        return "redirect:list";
    }

    @GetMapping("/anular")
    public String anular(Long id) {
        Optional<CitaEntity> optCita = repoCita.findByIdAndIdAlumno(id, general.getIdValidado());
        if (optCita.isEmpty()) {
            return "redirect:list?error=No se puede anular";
        }
        CitaEntity cita = optCita.get();
        cita.setIdAlumno(null);
        repoCita.save(cita);
        return "redirect:list";
    }

}
