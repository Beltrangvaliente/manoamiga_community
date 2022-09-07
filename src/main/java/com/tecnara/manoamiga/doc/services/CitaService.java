package com.tecnara.manoamiga.doc.services;

import com.tecnara.manoamiga.doc.entities.CitaEntity;
import com.tecnara.manoamiga.doc.repositories.ICitaRepository;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CitaService implements ICitaService{

    public Calendar createHour(Calendar day, String hora){
        Calendar cal=Calendar.getInstance();
        cal.setTime(day.getTime());
        cal.set(Calendar.HOUR, Integer.parseInt(hora.substring(0, 2)));
        cal.set(Calendar.MINUTE, Integer.parseInt(hora.substring(3, 5)));
        return cal;
    }
    
    public List<CitaEntity> generarCita(Long idCentroAtencion, Date fechaDesde, Date fechaHasta, String diasSemana, String horaInicio, String horaFinal, Integer intervaloMinutos) throws Exception {
        if (fechaDesde == null){ throw new Exception("Debe rellenar la fecha desde"); }
        if (fechaHasta == null){ throw new Exception("Debe rellenar la fecha hasta"); }
        if (diasSemana == null){ throw new Exception("Debe rellenar los días de la semana"); }
        if (horaInicio == null){ throw new Exception("Debe rellenar la hora de inicio"); }
        if (horaFinal == null){ throw new Exception("Debe rellenar la hora de fin"); }
        if (horaInicio.length() != 5){ throw new Exception("El formato de la hora debe ser hh:mm"); }
        if (horaFinal.length() != 5){ throw new Exception("El formato de la hora debe ser hh:mm"); }
        if (intervaloMinutos == null){ throw new Exception("Debe rellenar el intervalo"); }
        if (intervaloMinutos <= 0){ throw new Exception("El intervalo debe ser positivo"); }
    
        List<CitaEntity> resultado=new ArrayList<>();
        SimpleDateFormat format=new SimpleDateFormat("HH:mm");
        
        SimpleDateFormat formatLog=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        Calendar calHasta=Calendar.getInstance();
        calHasta.setTime(fechaHasta);
        
        Calendar cal=Calendar.getInstance();
        cal.setTime(fechaDesde);
        while (cal.after(calHasta)==false){
            System.out.println(cal.get(Calendar.DAY_OF_WEEK)+"");
            if (diasSemana.contains(cal.get(Calendar.DAY_OF_WEEK)+"")){
                Calendar calHorIni=createHour(cal, horaInicio);
                Calendar calHorFin=createHour(cal, horaFinal);
                while (calHorIni.after(calHorFin)==false){
                    CitaEntity cita=new CitaEntity();
                    cita.setEstado("Vacio");
                    cita.setFechaDeCita(cal.getTime());
                    cita.setHoraDeCita(format.format(calHorIni.getTime()));
                    cita.setIdCentroDeAtencion(idCentroAtencion);
                    calHorIni.add(Calendar.MINUTE, intervaloMinutos);
                    resultado.add(cita);
                    System.out.println(formatLog.format(calHorIni.getTime()));
                }
            }
            cal.add(Calendar.DATE, 1);
        }
        return resultado;
    }
    
    @Autowired
    private ICitaRepository repoCita;
    
    @Override
    public void generarAgenda(Long idCentroAtencion, Date fechaDesde, Date fechaHasta, String diasSemana, String horaInicio, String horaFinal, Integer intervaloMinutos) throws Exception {
        List<CitaEntity> lista=generarCita(idCentroAtencion, fechaDesde, fechaHasta, diasSemana, horaInicio, horaFinal, intervaloMinutos);
        lista.forEach(x->{
            repoCita.save(x);
        });
        repoCita.flush();
        
    }

    
}
