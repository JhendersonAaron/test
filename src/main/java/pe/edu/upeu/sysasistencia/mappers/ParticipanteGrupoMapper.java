package pe.edu.upeu.sysasistencia.mappers;

import pe.edu.upeu.sysasistencia.dtos.ParticipanteGrupoDTO;
import pe.edu.upeu.sysasistencia.modelo.ParticipanteGrupo;
import pe.edu.upeu.sysasistencia.modelo.EstadoParticipante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParticipanteGrupoMapper extends GenericMapper<ParticipanteGrupoDTO, ParticipanteGrupo> {

    @Mapping(source = "grupoPequeno.idGrupoPequeno", target = "grupoPequenoId")
    @Mapping(source = "persona.idPersona", target = "personaId")
    @Mapping(source = "estado", target = "estado")
    ParticipanteGrupoDTO toDTO(ParticipanteGrupo entity);

    @Mapping(source = "grupoPequenoId", target = "grupoPequeno.idGrupoPequeno")
    @Mapping(source = "personaId", target = "persona.idPersona")
    @Mapping(source = "estado", target = "estado")
    ParticipanteGrupo toEntity(ParticipanteGrupoDTO dto);

    default String estadoParticipanteToString(EstadoParticipante estado) {
        return estado != null ? estado.name() : null;
    }

    default EstadoParticipante stringToEstadoParticipante(String estado) {
        return estado != null ? EstadoParticipante.valueOf(estado) : null;
    }
}