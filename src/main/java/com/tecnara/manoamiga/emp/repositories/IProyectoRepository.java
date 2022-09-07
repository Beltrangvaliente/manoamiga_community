package com.tecnara.manoamiga.emp.repositories;

import com.tecnara.manoamiga.emp.entities.ProyectoEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.hibernate.query.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("emp_proyecto_repository")
public interface IProyectoRepository extends JpaRepository<ProyectoEntity, Long> {

    @Query(value = "SELECT emp.nombre, emp.fecha_inicio fechaInicio, emp.fecha_fin fechaFin, emp.id\n"
            + "FROM emp_proyectos emp", nativeQuery = true)

    public List<Map> listProyecto(String nombre, Date fechaInicio, Date fechaFin);

    @Query(value = "SELECT p.id,p.nombre, p.estado, p.fecha_inicio, p.fecha_fin, e.nombre empresa\n"
            + " FROM emp_proyectos p\n"
            + " LEFT JOIN emp_empresas e ON(e.id=p.id_empresa)\n"
            + " INNER JOIN (SELECT CONCAT('%',:filtro,'%') filtro, :fechaDesde fecha_desde, :fechaHasta fecha_hasta, :idEmpresa id_empresa)params\n"
            + " WHERE p.nombre LIKE params.filtro\n"
            + " AND (params.fecha_desde IS NULL OR p.fecha_inicio) \n"
            + " AND (params.fecha_hasta IS NULL OR p.fecha_fin )\n"
            + " AND (params.id_empresa IS NULL OR params.id_empresa = p.id_empresa )\n", nativeQuery = true)
    public List<Map> tecnicoList(String filtro, Date fechaDesde, Date fechaHasta, Long idEmpresa);

    @Query(value = "SELECT  par.id, par.nombre nombre, par.apellido1 apellido1 , par.apellido2 apellido2 , par.telefono telefono, par.email email\n"
            + "FROM emp_participantes par", nativeQuery = true)

    public List<Map> listParticipante(String nombre, String apellido1, String apellido2, String telefono, String email);

    @Query(value = "SELECT tCursos.*, CONCAT(par.nombre, ' ', par.apellido1, ' ', par.apellido2) participante\n"
            + "FROM (\n"
            + "	SELECT *, \n"
            + "		(SELECT IF(COUNT(*)>0,'Si','No')\n"
            + "			FROM  emp_formaciones_participantes fpar \n"
            + "			INNER JOIN emp_formaciones form ON (form.id = fpar.id_formacion)\n"
            + "			WHERE fpar.id_participante=ppar.id_participante \n"
            + "			AND form.id_proyecto = ppar.id_proyecto\n"
            + "			\n"
            + "		) cursos_activos\n"
            + "	FROM emp_proyectos_participantes ppar\n"
            + ") tCursos\n"
            + "INNER JOIN emp_participantes par ON (tCursos.id_participante=par.id)\n"
            + "INNER JOIN (SELECT :cursoActivo curso_activos, :idProyecto id_proyecto) params\n"
            + "WHERE (params.curso_activos='Todos' OR tCursos.cursos_activos = params.curso_activos)\n"
            + "AND tCursos.id_proyecto = params.id_proyecto", nativeQuery = true)
    public List<Map> tecnicoFormProyectoParticipantes(Long idProyecto, String cursoActivo);

    @Query(value = "SELECT f.id, f.nombre, f.fecha_inicio fechaInicio, f.fecha_fin fechaFin, f.estado\n"
            + "FROM emp_formaciones f\n"
            + "INNER JOIN (SELECT :idProyecto id_proyecto) params\n"
            + "WHERE params.id_proyecto = f.id_proyecto\n", nativeQuery = true)
    public List<Map> tecnicoFormProyectoFormaciones(Long idProyecto);

   
    @Query(value = "SELECT p.id_empresa, p.estado ,p.explicacion_html ,p.fecha_fin,p.fecha_inicio,p.nombre, p.id_imagen, p.mover_izda moverIzda\n"
            + "FROM emp_proyectos p\n"
            + "\n"
            + "WHERE estado= 'activo' AND Aceptar='si' "
            + "ORDER BY fecha_inicio desc", nativeQuery = true)
    public List<Map> publicListProyecto();

    public List<ProyectoEntity> findByNombreContaining(String filtro);

    @Query(value = "SELECT dp.id_fichero, dp.id, dp.nombre_documento, dp.fecha_subido\n"
            + "FROM emp_documentos_proyectos dp\n"
            + "INNER JOIN (SELECT :idProyecto id_proyecto)params\n"
            + "WHERE dp.id_proyecto=params.id_proyecto", nativeQuery = true)
    public List<Map> publicListDocumentacion(Long idProyecto);

}
