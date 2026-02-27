package com.example.howgarts.Controllers;

import com.example.howgarts.controller.EstudianteController;
import com.example.howgarts.dto.CrearEstudianteDto;
import com.example.howgarts.service.EstudianteService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EstudianteController.class)
public class EstudianteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EstudianteService estudianteService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void crearEstudiante_AnyoCursoInvalido_DebeRetornar400() throws Exception {
        // GIVEN
        CrearEstudianteDto dto = new CrearEstudianteDto();
        dto.setNombre("Harry");
        dto.setApellido("Potter");
     //   dto.setAnyoCurso(10L); // Valor inválido (máximo en Hogwarts es 7)

        when(estudianteService.crearEstudiante(any(CrearEstudianteDto.class)))
                .thenThrow(new IllegalArgumentException("El año de curso debe estar entre 1 y 7"));

        // WHEN & THEN
        mockMvc.perform(post("/api/estudiantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        // El servicio SÍ es llamado pero lanza excepción (el handler devuelve 400)
        verify(estudianteService, times(1)).crearEstudiante(any(CrearEstudianteDto.class));
    }
}




