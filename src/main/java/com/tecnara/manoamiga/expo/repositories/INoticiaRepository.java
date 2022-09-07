package com.tecnara.manoamiga.expo.repositories;

import com.tecnara.manoamiga.expo.entities.NoticiaEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface INoticiaRepository extends JpaRepository<NoticiaEntity, Long> {

    public List<NoticiaEntity> findByIdArea(Long idArea);

    public List<NoticiaEntity> findByIdEspacio(Long idEspacio);

    public List<NoticiaEntity> findByIdTrabajador(Long idTrabajador);

    public List<NoticiaEntity> findByIdImagen1(Long idImagen1);

    public List<NoticiaEntity> findByIdImagen2(Long idImagen2);

    public List<NoticiaEntity> findByIdImagen3(Long idImagen3);

    public List<NoticiaEntity> findByPantalla(String pantalla);

    public List<NoticiaEntity> findByPantallaOrPantalla(String pantalla, String pantalla2);

    public List<NoticiaEntity> findByEstadoContainingOrderByFechaInicioDesc(String estado);

    @Query(value = "SELECT * \n"
            + "FROM expo_noticias n\n"
            + "INNER JOIN (SELECT :idNoticia idNoticia )param\n"
            + "WHERE n.id=param.idNoticia", nativeQuery = true)
    public List<NoticiaEntity> publicNoticiasPlantilla(Long idNoticia);

    @Query(value = "SELECT numero_imagenes, numero_textos \n"
            + "FROM expo_plantillas\n"
            + "WHERE id = :idPlantilla\n"
            + "LIMIT 1", nativeQuery = true)
    public Optional<Map> coordinadorFormInfoPlantilla(Long idPlantilla);

    @Query(value = "SELECT numero_imagenes, numero_textos \n"
            + "FROM expo_plantillas\n"
            + "WHERE id = :idPlantilla\n"
            + "LIMIT 1", nativeQuery = true)
    public Optional<Map> trabajadorFormInfoPlantilla(Long idPlantilla);

    @Query(value = "SELECT n.* \n"
            + "FROM expo_noticias n\n"
            + "INNER JOIN (SELECT :estado estado, CONCAT('%',:filtro,'%') filtro, :fechaDesde fecha_desde, :fechaHasta fecha_hasta) param\n"
            + "WHERE (param.estado='' OR n.estado=param.estado) \n"
            + "AND n.titulo LIKE param.filtro\n"
            + "AND (param.fecha_desde IS NULL OR param.fecha_desde <= n.fecha_fin) \n"
            + "AND (param.fecha_hasta IS NULL OR param.fecha_hasta >= n.fecha_inicio)\n"
            + "ORDER BY n.fecha_inicio", nativeQuery = true)
    public List<NoticiaEntity> coordinadorList(String estado, String filtro, Date fechaDesde, Date fechaHasta);

    public List<NoticiaEntity> findByTituloContaining(String filtro);
}
