package com.tecnara.manoamiga.act.repositories;

import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.act.entities.AdministradorEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IAdministradorRepository extends JpaRepository<AdministradorEntity, Long> {

    public List<AdministradorEntity> findByIdUsuario(Long idUsuario);

    @Query(value = "SELECT u.* \n"
            + "FROM aaa_usuarios u\n"
            + "LEFT JOIN act_administradores a ON (u.id=a.id_usuario)\n"
            + "INNER JOIN (SELECT CONCAT('%',:filtro,'%') filtro) params\n"
            + "WHERE rol='ROLE_ACT_Admin'\n"
            + "AND (a.id IS NULL OR a.id = :id )\n"
            + "AND (u.usuario LIKE params.filtro OR email LIKE params.filtro)", nativeQuery = true)
    public List<Map> adminAdministradorFromBuscador(String filtro, Long id);     

    @Query(value="select a.id, a.nombre, a.apellido1, a.apellido2, u.usuario, u.email from act_administradores a left join aaa_usuarios u on (u.id=a.id_usuario)", nativeQuery = true)
    public List<Map> adminList();  

}
