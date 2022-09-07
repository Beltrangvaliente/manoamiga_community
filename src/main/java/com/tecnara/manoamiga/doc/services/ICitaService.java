package com.tecnara.manoamiga.doc.services;

import java.util.Date;

public interface ICitaService {
    public void generarAgenda(Long idCentroAtencion, Date fechaDesde, Date fechaHasta, String diasSemana, String horaInicio, String horaFinal, Integer intervaloMinutos) throws Exception;


}
