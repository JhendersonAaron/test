    package pe.edu.upeu.sysasistencia.repositorio;

    import pe.edu.upeu.sysasistencia.modelo.EventoEspecifico;
    import pe.edu.upeu.sysasistencia.modelo.EstadoEventoEspecifico;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;
    import org.springframework.stereotype.Repository;
    import java.time.LocalDate;
    import java.util.List;

    @Repository
    public interface IEventoEspecificoRepository extends ICrudGenericoRepository<EventoEspecifico, Long> {

        @Query("SELECT e FROM EventoEspecifico e WHERE e.eventoGeneral.idEventoGeneral = :eventoGeneralId")
        List<EventoEspecifico> findByEventoGeneralId(@Param("eventoGeneralId") Long eventoGeneralId);

        @Query("SELECT e FROM EventoEspecifico e WHERE e.fechaEvento = :fecha")
        List<EventoEspecifico> findByFechaEvento(@Param("fecha") LocalDate fecha);

        @Query("SELECT e FROM EventoEspecifico e WHERE e.estado = :estado")
        List<EventoEspecifico> findByEstado(@Param("estado") EstadoEventoEspecifico estado);
    }
