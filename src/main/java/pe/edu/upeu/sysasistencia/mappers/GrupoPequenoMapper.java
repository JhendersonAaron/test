package pe.edu.upeu.sysasistencia.mappers;

import pe.edu.upeu.sysasistencia.dtos.GrupoPequenoDTO;
import pe.edu.upeu.sysasistencia.modelo.GrupoPequeno;
import pe.edu.upeu.sysasistencia.modelo.EstadoGrupo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GrupoPequenoMapper extends GenericMapper<GrupoPequenoDTO, GrupoPequeno> {

    @Mapping(source = "grupoGeneral.idGrupoGeneral", target = "grupoGeneralId")
    @Mapping(source = "lider.idPersona", target = "liderId")
    @Mapping(source = "estado", target = "estado")
    GrupoPequenoDTO toDTO(GrupoPequeno entity);

    @Mapping(source = "grupoGeneralId", target = "grupoGeneral.idGrupoGeneral")
    @Mapping(source = "liderId", target = "lider.idPersona")
    @Mapping(source = "estado", target = "estado")
    @Mapping(target = "participantes", ignore = true)
    GrupoPequeno toEntity(GrupoPequenoDTO dto);

    default String estadoGrupoToString(EstadoGrupo estado) {
        return estado != null ? estado.name() : null;
    }

    default EstadoGrupo stringToEstadoGrupo(String estado) {
        return estado != null ? EstadoGrupo.valueOf(estado) : null;
    }
}