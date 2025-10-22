package pe.edu.upeu.sysasistencia.repositorio;

import pe.edu.upeu.sysasistencia.modelo.GrupoGeneral;
import pe.edu.upeu.sysasistencia.modelo.EstadoGrupo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IGrupoGeneralRepository extends ICrudGenericoRepository<GrupoGeneral, Long> {

    @Query("SELECT g FROM GrupoGeneral g WHERE g.eventoGeneral.idEventoGeneral = :eventoGeneralId")
    List<GrupoGeneral> findByEventoGeneralId(@Param("eventoGeneralId") Long eventoGeneralId);

    @Query("SELECT g FROM GrupoGeneral g WHERE g.programa.idPrograma = :programaId")
    List<GrupoGeneral> findByProgramaId(@Param("programaId") Long programaId);

    @Query("SELECT g FROM GrupoGeneral g WHERE g.estado = :estado")
    List<GrupoGeneral> findByEstado(@Param("estado") EstadoGrupo estado);
}