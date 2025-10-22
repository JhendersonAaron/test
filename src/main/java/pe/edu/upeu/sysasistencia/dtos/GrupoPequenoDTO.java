package pe.edu.upeu.sysasistencia.dtos;

import lombok.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GrupoPequenoDTO {
    private Long idGrupoPequeno;
    private Long grupoGeneralId;
    private Long liderId;
    private String nombreGrupo;
    private String descripcion;
    private Integer capacidadMaxima;
    private String estado;
    private LocalDateTime fechaCreacion;
}
