package pe.edu.upeu.sysasistencia.modelo;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "upeu_evento_general")
public class EventoGeneral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento_general")
    private Long idEventoGeneral;

    @Column(name = "nombre_evento", nullable = false)
    private String nombreEvento;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "lugar", nullable = false)
    private String lugar;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoEvento estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "eventoGeneral", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EventoEspecifico> eventosEspecificos;

    @OneToMany(mappedBy = "eventoGeneral", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GrupoGeneral> gruposGenerales;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
}
