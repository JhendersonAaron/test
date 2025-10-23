package pe.edu.upeu.sysasistencia.integracion;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sysasistencia.dtos.AsistenciaDTO;
import pe.edu.upeu.sysasistencia.modelo.*;
import pe.edu.upeu.sysasistencia.repositorio.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Tests de Integración de Asistencia")
class AsistenciaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IAsistenciaRepository asistenciaRepository;

    @Autowired
    private IEventoGeneralRepository eventoGeneralRepository;

    @Autowired
    private IEventoEspecificoRepository eventoEspecificoRepository;

    @Autowired
    private IPersonaRepository personaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private AsistenciaDTO asistenciaDto;
    private EventoEspecifico eventoEspecifico;
    private Persona persona;

    @BeforeEach
    void setUp() {
        // Crear datos base necesarios
        createTestData();

        // Crear DTO de prueba
        asistenciaDto = new AsistenciaDTO();
        asistenciaDto.setEventoEspecificoId(eventoEspecifico.getIdEventoEspecifico());
        asistenciaDto.setPersonaId(persona.getIdPersona());
        asistenciaDto.setFechaAsistencia(LocalDateTime.now());
        asistenciaDto.setEstadoAsistencia(EstadoAsistencia.PRESENTE.name());
        asistenciaDto.setObservaciones("Test de integración");
        asistenciaDto.setFechaRegistro(LocalDateTime.now());
    }

    private void createTestData() {
        // Crear EventoGeneral
        EventoGeneral eventoGeneral = new EventoGeneral();
        eventoGeneral.setNombreEvento("Evento Test");
        eventoGeneral.setDescripcion("Evento de prueba para tests");
        eventoGeneral.setFechaInicio(LocalDate.now());
        eventoGeneral.setFechaFin(LocalDate.now().plusDays(1));
        eventoGeneral.setLugar("Lugar Test");
        eventoGeneral.setEstado(EstadoEvento.ACTIVO);
        eventoGeneral = eventoGeneralRepository.save(eventoGeneral);

        // Crear EventoEspecifico
        eventoEspecifico = new EventoEspecifico();
        eventoEspecifico.setEventoGeneral(eventoGeneral);
        eventoEspecifico.setFechaEvento(LocalDate.now());
        eventoEspecifico.setHoraInicio(LocalTime.of(9, 0));
        eventoEspecifico.setHoraFin(LocalTime.of(17, 0));
        eventoEspecifico.setDescripcion("Evento específico de prueba");
        eventoEspecifico.setEstado(EstadoEventoEspecifico.PROGRAMADO);
        eventoEspecifico = eventoEspecificoRepository.save(eventoEspecifico);

        // Crear Persona
        persona = new Persona();
        persona.setCodigoEstudiante("TEST001");
        persona.setNombreCompleto("Estudiante Test");
        persona.setDocumento("12345678");
        persona.setCorreo("test@example.com");
        persona.setCelular("987654321");
        persona.setPais("Peru");
        persona.setTipoPersona(TipoPersona.ESTUDIANTE);
        persona = personaRepository.save(persona);
    }

    @Test
    @Order(1)
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Integración: Debe crear una asistencia completa")
    void testCrearAsistencia_Completo() throws Exception {
        String asistenciaJson = objectMapper.writeValueAsString(asistenciaDto);

        mockMvc.perform(post("/asistencia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asistenciaJson)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAsistencia").exists())
                .andExpect(jsonPath("$.eventoEspecificoId", is(eventoEspecifico.getIdEventoEspecifico().intValue())))
                .andExpect(jsonPath("$.personaId", is(persona.getIdPersona().intValue())))
                .andExpect(jsonPath("$.estadoAsistencia", is("PRESENTE")))
                .andExpect(jsonPath("$.observaciones", is("Test de integración")))
                .andExpect(jsonPath("$.fechaRegistro").exists());
    }

    @Test
    @Order(2)
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Integración: Debe obtener todas las asistencias")
    void testObtenerTodasLasAsistencias() throws Exception {
        mockMvc.perform(get("/asistencia")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @Order(3)
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Integración: Debe buscar asistencia por evento específico")
    void testBuscarAsistenciaPorEvento() throws Exception {
        // Crear asistencia primero
        Asistencia asistencia = new Asistencia();
        asistencia.setEventoEspecifico(eventoEspecifico);
        asistencia.setPersona(persona);
        asistencia.setFechaAsistencia(LocalDateTime.now());
        asistencia.setEstadoAsistencia(EstadoAsistencia.PRESENTE);
        asistencia.setObservaciones("Para prueba de búsqueda");
        asistenciaRepository.save(asistencia);

        mockMvc.perform(get("/asistencia/evento-especifico/{eventoId}", eventoEspecifico.getIdEventoEspecifico())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].eventoEspecificoId", is(eventoEspecifico.getIdEventoEspecifico().intValue())));
    }

    @Test
    @Order(4)
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Integración: Debe buscar asistencia por persona")
    void testBuscarAsistenciaPorPersona() throws Exception {
        // Crear asistencia primero
        Asistencia asistencia = new Asistencia();
        asistencia.setEventoEspecifico(eventoEspecifico);
        asistencia.setPersona(persona);
        asistencia.setFechaAsistencia(LocalDateTime.now());
        asistencia.setEstadoAsistencia(EstadoAsistencia.PRESENTE);
        asistencia.setObservaciones("Para prueba de búsqueda por persona");
        asistenciaRepository.save(asistencia);

        mockMvc.perform(get("/asistencia/persona/{personaId}", persona.getIdPersona())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].personaId", is(persona.getIdPersona().intValue())));
    }

    @Test
    @Order(5)
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Integración: Debe buscar asistencia por estado")
    void testBuscarAsistenciaPorEstado() throws Exception {
        // Crear asistencia primero
        Asistencia asistencia = new Asistencia();
        asistencia.setEventoEspecifico(eventoEspecifico);
        asistencia.setPersona(persona);
        asistencia.setFechaAsistencia(LocalDateTime.now());
        asistencia.setEstadoAsistencia(EstadoAsistencia.AUSENTE);
        asistencia.setObservaciones("Para prueba de búsqueda por estado");
        asistenciaRepository.save(asistencia);

        mockMvc.perform(get("/asistencia/estado/{estado}", EstadoAsistencia.AUSENTE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].estadoAsistencia", is("AUSENTE")));
    }

    @Test
    @Order(6)
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Integración: Debe manejar búsqueda sin resultados")
    void testBusquedaSinResultados() throws Exception {
        mockMvc.perform(get("/asistencia/evento-especifico/{eventoId}", 99999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Order(7)
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Integración: Ciclo completo CRUD de Asistencia")
    void testCicloCompletoCRUD() throws Exception {
        // 1️⃣ CREATE
        String asistenciaJson = objectMapper.writeValueAsString(asistenciaDto);

        String responseCreate = mockMvc.perform(post("/asistencia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asistenciaJson)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AsistenciaDTO created = objectMapper.readValue(responseCreate, AsistenciaDTO.class);
        Long id = created.getIdAsistencia();

        // 2️⃣ READ
        mockMvc.perform(get("/asistencia/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAsistencia", is(id.intValue())))
                .andExpect(jsonPath("$.estadoAsistencia", is("PRESENTE")));

        // 3️⃣ UPDATE
        AsistenciaDTO updateDto = new AsistenciaDTO();
        updateDto.setEventoEspecificoId(eventoEspecifico.getIdEventoEspecifico());
        updateDto.setPersonaId(persona.getIdPersona());
        updateDto.setFechaAsistencia(LocalDateTime.now());
        updateDto.setEstadoAsistencia(EstadoAsistencia.TARDANZA.name());
        updateDto.setObservaciones("Actualizado - llegó tarde");
        updateDto.setFechaRegistro(LocalDateTime.now());

        String updateJson = objectMapper.writeValueAsString(updateDto);

        mockMvc.perform(put("/asistencia/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estadoAsistencia", is("TARDANZA")))
                .andExpect(jsonPath("$.observaciones", is("Actualizado - llegó tarde")));

        // 4️⃣ DELETE
        mockMvc.perform(delete("/asistencia/{id}", id)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is(200)))
                .andExpect(jsonPath("$.message", is("Asistencia eliminada exitosamente")));
    }

    @Test
    @Order(8)
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Integración: Debe registrar múltiples asistencias")
    void testRegistrarMultipleAsistencias() throws Exception {
        // Crear lista de asistencias
        List<AsistenciaDTO> asistencias = Arrays.asList(
                createAsistenciaDTO(eventoEspecifico.getIdEventoEspecifico(), persona.getIdPersona(), EstadoAsistencia.PRESENTE),
                createAsistenciaDTO(eventoEspecifico.getIdEventoEspecifico(), persona.getIdPersona(), EstadoAsistencia.AUSENTE),
                createAsistenciaDTO(eventoEspecifico.getIdEventoEspecifico(), persona.getIdPersona(), EstadoAsistencia.TARDANZA)
        );

        String jsonAsistencias = objectMapper.writeValueAsString(asistencias);

        mockMvc.perform(post("/asistencia/registrar/{eventoId}", eventoEspecifico.getIdEventoEspecifico())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAsistencias)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is(200)))
                .andExpect(jsonPath("$.message", is("Asistencias registradas exitosamente")));
    }

    @Test
    @Order(9)
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Integración: Debe generar reporte de asistencia")
    void testGenerarReporteAsistencia() throws Exception {
        mockMvc.perform(get("/asistencia/reporte/{eventoId}", eventoEspecifico.getIdEventoEspecifico())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM));
    }

    @Test
    @Order(10)
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Integración: Debe manejar datos inválidos")
    void testManejarDatosInvalidos() throws Exception {
        AsistenciaDTO invalidAsistencia = new AsistenciaDTO();
        invalidAsistencia.setEventoEspecificoId(null);
        invalidAsistencia.setPersonaId(null);
        invalidAsistencia.setEstadoAsistencia("INVALID_STATE");

        String jsonInvalid = objectMapper.writeValueAsString(invalidAsistencia);

        mockMvc.perform(post("/asistencia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalid)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.statusCode", is(500)))
                .andExpect(jsonPath("$.message", containsString("No enum constant")));
    }

    @Test
    @Order(11)
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Integración: Debe manejar ID inexistente")
    void testManejarIdInexistente() throws Exception {
        mockMvc.perform(get("/asistencia/{id}", 99999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // Método auxiliar
    private AsistenciaDTO createAsistenciaDTO(Long eventoId, Long personaId, EstadoAsistencia estado) {
        AsistenciaDTO dto = new AsistenciaDTO();
        dto.setEventoEspecificoId(eventoId);
        dto.setPersonaId(personaId);
        dto.setFechaAsistencia(LocalDateTime.now());
        dto.setEstadoAsistencia(estado.name());
        dto.setObservaciones("Test de integración - " + estado.name());
        dto.setFechaRegistro(LocalDateTime.now());
        return dto;
    }
}