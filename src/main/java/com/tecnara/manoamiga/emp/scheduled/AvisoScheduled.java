/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecnara.manoamiga.emp.scheduled;

import com.tecnara.manoamiga.aaa.dtos.EmailInfoDto;
import com.tecnara.manoamiga.aaa.services.IEmailService;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.emp.entities.EmailAvisoExcesoHorarioEntity;
import com.tecnara.manoamiga.emp.repositories.IEmailAvisoExcesoHorarioRepository;
import java.util.Date;
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
public class AvisoScheduled {

    @Autowired
    private IEmailAvisoExcesoHorarioRepository emailAviso;
    @Autowired
    private IEmailService emailService;

    @Autowired
    private IGeneral general;

    @Scheduled(cron = "0 * * * * *")
    public void enviarAvisosDiaAntes() {
        System.out.println("Tu tiempo ha terminado, no pagamos horas extras");

        List<Map> lista = emailAviso.schEnviarAvisosDiaAntes();
        for (Map m : lista) {
            //2. Para cada uno, si no se le ha enviado un email hacer 3 y 4.
            EmailAvisoExcesoHorarioEntity emailA = new EmailAvisoExcesoHorarioEntity();
            {
                try {
                    Object objIdTecnico = m.get("id_tecnico");
                    if (objIdTecnico != null) {
                        Long idTecnico = Long.parseLong(objIdTecnico + "");
                        if (emailAviso.existsByIdTecnicoAndFecha(idTecnico, general.getFechaActual()) == false) {
                            //3. Si no se le enviado, enviar un email
                            EmailInfoDto info = new EmailInfoDto();
                            info.setEmail(m.get("mail") + "");
                            info.setTituloEmail("Jornada excedida de 8h");
                            info.setFicheroEmail("emp/jornada_excedida");
                            emailService.enviarEmail(info);

                            //4. Registrar en la tabla de email_avisos que se le ha enviado un email.
                            EmailAvisoExcesoHorarioEntity obj = new EmailAvisoExcesoHorarioEntity();
                            obj.setFecha(general.getFechaActual());
                            obj.setIdTecnico(idTecnico);
                            emailAviso.save(obj);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {

                    Object objIdTrabajador = m.get("id_trabajador");
                    if (objIdTrabajador != null) {
                        Long idTrabajador = Long.parseLong(objIdTrabajador + "");
                        if (emailAviso.existsByIdTrabajadorAndFecha(idTrabajador, general.getFechaActual()) == false) {

                            //3. Si no se le enviado, enviar un email
                            EmailInfoDto info = new EmailInfoDto();
                            info.setEmail(m.get("mail") + "");
                            info.setTituloEmail("Jornada excedida de 8h");
                            info.setFicheroEmail("emp/jornada_excedida");
                            emailService.enviarEmail(info);
                            //4. Registrar en la tabla de email_avisos que se le ha enviado un email.

                            EmailAvisoExcesoHorarioEntity obj = new EmailAvisoExcesoHorarioEntity();
                            obj.setFecha(general.getFechaActual());
                            obj.setIdTrabajador(idTrabajador);
                            emailAviso.save(obj);

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

    }

}
