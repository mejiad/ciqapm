package com.evoltech.ciqapm.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.stereotype.Component;
import org.unbescape.csv.CsvEscape;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Generador de nombres de personal random
 */
@Component
public class GeneradorRandom {

    private String lorem = "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo. Quisque sit amet est et sapien ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, commodo vitae, ornare sit amet, wisi. Aenean fermentum, elit eget tincidunt condimentum, eros ipsum rutrum orci, sagittis tempus lacus enim ac dui. Donec non enim in turpis pulvinar facilisis. Ut felis. Praesent dapibus, neque id cursus faucibus, tortor neque egestas augue, eu vulputate magna eros eu erat. Aliquam erat volutpat. Nam dui mi, tincidunt quis, accumsan porttitor, facilisis luctus, metus";

    private List<String> nombres = List.of( "Andrés", "Gerard", "Lionel", "Neymar", "Ana", "Enzo", "Eric",
            "Eva", "Hugo", "Iván", "Juan", "Lara", "Leo", "Luz", "Mar", "Nora", "Raúl", "Sara", "Héctor", "Helena", "Isis",
            "Ulises", "Zeus", "Alba", "Alejandro", "Álvaro", "Ana", "Emma", "Lucas", "Lucía", "Manuel", "Mariana", "Martín",
            "Bernardo", "Esther", "Gabriel", "Isabel", "Lucía", "Marta", "Moisés", "Raquel", "Samuel", "Guillermo",
            "Arturo", "Leticia", "Alberto", "Enrique", "Felipe", "Isabel", "Leticia", "Margarita");

    private List<String> apellidos = List.of( "Hernández", "García", "Martínez", "López", "González", "Pérez",
            "Rodríguez", "Sánchez", "Ramírez", "Cruz");


    private List<String> empresasPrefijo = List.of("Industrias " +
            "Comercializadora ",
            "Productos y Servicios",
            "Editorial ",
            "Sistemas y Equipo ",
            "Agricola ",
            "Transportes ",
            "Banco ",
            "Seguros "
    );


    private List<String> empresasSufijo = List.of("del Norte",
            "del Centro SA de CV",
            " Mexicana Inc.",
            " del Bajío Corp.",
            " Ramos SA de CV",
            " del Noreste SRL",
            " Sánchez Inc.",
            " Carso Corp.",
            " Motolinía Sa de Cv"
    );
    private List<Character> digits = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9' );
    private List<Character> alpha = List.of( 'a',
            'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
    'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' );

    private List<String> emailServer = List.of(
            "gmail.com",
            "yahoo.com",
            "futurism.com",
            "ibm.com",
            "axtel.com",
            "telcel.com"
    );

    private List<String> etapaNombre = List.of(
            "Análisis",
            "Diseño",
            "Generacion estudio inicial",
            "Generación prototipo",
            "Prueba de prototipo",
            "Presentación resultados",
            "Integración",
            "Análisis Funcional",
            "Análisis Detallado",
            "Entrega de Resultados"
    );


    private List<String> etapaEntregable = List.of(
            "Reporte de resultados",
            "Propuesta de actividades",
            "Analisis Inicial",
            "Prototipo funcional",
            "Anlisis de factibilidad",
            "Plan de acción",
            "Manual de procedimientos",
            "Manual de seguridad",
            "Documento de operación",
            "Reporte final"
    );

    private Random random = new Random();

    public GeneradorRandom() {
    }

    public String generaNombre(){

        String nombre = nombres.get(enteroMax(nombres.size()-1));
        String apellido = apellidos.get(enteroMax(apellidos.size()-1));
        return nombre + " " + apellido;
    }

    public String generaEmpresa() {
        String nombre = empresasPrefijo.get(enteroMax(empresasPrefijo.size()-1));
        String sufijo = empresasSufijo.get(enteroMax(empresasSufijo.size()-1));
        return nombre  + sufijo;

    }

    public int generaPctAvance(){
        return enteroLimitado(15,90);
    }

    public String generaDescripcion(int len){
        if (len > lorem.length()){
            len = lorem.length() -1;
        }
        return lorem.substring(0, len);
    }

    public LocalDate generaFecha(int year, int month, int day,  int offset){
        LocalDate ld = LocalDate.of( year, month , day);
        return ld.plusDays(offset);
    }

    public String generaTelefono(){
        StringBuffer sb = new StringBuffer();
        for(int i=0; i <10; i++){
            sb.append(digits.get(enteroMax(9)));
        }
        return sb.toString();
    }

    public String generaRfc(){
        StringBuffer sb = new StringBuffer();
        for(int i=0; i <6; i++){
            sb.append(alpha.get(enteroMax(alpha.size()-1)));
        }
        sb.append("-");
        for(int i=0; i <3; i++){
            sb.append(alpha.get(enteroMax(alpha.size()-1)));
        }
        return sb.toString();
    }

    public String generaEmail() {
        StringBuffer sb = new StringBuffer();
        for(int i=0; i <7; i++) {
            sb.append(alpha.get(enteroMax(alpha.size() - 1)));
        }
        sb.append('@');
        return sb.toString() + emailServer.get(enteroMax(emailServer.size()-1));

    }

    public String generaEtapaNombre(){
        return etapaNombre.get(enteroMax(etapaNombre.size()-1));
    }

    public String generaEntragable(){
        return etapaEntregable.get(enteroMax(etapaEntregable.size()-1));
    }

    public int enteroMax(int max){
        return random.nextInt(max);
    }

    public int enteroLimitado(int min, int max){
        return random.nextInt((max-min) + min);
    }
}
