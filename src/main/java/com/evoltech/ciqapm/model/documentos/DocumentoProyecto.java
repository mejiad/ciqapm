package com.evoltech.ciqapm.model.documentos;

import com.evoltech.ciqapm.model.Proyecto;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class DocumentoProyecto extends DocumentoAbstract{

    @ManyToOne
    private Proyecto proyecto;

    private String version;

}
