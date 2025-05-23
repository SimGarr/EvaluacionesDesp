package com.evaluaciones.evaluacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evaluaciones.evaluacion.models.entity.EvaluacionEntity;

@Repository
public interface EvaluacionRepository extends JpaRepository <EvaluacionEntity, Integer> {


    // Buscar una evaluación por su ID
    EvaluacionEntity findByIdEvaluacion(int idEvaluacion);

    // Verificar si existe una evaluación por su ID
    boolean existsByIdEvaluacion(int idEvaluacion);

    // Buscar todas las evaluaciones asociadas a un curso
    List<EvaluacionEntity> findByidCurso(String idCurso);

    // Verificar si existen evaluaciones para un curso
    boolean existsByidCurso(String idCurso);

    // Eliminar una evaluación por su ID
    void deleteByIdEvaluacion(int idEvaluacion);
    


}