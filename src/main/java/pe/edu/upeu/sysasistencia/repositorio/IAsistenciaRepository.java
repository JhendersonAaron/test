package pe.edu.upeu.sysasistencia.repositorio;

import pe.edu.upeu.sysasistencia.modelo.Asistencia;
import pe.edu.upeu.sysasistencia.modelo.EstadoAsistencia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IAsistenciaRepository extends ICrudGenericoRepository<Asistencia, Long> {

    @Query("SELECT a FROM Asistencia a WHERE a.eventoEspecifico.idEventoEspecifico = :eventoEspecificoId")
    List<Asistencia> findByEventoEspecificoId(@Param("eventoEspecificoId") Long eventoEspecificoId);

    @Query("SELECT a FROM Asistencia a WHERE a.persona.idPersona = :personaId")
    List<Asistencia> findByPersonaId(@Param("personaId") Long personaId);

    @Query("SELECT a FROM Asistencia a WHERE a.estadoAsistencia = :estado")
    List<Asistencia> findByEstadoAsistencia(@Param("estado") EstadoAsistencia estado);

    @Query("SELECT a FROM Asistencia a WHERE a.eventoEspecifico.idEventoEspecifico = :eventoEspecificoId AND a.persona.idPersona = :personaId")
    Optional<Asistencia> findByEventoEspecificoAndPersona(@Param("eventoEspecificoId") Long eventoEspecificoId, @Param("personaId") Long personaId);

    @Query("SELECT COUNT(a) FROM Asistencia a WHERE a.eventoEspecifico.idEventoEspecifico = :eventoEspecificoId AND a.estadoAsistencia = :estado")
    Long countByEventoEspecificoAndEstado(@Param("eventoEspecificoId") Long eventoEspecificoId, @Param("estado") EstadoAsistencia estado);
}
