package com.evaluaciones.evaluacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evaluaciones.evaluacion.models.entity.EvaluacionEntity;
import com.evaluaciones.evaluacion.repository.EvaluacionRepository;

@Service
public class EvaluacionServices {

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private CursoClienteService cursoClienteService;  // <-- Inyecta el cliente REST

    
    public EvaluacionEntity crearEvaluacion(EvaluacionEntity evaluacion) {
        
        validarEvaluacion(evaluacion);

   
        if (evaluacion.getIdCurso() == null || 
            !cursoClienteService.cursoExiste(evaluacion.getIdCurso())) {
            throw new IllegalArgumentException("El curso con ID " + evaluacion.getIdCurso() + " no existe.");
        }

        
        return evaluacionRepository.save(evaluacion);
    }

    private void validarEvaluacion(EvaluacionEntity evaluacion) {
        if (evaluacion.getNombreEvaluacion() == null || evaluacion.getNombreEvaluacion().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la evaluación no puede estar vacío.");
        }
    }

    public EvaluacionEntity obtenerEvaluacionPorId(int idEvaluacion) {
        return evaluacionRepository.findByIdEvaluacion(idEvaluacion);
    }

    public boolean existeEvaluacionPorId(int idEvaluacion) {
        return evaluacionRepository.existsByIdEvaluacion(idEvaluacion);
    }

    public void eliminarEvaluacionPorId(int idEvaluacion) {
        if (!existeEvaluacionPorId(idEvaluacion)) {
            throw new RuntimeException("Evaluación no encontrada con ID: " + idEvaluacion);
        }
        evaluacionRepository.deleteById(idEvaluacion);
    }

    public EvaluacionEntity guardarEvaluacion(EvaluacionEntity evaluacion) {
        return evaluacionRepository.save(evaluacion);
    }

    public List<EvaluacionEntity> obtenerTodasLasEvaluaciones() {
        return evaluacionRepository.findAll();
    }
}
