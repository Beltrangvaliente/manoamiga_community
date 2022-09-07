
package com.tecnara.manoamiga.doc.scheduled;

import com.tecnara.manoamiga.aaa.dtos.EmailInfoDto;
import com.tecnara.manoamiga.aaa.services.IEmailService;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.repositories.IDocumentoSolicitadoRepository;
import com.tecnara.manoamiga.doc.repositories.IDocumentoSubidoRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling

public class DocumentoSubidoScheduled {
    
    @Autowired 
    private IDocumentoSubidoRepository repoAviso;
    
    @Autowired 
    private IDocumentoSolicitadoRepository repoMail;
    
    @Autowired
    private IEmailService mail;
    
    @Autowired
    private IGeneral general;

    
    @Scheduled(cron = "0 0 8 * * *")
    public void enviarAvisosDiaAntes() {
        System.out.println("Recuerda que tienes que enviar documentos");

        List<Map> lista=repoMail.scheduledDocumentosFechaLimite();
        for (Map registro:lista){
            
            EmailInfoDto info=new EmailInfoDto();
            info.setNombre(registro.get("persona")+"");
            info.setEmail(registro.get("mail")+"");
            info.setNombreDocumento(registro.get("documento")+"");
            info.setFechaDocumento(general.getFechaCercana(3) );
            info.setTituloEmail("Recordatorio entrega de documentación");
            info.setFicheroEmail("doc/documentacion_fuera_plazo_email");
         
            mail.enviarEmail(info);  
 
        }
    
    }
    
}
