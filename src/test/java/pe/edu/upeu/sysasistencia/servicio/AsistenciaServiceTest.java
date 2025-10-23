package pe.edu.upeu.sysasistencia.servicio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sysasistencia.dtos.AsistenciaDTO;
import pe.edu.upeu.sysasistencia.modelo.*;
import pe.edu.upeu.sysasistencia.repositorio.IEventoEspecificoRepository;
import pe.edu.upeu.sysasistencia.repositorio.IEventoGeneralRepository;
import pe.edu.upeu.sysasistencia.repositorio.IPersonaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AsistenciaServiceTest {

    @Autowired
    private IAsistenciaService asistenciaService;

    @Autowired
    private IEventoGeneralRepository eventoGeneralRepository;

    @Autowired
    private IEventoEspecificoRepository eventoEspecificoRepository;

    @Autowired
    private IPersonaRepository personaRepository;

    private Long eventoEspecificoId;
    private Long personaId;

    @BeforeEach
    void setUp() {
        // Crear EventoGeneral
        EventoGeneral eventoGeneral = new EventoGeneral();
        eventoGeneral.setNombreEvento("Test Evento General");
        eventoGeneral.setDescripcion("Descripción del evento general");
        eventoGeneral.setLugar("Lugar de prueba");
        eventoGeneral.setFechaInicio(LocalDate.now());
        eventoGeneral.setFechaFin(LocalDate.now().plusDays(1));
        eventoGeneral.setEstado(EstadoEvento.ACTIVO);
        eventoGeneral.setFechaCreacion(LocalDateTime.now());
        EventoGeneral savedEventoGeneral = eventoGeneralRepository.save(eventoGeneral);

        // Crear EventoEspecifico
        EventoEspecifico eventoEspecifico = new EventoEspecifico();
        eventoEspecifico.setEventoGeneral(savedEventoGeneral);
        eventoEspecifico.setDescripcion("Descripción del evento específico");
        eventoEspecifico.setFechaEvento(LocalDate.now());
        eventoEspecifico.setHoraInicio(LocalTime.now());
        eventoEspecifico.setHoraFin(LocalTime.now().plusHours(2));
        eventoEspecifico.setEstado(EstadoEventoEspecifico.PROGRAMADO);
        eventoEspecifico.setFechaCreacion(LocalDateTime.now());
        EventoEspecifico savedEventoEspecifico = eventoEspecificoRepository.save(eventoEspecifico);
        eventoEspecificoId = savedEventoEspecifico.getIdEventoEspecifico();

        // Crear Persona
        Persona persona = new Persona();
        persona.setNombreCompleto("Test Persona");
        persona.setDocumento("12345678");
        persona.setCorreo("test@test.com");
        persona.setCelular("123456789");
        persona.setTipoPersona(TipoPersona.ESTUDIANTE);
        Persona savedPersona = personaRepository.save(persona);
        personaId = savedPersona.getIdPersona();
    }

    @Test
    void testSaveAndFindAsistencia() {
        // Crear DTO de prueba usando los IDs creados en setUp
        AsistenciaDTO dto = new AsistenciaDTO();
        dto.setEventoEspecificoId(eventoEspecificoId);
        dto.setPersonaId(personaId);
        dto.setFechaAsistencia(LocalDateTime.now());
        dto.setEstadoAsistencia("PRESENTE");
        dto.setObservaciones("Test servicio");

        // Guardar
        AsistenciaDTO saved = asistenciaService.save(dto);

        // Verificar
        assertNotNull(saved.getIdAsistencia());
        assertEquals("PRESENTE", saved.getEstadoAsistencia());
        assertEquals(eventoEspecificoId, saved.getEventoEspecificoId());
        assertEquals(personaId, saved.getPersonaId());
    }

    @Test
    void testFindAll() {
        assertNotNull(asistenciaService.findAll());
    }

    @Test
    void testFindByEventoEspecifico() {
        assertNotNull(asistenciaService.findByEventoEspecificoId(eventoEspecificoId));
    }
}