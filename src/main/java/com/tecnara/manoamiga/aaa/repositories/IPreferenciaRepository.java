package com.tecnara.manoamiga.aaa.repositories;

import com.tecnara.manoamiga.aaa.entities.PreferenciaEntity;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.act.entities.MonitorEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface IPreferenciaRepository extends JpaRepository<PreferenciaEntity, Long> {

    public Optional<PreferenciaEntity> findByClave(String clave);

    public Optional<PreferenciaEntity> findByClaveAndPerfil(String clave, String perfil);

    @Query(value = "SELECT id,clave,perfil,tipo,valor\n" +
"FROM aaa_preferencias \n" +
"  INNER JOIN(SELECT CONCAT('%',:filtro,'%') filtro) param \n" +
"   WHERE (clave LIKE param.filtro OR perfil LIKE param.filtro OR tipo LIKE param.filtro \n" +
"    OR valor LIKE param.filtro)", nativeQuery = true)

    public List<Map> preferList(String filtro );

    
    
}
