package com.evoltech.ciqapm.service;

import com.evoltech.ciqapm.model.Documento;
import com.evoltech.ciqapm.model.Proyecto;
import com.evoltech.ciqapm.model.TipoDocumento;
import com.evoltech.ciqapm.repository.DocumentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;

@Service
public class LoadDocumentService {
    Logger logger = LoggerFactory.getLogger(LoadDocumentService.class.getName());

    @Autowired
    DocumentoRepository documentoRepository;

    @Autowired
    StorageService storageService;

    public LoadDocumentService(DocumentoRepository documentoRepository, StorageService storageService) {
        this.documentoRepository = documentoRepository;
        this.storageService = storageService;
    }

    public Documento loadDocumento(Proyecto proyecto,
                             String filename,
                             TipoDocumento tipoDocumento,
                             byte[] contenido){
        logger.info("Carga de documento");

        Documento documento = new Documento();
        documento.setNombreArchivo(filename);
        documento.setDescripcion("Carga automática");
        documento.setNombre(filename);
        documento.setProyecto(proyecto);
        documento.setTipoDocumento(tipoDocumento);
        documento.setNombreArchivo(filename);
        documento.setData(contenido);
        Documento doc = documentoRepository.save(documento);
        logger.info("Carga de documento terminada");
        return doc;
    }

}
