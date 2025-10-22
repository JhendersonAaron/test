package pe.edu.upeu.sysasistencia.dtos;

import lombok.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParticipanteGrupoDTO {
    private Long idParticipante;
    private Long grupoPequenoId;
    private Long personaId;
    private LocalDateTime fechaInscripcion;
    private String estado;
}
