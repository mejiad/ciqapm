package com.evoltech.ciqapm.utils;

import com.evoltech.ciqapm.model.EmpConahcyt;
import com.evoltech.ciqapm.model.EmpIndustria;

import java.util.ArrayList;
import java.util.List;

public class EmpIndustriaWrapper {

    private Long industriaId;

    private List<EmpIndustria> empleados = new ArrayList<EmpIndustria>();

    public Long getIndustriaId() {
        return industriaId;
    }

    public void setIndustriaId(Long industriaId) {
        this.industriaId = industriaId;
    }


    public void addEmpIndustria(EmpIndustria empIndustria){
        this.empleados.add(empIndustria);
    }

    public List<EmpIndustria> getEmpleados() {
        return this.empleados;
    }

    public void setEmpleados(List<EmpIndustria> empleados) {
        this.empleados = empleados;
    }

}
