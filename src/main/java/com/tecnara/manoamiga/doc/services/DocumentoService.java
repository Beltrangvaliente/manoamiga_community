
package com.tecnara.manoamiga.doc.services;

import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.entities.DocumentoSubidoFicheroEntity;
import com.tecnara.manoamiga.doc.repositories.IDocumentoSubidoFicheroRepository;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentoService implements IDocumentoService{

    @Autowired
    private IDocumentoSubidoFicheroRepository repoDocuFichero;
    
    @Autowired
    private IGeneral general;
    
    @Override
    public void guardarDocumento(Long idFicheroSubido, MultipartFile file) {

        if (file.isEmpty() == false && file.isEmpty()==false) {
            try {
                DocumentoSubidoFicheroEntity docSubido=new DocumentoSubidoFicheroEntity();
                docSubido.setIdDocumentoSubido(idFicheroSubido);
                docSubido.setIdUsuarioVerificacion(general.getIdValidado());
                docSubido.setFechaSubido(general.getFechaActual());
                
                docSubido.setDatos(file.getBytes());
                docSubido.setNombreFichero(file.getOriginalFilename());
                
                repoDocuFichero.save(docSubido);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
