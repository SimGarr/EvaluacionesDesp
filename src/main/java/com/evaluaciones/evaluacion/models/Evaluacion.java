package com.evaluaciones.evaluacion.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evaluacion {

    private int idEvaluacion;
    private String nombreEvaluacion;
    private String fechaEvaluacion;
    private String horaEvaluacion;
    private String duracionEvaluacion;
    private String idCurso;


}
