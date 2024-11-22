package com.evoltech.ciqapm.utils;

import com.evoltech.ciqapm.model.CotizaServicio;
import com.evoltech.ciqapm.model.Etapa;
import com.evoltech.ciqapm.model.EtapaServicio;
import com.evoltech.ciqapm.model.Servicio;

import java.util.ArrayList;
import java.util.List;

public class EtapaServicioWrapper {

    private Long etapaId;
    private List<EtapaServicio> servicios = new ArrayList<EtapaServicio>();

    public Long getEtapaId() {
        return etapaId;
    }

    public void setEtapaId(Long etapaId) {
        this.etapaId = etapaId;
    }

    public void addEtapaServicio(EtapaServicio etapaServicio){
        this.servicios.add(etapaServicio);
    }

    public List<EtapaServicio> getServicios() {
        return this.servicios;
    }

    public void setServicios(List<EtapaServicio> servicios) {
        this.servicios = servicios;
    }

}
