package pe.edu.upeu.sysasistencia.servicio;

import pe.edu.upeu.sysasistencia.dtos.EventoEspecificoDTO;
import pe.edu.upeu.sysasistencia.modelo.EstadoEventoEspecifico;
import java.time.LocalDate;
import java.util.List;

public interface IEventoEspecificoService extends ICrudGenericoService<EventoEspecificoDTO, Long> {
    List<EventoEspecificoDTO> findByEventoGeneralId(Long eventoGeneralId);
    List<EventoEspecificoDTO> findByFechaEvento(LocalDate fecha);
    List<EventoEspecificoDTO> findByEstado(EstadoEventoEspecifico estado);
}
