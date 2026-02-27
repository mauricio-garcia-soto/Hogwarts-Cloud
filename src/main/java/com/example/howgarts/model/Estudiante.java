package com.example.howgarts.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
@Data
@Entity
@Table(name = "estudiante")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estudiante")
    private Long idEstudiante;


    private String nombre;


    private String apellido;



    @Column(name = "anyo_curso")
    private Long anyoCurso;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;



    @OneToOne(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("estudiante-mascota")
    @ToString.Exclude
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "id_casa")
    @JsonBackReference("estudiante-casa")
    @ToString.Exclude
    private Casa casa;

    @OneToMany(mappedBy = "estudiante")
    @JsonManagedReference("estudiante-nota")
    @ToString.Exclude
    private List<Nota> notas;

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
        if (mascota != null) {
            mascota.setEstudiante(this);
        }
    }
}