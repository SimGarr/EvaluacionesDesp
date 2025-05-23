package com.evaluaciones.evaluacion.models.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;



@Entity
@Data
public class EvaluacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEvaluacion;

    private String nombreEvaluacion;
    private String fechaEvaluacion;
    private String horaEvaluacion;
    private String duracionEvaluacion;

    @Column(name = "id_curso", nullable = false)
    private String idCurso;
}