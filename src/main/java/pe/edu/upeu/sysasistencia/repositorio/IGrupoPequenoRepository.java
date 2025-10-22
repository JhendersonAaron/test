package pe.edu.upeu.sysasistencia.repositorio;

import pe.edu.upeu.sysasistencia.modelo.GrupoPequeno;
import pe.edu.upeu.sysasistencia.modelo.EstadoGrupo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IGrupoPequenoRepository extends ICrudGenericoRepository<GrupoPequeno, Long> {

    @Query("SELECT g FROM GrupoPequeno g WHERE g.grupoGeneral.idGrupoGeneral = :grupoGeneralId")
    List<GrupoPequeno> findByGrupoGeneralId(@Param("grupoGeneralId") Long grupoGeneralId);

    @Query("SELECT g FROM GrupoPequeno g WHERE g.lider.idPersona = :liderId")
    List<GrupoPequeno> findByLiderId(@Param("liderId") Long liderId);

    @Query("SELECT g FROM GrupoPequeno g WHERE g.estado = :estado")
    List<GrupoPequeno> findByEstado(@Param("estado") EstadoGrupo estado);

    @Query("SELECT COUNT(p) FROM ParticipanteGrupo p WHERE p.grupoPequeno.idGrupoPequeno = :grupoPequenoId AND p.estado = 'ACTIVO'")
    Long countParticipantesActivos(@Param("grupoPequenoId") Long grupoPequenoId);
}
