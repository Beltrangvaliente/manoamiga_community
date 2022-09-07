package com.tecnara.manoamiga.expo.repositories;

import com.tecnara.manoamiga.expo.entities.EventoEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IEventoRepository extends JpaRepository<EventoEntity, Long> {

    public List<EventoEntity> findByIdArea(Long idArea);

    public List<EventoEntity> findByIdEspacio(Long idEspacio);

    public List<EventoEntity> findByIdTrabajador(Long idTrabajador);

    public List<EventoEntity> findByFechaActividad(Date fechaActividad);

    public List<EventoEntity> findByEstadoAndFechaActividadOrderByHora(String estado, Date fechaActividad);

    public List<EventoEntity> findByEstadoOrderByHora(String estado);

    public List<EventoEntity> findByEstadoAndFechaActividad(String estado, Date fechaActividad);

    public List<EventoEntity> findByFechaActividadBetweenOrderByHora(Date fechaActividad1, Date fechaActividad2);

    public List<EventoEntity> findByEstadoAndFechaActividadBetweenOrderByHora(String estado, Date primerDiaMes, Date ultimoDiaMes);

    public List<EventoEntity> findByIdTrabajadorOrderByFechaActividadDesc(Long idTrabajador);

    public List<EventoEntity> findByEstadoContainingOrderByFechaActividadDesc(String estado);

    @Query(value = "SELECT ev.id, ar.area, es.ubicacion, tr.nombre_trabajador nombreTrabajador, ev.nombre_actividad nombreActividad, ev.descripcion, ev.fecha_actividad fechaActividad, ev.hora, ev.estado\n"
            + "FROM expo_eventos ev\n"
            + "LEFT JOIN expo_areas ar  ON (ar.id  = ev.id_area)\n"
            + "LEFT JOIN expo_trabajadores tr ON (ev.id_trabajador=tr.id)\n"
            + "LEFT JOIN expo_espacios es ON (ev.id_espacio=es.id)\n"
            + "INNER JOIN (SELECT :estado estado, CONCAT('%', :filtro, '%') filtro , :fechaDesde fechaDesde, :fechaHasta fechaHasta) params\n"
            + "WHERE (params.estado='' OR ev.estado=params.estado )\n"
            + "AND (ar.area LIKE params.filtro OR es.ubicacion LIKE params.filtro OR ev.nombre_actividad LIKE params.filtro OR tr.nombre_trabajador LIKE params.filtro OR ev.descripcion  LIKE params.filtro )\n"
            + "AND (params.fechaDesde IS NULL OR ev.fecha_actividad >= params.fechaDesde)\n"
            + "AND (params.fechaHasta IS NULL OR ev.fecha_actividad <= params.fechaHasta)\n"
            + "ORDER BY ev.fecha_actividad DESC, ev.hora DESC\n"
            + "LIMIT 1001", nativeQuery = true)
    public List<Map> leerListaDeEventosParaCoordinador(String estado, String filtro, Date fechaDesde, Date fechaHasta);

}
