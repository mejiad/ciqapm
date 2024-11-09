package com.evoltech.ciqapm.utils;

import com.evoltech.ciqapm.model.CotizaEmpleado;

import java.util.ArrayList;
import java.util.List;

public class CotizaEmpleadoWrapper {

    private Long cotizaId;
    private List<CotizaEmpleado> empleados = new ArrayList<CotizaEmpleado>();

    public Long getCotizaId() {
        return cotizaId;
    }

    public void setCotizaId(Long cotizaId) {
        this.cotizaId = cotizaId;
    }


    public void addCotizaEmpleado(CotizaEmpleado cotizaEmpleado){
        this.empleados.add(cotizaEmpleado);
    }

    public List<CotizaEmpleado> getEmpleados() {
        return this.empleados;
    }

    public void setEmpleados(List<CotizaEmpleado> empleados) {
        this.empleados = empleados;
    }

}
