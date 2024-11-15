package com.evoltech.ciqapm.utils;

import com.evoltech.ciqapm.model.CotizaEmpleado;
import com.evoltech.ciqapm.model.EmpConahcyt;

import java.util.ArrayList;
import java.util.List;

public class EmpConahcytWrapper {

    private Long conahcytId;

    private List<EmpConahcyt> empleados = new ArrayList<EmpConahcyt>();

    public Long getConahcytId() {
        return conahcytId;
    }

    public void setConahcytId(Long conahcytId) {
        this.conahcytId = conahcytId;
    }


    public void addEmpConahcyt(EmpConahcyt empConahcyt){
        this.empleados.add(empConahcyt);
    }

    public List<EmpConahcyt> getEmpleados() {
        return this.empleados;
    }

    public void setEmpleados(List<EmpConahcyt> empleados) {
        this.empleados = empleados;
    }

}
