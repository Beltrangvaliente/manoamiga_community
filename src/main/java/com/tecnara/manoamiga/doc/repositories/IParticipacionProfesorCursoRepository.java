package com.tecnara.manoamiga.doc.repositories;

import com.tecnara.manoamiga.doc.entities.ParticipacionProfesorCursoEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IParticipacionProfesorCursoRepository extends JpaRepository<ParticipacionProfesorCursoEntity, Long>{
    
    public Optional<ParticipacionProfesorCursoEntity> findByIdProfesorAndIdCurso(Long idProfesor, Long idCurso);
    
    @Query(value = "SELECT  c.documento_titulo documentoTitulo,c.numero_alumnos numeroAlumnos, c.fecha_actualizacion fechaActualizacion, c.fecha_inicio fechaInicio, c.fecha_limite_inscripcion fechaLimiteInscripcion, c.horario , c.localidad, c.modalidad, c.nombre_curso nombreCurso, c.precio, c.referencia, c.requisitos, c.numero_horas numeroHoras\n"
            + "FROM doc_profesor p\n"
            + "INNER JOIN doc_profesores_cursos pc ON (p.id = pc.id_profesor)\n"
            + "INNER JOIN doc_cursos c ON ( c.id = pc.id_curso)\n"
            + "\n"
            + "INNER JOIN (SELECT :idCurso idCurso, :idProfesor idProfesor)params\n"
            + "WHERE pc.id_profesor =params.idProfesor "
            + " AND c.id = params.idCurso" , nativeQuery = true
    )
    
    public Optional<Map> profesorShow(Long idCurso, Long idProfesor);
    
    
    
    
}
