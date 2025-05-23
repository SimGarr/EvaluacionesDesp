package com.evaluaciones.evaluacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evaluaciones.evaluacion.models.entity.EvaluacionEntity;
import com.evaluaciones.evaluacion.service.EvaluacionServices;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/evaluaciones") // Ruta base para todas las operaciones de evaluación
public class EvaluacionController {

    @Autowired
    private EvaluacionServices evaluacionServices; // Inyección del servicio de evaluación

    // Endpoint para crear una nueva evaluación
    @Operation(summary = "Crear una nueva evaluación")
    @PostMapping
    public ResponseEntity<EvaluacionEntity> crearEvaluacion(@RequestBody EvaluacionEntity evaluacion) {
        try {
            // Llamada al servicio para crear la evaluación
            EvaluacionEntity nuevaEvaluacion = evaluacionServices.crearEvaluacion(evaluacion);
            return new ResponseEntity<>(nuevaEvaluacion, HttpStatus.CREATED); // Código HTTP 201 para creación exitosa
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new org.springframework.util.LinkedMultiValueMap<>(), HttpStatus.BAD_REQUEST); // Si la validación falla, retorna 400
        }
    }

    // Endpoint para obtener una evaluación por su ID
    @Operation(summary = "Obtener evaluación por ID")
    @GetMapping("/{idEvaluacion}")
    public ResponseEntity<EvaluacionEntity> obtenerEvaluacionPorId(@PathVariable int idEvaluacion) {
        EvaluacionEntity evaluacion = evaluacionServices.obtenerEvaluacionPorId(idEvaluacion);
        if (evaluacion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 si no se encuentra
        }
        return new ResponseEntity<>(evaluacion, HttpStatus.OK); // Retorna 200 si se encuentra la evaluación
    }

    // Endpoint para verificar si una evaluación existe por su ID

    @Operation(summary = "Verificar si la evaluación existe por ID")
    @GetMapping("/existe/{idEvaluacion}")
    public ResponseEntity<String> existeEvaluacionPorId(@PathVariable int idEvaluacion) {
        boolean existe = evaluacionServices.existeEvaluacionPorId(idEvaluacion);
        String mensaje = existe
            ? "La evaluación con ID " + idEvaluacion + " SÍ existe"
            : "La evaluación con ID " + idEvaluacion + " NO existe";
        return ResponseEntity.ok(mensaje);
    }

    @Operation(summary = "Eliminar evaluación por ID")
    @DeleteMapping("/{idEvaluacion}")
    public ResponseEntity<Void> eliminarEvaluacionPorId(@PathVariable int idEvaluacion) {
        try {
            evaluacionServices.eliminarEvaluacionPorId(idEvaluacion);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna 204 si la eliminación es exitosa
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 si la evaluación no existe
        }
    }

    @Operation(summary = "Actualizar evaluación por ID")
    @PutMapping("/{idEvaluacion}")
    public ResponseEntity<EvaluacionEntity> actualizarEvaluacion(@PathVariable int idEvaluacion, @RequestBody EvaluacionEntity evaluacion) {
        if (!evaluacionServices.existeEvaluacionPorId(idEvaluacion)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 si no existe la evaluación
        }

        // Asignar el ID de la evaluación al objeto recibido
        evaluacion.setIdEvaluacion(idEvaluacion);

        // Guardar la evaluación actualizada
        EvaluacionEntity evaluacionActualizada = evaluacionServices.guardarEvaluacion(evaluacion);
        return new ResponseEntity<>(evaluacionActualizada, HttpStatus.OK); // Retorna la evaluación actualizada con código 200
    }
    @Operation(summary = "Obtener todas las evaluaciones")
    @GetMapping
public ResponseEntity<List<EvaluacionEntity>> obtenerTodasLasEvaluaciones() {
    List<EvaluacionEntity> evaluaciones = evaluacionServices.obtenerTodasLasEvaluaciones();
    if (evaluaciones.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna 204 si no hay evaluaciones
    }
    return new ResponseEntity<>(evaluaciones, HttpStatus.OK); // Retorna 200 con la lista de evaluaciones
}

}
