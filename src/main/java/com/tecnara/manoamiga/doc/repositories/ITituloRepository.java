package com.tecnara.manoamiga.doc.repositories;

import com.tecnara.manoamiga.doc.entities.TituloEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ITituloRepository extends JpaRepository<TituloEntity, Long> {

    public List<TituloEntity> findByIdAlumno(Long idAlumno);

    public List<TituloEntity> findByTitulacionContainingIgnoreCase(String titulacion);

    @Query(value = "SELECT CONCAT(a.nombre,' ', a.apellido1,' ', a.apellido2) alumno, t.titulacion, t.nivel\n"
            + "FROM doc_titulos t\n"
            + "LEFT JOIN doc_alumnos a ON(t.id_alumno=a.id)\n"
            + "INNER JOIN(SELECT CONCAT('%', :filtro, '%') filtro) param\n"
            + "WHERE (a.nombre LIKE param.filtro OR a.apellido1 LIKE param.filtro OR a.apellido2 LIKE param.filtro \n"
            + "	OR t.titulacion LIKE param.filtro OR t.nivel LIKE param.filtro)", nativeQuery = true)

    public List<Map> adminTituloList(String filtro);

}
