package pe.edu.upeu.sysasistencia.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import pe.edu.upeu.sysasistencia.dtos.ImportResultDTO;
import pe.edu.upeu.sysasistencia.dtos.MatriculaDTO;
import pe.edu.upeu.sysasistencia.excepciones.CustomResponse;
import pe.edu.upeu.sysasistencia.mappers.MatriculaMapper;
import pe.edu.upeu.sysasistencia.modelo.Matricula;
import pe.edu.upeu.sysasistencia.modelo.TipoPersona;
import pe.edu.upeu.sysasistencia.servicio.IMatriculaService;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatriculaControllerTest {

    @Mock
    private IMatriculaService matriculaService;

    @Mock
    private MatriculaMapper matriculaMapper;

    @InjectMocks
    private MatriculaController matriculaController;

    private Matricula matricula;
    private MatriculaDTO matriculaDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        matricula = new Matricula();
        matricula.setIdMatricula(1L);
        matricula.setEstado("ACTIVO");

        matriculaDTO = new MatriculaDTO();
        matriculaDTO.setIdMatricula(1L);
        matriculaDTO.setEstado("ACTIVO");
    }

    @Test
    void testFindAll() {
        when(matriculaService.findAll()).thenReturn(List.of(matricula));
        when(matriculaMapper.toDTOs(anyList())).thenReturn(List.of(matriculaDTO));

        ResponseEntity<List<MatriculaDTO>> response = matriculaController.findAll();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getIdMatricula());
        assertEquals("ACTIVO", response.getBody().get(0).getEstado());

        verify(matriculaService, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(matriculaService.findById(1L)).thenReturn(matricula);
        when(matriculaMapper.toDTO(matricula)).thenReturn(matriculaDTO);

        ResponseEntity<MatriculaDTO> response = matriculaController.findById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getIdMatricula());
        assertEquals("ACTIVO", response.getBody().getEstado());

        verify(matriculaService, times(1)).findById(1L);
    }

    @Test
    void testFindByCodigoEstudiante() {
        when(matriculaService.findByCodigoEstudiante("A001")).thenReturn(List.of(matricula));
        when(matriculaMapper.toDTOs(anyList())).thenReturn(List.of(matriculaDTO));

        ResponseEntity<List<MatriculaDTO>> response = matriculaController.findByCodigoEstudiante("A001");

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getIdMatricula());

        verify(matriculaService, times(1)).findByCodigoEstudiante("A001");
    }

    @Test
    void testFindByFiltros() {
        when(matriculaService.findByFiltros(null, null, null, TipoPersona.ESTUDIANTE))
                .thenReturn(List.of(matricula));
        when(matriculaMapper.toDTOs(anyList())).thenReturn(List.of(matriculaDTO));

        ResponseEntity<List<MatriculaDTO>> response = matriculaController.findByFiltros(null, null, null, TipoPersona.ESTUDIANTE);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getIdMatricula());

        verify(matriculaService, times(1)).findByFiltros(null, null, null, TipoPersona.ESTUDIANTE);
    }

    @Test
    void testSave() {
        when(matriculaMapper.toEntity(matriculaDTO)).thenReturn(matricula);
        when(matriculaService.save(matricula)).thenReturn(matricula);
        when(matriculaMapper.toDTO(matricula)).thenReturn(matriculaDTO);

        ResponseEntity<MatriculaDTO> response = matriculaController.save(matriculaDTO);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getIdMatricula());
        assertEquals("ACTIVO", response.getBody().getEstado());

        verify(matriculaService, times(1)).save(any(Matricula.class));
    }

    @Test
    void testUpdate() {
        when(matriculaMapper.toEntity(matriculaDTO)).thenReturn(matricula);
        when(matriculaService.update(1L, matricula)).thenReturn(matricula);
        when(matriculaMapper.toDTO(matricula)).thenReturn(matriculaDTO);

        ResponseEntity<MatriculaDTO> response = matriculaController.update(1L, matriculaDTO);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getIdMatricula());
        assertEquals("ACTIVO", response.getBody().getEstado());

        verify(matriculaService, times(1)).update(eq(1L), any(Matricula.class));
    }

    @Test
    void testDelete() {
        CustomResponse mockResponse = new CustomResponse(
                200,
                LocalDateTime.now(),
                "Eliminado correctamente",
                "/matriculas/1"
        );

        when(matriculaService.delete(1L)).thenReturn(mockResponse);

        ResponseEntity<CustomResponse> response = matriculaController.delete(1L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getStatusCode());
        assertEquals("Eliminado correctamente", response.getBody().getMessage());

        verify(matriculaService, times(1)).delete(1L);
    }

    @Test
    void testImportarExcel_Success() throws Exception {
        // Crear resultado esperado con listas inicializadas
        ImportResultDTO resultDTO = new ImportResultDTO();
        resultDTO.setTotalRegistros(5);
        resultDTO.setExitosos(5);
        resultDTO.setFallidos(0);
        resultDTO.setErrores(new ArrayList<>());
        resultDTO.setWarnings(new ArrayList<>());
        resultDTO.getWarnings().add("Importación correcta");

        // Crear archivo de prueba
        MockMultipartFile file = new MockMultipartFile(
                "file", 
                "test.xlsx", 
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", 
                "test content".getBytes()
        );

        // Mock del servicio - usar doAnswer para control total
        doAnswer(invocation -> resultDTO).when(matriculaService).importarDesdeExcel(any(), any());

        // Ejecutar el test
        ResponseEntity<ImportResultDTO> response = matriculaController.importarExcel(
                file, null, null, null, TipoPersona.ESTUDIANTE
        );

        // Verificar resultados
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(5, response.getBody().getTotalRegistros());
        assertEquals(5, response.getBody().getExitosos());
        assertEquals(0, response.getBody().getFallidos());
        
        // Verificar que se llamó al servicio
        verify(matriculaService, times(1)).importarDesdeExcel(any(), any());
    }

    @Test
    void testImportarExcel_FileEmpty() throws Exception {
        MockMultipartFile emptyFile = new MockMultipartFile(
                "file", 
                "empty.xlsx", 
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", 
                new byte[0]
        );

        ResponseEntity<ImportResultDTO> response = matriculaController.importarExcel(
                emptyFile, null, null, null, TipoPersona.ESTUDIANTE
        );

        assertEquals(500, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getErrores().get(0).contains("Error general"));
    }
}