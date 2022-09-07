
package com.tecnara.manoamiga.doc.repositories;


import com.tecnara.manoamiga.doc.entities.AsistenciaEntity;
import com.tecnara.manoamiga.doc.entities.ParticipacionProfesorCursoEntity;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IAsistenciaRepository extends JpaRepository<AsistenciaEntity, Long> {

    public List<AsistenciaEntity> findByIdAlumno(Long idAlumno);


    public List<AsistenciaEntity> findByIdCurso(Long idCurso);
    
      public Optional<AsistenciaEntity> findByIdAlumnoAndIdCursoAndFecha(Long idAlumno, Long idCurso, Date fecha);

    @Query(value = "   SELECT alum.nombre , alum.apellido1, alum.apellido2 \n"
            + "     FROM doc_alumnos alum\n"
            + "     INNER JOIN doc_asistencias asis ON(alum.id=asis.id_alumno)\n"
            + "     INNER JOIN doc_cursos cur ON(cur.id=asis.id_curso)\n"
            + "     INNER JOIN doc_profesores_cursos dpc ON(dpc.id_curso= cur.id)\n"
            + "     INNER JOIN (SELECT :idCurso id_curso, :idProfesor id_profesor)params\n"
            + "     WHERE dpc.id_profesor = params.id_profesor "
            + "     AND cur.id=params.id_curso "
            + "     AND asis.fecha=CURDATE() ", nativeQuery = true)
            public List<Map> profesorPasarLista(Long idCurso, Long idProfesor); 
    
    @Query(value = "SELECT c.id curso, pc.id_profesor profesor\n"
            + "\n"
            + "FROM doc_alumnos a\n"
            + "INNER JOIN doc_asistencias asis ON(a.id=asis.id_alumno)\n"
            + "INNER JOIN doc_cursos c ON(asis.id_curso=c.id)\n"
            + "INNER JOIN doc_profesores_cursos pc ON(pc.id_curso=c.id)\n"
            + "INNER JOIN (SELECT CONCAT ('%', :filtro,'%')filtro, :idProfesor id_profesor) params\n"
            + "WHERE pc.id_profesor = params.id_profesor\n"
            + "AND c.nombre_curso LIKE params.filtro", nativeQuery = true)
    public List<Map> buscador(String filtro, Long idProfesor);
    
    
    @Modifying
    @Transactional
    public void deleteByIdAlumnoAndIdCursoAndFecha(Long idAlumno, Long idCurso, Date fecha);
    
}
