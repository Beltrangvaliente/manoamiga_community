package com.tecnara.manoamiga.expo.repositories;

import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.act.entities.EvaluacionMonitorEntity;
import com.tecnara.manoamiga.expo.entities.CoordinadorEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICoordinadorRepository extends JpaRepository<CoordinadorEntity, Long> {

    public List<CoordinadorEntity> findByIdUsuario(Long idUsuario);

   
     @Query(value = "SELECT u.* \n"
            + "FROM aaa_usuarios u\n"
            + "LEFT JOIN expo_coordinadores tra ON (u.id=tra.id_usuario)\n"
            + "INNER JOIN (SELECT CONCAT('%',:filtro,'%') filtro) params\n"
            + "WHERE rol='ROLE_EXPO_Coordinador'\n"
            + "AND (tra.id IS NULL OR tra.id = :id )\n"
            + "AND (u.usuario LIKE params.filtro OR email LIKE params.filtro)", nativeQuery = true)

    public List<Map> coordinadorFromBuscador(String filtro, Long id);    

}
