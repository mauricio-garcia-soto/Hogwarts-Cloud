package com.example.howgarts.Services;
import com.example.howgarts.model.Estudiante;
import com.example.howgarts.model.Mascota;
import com.example.howgarts.repository.EstudianteRepository;
import com.example.howgarts.repository.MascotaRepository;
import com.example.howgarts.service.impl.EstudianteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstudianteServiceTest {

    @Mock
    private EstudianteRepository estudianteRepository;

    @Mock
    private MascotaRepository mascotaRepository;

    @InjectMocks
    private EstudianteServiceImpl estudianteService;

    private Estudiante estudianteTest;

    @BeforeEach
    void setUp() {
        estudianteTest = new Estudiante();
        estudianteTest.setIdEstudiante(1L);
        estudianteTest.setNombre("Harry");
        estudianteTest.setApellido("Potter");
        estudianteTest.setAnyoCurso(1L);

        Mascota mascota = new Mascota();
        mascota.setNombre("Hedwig");
        estudianteTest.setMascota(mascota);
    }

    @Test
    void eliminarEstudiante_Exito() {
        // GIVEN
        Long id = 1L;
        when(estudianteRepository.findById(id)).thenReturn(Optional.of(estudianteTest));

        // WHEN
        estudianteService.eliminarEstudiante(id);

        // THEN
        verify(estudianteRepository, times(1)).delete(estudianteTest);
        // Ya no verificamos mascotaRepository porque la cascada lo gestiona
    }
}