
package com.tecnara.manoamiga.doc.scheduled;

import com.tecnara.manoamiga.aaa.dtos.EmailInfoDto;
import com.tecnara.manoamiga.aaa.services.IEmailService;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.repositories.IDocumentoSolicitadoRepository;
import com.tecnara.manoamiga.doc.repositories.IDocumentoSubidoRepository;
import com.tecnara.manoamiga.doc.repositories.IMatriculaRepository;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class MatriculaScheduled {
    @Autowired 
    private IDocumentoSubidoRepository repoAviso;
    
    @Autowired 
    private IDocumentoSolicitadoRepository repoMail;
    
    @Autowired
    private IMatriculaRepository repoMatricula;
    
    @Autowired
    private IEmailService mail;
    
    @Autowired
    private IGeneral general;

    
    @Scheduled(cron = "0 0 10 * * *")
    public void enviarAvisosDiaAntes() {
        System.out.println("Recuerda que el curso comienza en 3 días");

        List<Map> lista=repoMatricula.scheduledMatriculaFechaInicio();
        for (Map registro:lista){
            
            EmailInfoDto info=new EmailInfoDto();
            info.setNombre(registro.get("persona")+"");
            info.setEmail(registro.get("mail")+"");
            info.setNombreCurso(registro.get("nombre_curso")+"");
            info.setNombreCentro(registro.get("nombre")+"");
            info.setHorario(registro.get("horario")+"");
            info.setFechaCurso(general.getFechaCercana(3) );
            info.setTituloEmail("Recordatorio inicio de curso");
            info.setFicheroEmail("doc/inicio_curso_email");
         
            mail.enviarEmail(info);  
 
        }
    
    }
    
    
}
