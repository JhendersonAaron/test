package pe.edu.upeu.sysasistencia.servicio;

import pe.edu.upeu.sysasistencia.dtos.EventoGeneralDTO;
import pe.edu.upeu.sysasistencia.modelo.EstadoEvento;
import java.util.List;

public interface IEventoGeneralService extends ICrudGenericoService<EventoGeneralDTO, Long> {
    List<EventoGeneralDTO> findByEstado(EstadoEvento estado);
    List<EventoGeneralDTO> findByNombreContaining(String nombre);
    List<EventoGeneralDTO> findByFechaBetween(java.time.LocalDate fecha);
}
