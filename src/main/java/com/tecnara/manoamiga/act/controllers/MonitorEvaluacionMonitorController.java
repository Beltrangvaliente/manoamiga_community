/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.entities.EvaluacionMonitorEntity;
import com.tecnara.manoamiga.act.repositories.IEvaluacionMonitorRepository;
import com.tecnara.manoamiga.expo.entities.AreaEntity;
import java.util.List;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/monitor/evaluacion_monitor")
public class MonitorEvaluacionMonitorController extends TecnaraBaseController {

    @Autowired
    private IGeneral general;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private IEvaluacionMonitorRepository repoEvaMoni;

    @GetMapping("/list")
    public String list(Model m) {
        return "act/monitor/evaluacion_monitor_list";
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData() {
        Long idMonitor=general.getIdValidado();
        return repoEvaMoni.monitorList(general.getIdValidado());
    }

}
