package pe.edu.upeu.sysasistencia.dtos;

import lombok.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GrupoGeneralDTO {
    private Long idGrupoGeneral;
    private Long eventoGeneralId;
    private Long programaId;
    private String nombreGrupo;
    private String descripcion;
    private String estado;
    private LocalDateTime fechaCreacion;
}
