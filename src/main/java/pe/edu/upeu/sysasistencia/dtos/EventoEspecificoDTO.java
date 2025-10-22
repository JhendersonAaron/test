package pe.edu.upeu.sysasistencia.dtos;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventoEspecificoDTO {
    private Long idEventoEspecifico;
    private Long eventoGeneralId;
    private LocalDate fechaEvento;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String descripcion;
    private String estado;
    private LocalDateTime fechaCreacion;
}
