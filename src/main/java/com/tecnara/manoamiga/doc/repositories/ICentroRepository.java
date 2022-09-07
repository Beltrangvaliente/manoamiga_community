package com.tecnara.manoamiga.doc.repositories;

import com.tecnara.manoamiga.doc.entities.CentroEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICentroRepository extends JpaRepository<CentroEntity, Long> {

    public List<CentroEntity> findByNombreContainingIgnoreCase(String nombre);

    @Query(value = "SELECT cu.id, cu.nombre_curso nombreCurso, cu.modalidad modalidad, cen.nombre nombreCentro, cu.horario horario, cu.numero_horas  numeroHoras, cu.fecha_inicio fechaInicio, cu.fecha_fin fechaFin\n"
            + "FROM doc_cursos cu\n"
            + "LEFT JOIN doc_centros cen ON (cen.id = cu.id_centro)\n"
            + "LEFT JOIN doc_empresas em ON (cu.id_empresa = em.id)\n"
            + "INNER JOIN(SELECT CONCAT ('%',:filtro,'%') filtro ) params\n"
            + "WHERE cu.nombre_curso LIKE params.filtro OR cen.nombre LIKE params.filtro", nativeQuery = true)
    public List<Map> adminCursoList(String filtro);
    //public List<AlumnoEntity> findByNombreContainingIgnoreCaseOrApellido1ContainingIgnoreCaseOrApellido2ContainingIgnoreCase

    //public List<CentroEntity> findByNombreContainingIgnoreCase(String nombre);
    @Query(value = "SELECT c.id,c.nombre,c.direccion,c.horario_atencion horarioAtencion ,c.contacto_telefono contactoTelefono\n"
            + "FROM doc_centros c\n"
            
            + "INNER JOIN (SELECT  CONCAT('%',:filtro,'%') filtro)params\n"
            + "WHERE (c.nombre LIKE params.filtro OR c.direccion LIKE params.filtro \n"
            + "OR c.horario_atencion LIKE params.filtro OR contacto_telefono LIKE params.filtro)", nativeQuery = true)

    public List<Map> publicCentroList(String filtro);

    @Query(value = "SELECT al.id_usuario,al.nombre, al.apellido1, al.apellido2,al.mail,al.telefono \n"
            + "FROM doc_alumnos al\n"
            + "INNER JOIN doc_matriculas ma ON (ma.id_alumno=al.id)\n"
            + "INNER JOIN doc_cursos cu ON (ma.id_curso=cu.id)\n"
            + "INNER JOIN doc_profesores_cursos pc ON (pc.id_profesor=al.id)\n"
            + "INNER JOIN(SELECT CONCAT('%',:filtro,'%')filtro) params \n"
            + "WHERE (ma.id_alumno LIKE params.filtro OR pc.id_profesor LIKE params.filtro)", nativeQuery = true)

    public List<Map> profesorShow(Long filtro);

}
