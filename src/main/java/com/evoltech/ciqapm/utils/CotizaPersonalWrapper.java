package com.evoltech.ciqapm.utils;

import com.evoltech.ciqapm.model.CotizaPersonal;
import com.evoltech.ciqapm.model.CotizaServicio;

import java.util.ArrayList;
import java.util.List;

public class CotizaPersonalWrapper {

    private Long cotizaId;
    private List<CotizaPersonal> personal = new ArrayList<CotizaPersonal>();

    public Long getCotizaId() {
        return cotizaId;
    }

    public void setCotizaId(Long cotizaId) {
        this.cotizaId = cotizaId;
    }


    public void addCotizaPersonal(CotizaPersonal cotizaPersonal){
        this.personal.add(cotizaPersonal);
    }

    public List<CotizaPersonal> getPersonal() {
        return this.personal;
    }

    public void setPersonal(List<CotizaPersonal> personal) {
        this.personal = personal;
    }

}
