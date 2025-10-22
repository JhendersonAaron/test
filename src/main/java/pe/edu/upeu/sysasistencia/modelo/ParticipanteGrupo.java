package pe.edu.upeu.sysasistencia.modelo;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "upeu_participante_grupo")
public class ParticipanteGrupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_participante")
    private Long idParticipante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_pequeno_id", nullable = false)
    private GrupoPequeno grupoPequeno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @Column(name = "fecha_inscripcion")
    private LocalDateTime fechaInscripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoParticipante estado;

    @PrePersist
    protected void onCreate() {
        fechaInscripcion = LocalDateTime.now();
        estado = EstadoParticipante.ACTIVO;
    }
}