package com.tecnara.manoamiga.aaa.services;

import com.tecnara.manoamiga.aaa.dtos.EmailInfoDto;
import com.tecnara.manoamiga.aaa.entities.EmailEntity;
import com.tecnara.manoamiga.aaa.repositories.IEmailRepository;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {

    @Autowired
    private JavaMailSender mail;
    
    private boolean createdJMS;
 
    @Value("${tecnara.theme}")
    private String tema;

    @Autowired
    private IGeneral general;
    
    private void createJavaMailSender(){
        if (createdJMS==false){
            createdJMS=true;
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(general.getPreferencia("email_smtp", "ssl0.ovh.net"));
            mailSender.setPort(587);

            mailSender.setUsername(general.getPreferencia("email_username", "manoamiga@jorgerubira.com"));
            mailSender.setPassword(general.getPreferencia("email_password", "QWEASDqweasd2!"));

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", general.getPreferencia("email_transport_protocol", "smtp"));
            props.put("mail.smtp.auth", general.getPreferencia("email_smtp_auth", "true"));
            props.put("mail.smtp.starttls.enable", general.getPreferencia("email_smtp_starttls_enable", "true"));
            props.put("mail.smtp.ssl.trust", general.getPreferencia("email_smtp_ssl_trust", "ssl0.ovh.net") );
            props.put("mail.debug", general.getPreferencia("email_debug", "false"));

            mail=mailSender;
        }
    }
    
    
    @Override
    public void enviarEmail(EmailInfoDto info) {
        createJavaMailSender();
        try {
            Resource r = new ClassPathResource("\\emails\\default\\" + info.getFicheroEmail() + ".html");
            
            String textoEmail = new BufferedReader(
              new InputStreamReader(r.getInputStream(), StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));            
            
            textoEmail = textoEmail.replaceAll("\\$\\{nombre}", (info.getNombre() + "").replaceAll("null", ""));
            textoEmail = textoEmail.replaceAll("\\$\\{apellido1}", (info.getApellido1() + "").replaceAll("null", ""));
            textoEmail = textoEmail.replaceAll("\\$\\{apellido2}", (info.getApellido2() + "").replaceAll("null", ""));
            textoEmail = textoEmail.replaceAll("\\$\\{usuario}", (info.getUsuario() + "").replaceAll("null", ""));
            textoEmail = textoEmail.replaceAll("\\$\\{email}", (info.getEmail() + "").replaceAll("null", ""));
            textoEmail = textoEmail.replaceAll("\\$\\{email2}", (info.getEmail2() + "").replaceAll("null", ""));
            textoEmail = textoEmail.replaceAll("\\$\\{codigo_verificacion_usuario}", (info.getCodigoUsuario() + "").replaceAll("null", ""));
            textoEmail = textoEmail.replaceAll("\\$\\{dominio}", general.getPreferencia("email_dominio_link", "http://localhost:8080") ); 
            textoEmail = textoEmail.replaceAll("\\$\\{tema}", tema);
            textoEmail = textoEmail.replaceAll("\\$\\{fecha_cita}", (info.getFechaCita() + "").replaceAll("null", ""));
            textoEmail = textoEmail.replaceAll("\\$\\{hora_cita}", (info.getHoraCita() + "").replaceAll("null", ""));
            textoEmail = textoEmail.replaceAll("\\$\\{centro_atencion_cita}", (info.getCentroAtencionCita() + "").replaceAll("null", ""));
            textoEmail = textoEmail.replaceAll("\\$\\{nombre_curso}", (info.getNombreCurso() + "").replaceAll("null", ""));
            textoEmail = textoEmail.replaceAll("\\$\\{fecha_curso}", (info.getFechaCurso() + "").replaceAll("null", ""));
            textoEmail = textoEmail.replaceAll("\\$\\{nombre_documento}", (info.getNombreDocumento()+ "").replaceAll("null", ""));
            textoEmail = textoEmail.replaceAll("\\$\\{fecha_documento}", (info.getFechaDocumento() + "").replaceAll("null", ""));
            textoEmail = textoEmail.replaceAll("\\$\\{nombre_centro}", (info.getNombreCentro()+ "").replaceAll("null", ""));
            textoEmail = textoEmail.replaceAll("\\$\\{horario}", (info.getHorario()+ "").replaceAll("null", ""));            
            
            MimeMessage mensaje = mail.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, "utf-8");
            helper.setFrom(general.getPreferencia("email_from","manoamiga@jorgerubira.com") );
            helper.setTo(info.getEmail());
            helper.setSubject(info.getTituloEmail());
            helper.setText(textoEmail, true);

            mail.send(mensaje);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Date getDateRandom() {
        return general.getFechaCercana((int) (Math.random() * 10 - 5));
    }
    @Autowired
    private IEmailRepository repoEmail;

    private void crearEmail() {
        EmailEntity email = new EmailEntity();
        email.setId(1L);
        email.setFecha(getDateRandom());
        email.setHora("14:00");
        email.setMailDestinatario("tecnara@gmail.com");;
        email.setTitulo("Recordatorio");
        email.setTextoEnviado("Hola, ¿qué tal?");;

        repoEmail.save(email);
    }

    @Override
    public void resetConfiguration() {
        createdJMS=false;
    }
}
