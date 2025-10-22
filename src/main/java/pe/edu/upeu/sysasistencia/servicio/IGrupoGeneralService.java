package pe.edu.upeu.sysasistencia.servicio;

import pe.edu.upeu.sysasistencia.dtos.GrupoGeneralDTO;
import pe.edu.upeu.sysasistencia.modelo.EstadoGrupo;
import java.util.List;

public interface IGrupoGeneralService extends ICrudGenericoService<GrupoGeneralDTO, Long> {
    List<GrupoGeneralDTO> findByEventoGeneralId(Long eventoGeneralId);
    List<GrupoGeneralDTO> findByProgramaId(Long programaId);
    List<GrupoGeneralDTO> findByEstado(EstadoGrupo estado);
}