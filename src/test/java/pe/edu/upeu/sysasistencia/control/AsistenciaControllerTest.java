package pe.edu.upeu.sysasistencia.control;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.edu.upeu.sysasistencia.dtos.AsistenciaDTO;

// Importamos la versión que el Controlador usa para el tipo genérico de ResponseEntity
import pe.edu.upeu.sysasistencia.utils.CustomResponse;
// NO necesitamos la importación con alias.

import pe.edu.upeu.sysasistencia.mappers.AsistenciaMapper;
import pe.edu.upeu.sysasistencia.modelo.EstadoAsistencia;
import pe.edu.upeu.sysasistencia.servicio.IAsistenciaService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@ExtendWith(MockitoExtension.class)
public class AsistenciaControllerTest {

    @Mock
    private IAsistenciaService service;

    @Mock
    private AsistenciaMapper mapper;

    @InjectMocks
    private AsistenciaController controller;

    private AsistenciaDTO asistenciaDTO;
    private final Long ASISTENCIA_ID = 1L;
    private final Long EVENTO_ID = 10L;
    private final Long PERSONA_ID = 20L;
    private static final Logger logger = Logger.getLogger(AsistenciaControllerTest.class.getName());

    @BeforeEach
    void setUp() {
        asistenciaDTO = new AsistenciaDTO(ASISTENCIA_ID, EVENTO_ID, PERSONA_ID, LocalDateTime.now(), "PRESENTE", "Ok", LocalDateTime.now());
    }

    // --- Tests de Métodos CRUD Base ---
    // ... (Mantener el código de findAll, findById, save, update) ...

    @Test
    public void testFindAll_ReturnsListOfAsistenciaDTO_WithHttpStatusOK() {
        // Given
        List<AsistenciaDTO> dtos = List.of(asistenciaDTO);
        BDDMockito.given(service.findAll()).willReturn(dtos);

        // When
        ResponseEntity<List<AsistenciaDTO>> response = controller.findAll();

        // Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(1, response.getBody().size());
        BDDMockito.then(service).should().findAll();
    }

    @Test
    void testFindById_ReturnsAsistenciaDTO_WithHttpStatusOK() {
        // Given
        BDDMockito.given(service.findById(ASISTENCIA_ID)).willReturn(asistenciaDTO);

        // When
        ResponseEntity<AsistenciaDTO> response = controller.findById(ASISTENCIA_ID);

        // Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(asistenciaDTO, response.getBody());
        BDDMockito.then(service).should().findById(ASISTENCIA_ID);
    }

    @Test
    void testSave_ReturnsAsistenciaDTO_WithHttpStatusOK() {
        // Given
        BDDMockito.given(service.save(asistenciaDTO)).willReturn(asistenciaDTO);

        // When
        ResponseEntity<AsistenciaDTO> response = controller.save(asistenciaDTO);

        // Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(asistenciaDTO, response.getBody());
        BDDMockito.then(service).should().save(asistenciaDTO);
    }

    @Test
    void testUpdate_ReturnsUpdatedAsistenciaDTO_WithHttpStatusOK() {
        // Given
        AsistenciaDTO updatedDTO = new AsistenciaDTO(ASISTENCIA_ID, EVENTO_ID, PERSONA_ID, LocalDateTime.now(), "TARDE", "Llego tarde", LocalDateTime.now());
        BDDMockito.given(service.update(ASISTENCIA_ID, updatedDTO)).willReturn(updatedDTO);

        // When
        ResponseEntity<AsistenciaDTO> response = controller.update(ASISTENCIA_ID, updatedDTO);

        // Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(updatedDTO, response.getBody());
        BDDMockito.then(service).should().update(ASISTENCIA_ID, updatedDTO);
    }

    @Test
    void testDelete_ReturnsCustomResponse_WithHttpStatusOK() {
        // Given
        // Creamos la instancia usando el nombre de paquete COMPLETO, ya que el servicio lo requiere.
        // Esto soluciona el error de "no suitable method found" y el error de importación.
        pe.edu.upeu.sysasistencia.excepciones.CustomResponse mockServiceResponse =
                new pe.edu.upeu.sysasistencia.excepciones.CustomResponse();

        // Mockeamos el servicio. Ahora el tipo coincide con lo que 'willReturn' espera.
        BDDMockito.given(service.delete(ASISTENCIA_ID)).willReturn(mockServiceResponse);

        // When
        // La variable de respuesta usa la clase importada (pe.edu.upeu.sysasistencia.utils.CustomResponse),
        // lo que resuelve el error de tipos incompatibles del controlador.
        ResponseEntity<CustomResponse> response = controller.delete(ASISTENCIA_ID);

        // Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(200, response.getBody().getStatusCode());
        Assertions.assertEquals("Asistencia eliminada exitosamente", response.getBody().getMessage());
        BDDMockito.then(service).should().delete(ASISTENCIA_ID);
    }

    // --- Tests de Endpoints Específicos ---
    // ... (Mantener el resto del código) ...

    @Test
    public void testFindByEventoEspecifico_ReturnsListOfAsistenciaDTO_WithHttpStatusOK() {
        // Given
        List<AsistenciaDTO> dtos = List.of(asistenciaDTO);
        BDDMockito.given(service.findByEventoEspecificoId(EVENTO_ID)).willReturn(dtos);

        // When
        ResponseEntity<List<AsistenciaDTO>> response = controller.findByEventoEspecifico(EVENTO_ID);

        // Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(1, response.getBody().size());
        Assertions.assertEquals(EVENTO_ID, response.getBody().get(0).getEventoEspecificoId());
        BDDMockito.then(service).should().findByEventoEspecificoId(EVENTO_ID);
    }

    @Test
    public void testFindByPersona_ReturnsListOfAsistenciaDTO_WithHttpStatusOK() {
        // Given
        List<AsistenciaDTO> dtos = List.of(asistenciaDTO);
        BDDMockito.given(service.findByPersonaId(PERSONA_ID)).willReturn(dtos);

        // When
        ResponseEntity<List<AsistenciaDTO>> response = controller.findByPersona(PERSONA_ID);

        // Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(1, response.getBody().size());
        Assertions.assertEquals(PERSONA_ID, response.getBody().get(0).getPersonaId());
        BDDMockito.then(service).should().findByPersonaId(PERSONA_ID);
    }

    @Test
    public void testFindByEstadoAsistencia_ReturnsListOfAsistenciaDTO_WithHttpStatusOK() {
        // Given
        EstadoAsistencia estado = EstadoAsistencia.PRESENTE;
        List<AsistenciaDTO> dtos = List.of(asistenciaDTO);
        BDDMockito.given(service.findByEstadoAsistencia(estado)).willReturn(dtos);

        // When
        ResponseEntity<List<AsistenciaDTO>> response = controller.findByEstadoAsistencia(estado);

        // Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(1, response.getBody().size());
        Assertions.assertEquals(estado.name(), response.getBody().get(0).getEstadoAsistencia());
        BDDMockito.then(service).should().findByEstadoAsistencia(estado);
    }

    @Test
    public void testRegistrarAsistencia_ReturnsCustomResponse_WithHttpStatusOK() {
        // Given
        List<AsistenciaDTO> asistencias = List.of(asistenciaDTO);
        BDDMockito.doNothing().when(service).registrarAsistencia(EVENTO_ID, asistencias);

        // When
        ResponseEntity<CustomResponse> response = controller.registrarAsistencia(EVENTO_ID, asistencias);

        // Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Asistencias registradas exitosamente", response.getBody().getMessage());
        BDDMockito.then(service).should().registrarAsistencia(EVENTO_ID, asistencias);
    }

    @Test
    public void testGetReporteAsistencia_ReturnsByteArray_WithHttpStatusOK() {
        // Given
        byte[] reporteBytes = new byte[0];

        // When
        ResponseEntity<byte[]> response = controller.getReporteAsistencia(EVENTO_ID);

        // Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(0, response.getBody().length);
    }
}