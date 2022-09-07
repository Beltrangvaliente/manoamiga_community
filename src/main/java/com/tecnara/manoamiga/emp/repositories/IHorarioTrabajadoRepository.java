/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tecnara.manoamiga.emp.repositories;

import com.tecnara.manoamiga.emp.entities.HorarioTrabajadoEntity;
import com.tecnara.manoamiga.emp.entities.TecnicoEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jorge
 */
@Repository("emp_horarios_trabajados")
public interface IHorarioTrabajadoRepository extends JpaRepository<HorarioTrabajadoEntity, Long> {

    @Query(value = "SELECT ht.id id, ht.id_trabajador, ht.id_tecnico, SUM(ht.tiempo_minutos) minutosTrabajados, \n"
            + "	TRIM(CONCAT(\n"
            + "		CONCAT(IFNULL(t.nombre,''), ' ', IFNULL(t.apellido1,''), ' ', IFNULL(t.apellido2,'')),\n"
            + "		CONCAT(IFNULL(te.nombre,''), ' ', IFNULL(te.apellidos,''))\n"
            + "		)) nombre, SUM( IF(ht.modo_fichaje='Retrospectivo',1,0) )retrospectivo,SUM(IF(ht.tipo_registro='Presencial',ht.tiempo_minutos,0))presencial,\n"
            + "SUM(IF(ht.tipo_registro='Teletrabajo',ht.tiempo_minutos,0))teletrabajo, SUM(IF(ht.tipo_registro='Desplazamiento',ht.tiempo_minutos,0))desplazamiento,\n"
            + "SUM(IF(ht.tipo_registro='Permiso',ht.tiempo_minutos,0))permiso,\n"
            + "SUM(IF(ht.tipo_registro='Vacaciones',ht.tiempo_minutos,0))vacaciones,\n"
            + "SUM(IF(ht.tipo_registro='Baja',ht.tiempo_minutos,0))bajas\n"
            + "FROM emp_horarios_trabajados ht\n"
            + "LEFT JOIN emp_trabajadores t ON (t.id= ht.id_trabajador)\n"
            + "LEFT JOIN emp_tecnicos te ON(te.id= ht.id_tecnico)\n"
            + "INNER JOIN( SELECT :fechaDesde fechaDesde, :fechaHasta fechaHasta)params\n"
            + " WHERE(params.fechaDesde is null OR ht.fecha >= params.fechaDesde) "
            + " AND (params.fechaHasta is null OR ht.fecha <= params.fechaHasta) \n"
            + "GROUP BY ht.id_trabajador, ht.id_tecnico\n"
            + "ORDER BY nombre", nativeQuery = true)
    public List<Map> tecnicoListadoPorTrabajador(Date fechaDesde, Date fechaHasta);

    @Query(value = "SELECT SUM(IFNULL(ht.tiempo_minutos, ( SUBSTR(NOW(),12,2)-SUBSTR(ht.hora_inicio,1,2) )*60 + SUBSTR(NOW(),15,2) - SUBSTR(ht.hora_inicio,4,2))  ) tiempo\n" +
"FROM emp_horarios_trabajados ht\n" +
"LEFT JOIN emp_tecnicos te ON (te.id= ht.id_tecnico)\n" +
"INNER JOIN (SELECT :idTecnico idTecnico)params\n" +
"WHERE (ht.id_tecnico= params.idTecnico) AND ( ht.fecha= CURDATE())", nativeQuery = true)
    public Integer tecnicoHorarioTrabajadoTiempo(Long idTecnico);
    
    @Query(value = "SELECT SUM(IFNULL(ht.tiempo_minutos, ( SUBSTR(NOW(),12,2)-SUBSTR(ht.hora_inicio,1,2) )*60 + SUBSTR(NOW(),15,2) - SUBSTR(ht.hora_inicio,4,2))  ) tiempo\n" +
"FROM emp_horarios_trabajados ht\n" +
"LEFT JOIN emp_trabajadores tr ON (tr.id= ht.id_trabajador)\n" +
"INNER JOIN (SELECT :idTrabajador idTrabajador)params\n" +
"WHERE (ht.id_trabajador= params.idTrabajador) AND ( ht.fecha= CURDATE())", nativeQuery = true)
    public Integer trabajadorHorarioTrabajadoTiempo(Long idTrabajador);

    @Query(value = "SELECT ht.id id, ht.id_trabajador, ht.id_tecnico, SUM(ht.tiempo_minutos) minutosTrabajados\n"
            + "	TRIM(CONCAT(\n"
            + "		CONCAT(IFNULL(t.nombre,''), ' ', IFNULL(t.apellido1,''), ' ', IFNULL(t.apellido2,'')),\n"
            + "            	CONCAT(IFNULL(te.nombre,''), ' ', IFNULL(te.apellidos,''))\n"
            + "		)) nombre\n"
            + "FROM emp_horarios_trabajados ht\n"
            + "LEFT JOIN emp_trabajadores t ON (t.id= ht.id_trabajador)\n"
            + "LEFT JOIN emp_tecnicos te ON(te.id= ht.id_tecnico)\n"
            + "INNER JOIN( SELECT MONTH(CURRENT_DATE()))params\n"
            + "GROUP BY ht.id_trabajador, ht.id_tecnico\n"
            + "ORDER BY nombre", nativeQuery = true)
    public List<Map> tecnicoListadoPorTrabajadorMesActual(Date mesActual);

    @Query(value = "SELECT ht.id id, ht.id_trabajador, ht.id_tecnico, SUM(ht.tiempo_minutos) minutosTrabajados\n"
            + "	TRIM(CONCAT(\n"
            + "		CONCAT(IFNULL(t.nombre,''), ' ', IFNULL(t.apellido1,''), ' ', IFNULL(t.apellido2,'')),\n"
            + "            	CONCAT(IFNULL(te.nombre,''), ' ', IFNULL(te.apellidos,''))\n"
            + "		)) nombre\n"
            + "FROM emp_horarios_trabajados ht\n"
            + "LEFT JOIN emp_trabajadores t ON (t.id= ht.id_trabajador)\n"
            + "LEFT JOIN emp_tecnicos te ON(te.id= ht.id_tecnico)\n"
            + "INNER JOIN( SELECT MONTH(CURRENT_DATE()-1))params\n"
            + "GROUP BY ht.id_trabajador, ht.id_tecnico\n"
            + "ORDER BY nombre", nativeQuery = true)
    public List<Map> tecnicoListadoPorTrabajadorMesAnterior(Date mesAnterior);

    @Query(value = "SELECT h.id,\n"
            + "	TRIM(CONCAT(\n"
            + "		CONCAT(IFNULL(tr.nombre,''), ' ', IFNULL(tr.apellido1,''), ' ' , IFNULL(tr.apellido2,'')), \n"
            + "		CONCAT(IFNULL(te.nombre,''), ' ', IFNULL(te.apellidos,''))\n"
            + "	)) nombre,\n"
            + "	h.fecha, h.hora_inicio horaInicio, h.hora_fin horaFin,h.tipo_registro tipoRegistro, h.tiempo_minutos tiempoMinutos\n"
            + "FROM emp_horarios_trabajados h \n"
            + "LEFT JOIN emp_trabajadores tr ON(tr.id=h.id_trabajador)\n"
            + "LEFT JOIN emp_tecnicos te ON(te.id=h.id_tecnico)\n"
            + "INNER JOIN (SELECT :idTrabajador idTrabajador) params\n"
            + "WHERE tr.id = params.idTrabajador\n"
            + "ORDER BY h.fecha DESC, h.hora_inicio DESC", nativeQuery = true)
    public List<Map> trabajadorList(Long idTrabajador);

    @Query(value = "SELECT emp.id,\n"
            + "            TRIM(CONCAT(CONCAT(IFNULL(tr.nombre,''), ' ', IFNULL(tr.apellido1,''), ' ' , IFNULL(tr.apellido2,'')), \n"
            + "            CONCAT(IFNULL(tc.nombre,''), ' ', IFNULL(tc.apellidos,'')))) \n"
            + "            nombre,emp.fecha, emp.hora_inicio, emp.hora_fin, emp.tiempo_minutos horas ,emp.tipo_registro,emp.modo_fichaje\n"
            + "            FROM emp_horarios_trabajados emp\n"
            + "            LEFT JOIN emp_trabajadores tr ON (tr.id = emp.id_trabajador)\n"
            + "            LEFT JOIN emp_tecnicos tc ON (tc.id = emp.id_tecnico)\n"
            + "            INNER JOIN (SELECT CONCAT('%', :filtro, '%') filtro) params\n"
            + "            WHERE (tr.nombre LIKE params.filtro\n"
            + "            OR tc.nombre LIKE params.filtro\n"
            + "            OR tr.apellido1 LIKE params.filtro\n"
            + "            OR tr.apellido2 LIKE params.filtro\n"
            + "            OR tc.apellidos LIKE params.filtro\n"
            + "            OR emp.tipo_registro LIKE params.filtro\n"
            + "            OR emp.modo_fichaje LIKE params.filtro)\n"
            + "            ORDER BY emp.fecha DESC, emp.hora_inicio DESC", nativeQuery = true)
    public List<Map> tecnicoList(String filtro);

    @Modifying
    @Query(value = "UPDATE emp_horarios_trabajados\n"
            + "SET hora_fin = SUBSTR(NOW(),12,5),\n"
            + "	tiempo_minutos=( SUBSTR(NOW(),12,2)-SUBSTR(hora_inicio,1,2) )*60 + SUBSTR(NOW(),15,2) - SUBSTR(hora_inicio,4,2)\n"
            + "WHERE hora_fin IS NULL\n"
            + "AND id_trabajador =:idValidado\n"
            + "AND fecha = CURDATE()", nativeQuery = true)
    public void trabajadorPanelCerrarHorasHoy(Long idValidado);

    @Modifying
    @Query(value = "UPDATE emp_horarios_trabajados\n"
            + "SET hora_fin = SUBSTR(NOW(),12,5),\n"
            + "	tiempo_minutos=( SUBSTR( IFNULL((SELECT hora_salida FROM emp_trabajadores tr WHERE tr.id=:idValidado),'23:59') ,12,2)-SUBSTR(hora_inicio,1,2) )*60 + SUBSTR( IFNULL((SELECT hora_salida FROM emp_trabajadores tr WHERE tr.id=:idValidado),'23:59') ,15,2) - SUBSTR(hora_inicio,4,2)\n"
            + "WHERE hora_fin IS NULL\n"
            + "AND id_trabajador = :idValidado\n"
            + "AND fecha < CURDATE()", nativeQuery = true)
    public void trabajadorPanelCerrarAutomatico(Long idValidado);

    @Modifying
    @Query(value = "INSERT INTO emp_horarios_trabajados(fecha, hora_inicio, id_trabajador, modo_fichaje, tipo_registro)\n"
            + "VALUES(CURDATE(), SUBSTR(NOW(),12,5), :idValidado, 'Inmediato', :accion)", nativeQuery = true)
    public void trabajadorPanelAbrirFichaje(String accion, Long idValidado);

    @Query(value = "SELECT ht.tipo_registro tipoRegistro, SUM(ht.tiempo_minutos) horasTrabajadas\n"
            + "FROM emp_horarios_trabajados ht\n"
            + "LEFT JOIN emp_trabajadores t ON (t.id= ht.id_trabajador)\n"
            + "LEFT JOIN emp_tecnicos te ON(te.id= ht.id_tecnico)\n"
            + "INNER JOIN( SELECT :fechaDesde fechaDesde, :fechaHasta fechaHasta, :idTrabajador idTrabajador, :idTecnico idTecnico)params\n"
            + "WHERE(params.fechaDesde is null OR ht.fecha >= params.fechaDesde) "
            + "AND (params.fechaHasta is null OR ht.fecha <= params.fechaHasta) \n"
            + "AND (params.idTrabajador is null OR ht.id_trabajador = params.idTrabajador) \n"
            + "AND (params.idTecnico is null OR ht.id_tecnico = params.idTecnico) \n"
            + "GROUP BY ht.tipo_registro\n"
            + "ORDER BY horasTrabajadas DESC", nativeQuery = true)
    public List<Map> tecnicoGraficaPorTipoRegistro(Date fechaDesde, Date fechaHasta, Long idTrabajador, Long idTecnico);

    @Query(value = "SELECT tipo_registro\n"
            + "FROM emp_horarios_trabajados\n"
            + "INNER JOIN (SELECT :idValidado idTrabajador) params\n"
            + "WHERE hora_fin IS NULL\n"
            + "AND id_trabajador = params.idTrabajador", nativeQuery = true)
    public String trabajadorPanelEstado(Long idValidado);

    @Query(value = "SELECT tipo_registro\n"
            + "FROM emp_horarios_trabajados\n"
            + "INNER JOIN (SELECT :idValidado idTecnico) params\n"
            + "WHERE hora_fin IS NULL\n"
            + "AND id_tecnico = params.idTecnico", nativeQuery = true)
    public String tecnicoPanelEstado(Long idValidado);

    @Modifying
    @Query(value = "UPDATE emp_horarios_trabajados\n"
            + "SET hora_fin = SUBSTR(NOW(),12,5),\n"
            + "	tiempo_minutos=( SUBSTR(NOW(),12,2)-SUBSTR(hora_inicio,1,2) )*60 + SUBSTR(NOW(),15,2) - SUBSTR(hora_inicio,4,2)\n"
            + "WHERE hora_fin IS NULL\n"
            + "AND id_tecnico =:idValidado\n"
            + "AND fecha = CURDATE()", nativeQuery = true)
    public void tecnicoPanelCerrarHorasHoy(Long idValidado);

    @Modifying
    @Query(value = "UPDATE emp_horarios_trabajados\n"
            + "SET hora_fin = SUBSTR(NOW(),12,5),\n"
            + "	tiempo_minutos=( SUBSTR(  IFNULL((SELECT hora_salida FROM emp_tecnicos tr WHERE tr.id=:idValidado),'23:59') ,12,2)-SUBSTR(hora_inicio,1,2) )*60 + SUBSTR( IFNULL((SELECT hora_salida FROM emp_tecnicos tr WHERE tr.id=:idValidado),'23:59') ,15,2) - SUBSTR(hora_inicio,4,2)\n"
            + "WHERE hora_fin IS NULL\n"
            + "AND id_tecnico = :idValidado\n"
            + "AND fecha < CURDATE()", nativeQuery = true)
    public void tecnicoPanelCerrarAutomatico(Long idValidado);

    @Modifying
    @Query(value = "INSERT INTO emp_horarios_trabajados(fecha, hora_inicio, id_tecnico, modo_fichaje, tipo_registro)\n"
            + "VALUES(CURDATE(), SUBSTR(NOW(),12,5), :idValidado, 'Inmediato', :accion)", nativeQuery = true)
    public void tecnicoPanelAbrirFichaje(String accion, Long idValidado);

    @Query(value = "SELECT  ho.fecha 'Fecha', sum(ho.tiempo_minutos) 'Horas'\n"
            + "FROM emp_trabajadores tra\n"
            + "INNER JOIN emp_horarios_trabajados ho ON(ho.id_trabajador=tra.id)\n"
            + " inner join (select :idTrabajador idTrabajador) params\n"
            + " where ho.id_trabajador = params.idTrabajador\n"
            + "GROUP BY ho.fecha\n"
            + "ORDER BY ho.fecha DESC", nativeQuery = true)
    public List<Map> trabajadorListFecha(Long idTrabajador);

    @Query(value = "SELECT tr.nombre nombre, tr.apellido1 apellido1, tr.apellido2 apellido2, tr.id, tFechas.fecha\n"
            + "FROM (\n"
            + "	SELECT DISTINCT fecha \n"
            + "	FROM emp_horarios_trabajados \n"
            + "	WHERE fecha BETWEEN DATE_ADD(CURDATE(), INTERVAL -31 DAY) AND DATE_ADD(CURDATE(), INTERVAL -1 DAY)\n"
            + ") tFechas\n"
            + "INNER JOIN emp_trabajadores tr ON (obligacion_registro_horario = 'Si')\n"
            + "LEFT JOIN emp_horarios_trabajados ht ON (tFechas.fecha = ht.fecha AND ht.id_trabajador = tr.id)\n"
            + "WHERE ht.id IS NULL\n"
            + "ORDER BY fecha, nombre, apellido1, apellido2", nativeQuery = true)
    public List<Map> tecnicoListadoDiasPendientes();

    @Query(value = "SELECT h.id,\n"
            + "       TRIM(CONCAT(\n"
            + "		CONCAT(IFNULL(tr.nombre,''), ' ', IFNULL(tr.apellido1,''), ' ' , IFNULL(tr.apellido2,'')), \n"
            + "		CONCAT(IFNULL(te.nombre,''), ' ', IFNULL(te.apellidos,''))\n"
            + "       )) nombre, diaSemana,\n"
            + "       h.fecha\n"
            + "       FROM emp_horarios_trabajados h \n"
            + "       LEFT JOIN emp_trabajadores tr ON(tr.id=h.id_trabajador)\n"
            + "       LEFT JOIN emp_tecnicos te ON(te.id=h.id_tecnico)\n"
            + "       INNER JOIN (\n"
            + "		SELECT 2 d, 'Lunes' diaSemana UNION ALL\n"
            + "		SELECT 3 d, 'Martes' diaSemana UNION ALL\n"
            + "		SELECT 4 d, 'Miércoles' diaSemana UNION ALL\n"
            + "		SELECT 5 d, 'Jueves' diaSemana UNION ALL\n"
            + "		SELECT 6 d, 'Viernes' diaSemana UNION ALL\n"
            + "		SELECT 7 d, 'Sábado' diaSemana UNION ALL\n"
            + "		SELECT 1 d, 'Domingo' diaSemana \n"
            + "       ) tDias ON (DAYOFWEEK(h.fecha) = tDias.d)\n"
            + "INNER JOIN (SELECT :idTrabajador idTrabajador) params       \n"
            + "WHERE h.tipo_registro='Vacaciones'\n"
            + "AND tr.id = params.idTrabajador\n"
            + "ORDER BY nombre, h.fecha desc, h.hora_inicio DESC", nativeQuery = true)
    public List<Map> tecnicoVacaciones(Long idTrabajador);

    @Query(value = "SELECT h.id,\n"
            + "       TRIM(CONCAT(\n"
            + "		CONCAT(IFNULL(tr.nombre,''), ' ', IFNULL(tr.apellido1,''), ' ' , IFNULL(tr.apellido2,'')), \n"
            + "		CONCAT(IFNULL(te.nombre,''), ' ', IFNULL(te.apellidos,''))\n"
            + "       )) nombre, diaSemana,\n"
            + "       h.fecha\n"
            + "       FROM emp_horarios_trabajados h \n"
            + "       LEFT JOIN emp_trabajadores tr ON(tr.id=h.id_trabajador)\n"
            + "       LEFT JOIN emp_tecnicos te ON(te.id=h.id_tecnico)\n"
            + "       INNER JOIN (\n"
            + "		SELECT 2 d, 'Lunes' diaSemana UNION ALL\n"
            + "		SELECT 3 d, 'Martes' diaSemana UNION ALL\n"
            + "		SELECT 4 d, 'Miércoles' diaSemana UNION ALL\n"
            + "		SELECT 5 d, 'Jueves' diaSemana UNION ALL\n"
            + "		SELECT 6 d, 'Viernes' diaSemana UNION ALL\n"
            + "		SELECT 7 d, 'Sábado' diaSemana UNION ALL\n"
            + "		SELECT 1 d, 'Domingo' diaSemana \n"
            + "       ) tDias ON (DAYOFWEEK(h.fecha) = tDias.d)\n"
            + "INNER JOIN (SELECT :idTrabajador idTrabajador) params       \n"
            + "WHERE h.tipo_registro='Baja'\n"
            + "AND tr.id = params.idTrabajador\n"
            + "ORDER BY nombre, h.fecha desc, h.hora_inicio DESC", nativeQuery = true)
    public List<Map> tecnicoBaja(Long idTrabajador);    
    
    
    @Query(value = 
            "select * from ( " +
            "SELECT  DATE_FORMAT(ht.fecha, \"%d/%m/%Y\") 'fecha', "
            + "      SUM(IFNULL(ht.tiempo_minutos, ( SUBSTR(NOW(),12,2)-SUBSTR(ht.hora_inicio,1,2) )*60 + SUBSTR(NOW(),15,2) - SUBSTR(ht.hora_inicio,4,2))) 'Horas',\n" 
            + "      MIN(ht.hora_inicio ) hora_inicio, "
            + "      MAX(ht.hora_fin ) hora_fin, " +
        "            TRIM(CONCAT(\n" +
        "			CONCAT(IFNULL(t.nombre,''), ' ', IFNULL(t.apellido1,''), ' ', IFNULL(t.apellido2,'')),\n" +
        "			CONCAT(IFNULL(te.nombre,''), ' ', IFNULL(te.apellidos,''))\n" +
        "            )) nombre \n" +
        "    FROM emp_horarios_trabajados ht\n" +
        "    LEFT JOIN emp_trabajadores t ON (t.id= ht.id_trabajador)\n" +
        "    LEFT JOIN emp_tecnicos te ON(te.id= ht.id_tecnico)\n" +
        "    INNER JOIN (SELECT :idTrabajador idTrabajador, :idTecnico idTecnico, :fechaDesde fechaDesde, :fechaHasta fechaHasta) params\n" +
        "    WHERE(params.fechaDesde IS NULL OR ht.fecha >= params.fechaDesde)\n" +
        "    AND (params.fechaHasta IS NULL OR ht.fecha < DATE_ADD(params.fechaHasta,INTERVAL 1 DAY))\n" +
        "    AND	(params.idTrabajador IS NULL OR ht.id_trabajador = params.idTrabajador )\n" +
        "    AND	(params.idTecnico IS NULL OR ht.id_tecnico = params.idTecnico)\n" +
        "    GROUP BY ht.fecha\n" +
        "    ORDER BY ht.fecha DESC" +
           " ) tabla " +
            " UNION ALL " +

            "SELECT  '' fecha, "
            + "      SUM(IFNULL(ht.tiempo_minutos, ( SUBSTR(NOW(),12,2)-SUBSTR(ht.hora_inicio,1,2) )*60 + SUBSTR(NOW(),15,2) - SUBSTR(ht.hora_inicio,4,2))) 'Horas',\n" 
            + "      '' hora_inicio, "
            + "      '' hora_fin, " +
        "            'Total' nombre \n" +
        "    FROM emp_horarios_trabajados ht\n" +
        "    LEFT JOIN emp_trabajadores t ON (t.id= ht.id_trabajador)\n" +
        "    LEFT JOIN emp_tecnicos te ON(te.id= ht.id_tecnico)\n" +
        "    INNER JOIN (SELECT :idTrabajador idTrabajador, :idTecnico idTecnico, :fechaDesde fechaDesde, :fechaHasta fechaHasta) params\n" +
        "    WHERE(params.fechaDesde IS NULL OR ht.fecha >= params.fechaDesde)\n" +
        "    AND (params.fechaHasta IS NULL OR ht.fecha < DATE_ADD(params.fechaHasta,INTERVAL 1 DAY))\n" +
        "    AND	(params.idTrabajador IS NULL OR ht.id_trabajador = params.idTrabajador )\n" +
        "    AND	(params.idTecnico IS NULL OR ht.id_tecnico = params.idTecnico)\n" 
            , nativeQuery = true)
    public List<Map> tecnicoHorarioTrabajadoListadoPorFecha(Long idTrabajador,Long idTecnico, Date fechaDesde,Date fechaHasta);
        
    
}
