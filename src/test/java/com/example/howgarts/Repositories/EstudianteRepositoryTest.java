package com.example.howgarts.Repositories;

import com.example.howgarts.model.Casa;
import com.example.howgarts.model.Estudiante;
import com.example.howgarts.model.Mascota;
import com.example.howgarts.repository.EstudianteRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Configura una BD en memoria y carga solo entidades y repositorios
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
public class EstudianteRepositoryTest {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private EntityManager entityManager; // Usamos el motor de JPA para consultas directas

    @Test
    void eliminarEstudiante_DebeEliminarMascotaEnCascada() {
        // 1. GIVEN: Preparamos los datos
        Casa casa = new Casa();
        casa.setNombre("Gryffindor");
        entityManager.persist(casa);

        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Harry");
        estudiante.setApellido("Potter");
        estudiante.setAnyoCurso(1L);
        estudiante.setCasa(casa);

        Mascota mascota = new Mascota();
        mascota.setNombre("Hedwig");
        mascota.setEspecie("Lechuza");

        estudiante.setMascota(mascota); // Sincronizamos la relación bidireccional

        // Persistimos el estudiante (y por cascada, la mascota)
        Estudiante guardado = estudianteRepository.save(estudiante);
        Long idEstudiante = guardado.getIdEstudiante();
        Long idMascota = guardado.getMascota().getId();

        // 2. WHEN: Ejecutamos la acción de borrado
        estudianteRepository.delete(guardado);
        estudianteRepository.flush();
        entityManager.clear();

        // 3. THEN: Ambos han desaparecido de la base de datos
        Estudiante estudianteBD = entityManager.find(Estudiante.class, idEstudiante);
        Mascota mascotaBD = entityManager.find(Mascota.class, idMascota);

        assertNull(estudianteBD);
        assertNull(mascotaBD);
    }
}