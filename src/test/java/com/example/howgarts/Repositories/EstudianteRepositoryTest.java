package com.example.howgarts.Repositories;


import com.example.howgarts.model.Casa;
import com.example.howgarts.model.Estudiante;
import com.example.howgarts.model.Mascota;
import com.example.howgarts.repository.EstudianteRepository;
import com.example.howgarts.repository.MascotaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // Usa H2 en memoria, no necesita PostgreSQL arrancado
public class EstudianteRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    private Estudiante estudianteTest;
    private Mascota mascotaTest;

    @BeforeEach
    void setUp() {
        // Preparamos una casa
        Casa casa = new Casa();
        casa.setNombre("Gryffindor");
        entityManager.persist(casa);

        // Preparamos el estudiante
        estudianteTest = new Estudiante();
        estudianteTest.setNombre("Harry");
        estudianteTest.setApellido("Potter");
        estudianteTest.setAnyoCurso(1L);
        estudianteTest.setCasa(casa);
        entityManager.persist(estudianteTest);

        // Preparamos la mascota y la asociamos al estudiante
        mascotaTest = new Mascota();
        mascotaTest.setNombre("Hedwig");
        mascotaTest.setEstudiante(estudianteTest);
        entityManager.persist(mascotaTest);

        entityManager.flush();
    }

    @Test
    void alBorrarEstudiante_suMascotaTambienDebeDesaparecer() {
        // GIVEN: Harry y Hedwig existen en la base de datos
        Long harryId = estudianteTest.getIdEstudiante();
        Long hedwigId = mascotaTest.getId();

        assertThat(estudianteRepository.findById(harryId)).isPresent();
        assertThat(mascotaRepository.findById(hedwigId)).isPresent();

        // WHEN: Eliminamos a Harry Potter
        estudianteRepository.deleteById(harryId);
        entityManager.flush();
        entityManager.clear();

        // THEN: Harry y Hedwig ya no existen (borrado en cascada)
        assertThat(estudianteRepository.findById(harryId)).isEmpty();
        assertThat(mascotaRepository.findById(hedwigId)).isEmpty();
    }
}
