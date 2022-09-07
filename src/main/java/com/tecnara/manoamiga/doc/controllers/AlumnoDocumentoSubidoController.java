package com.tecnara.manoamiga.doc.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.entities.DocumentoRequeridoEntity;
import com.tecnara.manoamiga.doc.entities.DocumentoSubidoEntity;
import com.tecnara.manoamiga.doc.entities.DocumentoSubidoFicheroEntity;
import com.tecnara.manoamiga.doc.repositories.IDocumentoRequeridoRepository;
import com.tecnara.manoamiga.doc.repositories.IDocumentoSolicitadoRepository;
import com.tecnara.manoamiga.doc.repositories.IDocumentoSubidoFicheroRepository;
import com.tecnara.manoamiga.doc.repositories.IDocumentoSubidoRepository;
import com.tecnara.manoamiga.doc.services.IDocumentoService;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import com.tecnara.manoamiga.expo.entities.NoticiaEntity;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/doc/alumno/documento_subido")
public class AlumnoDocumentoSubidoController extends TecnaraBaseController {

    @Autowired
    private IUtilidadesService utils;

    @Autowired
    private IDocumentoSubidoRepository repoDocuSubi;

    @Autowired
    private IDocumentoSubidoFicheroRepository repoDocuSubiFichero;

    @Autowired
    private IDocumentoSolicitadoRepository repoDocuSoli;

    @Autowired
    private IDocumentoRequeridoRepository repoDocuReque;

    @Autowired
    private IGeneral general;

    @Autowired
    private IDocumentoService docuService;

    @GetMapping("/form")
    public String form(Model m, Long id) {
        //id es el idDocumentoSolicitado
        m.addAttribute("documento", repoDocuSoli.alumnoBuscadorId(id));
        m.addAttribute("idDocumentoRequerido", id);
        return "doc/alumno/documento_subido_form";
    }

    @GetMapping("/list")
    public String list() {
        return "doc/alumno/documento_subido_list";
    }

    @GetMapping("/show")
    public String show(Model m, Long id) {
        m.addAttribute("registro", repoDocuSubi.alumnoShow(id, general.getIdValidado()));
        return "doc/alumno/documento_subido_show";
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData(String verificacion, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) {
        return repoDocuSubi.alumnoDocumentoSubidoList(verificacion, fechaDesde, fechaHasta);

    }

    @GetMapping("/borrar")
    public String borrar(Long id, String verificacion) {
        if (repoDocuSubi.findByIdAndIdAlumnoAndVerificacionNot(id, general.getIdValidado(), "pendiente").isEmpty()) {
            return "redirect:list?error=No se puede borrar";
        } else if (repoDocuSubi.findByIdAndIdAlumnoAndVerificacionNot(id, general.getIdValidado(), "aceptado").isEmpty()) {
            return "redirect:list?error=No se puede borrar";
        } else {
            repoDocuSubi.deleteById(id);
        }
        return "redirect:list";
    }

    @PostMapping("/subir_fichero")
    public String subirFicheros(MultipartFile fichero1, MultipartFile fichero2, MultipartFile fichero3) {

        return "redirect:list";
    }

    
    @PostMapping("/guardar")
    public String guardar(Long idDocumentoSolicitado, MultipartFile fichero[]) {

        if (fichero != null && fichero.length>0) {
            DocumentoSubidoEntity doc=new DocumentoSubidoEntity();
            doc.setFechaSubida(general.getFechaActual());
            doc.setIdAlumno(general.getIdValidado());
            doc.setVerificacion("Pendiente");
            doc=repoDocuSubi.save(doc);

            for (MultipartFile f:fichero){
                docuService.guardarDocumento(doc.getId(), f);    
            }

        }
        return "redirect:list";
    }

    @GetMapping(value = "/descargar", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    byte[] descargar(Long id) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(bos);

            List<DocumentoSubidoFicheroEntity> ficheros = repoDocuSubiFichero.findByIdDocumentoSubido(id);
            int pos = 1;
            for (DocumentoSubidoFicheroEntity docFic : ficheros) {
                zos.putNextEntry(new ZipEntry(pos + "_" + docFic.getNombreFichero()));
                zos.write(docFic.getDatos());
                zos.closeEntry();
            }
            zos.close();
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
