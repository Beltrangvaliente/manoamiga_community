
package com.tecnara.manoamiga.aaa.controllers;

import com.tecnara.manoamiga.aaa.dtos.EmailInfoDto;
import com.tecnara.manoamiga.aaa.services.IEmailService;
import com.tecnara.manoamiga.doc.scheduled.CitaScheduled;
import com.tecnara.manoamiga.doc.scheduled.DocumentoSubidoScheduled;
import com.tecnara.manoamiga.doc.scheduled.MatriculaScheduled;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/pruebas/email")
public class EmailPruebasController {

    @Autowired
    private IEmailService mail;

    @GetMapping("/enviar_correo")
    @ResponseBody
    public String enviar() {
        EmailInfoDto info = new EmailInfoDto();
        info.setNombre("Juan");
        info.setApellido1("Fran");
        info.setApellido2("Lopez");
        info.setCodigoUsuario("1111");
        info.setTituloEmail("Alta usuario");
        info.setFicheroEmail("doc/cita_recordatorio_calendario_email");
        info.setEmail("eastier@gmail.com");

        mail.enviarEmail(info);
        return "Ok";
    }

    
    @Autowired
    private CitaScheduled schCita;
    
    @GetMapping("/recordatorio_cita")
    @ResponseBody
    public String enviarAvisosDiaAntes() {
        schCita.enviarAvisosDiaAntes();
        return "Ok";
        
    }
    
    
    @Autowired
    private DocumentoSubidoScheduled schDoc;    
    
    @GetMapping("/documentos")
    @ResponseBody
    public String documentos(){
        schDoc.enviarAvisosDiaAntes();
        return "ok";
    }
    
   @Autowired
    private MatriculaScheduled schMat;    
    
    @GetMapping("/inicio")
    @ResponseBody
    public String inicio(){
        schMat.enviarAvisosDiaAntes();
        return "ok";
    } 
    
    
}