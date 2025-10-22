package pe.edu.upeu.sysasistencia.servicio;

import pe.edu.upeu.sysasistencia.dtos.GrupoPequenoDTO;
import pe.edu.upeu.sysasistencia.dtos.ParticipanteGrupoDTO;
import pe.edu.upeu.sysasistencia.modelo.EstadoGrupo;
import java.util.List;

public interface IGrupoPequenoService extends ICrudGenericoService<GrupoPequenoDTO, Long> {
    List<GrupoPequenoDTO> findByGrupoGeneralId(Long grupoGeneralId);
    List<GrupoPequenoDTO> findByLiderId(Long liderId);
    List<GrupoPequenoDTO> findByEstado(EstadoGrupo estado);
    List<ParticipanteGrupoDTO> getParticipantes(Long grupoPequenoId);
    void addParticipante(Long grupoPequenoId, Long personaId);
    void removeParticipante(Long grupoPequenoId, Long personaId);
    Long countParticipantesActivos(Long grupoPequenoId);
}