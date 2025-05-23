package com.evaluaciones.evaluacion.service;

import com.evaluaciones.evaluacion.models.entity.EvaluacionEntity;
import com.evaluaciones.evaluacion.repository.EvaluacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluacionServices {

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private CursoClienteService cursoClienteService;  // <-- Inyecta el cliente REST

    // Crear una nueva evaluación con validación de curso
    public EvaluacionEntity crearEvaluacion(EvaluacionEntity evaluacion) {
        // Validar campos básicos de la evaluación
        validarEvaluacion(evaluacion);

        // Validar que el curso exista antes de guardar
        if (evaluacion.getIdCurso() == null || 
            !cursoClienteService.cursoExiste(evaluacion.getIdCurso())) {
            throw new IllegalArgumentException("El curso con ID " + evaluacion.getIdCurso() + " no existe.");
        }

        // Guardar la evaluación en la base de datos
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
