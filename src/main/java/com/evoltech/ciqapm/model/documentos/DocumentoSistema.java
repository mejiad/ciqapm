package com.evoltech.ciqapm.model.documentos;

import com.evoltech.ciqapm.model.TipoDocumentoSistema;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class DocumentoSistema extends DocumentoAbstract {

    private TipoDocumentoSistema tipoDocumentoSistema;

    private LocalDate inicioVigencia;

    private LocalDate finVigencia;

}
