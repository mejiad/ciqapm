package com.evoltech.ciqapm.utils;

import com.evoltech.ciqapm.model.Estado;
import com.evoltech.ciqapm.model.PersonalCategoria;
import com.evoltech.ciqapm.model.TipoProyecto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.stereotype.Component;
import org.unbescape.csv.CsvEscape;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
 * Generador de nombres de personal random
 */
@Component
public class GeneradorRandom {

    private String lorem = """
                    Lorem ipsum dolor sit amet. Et rerum assumenda et fuga sequi hic quibusdam obcaecati qui autem laudantium et consequatur assumenda quo sint consectetur aut maiores delectus. Qui asperiores sint ut saepe possimus et velit quas id rerum veniam aut explicabo sequi sit quasi rerum ea numquam laudantium.
                    
                    Et ipsa minima aut exercitationem illum eum quisquam sint sed voluptas minus qui modi quos aut debitis reiciendis qui soluta dolores. Ea dolorum assumenda qui nobis facilis qui iusto sequi id inventore voluptas eum voluptas corrupti.
                    
                    Id natus nostrum qui illo aliquid aut dolor sapiente hic quis autem in sunt velit. Ut minus modi non accusamus quod quo atque ipsum et quod repellendus vel pariatur libero sed consequatur blanditiis. Est esse cumque ut voluptas nesciunt et deserunt enim.
                    """;


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

    public PersonalCategoria generaPersonalCategoria() {
        int len = PersonalCategoria.values().length;

        return Arrays.stream(PersonalCategoria.values()).toList().get(enteroMax(len -1));
    }

    public TipoProyecto generaProyectoTipo() {
        int len = TipoProyecto.values().length;

        return Arrays.stream(TipoProyecto.values()).toList().get(enteroMax(len -1));
    }

    public Estado generaEstado() {
        int len = Estado.values().length;
        return Arrays.stream(Estado.values()).toList().get(enteroMax(len -1));
    }

    public int enteroMax(int max){
        return random.nextInt(max);
    }

    public int enteroLimitado(int min, int max){
        return random.nextInt((max-min) + min);
    }
}
