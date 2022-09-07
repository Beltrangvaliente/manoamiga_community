package com.tecnara.manoamiga.doc.repositories;

import com.tecnara.manoamiga.doc.entities.AlumnoNecesidadFormacionEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author tecnara
 */
public interface IAlumnoNecesidadFormacionRepository extends JpaRepository<AlumnoNecesidadFormacionEntity, Long> {

    public List<AlumnoNecesidadFormacionEntity> findByIdAlumno(long idAlumno);

    public List<AlumnoNecesidadFormacionEntity> findByIdNecesidadFormacion(long idNecesidadFormacion);
    @Query(value = "SELECT anf.id,anf.id_alumno, nf.nombre, nf.idioma, nf.edad\n"
            + "FROM doc_necesidades_formacion nf\n"
            + "LEFT JOIN doc_alumnos_necesidades_formacion anf ON(nf.id= anf.id_necesidad_formacion)\n"
            + "LEFT JOIN doc_alumnos a ON(a.id=anf.id_alumno)\n"
            + "INNER JOIN(SELECT :idAlumno idAlumno)param\n"
            + "WHERE anf.id_alumno=param.idAlumno;", nativeQuery = true)
    public List<Map> adminFormNecesidadFormacion(Long idAlumno); 
}


