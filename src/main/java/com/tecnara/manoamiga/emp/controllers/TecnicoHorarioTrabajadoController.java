/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.entities.CitaEntity;
import com.tecnara.manoamiga.emp.entities.TecnicoEntity;
import com.tecnara.manoamiga.emp.entities.HorarioTrabajadoEntity;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.emp.repositories.IHorarioTrabajadoRepository;
import com.tecnara.manoamiga.emp.repositories.ITecnicoRepository;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/emp/tecnico/horario_trabajado")
public class TecnicoHorarioTrabajadoController extends TecnaraBaseController {

    @Autowired
    private IHorarioTrabajadoRepository repoHorario;

    @Autowired
    private ITecnicoRepository repoTecnico;

    @Autowired
    private IGeneral general;

    @Autowired
    private IHorarioTrabajadoRepository repoHorarioTrabajado;

    @GetMapping("/list")
    public String List(Model m) {
        return "emp/tecnico/horario_trabajado_list";
    }

    @GetMapping("/listado_dias_pendientes")
    public String List2(Model m) {
        return "emp/tecnico/horario_trabajado_listado_dias_pendientes";
    }

    @GetMapping("/listado_dias_pendientes_data")
    @ResponseBody
    public List<Map> listData2() {
        return repoHorario.tecnicoListadoDiasPendientes();
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData(String filtro) {
        return repoHorario.tecnicoList(filtro);
    }

    @GetMapping("/listado_por_trabajador")
    public String ListadoPorTrabajador(Model m) {
        m.addAttribute("fechaActualInicio", general.formatDateSQL(general.getPrimerDiaMes()));
        m.addAttribute("fechaActualFin", general.formatDateSQL(general.getUltimoDiaMes()));
        m.addAttribute("fechaAnteriorInicio", general.formatDateSQL(general.getPrimerDiaMesAnterior()));
        m.addAttribute("fechaAnteriorFin", general.formatDateSQL(general.getUltimoDiaMesAnterior()));

        return "emp/tecnico/horario_trabajado_listado_trabajador";
    }

    @GetMapping("/list_data_por_trabajador")
    @ResponseBody
    public List<Map> listDataPorTrabajador(@DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) {
        return repoHorario.tecnicoListadoPorTrabajador(fechaDesde, fechaHasta);
    }

    @GetMapping("/list_data_por_fecha")
    @ResponseBody
    public List<Map> tecnicoHorarioTrabajadoListadoPorFecha(@DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta, Long idTrabajador, Long idTecnico) {
        return repoHorarioTrabajado.tecnicoHorarioTrabajadoListadoPorFecha(idTrabajador, idTecnico, fechaDesde, fechaHasta);
    }

    @GetMapping("/listado_por_fecha")
    public String listPorFecha(Model m) {
        m.addAttribute("fechaActualInicio", general.formatDateSQL(general.getPrimerDiaMes()));
        m.addAttribute("fechaActualFin", general.formatDateSQL(general.getUltimoDiaMes()));
        m.addAttribute("fechaAnteriorInicio", general.formatDateSQL(general.getPrimerDiaMesAnterior()));
        m.addAttribute("fechaAnteriorFin", general.formatDateSQL(general.getUltimoDiaMesAnterior()));
        
        return "emp/tecnico/horario_trabajado_listado_por_fecha";
    }

    @PostMapping("/guardar")
    public String guardar(HorarioTrabajadoEntity registro) {
        registro.setIdTecnico(general.getIdValidado());
        registro.setModoFichaje("Retrospectivo");
        repoHorario.save(registro);
        return "redirect:list";
    }

    @GetMapping("/fichar_puesto")
    public String ficharPuesto() {
        return "emp/tecnico/horario_trabajado_fichar_puesto_form";
    }

    @GetMapping("/panel")
    public String Panel(Model m) {
        TecnicoEntity te = repoTecnico.findById(general.getIdValidado()).get();
        m.addAttribute("puedeRegistrarPermiso", te.getPuedeRegistrarPermiso());

        return "emp/tecnico/horario_trabajado_panel";
    }

    @GetMapping("/grafica_por_tipo_registro")
    public String graficaRegistro() {
        return "emp/tecnico/horario_trabajado_grafica_por_tipo_registro";
    }

    @GetMapping("/list_data_grafica_registro")
    @ResponseBody
    public List<Map> listDataGraficaRegistro(@DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta, Long idTrabajador, Long idTecnico) {
        return repoHorario.tecnicoGraficaPorTipoRegistro(fechaDesde, fechaHasta, idTrabajador, idTecnico);
    }

    @GetMapping("/panel_fichar")
    @Transactional
    @ResponseBody
    public String panelFichar(String accion) {
        Long idTecnico = general.getIdValidado();

        //Update idTrabajador
        //Insertar idTrabajador, accion
        repoHorario.tecnicoPanelCerrarAutomatico(idTecnico); //*
        repoHorario.tecnicoPanelCerrarHorasHoy(idTecnico); //*

        //Si no has pulsado finalizar. Accion != "finalizar"
        repoHorario.tecnicoPanelAbrirFichaje(accion, idTecnico); //*

        return "ok";
    }

    @GetMapping("/panel_finalizar")
    @Transactional
    @ResponseBody
    public String panelFinalizar() {
        Long idTecnico = general.getIdValidado();
        repoHorario.tecnicoPanelCerrarAutomatico(idTecnico);
        repoHorario.tecnicoPanelCerrarHorasHoy(idTecnico);
        return "ok";
    }

    @GetMapping("/panel_refrescar")
    @ResponseBody
    public String tipoRegistro() {
        Long idTecnico = general.getIdValidado();
        String texto = repoHorario.tecnicoPanelEstado(idTecnico);
        if (texto == null) {
            return "Pulse un botón para iniciar la Jornada";
        }
        return texto;
    }

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new HorarioTrabajadoEntity());
        } else {
            m.addAttribute("registro", repoHorario.findById(id).get());

        }
        return "emp/tecnico/horario_trabajado_form";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoHorario.deleteById(id);
        return "redirect:list";
    }

    @GetMapping("/vacaciones")
    public String vacaciones() {
        return "emp/tecnico/horario_trabajado_vacaciones";
    }

    @PostMapping("/vacaciones_guardar")
    @ResponseBody
    public String guardarVacaciones(Long idTrabajador, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin, String diaSemana) {
        if (idTrabajador == null) {
            return "Debes introducir el trabajador";
        }
        if (fechaInicio == null) {
            return "Debes introducir la fecha de inicio";
        }
        if (fechaFin == null) {
            return "Debes introducir la fecha de fin";
        }
        if (diaSemana == null || diaSemana.length() == 0) {
            return "Debes seleccionar algún día de la semana";
        }
        //if (horaInicio.length() != 5){ return "redirect:vacaciones?error=La hora debe ser hh:mm"; }
        //if (horaFin.length() != 5){ return "redirect:vacaciones?error=La hora debe ser hh:mm"; }

        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaInicio);

        Calendar calHasta = Calendar.getInstance();
        calHasta.setTime(fechaFin);

        while (cal.after(calHasta) == false) {
            //if el dia de la semana {
            //if (diasSemana.contains(cal.get(Calendar.DAY_OF_WEEK)+"")){
            // Calendar calHorIni=createHour(cal, horaInicio);
            //Calendar calHorFin=createHour(cal, horaFinal);
            if (diaSemana.contains(cal.get(Calendar.DAY_OF_WEEK) + "")) {
                HorarioTrabajadoEntity nuevo = new HorarioTrabajadoEntity();
                nuevo.setFecha(cal.getTime());
                nuevo.setHoraInicio("00:00");
                nuevo.setHoraFin("00:00");
                nuevo.setTiempoMinutos(8 * 60);
                nuevo.setIdTrabajador(idTrabajador);
                nuevo.setModoFichaje("Retrospectivo");
                nuevo.setTipoRegistro("Vacaciones");

                //.......Fechas y horas
                repoHorario.save(nuevo);
            }
            cal.add(Calendar.DATE, 1);
        }

        return "FELICIDADES!...Sus vacaciones se han generado satisfactoriamente";
    }

    @GetMapping("/list_data_vacaciones")
    @ResponseBody
    public List<Map> listDataVacaciones(Long idTrabajador) {
        return repoHorario.tecnicoVacaciones(idTrabajador);
    }

    @GetMapping("/bajas")
    public String bajas() {
        return "emp/tecnico/horario_trabajado_bajas";
    }

    @PostMapping("/bajas_guardar")
    @ResponseBody
    public String guardarBaja(Long idTrabajador, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin, String diaSemana) {
        if (idTrabajador == null) {
            return "Debes introducir el trabajador";
        }
        if (fechaInicio == null) {
            return "Debes introducir la fecha de inicio";
        }
        if (fechaFin == null) {
            return "Debes introducir la fecha de fin";
        }
        if (diaSemana == null || diaSemana.length() == 0) {
            return "Debes seleccionar algún día de la semana";
        }
        //if (horaInicio.length() != 5){ return "redirect:vacaciones?error=La hora debe ser hh:mm"; }
        //if (horaFin.length() != 5){ return "redirect:vacaciones?error=La hora debe ser hh:mm"; }

        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaInicio);

        Calendar calHasta = Calendar.getInstance();
        calHasta.setTime(fechaFin);

        while (cal.after(calHasta) == false) {
            //if el dia de la semana {
            //if (diasSemana.contains(cal.get(Calendar.DAY_OF_WEEK)+"")){
            // Calendar calHorIni=createHour(cal, horaInicio);
            //Calendar calHorFin=createHour(cal, horaFinal);
            if (diaSemana.contains(cal.get(Calendar.DAY_OF_WEEK) + "")) {
                HorarioTrabajadoEntity nuevo = new HorarioTrabajadoEntity();
                nuevo.setFecha(cal.getTime());
                nuevo.setHoraInicio("00:00");
                nuevo.setHoraFin("00:00");
                nuevo.setTiempoMinutos(8 * 60);  //Cambiar si no es 0
                nuevo.setIdTrabajador(idTrabajador);
                nuevo.setModoFichaje("Retrospectivo");
                nuevo.setTipoRegistro("Baja");

                //.......Fechas y horas
                repoHorario.save(nuevo);
            }
            cal.add(Calendar.DATE, 1);
        }

        return "Baja registrada";
    }

    @GetMapping("/list_data_bajas")
    @ResponseBody
    public List<Map> listDataBaja(Long idTrabajador) {
        return repoHorario.tecnicoBaja(idTrabajador);
    }


    @GetMapping("/tiempo_trabajado")
    @ResponseBody
    public Integer tiempo( ){
        return repoHorario.tecnicoHorarioTrabajadoTiempo(general.getIdValidado()); 
    }
    
    
}
