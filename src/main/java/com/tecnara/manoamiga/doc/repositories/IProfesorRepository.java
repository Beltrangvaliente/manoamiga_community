package com.tecnara.manoamiga.doc.repositories;


import com.tecnara.manoamiga.doc.entities.ProfesorEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("act_profesor_repository")
public interface IProfesorRepository extends JpaRepository<ProfesorEntity, Long> {

    public List<ProfesorEntity> findByNombreProfContainingIgnoreCaseOrApellido1ProfContainingIgnoreCaseOrApellido2ProfContainingIgnoreCase(String nombreProf, String apellido1Prof, String apellido2Prof);

    @Query(value = "SELECT p.id, u.usuario usuario, p.nombre_prof nombreProf, p.apellido1prof apellido1Prof, p.apellido2prof apellido2Prof\n"
            + "FROM  doc_profesor p\n"
            + "LEFT JOIN   aaa_usuarios u ON(u.id =p.id_usuario)\n"
            + "INNER JOIN (SELECT CONCAT ('%', :filtro,'%') filtro )params\n"
            + "WHERE p.nombre_prof LIKE params.filtro \n"
            + "      OR p.apellido1prof LIKE params.filtro \n"
            + "      OR p.apellido2prof LIKE params.filtro \n"
            + "      OR u.usuario LIKE params.filtro ", nativeQuery = true)
    public List<Map> adminList(String filtro);

    public List<ProfesorEntity> findByIdUsuario(Long id);



}
