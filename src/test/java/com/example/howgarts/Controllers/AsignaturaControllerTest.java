package Controllers;

import com.example.howgarts.service.AsignaturaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(com.example.howgarts.controller.AsignaturaController.class)
public class AsignaturaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AsignaturaService asignaturaService;

    @Test
    void eliminarAsignaturaConAlumnos_debeRetornar409() throws Exception {
        // GIVEN: La asignatura 1 tiene alumnos matriculados
        doThrow(new IllegalStateException("No se puede eliminar una asignatura con alumnos matriculados"))
                .when(asignaturaService).delete(anyLong());

        // WHEN: DELETE /api/asignaturas/1
        // THEN: Se espera 409 Conflict
        mockMvc.perform(delete("/api/asignaturas/{id}", 1L))
                .andExpect(status().isConflict());
    }
}