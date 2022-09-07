package com.tecnara.manoamiga.act.repositories;

import com.tecnara.manoamiga.act.entities.TutorEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ITutorRepository extends JpaRepository<TutorEntity, Long>{
    public List <TutorEntity> findByidUsuario(Long idUsuario);
    public List <TutorEntity> findByNombreContainingIgnoreCase(String nombre);
    
    @Query(value = "SELECT u.* \n"
            + "FROM aaa_usuarios u\n"
            + "LEFT JOIN act_tutores a ON (u.id=a.id_usuario)\n"
            + "INNER JOIN (SELECT CONCAT('%',:filtro,'%') filtro) params\n"
            + "WHERE rol='ROLE_ACT_Tutor'\n"
            + "AND (a.id IS NULL OR a.id = :id )\n"
            + "AND (u.usuario LIKE params.filtro OR email LIKE params.filtro)", nativeQuery = true)
    public List<Map> adminTutorFromBuscador(String filtro, Long id); 

    @Query(value = "SELECT count(1) \n"
            + "FROM aaa_usuarios u\n"
            + "INNER JOIN act_tutores a ON (u.id=a.id_usuario)\n"
            + "WHERE rol='ROLE_ACT_Tutor'\n"
            + "AND u.estado='Pendiente'", nativeQuery = true)
    public Long adminContarUsuariosPendientes();
}
