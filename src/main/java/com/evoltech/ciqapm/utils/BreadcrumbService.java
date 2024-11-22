package com.evoltech.ciqapm.utils;

import com.evoltech.ciqapm.model.Conahcyt;
import com.evoltech.ciqapm.model.Industria;
import com.evoltech.ciqapm.model.Interno;
import com.evoltech.ciqapm.model.Proyecto;

public class BreadcrumbService {

    public String getPathTipoProyecto(Proyecto proyecto){
        String pathTipo = new String();
        String tipo = proyecto.getTipoProyecto().toString();
        if (tipo.equals("CONAHCYT"))  {
            pathTipo = "/conahcyt/list";
        } else if (tipo.equals("INDUSTRIA"))  {
            pathTipo = "/industria/list";
        } else if (tipo.equals("INTERNO"))   {
            pathTipo = "/internos/list";
        } else {
            pathTipo = "/home";
        }
        return pathTipo;
    }

    public String getPathProyecto(Proyecto proyecto){
        String pathProyecto = new String();
        String tipo = proyecto.getTipoProyecto().toString();
        if (tipo.equals("CONAHCYT"))  {
            pathProyecto = "/conahcyt/view?id=" + proyecto.getId();
        } else if (tipo.equals("INDUSTRIA"))  {
            pathProyecto = "/industria/view?id=" + proyecto.getId();
        } else if (tipo.equals("INTERNO"))   {
            pathProyecto = "/internos/view?id=" + proyecto.getId();
        } else {
            pathProyecto = "/home";
        }
        return pathProyecto;
    }

    public String getTagTipoProyecto(Proyecto proyecto){
        String tag = new String();
        String tipo = proyecto.getTipoProyecto().toString();
        if (tipo.equals("CONAHCYT"))  {
            tag = "Proyectos CONAHCYT";
        } else if (tipo.equals("INDUSTRIA"))  {
            tag = "Proyectos Industria";
        } else if (tipo.equals("INTERNO"))   {
            tag = "Proyectos Internos";
        } else {
            tag = "Proyecto";
        }
        return tag;
    }

}
