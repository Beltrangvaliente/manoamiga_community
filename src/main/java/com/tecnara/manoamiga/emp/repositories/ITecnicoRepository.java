package com.tecnara.manoamiga.emp.repositories;

import com.tecnara.manoamiga.emp.entities.TecnicoEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ITecnicoRepository extends JpaRepository<TecnicoEntity, Long> {

    public List<TecnicoEntity> findByNombreContainingOrApellidosContaining(String nombre, String apellidos);

    @Query(value = "SELECT t.nombre nombre,t.apellidos apellidos,t.email email,t.telefono telefono,t.id_usuario idUsuario, t.id id\n"
            + "FROM emp_tecnicos t\n"
            + "INNER JOIN (SELECT  CONCAT('%',:filtro,'%') filtro)params\n"
            + "WHERE t.nombre  LIKE params.filtro \n"
            + "OR t.apellidos LIKE params.filtro", nativeQuery = true)
    public List<Map> tecnicoList(String filtro);

    public List<TecnicoEntity> findByIdUsuario(Long id);

    @Query(value = "SELECT * \n"
            + "FROM emp_tecnicos\n"
            + "WHERE recibir_email_registro_participante = 'Si'", nativeQuery = true)
    public List<TecnicoEntity> enviarcorreo();

    @Query(value = "SELECT u.* \n"
            + "FROM aaa_usuarios u\n"
            + "LEFT JOIN emp_tecnicos tra ON (u.id=tra.id_usuario)\n"
            + "INNER JOIN (SELECT CONCAT('%',:filtro,'%') filtro) params\n"
            + "WHERE rol='ROLE_EMP_Tecnico'\n"
            + "AND (tra.id IS NULL OR tra.id = :id )\n"
            + "AND (u.usuario LIKE params.filtro OR u.email LIKE params.filtro)", nativeQuery = true)

    public List<Map> tecnicoFromBuscador(String filtro, Long id); 
    

}
