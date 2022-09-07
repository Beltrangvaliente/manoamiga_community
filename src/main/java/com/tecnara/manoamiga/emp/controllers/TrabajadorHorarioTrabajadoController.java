/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.emp.entities.HorarioTrabajadoEntity;
import com.tecnara.manoamiga.emp.entities.TecnicoEntity;
import com.tecnara.manoamiga.emp.entities.TrabajadorEntity;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tecnara.manoamiga.emp.repositories.IHorarioTrabajadoRepository;
import com.tecnara.manoamiga.emp.repositories.ITrabajadorRepository;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import static org.springframework.boot.SpringApplication.main;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/emp/trabajador/horario_trabajado")
public class TrabajadorHorarioTrabajadoController extends TecnaraBaseController {

    @Autowired
    private IHorarioTrabajadoRepository repoHorariotrabajado;

    @Autowired
    private ITrabajadorRepository repoTrabajador;

    @Autowired
    private IGeneral general;

    @GetMapping("/list")
    public String trabajadorList() {
        return "emp/trabajador/horario_trabajado_list";
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData() {
        return repoHorariotrabajado.trabajadorList(general.getIdValidado());
    }


    //form -> guardar, borrar
    //form --> Crear la vista
    
 
    @GetMapping("/listado_por_fecha")
    public String trabajadorListFecha() {
        return "emp/trabajador/horario_trabajado_listado_por_fecha_list";
    }


    @GetMapping("/list_data_fecha") //Json
    @ResponseBody
    public List<Map> trabajadorListFechaJson() {
        return repoHorariotrabajado.trabajadorListFecha(general.getIdValidado());
    }

    
    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new HorarioTrabajadoEntity());
        } else {
            m.addAttribute("registro", repoHorariotrabajado.findById(id).get());

        }
        return "emp/trabajador/horario_trabajado_form";
    }

    @PostMapping("/guardar")
    public String guardar(HorarioTrabajadoEntity registro) {
        registro.setIdTrabajador(general.getIdValidado());
        registro.setModoFichaje("Retrospectivo");
        repoHorariotrabajado.save(registro);
        return "redirect:list";
    }

    @GetMapping("/fichar_puesto")
    public String ficharPuesto() {
        TrabajadorEntity tr=repoTrabajador.getById(general.getIdValidado());
        if ("Si".equalsIgnoreCase(tr.getActivarPanelGrupal())==false){
            return "redirect:../index?error=No tiene permisos para acceder";
        }
        return "emp/trabajador/horario_trabajado_fichar_puesto_form";
    }

    @GetMapping("/fichar_puesto_nuevo_registro")
    @ResponseBody
    @Transactional
    public Optional<TrabajadorEntity> ficharPuestoNuevo(Long idTrabajador) {
        repoHorariotrabajado.trabajadorList(idTrabajador);

        //Update idTrabajador
        //Insertar idTrabajador, accion
        repoHorariotrabajado.trabajadorPanelCerrarAutomatico(idTrabajador);
        repoHorariotrabajado.trabajadorPanelCerrarHorasHoy(idTrabajador);

        //Si no has pulsado finalizar. Accion != "finalizar"
        repoHorariotrabajado.trabajadorPanelAbrirFichaje("Presencial", idTrabajador);

        return repoTrabajador.findById(idTrabajador);
    }

    @GetMapping("/fichar_puesto_finalizar")
    @ResponseBody
    @Transactional
    public Optional<TrabajadorEntity> finalizar(Long idTrabajador) {
        repoHorariotrabajado.trabajadorPanelCerrarAutomatico(idTrabajador);
        repoHorariotrabajado.trabajadorPanelCerrarHorasHoy(idTrabajador);

        return repoTrabajador.findById(idTrabajador);

        /*TrabajadorEntity t=repoTrabajador.findById(idTrabajador).orElse(new TrabajadorEntity());
         return general.concat(t.getNombre(),t.getApellido1(),t.getApellido2());*/
        
    }         
     //Si se pulsa Confirmar se registrará una hora o se cerrará en función de que se haya pulsado “Inicio” o “Fin”. 
     
     
     
     @GetMapping("/panel")
    public String Panel(Model m) {
        TrabajadorEntity tr=repoTrabajador.getById(general.getIdValidado());
        if ("Si".equalsIgnoreCase(tr.getActivarPanelIndividual())==false){
            return "redirect:../index?error=No tiene permisos para acceder ";
        }        
        TrabajadorEntity te= repoTrabajador.findById(general.getIdValidado()).get();
        m.addAttribute("puedeRegistrarPermiso", te.getPuedeRegistrarPermiso());
        
        return "emp/trabajador/horario_trabajado_panel";
    }

    @GetMapping("/qr")
    public String Qr(Model m) {
        m.addAttribute("idTrabajador", general.getIdValidado());
        return "emp/trabajador/horario_trabajado_qr";
        
    
    }
    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoHorariotrabajado.deleteById(id);
        return "redirect:list";
    }

    @GetMapping("panel_fichar")
    @Transactional
    @ResponseBody
    public String panelFichar(String accion) {
        Long idTrabajador = general.getIdValidado();
         
         //Update idTrabajador
         //Insertar idTrabajador, accion
         repoHorariotrabajado.trabajadorPanelCerrarAutomatico(idTrabajador);
         repoHorariotrabajado.trabajadorPanelCerrarHorasHoy(idTrabajador);
         
         //Si no has pulsado finalizar. Accion != "finalizar"
         repoHorariotrabajado.trabajadorPanelAbrirFichaje(accion, idTrabajador);
         
         return "ok";
    }

    @GetMapping("/panel_finalizar")
    @Transactional
    @ResponseBody
    public String panelFinalizar() {

        Long idTrabajador = general.getIdValidado();
        repoHorariotrabajado.trabajadorPanelCerrarAutomatico(idTrabajador);
        repoHorariotrabajado.trabajadorPanelCerrarHorasHoy(idTrabajador);
        return "ok";
    }

    @GetMapping("/panel_refrescar")
    @ResponseBody
    public String tipoRegistro(String tipoRegistro) {
        Long idTrabajador = general.getIdValidado();
        String texto = repoHorariotrabajado.trabajadorPanelEstado(idTrabajador);
        if (texto == null) {
            return "Pulse un botón para iniciar la Jornada";
        }
        return texto;
    }
    
    @GetMapping("/tiempo_trabajado")
    @ResponseBody
    public Integer tiempo( ){
        return repoHorariotrabajado.trabajadorHorarioTrabajadoTiempo(general.getIdValidado()); 
    }

}
