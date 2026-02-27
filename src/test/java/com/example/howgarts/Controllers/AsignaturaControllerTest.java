package com.example.howgarts.Controllers;
import com.example.howgarts.controller.AsignaturaController;
import com.example.howgarts.Exception.GlobalExceptionHandler;
import com.example.howgarts.service.AsignaturaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AsignaturaController.class)
@Import(GlobalExceptionHandler.class)
public class AsignaturaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AsignaturaService asignaturaService;

    @Test
    void eliminarAsignatura_ConAlumnos_DebeRetornar409() throws Exception {
        // GIVEN
        doThrow(new IllegalStateException("La asignatura tiene alumnos matriculados"))
                .when(asignaturaService).delete(1L);

        // WHEN & THEN
        mockMvc.perform(delete("/api/asignaturas/1"))
                .andExpect(status().isConflict());
    }
}


