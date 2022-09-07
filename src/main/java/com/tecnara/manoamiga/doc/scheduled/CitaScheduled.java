/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecnara.manoamiga.doc.scheduled;

import com.tecnara.manoamiga.aaa.dtos.EmailInfoDto;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.aaa.services.IEmailService;
import com.tecnara.manoamiga.doc.repositories.IAlumnoRepository;
import com.tecnara.manoamiga.doc.repositories.ICitaRepository;
import static java.awt.SystemColor.info;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;

@Configuration
@EnableScheduling
public class CitaScheduled {
    @Autowired
    private IUsuarioRepository repoUsuario;

    @Autowired
    private IAlumnoRepository repoAlumno;

    
    @Autowired
    private ICitaRepository repoCita;    
  
    @Autowired
    private IEmailService mail;

    //@Scheduled(fixedDelay = 1000, initialDelay = 1000)
    //@Scheduled(cron = "0 0 8 * * *")
    @Scheduled(cron = "0 0 8 * * *")
    public void enviarAvisosDiaAntes() {
        System.out.println("Recuerda que tienes una cita mañana a las 8:00");
        String CodigoVerificacion=UUID.randomUUID().toString();
        List<Map> lista=repoCita.scheduledCitaList();
        for (Map registro:lista){
            //a.nombre,a.apellido1,a.apellido2,a.mail,c.fecha_de_cita,c.hora_de_cita,c.estado
            EmailInfoDto info=new EmailInfoDto();
            info.setNombre(registro.get("nombre")+"");
            info.setApellido1(registro.get("apellido1")+"");
            info.setApellido2(registro.get("apellido2")+"");
            info.setEmail(registro.get("mail")+"");
            info.setHoraCita(registro.get("horaDeCita")+"");
            info.setTituloEmail(registro.get("Recordatorio Cita")+"");
            info.setFicheroEmail("doc/cita_recordatorio_calendario_email");
         
            mail.enviarEmail(info);  
          
        }
        
        
        
        
        //save
        //EmailInfoDto info=new EmailInfoDto();
        //info.setNombre(alumno.getNombre());
        //info.setApellido1(alumno.getApellido1());
        //info.setApellido2(alumno.getApellido2());
        //nfo.setCodigoUsuario(usuario.getCodigoVerificacion());
        //info.setTituloEmail("Registro usuario");
        //info.setEmail(usuario.getEmail());
      
        //mail.enviarEmail(info); 
      
    }    
    
    
}
