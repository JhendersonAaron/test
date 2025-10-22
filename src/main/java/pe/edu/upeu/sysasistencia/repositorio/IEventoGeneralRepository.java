package pe.edu.upeu.sysasistencia.repositorio;

import pe.edu.upeu.sysasistencia.modelo.EventoGeneral;
import pe.edu.upeu.sysasistencia.modelo.EstadoEvento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IEventoGeneralRepository extends ICrudGenericoRepository<EventoGeneral, Long> {

    @Query("SELECT e FROM EventoGeneral e WHERE e.estado = :estado")
    List<EventoGeneral> findByEstado(@Param("estado") EstadoEvento estado);

    @Query("SELECT e FROM EventoGeneral e WHERE e.nombreEvento LIKE %:nombre%")
    List<EventoGeneral> findByNombreContaining(@Param("nombre") String nombre);

    @Query("SELECT e FROM EventoGeneral e WHERE e.fechaInicio <= :fecha AND e.fechaFin >= :fecha")
    List<EventoGeneral> findByFechaBetween(@Param("fecha") java.time.LocalDate fecha);
}
