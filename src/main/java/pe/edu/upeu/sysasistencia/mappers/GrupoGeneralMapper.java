package pe.edu.upeu.sysasistencia.mappers;

import pe.edu.upeu.sysasistencia.dtos.GrupoGeneralDTO;
import pe.edu.upeu.sysasistencia.modelo.GrupoGeneral;
import pe.edu.upeu.sysasistencia.modelo.EstadoGrupo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GrupoGeneralMapper extends GenericMapper<GrupoGeneralDTO, GrupoGeneral> {

    @Mapping(source = "eventoGeneral.idEventoGeneral", target = "eventoGeneralId")
    @Mapping(source = "programa.idPrograma", target = "programaId")
    @Mapping(source = "estado", target = "estado")
    GrupoGeneralDTO toDTO(GrupoGeneral entity);

    @Mapping(source = "eventoGeneralId", target = "eventoGeneral.idEventoGeneral")
    @Mapping(source = "programaId", target = "programa.idPrograma")
    @Mapping(source = "estado", target = "estado")
    @Mapping(target = "gruposPequenos", ignore = true)
    GrupoGeneral toEntity(GrupoGeneralDTO dto);

    default String estadoGrupoToString(EstadoGrupo estado) {
        return estado != null ? estado.name() : null;
    }

    default EstadoGrupo stringToEstadoGrupo(String estado) {
        return estado != null ? EstadoGrupo.valueOf(estado) : null;
    }
}