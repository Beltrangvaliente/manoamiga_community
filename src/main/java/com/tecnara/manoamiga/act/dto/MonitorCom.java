/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.act.dto;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author tecnara
 */
public class MonitorCom {
    public Long id;
    public String Monitor;
    public String texto;
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    public Date fechaFaltaAsistencia;
    
}
