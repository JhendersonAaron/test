package pe.edu.upeu.sysasistencia.repositorio;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pe.edu.upeu.sysasistencia.modelo.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IAsistenciaRepositoryTest {

    @Autowired
    private IAsistenciaRepository asistenciaRepository;

    @Autowired
    private IPersonaRepository personaRepository;

    @Autowired
    private IEventoEspecificoRepository eventoEspecificoRepository;

    @Autowired
    private IEventoGeneralRepository eventoGeneralRepository;

    private Long asistenciaId;
    private Long personaId;
    private Long eventoEspecificoId;

    @BeforeEach
    @Rollback(false)
    public void setUp() {
        // Crear persona
        Persona persona = Persona.builder()
                .nombreCompleto("Juan Pérez")
                .codigoEstudiante("2025001")
                .documento("12345678")
                .correo("juan@gmail.com")
                .correoInstitucional("juan@upeu.edu.pe")
                .celular("987654321")
                .pais("Perú")
                .religion("Católica")
                .tipoPersona(TipoPersona.ESTUDIANTE)
                .build();
        personaRepository.save(persona);
        personaId = persona.getIdPersona();

        // Crear evento general
        EventoGeneral eventoGeneral = EventoGeneral.builder()
                .nombreEvento("Retiro Espiritual")
                .descripcion("Evento de fe y reflexión")
                .fechaInicio(LocalDate.now())
                .fechaFin(LocalDate.now().plusDays(2))
                .lugar("Auditorio Central")
                .estado(EstadoEvento.ACTIVO)
                .build();
        eventoGeneralRepository.save(eventoGeneral);

        // Crear evento específico
        EventoEspecifico eventoEspecifico = EventoEspecifico.builder()
                .eventoGeneral(eventoGeneral)
                .fechaEvento(LocalDate.now())
                .horaInicio(LocalTime.of(8, 0))
                .horaFin(LocalTime.of(12, 0))
                .descripcion("Primera jornada")
                .estado(EstadoEventoEspecifico.PROGRAMADO)
                .build();
        eventoEspecificoRepository.save(eventoEspecifico);
        eventoEspecificoId = eventoEspecifico.getIdEventoEspecifico();

        // Crear asistencia
        Asistencia asistencia = Asistencia.builder()
                .eventoEspecifico(eventoEspecifico)
                .persona(persona)
                .estadoAsistencia(EstadoAsistencia.PRESENTE)
                .observaciones("Llegó puntual")
                .fechaAsistencia(LocalDateTime.now())
                .build();

        asistenciaRepository.save(asistencia);
        asistenciaId = asistencia.getIdAsistencia();
    }

    @Test @Order(1)
    void testGuardarAsistencia() {
        Optional<Asistencia> asistenciaOpt = asistenciaRepository.findById(asistenciaId);
        assertTrue(asistenciaOpt.isPresent());
        assertEquals(EstadoAsistencia.PRESENTE, asistenciaOpt.get().getEstadoAsistencia());
    }

    @Test @Order(2)
    void testBuscarPorEventoEspecificoId() {
        List<Asistencia> lista = asistenciaRepository.findByEventoEspecificoId(eventoEspecificoId);
        assertFalse(lista.isEmpty());
        assertEquals(eventoEspecificoId, lista.get(0).getEventoEspecifico().getIdEventoEspecifico());
    }

    @Test @Order(3)
    void testBuscarPorPersonaId() {
        List<Asistencia> lista = asistenciaRepository.findByPersonaId(personaId);
        assertFalse(lista.isEmpty());
        assertEquals(personaId, lista.get(0).getPersona().getIdPersona());
    }

    @Test @Order(4)
    void testBuscarPorEstadoAsistencia() {
        List<Asistencia> lista = asistenciaRepository.findByEstadoAsistencia(EstadoAsistencia.PRESENTE);
        assertFalse(lista.isEmpty());
        assertEquals(EstadoAsistencia.PRESENTE, lista.get(0).getEstadoAsistencia());
    }

    @Test @Order(5)
    void testBuscarPorEventoYPersona() {
        Optional<Asistencia> asistenciaOpt =
                asistenciaRepository.findByEventoEspecificoAndPersona(eventoEspecificoId, personaId);
        assertTrue(asistenciaOpt.isPresent());
    }

    @Test @Order(6)
    void testContarPorEventoYEstado() {
        Long count = asistenciaRepository.countByEventoEspecificoAndEstado(eventoEspecificoId, EstadoAsistencia.PRESENTE);
        assertNotNull(count);
        assertTrue(count > 0);
    }

    @Test @Order(7)
    void testActualizarAsistencia() {
        Asistencia asistencia = asistenciaRepository.findById(asistenciaId).orElseThrow();
        asistencia.setEstadoAsistencia(EstadoAsistencia.TARDANZA);
        asistencia.setObservaciones("Llegó tarde");
        Asistencia actualizada = asistenciaRepository.save(asistencia);
        assertEquals(EstadoAsistencia.TARDANZA, actualizada.getEstadoAsistencia());
    }

    @Test @Order(8)
    void testEliminarAsistencia() {
        asistenciaRepository.deleteById(asistenciaId);
        Optional<Asistencia> eliminada = asistenciaRepository.findById(asistenciaId);
        assertFalse(eliminada.isPresent());
    }
}
