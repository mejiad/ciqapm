package com.evoltech.ciqapm.validation;

import com.evoltech.ciqapm.model.Etapa;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EtapaValidator implements ConstraintValidator<CheckEtapaDates, Etapa> {

    @Override
    public void initialize(CheckEtapaDates constraintAnnotations) {
        System.out.println("Inicializando EtapasValidator");
    }

    @Override
    public boolean isValid(Etapa etapa, ConstraintValidatorContext context) {
        boolean result = true;
        var inicio = etapa.getFechaEstimadaInicio();
        var fin = etapa.getFechaEstimadaTerminacion();
        if (inicio == null || fin == null){
            result = false;
        } else {
            if (fin.isBefore(inicio)) {
                result = false;
            }
        }

        return result;
    }
}
