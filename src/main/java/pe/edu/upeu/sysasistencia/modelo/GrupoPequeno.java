package pe.edu.upeu.sysasistencia.modelo;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "upeu_grupo_pequeno")
public class GrupoPequeno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo_pequeno")
    private Long idGrupoPequeno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_general_id", nullable = false)
    private GrupoGeneral grupoGeneral;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lider_id", nullable = false)
    private Persona lider;

    @Column(name = "nombre_grupo", nullable = false)
    private String nombreGrupo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Builder.Default
    @Column(name = "capacidad_maxima", nullable = false)
    private Integer capacidadMaxima = 20;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoGrupo estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "grupoPequeno", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ParticipanteGrupo> participantes;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
}