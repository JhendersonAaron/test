package pe.edu.upeu.sysasistencia.repositorio;

import pe.edu.upeu.sysasistencia.modelo.ParticipanteGrupo;
import pe.edu.upeu.sysasistencia.modelo.EstadoParticipante;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IParticipanteGrupoRepository extends ICrudGenericoRepository<ParticipanteGrupo, Long> {

    @Query("SELECT p FROM ParticipanteGrupo p WHERE p.grupoPequeno.idGrupoPequeno = :grupoPequenoId")
    List<ParticipanteGrupo> findByGrupoPequenoId(@Param("grupoPequenoId") Long grupoPequenoId);

    @Query("SELECT p FROM ParticipanteGrupo p WHERE p.persona.idPersona = :personaId")
    List<ParticipanteGrupo> findByPersonaId(@Param("personaId") Long personaId);

    @Query("SELECT p FROM ParticipanteGrupo p WHERE p.grupoPequeno.idGrupoPequeno = :grupoPequenoId AND p.persona.idPersona = :personaId")
    Optional<ParticipanteGrupo> findByGrupoPequenoAndPersona(@Param("grupoPequenoId") Long grupoPequenoId, @Param("personaId") Long personaId);

    @Query("SELECT p FROM ParticipanteGrupo p WHERE p.estado = :estado")
    List<ParticipanteGrupo> findByEstado(@Param("estado") EstadoParticipante estado);
}
