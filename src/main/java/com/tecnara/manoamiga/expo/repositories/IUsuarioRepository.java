package com.tecnara.manoamiga.expo.repositories;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.act.entities.AdministradorEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


/*        
public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    public List<UsuarioEntity> findByUsuarioContainingIgnoreCaseOrEmailContainingIgnoreCase(String usuario, String email);

    public List<UsuarioEntity> findByUsuarioContainingIgnoreCase(String usuario);

    public List<UsuarioEntity> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE aaa_usuarios u\n"
            + "SET u.password=:password\n"
            + "WHERE u.codigo_verificacion = :codigoVerificacion", nativeQuery = true)
    public int cambiarPassword(String password, String codigoVerificacion);

    public Optional<UsuarioEntity> findByUsuario(String usuario);

    public Optional<UsuarioEntity> findByCodigoVerificacion(String codigoVerificacion);

    @Modifying
    @Transactional
    @Query(value = "UPDATE aaa_usuarios\n"
            + "SET estado=:estado\n"
            + "WHERE codigo_verificacion = :codigoVerificacion", nativeQuery = true)

    public int updateEstadoByCodigoVerificacion(String estado, String codigoVerificacion);
    
       @Modifying
    @Transactional
    @Query(value = "UPDATE aaa_usuarios\n"
            + "SET estado=:estado\n"
            + "WHERE codigo_verificacion = :codigoVerificacion", nativeQuery = true)

    public int validarEstadoByCodigoVerificacion(String estado, String codigoVerificacion);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM  aaa_usuarios u\n"
            + "WHERE  u.codigo_verificacion=:codigoVerificacion"
            +"AND estado='Pendiente de virificacion'\n", nativeQuery = true)

    public int deleteByCodigoVerificacion(String codigoVerificacion);

    @Query(value = "SELECT a.id, a.usuario,a.estado,a.rol\n"
            + "FROM aaa_usuarios a\n"
            + "INNER JOIN(SELECT :estado estado,CONCAT('%', :usuario, '%')usuario, :rol rol )params\n"
            + "WHERE (params.estado='Todos' OR a.estado = params.estado) \n"
            + "AND (a.usuario LIKE params.usuario) AND (params.rol='Todos' OR a.rol=params.rol)", nativeQuery = true)
    public List<Map> adminListData(String estado, String usuario, String rol);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM  aaa_usuarios u\n"
            + "WHERE  u.codigo_verificacion=:codigoVerificacion"
            +"AND estado='Pendiente de virificacion'\n", nativeQuery = true)

    public int cancelarByCodigoVerificacion( String codigoVerificacion );
    
    
}*/
