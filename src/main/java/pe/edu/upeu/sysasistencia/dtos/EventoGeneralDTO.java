package pe.edu.upeu.sysasistencia.dtos;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventoGeneralDTO {
    private Long idEventoGeneral;
    private String nombreEvento;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String lugar;
    private String estado;
    private LocalDateTime fechaCreacion;
}
