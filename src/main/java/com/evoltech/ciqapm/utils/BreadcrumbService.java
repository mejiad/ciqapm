package com.evoltech.ciqapm.utils;

import com.evoltech.ciqapm.model.Conahcyt;
import com.evoltech.ciqapm.model.Industria;
import com.evoltech.ciqapm.model.Interno;
import com.evoltech.ciqapm.model.Proyecto;

public class BreadcrumbService {

    public String getPathTipoProyecto(Proyecto proyecto){
        String pathTipo = new String();
        if (proyecto  instanceof Conahcyt) {
            pathTipo = "/conahcyt/list";
        } else if (proyecto  instanceof Industria) {
            pathTipo = "/industria/list";
        } else if (proyecto  instanceof Interno)  {
            pathTipo = "/internos/list";
        } else {
            pathTipo = "/home";
        }
        return pathTipo;
    }

    public String getPathProyecto(Proyecto proyecto){
        String pathProyecto = new String();
        if (proyecto  instanceof Conahcyt) {
            pathProyecto = "/conahcyt/view?id=" + proyecto.getId();
        } else if (proyecto  instanceof Industria) {
            pathProyecto = "/industria/view?id=" + proyecto.getId();
        } else if (proyecto  instanceof Interno)  {
            pathProyecto = "/internos/view?id=" + proyecto.getId();
        } else {
            pathProyecto = "/home";
        }
        return pathProyecto;
    }

    public String getTagTipoProyecto(Proyecto proyecto){
        String tag = new String();
        if (proyecto  instanceof Conahcyt) {
            tag = "Proyectos CONAHCYT";
        } else if (proyecto  instanceof Industria) {
            tag = "Proyectos Industria";
        } else if (proyecto  instanceof Interno)  {
            tag = "Proyectos Internos";
        } else {
            tag = "Proyecto";
        }
        return tag;
    }

}
