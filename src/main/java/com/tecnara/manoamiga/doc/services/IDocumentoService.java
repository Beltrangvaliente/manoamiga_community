
package com.tecnara.manoamiga.doc.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public interface IDocumentoService {
    public void guardarDocumento(Long idFicheroSubido, MultipartFile file);
}
