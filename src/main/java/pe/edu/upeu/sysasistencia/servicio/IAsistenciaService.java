package pe.edu.upeu.sysasistencia.servicio;

import pe.edu.upeu.sysasistencia.dtos.AsistenciaDTO;
import pe.edu.upeu.sysasistencia.modelo.EstadoAsistencia;
import java.util.List;

public interface IAsistenciaService extends ICrudGenericoService<AsistenciaDTO, Long> {
    List<AsistenciaDTO> findByEventoEspecificoId(Long eventoEspecificoId);
    List<AsistenciaDTO> findByPersonaId(Long personaId);
    List<AsistenciaDTO> findByEstadoAsistencia(EstadoAsistencia estado);
    void registrarAsistencia(Long eventoEspecificoId, List<AsistenciaDTO> asistencias);
    Long countByEventoEspecificoAndEstado(Long eventoEspecificoId, EstadoAsistencia estado);
}