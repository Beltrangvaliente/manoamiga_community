package com.tecnara.manoamiga.doc.controllers;


import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.entities.CitaEntity;
import com.tecnara.manoamiga.doc.repositories.ICitaRepository;
import com.tecnara.manoamiga.doc.services.ICitaService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/doc/admin/cita")
public class AdminCitaController extends TecnaraBaseController{

    @Autowired
    private ICitaRepository repoCita; //dao - Data Acess Object
    
    @Autowired
    private IGeneral general; //informacion de sesion
    
    @Autowired 
    private ICitaService servCitas;
    

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id==null){
            m.addAttribute("registro", new CitaEntity());
        }else{
            m.addAttribute("registro", repoCita.findById(id).get());
        }
        return "doc/admin/cita_form";//Esto es el html
    }

    @GetMapping("/formsinmenu")
    public String formsinmenu(Model m, Long id) {
        if (id==null){
            m.addAttribute("registro", new CitaEntity());
        }else{
            m.addAttribute("registro", repoCita.findById(id).get());
        }
        m.addAttribute("sinmenu", true);        
        return "doc/admin/cita_form";//Esto es el html
    }

    @GetMapping("/list")
    public String list(Model m) {
        return "doc/admin/cita_list";
    }

    @GetMapping("/help")
    public String help() {
        return "doc/admin/cita_help"; //HTML que queremos mostrar
    }

    @PostMapping("/guardar")
    public String guardar(CitaEntity registro) {
        repoCita.save(registro); // con "registro" le decimos los datos que vamos a guardar del formulario rellenados por el usuario
        return "redirect:list";
    }

    @GetMapping("/list_data")
    @ResponseBody // para que devuelva un JSON
    public List<Map> listData(@DateTimeFormat (pattern= "yyyy-MM-dd") Date fechaDeCita, String centroDeAtencion, String estado, String nombreAlumno) {
        return repoCita.adminCitaListUsuariosApuntados(fechaDeCita, centroDeAtencion, estado, nombreAlumno);
    }
   
    
    @GetMapping("/borrar")
    public String borrar(Long id){
        repoCita.deleteById(id);
        return "redirect:list";   
    }
    
    @GetMapping("/agenda")
    public String citar(Model m, Long id){
        m.addAttribute("mes", repoCita.adminAgenda());
        return "doc/admin/cita_agenda";//Esto es el html
    }
    
    
     
    @PostMapping("/generar") 
    public String generar(Long idCentroDeAtencion, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio, 
                                @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,
                                String diaSemana, 
                                String horaInicio,
                                String horaFin,
                                Integer intervaloMinutos
                                ){
        try{
            servCitas.generarAgenda(idCentroDeAtencion, fechaInicio, fechaFin, diaSemana, horaInicio, horaFin, intervaloMinutos);
        }catch(Exception e){
            return "redict:agenda?error=" + e.getMessage();
        }
        
        return "redirect:agenda";
    
    
    
    }   

    
}
