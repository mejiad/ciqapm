package com.evoltech.ciqapm.utils;

import com.evoltech.ciqapm.model.CotizaServicio;

import java.util.ArrayList;
import java.util.List;

public class CotizaServiciosWrapper {

    private Long cotizaId;
    private List<CotizaServicio> servicios = new ArrayList<CotizaServicio>();

    public Long getCotizaId() {
        return cotizaId;
    }

    public void setCotizaId(Long cotizaId) {
        this.cotizaId = cotizaId;
    }


    public void addCotizaServicio(CotizaServicio cotizaServicios){
        this.servicios.add(cotizaServicios);
    }

    public List<CotizaServicio> getServicios() {
        return this.servicios;
    }

    public void setServicios(List<CotizaServicio> servicios) {
        this.servicios = servicios;
    }

}
