package pe.edu.upeu.sysasistencia.modelo;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "upeu_asistencia")
public class Asistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asistencia")
    private Long idAsistencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_especifico_id", nullable = false)
    private EventoEspecifico eventoEspecifico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @Column(name = "fecha_asistencia")
    private LocalDateTime fechaAsistencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_asistencia", nullable = false)
    private EstadoAsistencia estadoAsistencia;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
        if (fechaAsistencia == null) {
            fechaAsistencia = LocalDateTime.now();
        }
    }
}

