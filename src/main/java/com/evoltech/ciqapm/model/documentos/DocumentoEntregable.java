package com.evoltech.ciqapm.model.documentos;

import com.evoltech.ciqapm.model.Entregable;
import com.evoltech.ciqapm.model.Etapa;
import com.evoltech.ciqapm.model.Proyecto;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class DocumentoEntregable extends DocumentoAbstract {

    @ManyToOne
    private Proyecto proyecto;

    @ManyToOne
    private Etapa etapa;

    @ManyToOne
    private Entregable entregable;
}
