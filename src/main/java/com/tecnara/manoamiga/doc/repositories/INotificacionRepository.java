/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.doc.repositories;

import com.tecnara.manoamiga.doc.entities.NotificacionEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tecnara
 */
public interface INotificacionRepository extends JpaRepository<NotificacionEntity, Long> {

    @Query(value = "SELECT n.id id, n.hora_notificacion hora, n.leido leido, n.texto_notificacion notificacion, n.url_link enlace, n.fecha_notificacion fecha\n"
            + "FROM doc_notificaciones n\n"
            + "INNER JOIN doc_alumnos a ON (a.id=n.id_alumno)\n"
            + "INNER JOIN (SELECT :idAlumno idAlumno )param\n"
            + "WHERE param.idAlumno=n.id_alumno",
            nativeQuery = true
    )
    public List<Map> alumnoList(Long idAlumno);

    @Modifying
    @Transactional
    @Query(value = "UPDATE doc_notificaciones \n"
            + "SET leido='Si'\n"
            + "WHERE id_alumno =:idAlumno", nativeQuery = true)
    public void marcarNotificaciones(Long idAlumno);

    @Query(value = "SELECT COUNT(1) total\n"
            + "FROM doc_notificaciones n\n"
            + "\n"
            + "INNER JOIN (SELECT :idAlumno id_alumno) params\n"
            + "WHERE n.id_alumno = params.id_alumno\n"
            + "AND n.leido = 'No'", nativeQuery = true)

    public Integer alumnoNotificaciones(Long idAlumno);


    @Modifying
    @Transactional
    @Query(value = "UPDATE doc_notificaciones\n"
            + "SET leido='Si'\n"
            + "WHERE id= :id "
            + "AND id_alumno=:idAlumno ", nativeQuery = true)
    public Integer alumnoNotificacionBoton(Long id, Long idAlumno);
}





