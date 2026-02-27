package Controllers;

import com.example.howgarts.dto.CrearEstudianteDto;
import com.example.howgarts.service.EstudianteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(com.example.howgarts.controller.EstudianteController.class)
public class EstudianteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EstudianteService estudianteService;

    private CrearEstudianteDto dtoInvalido;

    @BeforeEach
    void setUp() {
        // Preparamos un estudiante con año de curso inválido (máximo en Hogwarts es 7)
        dtoInvalido = new CrearEstudianteDto();
        dtoInvalido.setNombre("Tom");
        dtoInvalido.setApellido("Riddle");
        dtoInvalido.setAnyoCurso(10L); // Año inválido
    }

    @Test
    void crearEstudianteConAnyoCursoInvalido_debeRetornar400() throws Exception {
        // GIVEN: El servicio lanza excepción al recibir año de curso 10
        when(estudianteService.crearEstudiante(any(CrearEstudianteDto.class)))
                .thenThrow(new IllegalArgumentException("El año de curso debe estar entre 1 y 7"));

        // WHEN: POST /api/estudiantes con año de curso 10
        // THEN: Se espera 400 Bad Request
        mockMvc.perform(post("/api/estudiantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());
    }
}



