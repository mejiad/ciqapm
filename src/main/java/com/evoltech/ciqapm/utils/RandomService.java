package com.evoltech.ciqapm.utils;

/*
 * generador de random con diferentes criterios y límites
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
public class RandomService {

    @Autowired
    private GeneradorRandom generadorRandom;

    public RandomService(GeneradorRandom generadorRandom) {
        this.generadorRandom = generadorRandom;
    }

    public String nombre(){
        return generadorRandom.generaNombre();
    }

    public String empresa() {
        return generadorRandom.generaEmpresa();
    }

    public String telefono(){
        return generadorRandom.generaTelefono();
    }

    public String rfc() {
        return generadorRandom.generaRfc();
    }

    public String email() {
        return generadorRandom.generaEmail();
    }

    public String descripcion(int len){
        return generadorRandom.generaDescripcion(len-1);
    }

    public String etapaNombre() {
        return generadorRandom.generaEtapaNombre();
    }

    public String etapaEntregable() {
        return generadorRandom.generaEntragable();
    }

    public LocalDate fechaConOffset(int year, int month, int date, int offset){
        return generadorRandom.generaFecha(year, month, date, offset);
    }

    public LocalDate fechaConOffset(LocalDate startDate){
        int offset_inicio =  generadorRandom.enteroMax(35);
        return startDate.plusDays(offset_inicio);
    }

    public int avances() {
        return generadorRandom.generaPctAvance();
    }


}
