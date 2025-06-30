package com.evaluaciones.evaluacion.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CursoClienteService {

    @Autowired
    private RestTemplate restTemplate;

    private final String CURSO_BASE_URL = "http://54.221.149.255:8080/curso";

    public boolean cursoExiste(String idCurso) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(
                CURSO_BASE_URL + "/obtenerCurso/" + idCurso,
                String.class
            );
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar curso: " + e.getMessage());
        }
    }
}
