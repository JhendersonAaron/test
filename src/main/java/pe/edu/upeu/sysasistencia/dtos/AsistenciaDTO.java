package pe.edu.upeu.sysasistencia.dtos;

import lombok.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AsistenciaDTO {
    private Long idAsistencia;
    private Long eventoEspecificoId;
    private Long personaId;
    private LocalDateTime fechaAsistencia;
    private String estadoAsistencia;
    private String observaciones;
    private LocalDateTime fechaRegistro;
}
