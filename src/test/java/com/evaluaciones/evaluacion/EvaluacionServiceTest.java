package com.evaluaciones.evaluacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.evaluaciones.evaluacion.models.entity.EvaluacionEntity;
import com.evaluaciones.evaluacion.repository.EvaluacionRepository;
import com.evaluaciones.evaluacion.service.CursoClienteService;
import com.evaluaciones.evaluacion.service.EvaluacionServices;

class EvaluacionServicesTest {

    @InjectMocks
    private EvaluacionServices evaluacionServices;

    @Mock
    private EvaluacionRepository evaluacionRepository;

    @Mock
    private CursoClienteService cursoClienteService;

    private EvaluacionEntity evaluacion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        evaluacion = new EvaluacionEntity();
        evaluacion.setIdEvaluacion(1);
        evaluacion.setNombreEvaluacion("Prueba Final");
        evaluacion.setFechaEvaluacion("2025-07-01");
        evaluacion.setHoraEvaluacion("10:00");
        evaluacion.setDuracionEvaluacion("2 horas");
        evaluacion.setIdCurso("CURSO123");
    }

    /**
     * Verifica que se pueda crear y guardar una evaluación cuando todos los datos son válidos
     * y el curso asociado existe.
     */
    @Test
    void crearEvaluacion_deberiaGuardarCuandoDatosSonValidos() {
        when(cursoClienteService.cursoExiste("CURSO123")).thenReturn(true);
        when(evaluacionRepository.save(evaluacion)).thenReturn(evaluacion);

        EvaluacionEntity resultado = evaluacionServices.crearEvaluacion(evaluacion);

        assertNotNull(resultado);
        verify(evaluacionRepository, times(1)).save(evaluacion);
    }

    /**
     * Verifica que se lanza una excepción si el nombre de la evaluación es nulo.
     * Esto prueba la validación del campo obligatorio.
     */
    @Test
    void crearEvaluacion_deberiaLanzarExcepcionSiNombreEsNulo() {
        evaluacion.setNombreEvaluacion(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                evaluacionServices.crearEvaluacion(evaluacion));

        assertEquals("El nombre de la evaluación no puede estar vacío.", exception.getMessage());
    }

    /**
     * Verifica que se lanza una excepción si el curso asociado no existe.
     * Esto evita que se creen evaluaciones con cursos inválidos.
     */
    @Test
    void crearEvaluacion_deberiaLanzarExcepcionSiCursoNoExiste() {
        when(cursoClienteService.cursoExiste("CURSO123")).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                evaluacionServices.crearEvaluacion(evaluacion));

        assertEquals("El curso con ID CURSO123 no existe.", exception.getMessage());
    }

    /**
     * Verifica que el método obtenerEvaluacionPorId retorne correctamente una evaluación
     * cuando se busca por ID.
     */
    @Test
    void obtenerEvaluacionPorId_deberiaRetornarEvaluacion() {
        when(evaluacionRepository.findByIdEvaluacion(1)).thenReturn(evaluacion);

        EvaluacionEntity resultado = evaluacionServices.obtenerEvaluacionPorId(1);

        assertEquals("Prueba Final", resultado.getNombreEvaluacion());
    }

    /**
     * Verifica que se puede comprobar la existencia de una evaluación por su ID.
     */
    @Test
    void existeEvaluacionPorId_deberiaRetornarTrueSiExiste() {
        when(evaluacionRepository.existsByIdEvaluacion(1)).thenReturn(true);

        assertTrue(evaluacionServices.existeEvaluacionPorId(1));
    }

    /**
     * Verifica que se elimina correctamente una evaluación cuando existe.
     */
    @Test
    void eliminarEvaluacionPorId_deberiaEliminarSiExiste() {
        when(evaluacionRepository.existsByIdEvaluacion(1)).thenReturn(true);

        evaluacionServices.eliminarEvaluacionPorId(1);

        verify(evaluacionRepository, times(1)).deleteById(1);
    }

    /**
     * Verifica que se lanza una excepción si se intenta eliminar una evaluación que no existe.
     */
    @Test
    void eliminarEvaluacionPorId_deberiaLanzarExcepcionSiNoExiste() {
        when(evaluacionRepository.existsByIdEvaluacion(1)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () ->
                evaluacionServices.eliminarEvaluacionPorId(1));

        assertEquals("Evaluación no encontrada con ID: 1", exception.getMessage());
    }

    /**
     * Verifica que se guarda correctamente una evaluación mediante el método guardarEvaluacion().
     */
    @Test
    void guardarEvaluacion_deberiaGuardarCorrectamente() {
        when(evaluacionRepository.save(evaluacion)).thenReturn(evaluacion);

        EvaluacionEntity resultado = evaluacionServices.guardarEvaluacion(evaluacion);

        assertEquals("Prueba Final", resultado.getNombreEvaluacion());
    }

    /**
     * Verifica que el método obtenerTodasLasEvaluaciones retorna correctamente una lista de evaluaciones.
     */
    @Test
    void obtenerTodasLasEvaluaciones_deberiaRetornarLista() {
        List<EvaluacionEntity> lista = Arrays.asList(evaluacion);
        when(evaluacionRepository.findAll()).thenReturn(lista);

        List<EvaluacionEntity> resultado = evaluacionServices.obtenerTodasLasEvaluaciones();

        assertEquals(1, resultado.size());
    }
}
