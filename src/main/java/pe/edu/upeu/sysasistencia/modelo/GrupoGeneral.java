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
@Table(name = "upeu_grupo_general")
public class GrupoGeneral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo_general")
    private Long idGrupoGeneral;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_general_id", nullable = false)
    private EventoGeneral eventoGeneral;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programa_id", nullable = false)
    private ProgramaEstudio programa;

    @Column(name = "nombre_grupo", nullable = false)
    private String nombreGrupo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoGrupo estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "grupoGeneral", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GrupoPequeno> gruposPequenos;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
}
