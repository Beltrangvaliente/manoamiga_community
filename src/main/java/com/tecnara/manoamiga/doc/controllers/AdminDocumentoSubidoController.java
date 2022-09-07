package com.tecnara.manoamiga.doc.controllers;


import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.dtos.EmailInfoDto;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.services.IEmailService;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.dto.DocumentoSubidoAlumnosDto;
import com.tecnara.manoamiga.doc.entities.AlumnoEntity;
import com.tecnara.manoamiga.doc.entities.DocumentoRequeridoEntity;
import com.tecnara.manoamiga.doc.entities.DocumentoSubidoEntity;
import com.tecnara.manoamiga.doc.entities.DocumentoSubidoFicheroEntity;
import com.tecnara.manoamiga.doc.repositories.IAlumnoRepository;
import com.tecnara.manoamiga.doc.repositories.IDocumentoRequeridoRepository;
import com.tecnara.manoamiga.doc.repositories.IDocumentoSubidoFicheroRepository;
import com.tecnara.manoamiga.doc.repositories.IDocumentoSubidoRepository;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import com.tecnara.manoamiga.expo.entities.ImagenEntity;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/doc/admin/documento_subido") 
public class AdminDocumentoSubidoController extends TecnaraBaseController{
    
    @Autowired
    private IEmailService mail;
    
    @Autowired
    private IEmailService alumno;
    

    @Autowired
    private IUtilidadesService utils;

    @Autowired
    private IDocumentoSubidoRepository repoDocumentoSubido;
    
    
    
    @Autowired
    private IDocumentoRequeridoRepository repoDocuReque;

    @Autowired
    private IAlumnoRepository repoAlumno;
    
 

    @GetMapping("/form")
    public String form(Model m) {
        m.addAttribute("registro", new DocumentoSubidoEntity());
        return "doc/admin/documento_subido_form";
    }
    
    @GetMapping("/formsinmenu")
    public String formsinmenu(Model m) {
        m.addAttribute("registro", new DocumentoSubidoEntity());
        m.addAttribute("sinmenu", true);
        return "doc/admin/documento_subido_form";
    }

    @GetMapping("/list")
    public String list(Model m) {

        return "doc/admin/documento_subido_list";
    }

    @GetMapping("/help")
    public String help(Model m) {

        return "doc/admin/documento_subido_help"; 
    }

    @PostMapping("/guardar")
    public String guardar(DocumentoSubidoEntity registro) {
        repoDocumentoSubido.save(registro);
        return "redirect:list";
    }
/*
    public DocumentoSubidoAlumnosDto convertirDto(DocumentoSubidoEntity origen) { 
        DocumentoSubidoAlumnosDto nuevo = new DocumentoSubidoAlumnosDto();
        nuevo.setFechaSubida(utils.formatearFecha(origen.getFechaSubida()));
        nuevo.setFechaLimite(utils.formatearFecha(origen.getFechaLimite()));
        nuevo.setVerificacion(origen.getVerificacion());

        AlumnoEntity origen2 = repoAlumno.findById(origen.getIdAlumno()).orElse(new AlumnoEntity());
        nuevo.setNombreAlumno(origen2.getNombre() + " " + origen2.getApellido1() + " " + origen2.getApellido2());

        DocumentoRequeridoEntity docReq=utils.findById(repoDocuReque, origen.getIdDocumentoRequerido(), DocumentoRequeridoEntity.class);
        nuevo.setTipoDocumento(docReq.getTipoDeDocumento());
        if (docReq.getDescripcion()!=null){
            nuevo.setTipoDocumento(docReq.getTipoDeDocumento() + " " + docReq.getDescripcion());
        }
        
        return nuevo; 
    }
    */
    @Autowired
    private IDocumentoSubidoRepository repoDocSub;
    
    @Autowired
    private IGeneral general;   //La información de sesion
    
    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listado(String filtro) {
        return repoDocumentoSubido.adminVerificacion(filtro);
    }
    
        
    @GetMapping("/list_data_plazo_finalizado")
    @ResponseBody
    public List<Map> listadoPlazoFinalizado(String filtro) {
        return repoDocumentoSubido.adminPlazoFinalizado(filtro);
    }

      
    
    @GetMapping("/borrar")
    public String borrar(Long id){
        repoDocumentoSubido.deleteById(id);
        return "redirect:list";
        
    }    

    @GetMapping("/verificacion")
    public String verificar(Model m) { 
        return "doc/admin/documento_subido_verificacion";
    }

    @GetMapping("/plazo_finalizado")
    public String plazoFinalizado(Model m) {
        return "doc/admin/documento_subido_plazo_finalizado";
    }    
    
  //  @GetMapping("/aceptar")
    //public String aceptar(Long id) {
      //  DocumentoSubidoEntity obj=repoDocumentoSubido.findById(id).get();
       // obj.setVerificacion("Aprobado");
        //repoDocumentoSubido.save(obj);
       // return "redirect:verificacion";
  //  }
   // @GetMapping("/rechazar")
   // public String rechazarEvento(Long id) {
      //  DocumentoSubidoEntity obj=repoDocumentoSubido.findById(id).get();
       // obj.setVerificacion("Rechazado");
      //  repoDocumentoSubido.save(obj);
       // return "redirect:verificacion";
    
   // }
    
    /*
    @PostMapping("/enviarmail")
    public String enviarmail(String persona, String documento, @DateTimeFormat (pattern="YYYY-mm-dd") Date fechaLimite, String nombreCurso) {
        repoDocumentoSubido.findById(id).get()){
        
        
        EmailInfoDto info = new EmailInfoDto();
        info.setNombre(nombre);
        info.setApellido1(apellido1);
        info.setApellido2(apellido2);
        info.setEmail(email);
        info.setCodigoUsuario(UUID.randomUUID().toString());
        info.setTituloEmail("Registro usuario");
        info.setFicheroEmail("act/usuario_registro_email");
        info.setEmail(usuario.getEmail());

        mail.enviarEmail(info);
        return "act/public/usuario_registro_confirmacion";
            return "redirect:";
        }
    }*/
        
    
    @Autowired
    private IDocumentoSubidoFicheroRepository repoDocuSubiFichero;
    
    @GetMapping(value = "/descargar", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    byte[] descargar(Long id, HttpServletResponse response) {
        try {
            String nombre=repoDocuSubiFichero.adminDescargar(id);
            if (nombre.length()==0){
                nombre="descarga.zip";
            }else{
                nombre+=".zip";
            }
            
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(bos);
            
            List<DocumentoSubidoFicheroEntity> ficheros=repoDocuSubiFichero.findByIdDocumentoSubido(id); 
            int pos=1;
            for (DocumentoSubidoFicheroEntity docFic:ficheros){
                zos.putNextEntry(new ZipEntry(pos + "_" + docFic.getNombreFichero()));
                zos.write(docFic.getDatos());
                zos.closeEntry();
            }
            zos.close();
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + nombre + "\""); ;
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }    
    
}
