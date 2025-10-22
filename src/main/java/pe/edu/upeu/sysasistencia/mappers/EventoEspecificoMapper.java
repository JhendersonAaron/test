package pe.edu.upeu.sysasistencia.mappers;

import pe.edu.upeu.sysasistencia.dtos.EventoEspecificoDTO;
import pe.edu.upeu.sysasistencia.modelo.EventoEspecifico;
import pe.edu.upeu.sysasistencia.modelo.EstadoEventoEspecifico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventoEspecificoMapper extends GenericMapper<EventoEspecificoDTO, EventoEspecifico> {

    @Mapping(source = "eventoGeneral.idEventoGeneral", target = "eventoGeneralId")
    @Mapping(source = "estado", target = "estado")
    EventoEspecificoDTO toDTO(EventoEspecifico entity);

    @Mapping(source = "eventoGeneralId", target = "eventoGeneral.idEventoGeneral")
    @Mapping(source = "estado", target = "estado")
    @Mapping(target = "asistencias", ignore = true)
    EventoEspecifico toEntity(EventoEspecificoDTO dto);

    default String estadoEventoEspecificoToString(EstadoEventoEspecifico estado) {
        return estado != null ? estado.name() : null;
    }

    default EstadoEventoEspecifico stringToEstadoEventoEspecifico(String estado) {
        return estado != null ? EstadoEventoEspecifico.valueOf(estado) : null;
    }
}