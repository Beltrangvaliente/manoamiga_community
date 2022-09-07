/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.emp.repositories;

import com.tecnara.manoamiga.emp.entities.EmailAvisoExcesoHorarioEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author tecnara
 */
public interface IEmailAvisoExcesoHorarioRepository extends JpaRepository<EmailAvisoExcesoHorarioEntity, Long> {

    @Query(value = "SELECT ht.id id, ht.id_trabajador, ht.id_tecnico, SUM(ht.tiempo_minutos) minutosTrabajados, \n"
            + "	TRIM(CONCAT(\n"
            + "		CONCAT(IFNULL(t.nombre,''), ' ', IFNULL(t.apellido1,''), ' ', IFNULL(t.apellido2,'')),\n"
            + "		CONCAT(IFNULL(te.nombre,''), ' ', IFNULL(te.apellidos,''))\n"
            + "		)) nombre, ifnull(usu_tec.email,usu_tra.email) mail\n"
            + "FROM emp_horarios_trabajados ht\n"
            + "LEFT JOIN emp_trabajadores t ON (t.id= ht.id_trabajador)\n"
            + "LEFT JOIN emp_tecnicos te ON(te.id= ht.id_tecnico)\n"
            + "LEFT JOIN aaa_usuarios usu_tec on (usu_tec.id = te.id_usuario )\n "
            + "LEFT JOIN aaa_usuarios usu_tra on (usu_tra.id = t.id_usuario )\n "
            + " WHERE ht.fecha = CURDATE()\n "
            + "GROUP BY ht.id_trabajador, ht.id_tecnico\n", nativeQuery = true)
    public List<Map> schEnviarAvisosDiaAntes();

    public boolean existsByIdTecnicoAndFecha(Long idTecnico, Date fechaActual);

    public boolean existsByIdTrabajadorAndFecha(Long idTrabajador, Date fechaActual);

}
