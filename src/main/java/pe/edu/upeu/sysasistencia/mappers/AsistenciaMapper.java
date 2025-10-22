package pe.edu.upeu.sysasistencia.mappers;

import pe.edu.upeu.sysasistencia.dtos.AsistenciaDTO;
import pe.edu.upeu.sysasistencia.modelo.Asistencia;
import pe.edu.upeu.sysasistencia.modelo.EstadoAsistencia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AsistenciaMapper extends GenericMapper<AsistenciaDTO, Asistencia> {

    @Mapping(source = "eventoEspecifico.idEventoEspecifico", target = "eventoEspecificoId")
    @Mapping(source = "persona.idPersona", target = "personaId")
    @Mapping(source = "estadoAsistencia", target = "estadoAsistencia")
    AsistenciaDTO toDTO(Asistencia entity);

    @Mapping(source = "eventoEspecificoId", target = "eventoEspecifico.idEventoEspecifico")
    @Mapping(source = "personaId", target = "persona.idPersona")
    @Mapping(source = "estadoAsistencia", target = "estadoAsistencia")
    Asistencia toEntity(AsistenciaDTO dto);

    default String estadoAsistenciaToString(EstadoAsistencia estado) {
        return estado != null ? estado.name() : null;
    }

    default EstadoAsistencia stringToEstadoAsistencia(String estado) {
        return estado != null ? EstadoAsistencia.valueOf(estado) : null;
    }
}
