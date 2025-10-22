package pe.edu.upeu.sysasistencia.mappers;

import pe.edu.upeu.sysasistencia.dtos.EventoGeneralDTO;
import pe.edu.upeu.sysasistencia.modelo.EventoGeneral;
import pe.edu.upeu.sysasistencia.modelo.EstadoEvento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventoGeneralMapper extends GenericMapper<EventoGeneralDTO, EventoGeneral> {

    @Mapping(source = "estado", target = "estado")
    EventoGeneralDTO toDTO(EventoGeneral entity);

    @Mapping(source = "estado", target = "estado")
    @Mapping(target = "eventosEspecificos", ignore = true)
    @Mapping(target = "gruposGenerales", ignore = true)
    EventoGeneral toEntity(EventoGeneralDTO dto);

    default String estadoEventoToString(EstadoEvento estado) {
        return estado != null ? estado.name() : null;
    }

    default EstadoEvento stringToEstadoEvento(String estado) {
        return estado != null ? EstadoEvento.valueOf(estado) : null;
    }
}